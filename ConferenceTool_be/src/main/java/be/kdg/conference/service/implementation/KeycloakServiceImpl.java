package be.kdg.conference.service.implementation;

import be.kdg.conference.controller.dto.KeycloakUserDTO;
import be.kdg.conference.controller.dto.MakeUserDTO;
import be.kdg.conference.service.KeycloakService;
import be.kdg.conference.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jetbrains.annotations.NotNull;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.*;
import java.util.stream.StreamSupport;

@Service
public class KeycloakServiceImpl implements KeycloakService {
    private final Keycloak keycloak;
    private final EmailService emailService;
    private final UserService userService;

    @Autowired
    public KeycloakServiceImpl(Keycloak keycloak, EmailService emailService, UserService userService) {
        this.keycloak = keycloak;
        this.emailService = emailService;
        this.userService = userService;
    }

    @Override
    @Transactional
    public void createUser(MakeUserDTO makeUserDTO) throws MessagingException {
        UsersResource usersResource = keycloak.realm("conferenceTool").users();

        UserRepresentation user = getUserRepresentation(makeUserDTO);

        CredentialRepresentation passwordCred = new CredentialRepresentation();
        passwordCred.setType(CredentialRepresentation.PASSWORD);
        String password = generatePassword(8);
        passwordCred.setValue(password);
        user.setCredentials(List.of(passwordCred));

        Response response = usersResource.create(user);

        if (response.getStatusInfo().getFamily() != Response.Status.Family.SUCCESSFUL) {
            String errorMessage = response.readEntity(String.class);
            response.close();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage);
        }

        String userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");

        setUserInRole(userId, makeUserDTO.getRole());

        emailService.sendPassword(user.getFirstName(), user.getEmail(), password);

        response.close();

        userService.createUser(userId, makeUserDTO);
    }

    @Override
    @Transactional
    public List<KeycloakUserDTO> getUsersInRole(String role) {
        List<KeycloakUserDTO> usersInRole = new ArrayList<>();

        RealmResource realmResource = keycloak.realm("conferenceTool");
        RoleRepresentation roleRepresentation = realmResource.roles().get(role).toRepresentation();
        List<UserRepresentation> usersWithRole = realmResource.roles().get(roleRepresentation.getName()).getUserMembers();

        for (UserRepresentation user : usersWithRole) {
            KeycloakUserDTO keycloakUserDTO = new KeycloakUserDTO(user);
            usersInRole.add(keycloakUserDTO);
        }

        return usersInRole;
    }

    @Override
    public Map<Integer, String> getRoles() {
        Map<Integer, String> rolesMap = new HashMap<>();

        RealmResource realmResource = keycloak.realm("conferenceTool");
        RolesResource rolesResource = realmResource.roles();

        List<RoleRepresentation> roles = rolesResource.list();
        int index = 1;

        for (RoleRepresentation role : roles) {
            if (!Arrays.asList("default-roles-conferencetool", "offline_access", "uma_authorization")
                    .contains(role.getName())) {
                rolesMap.put(index++, role.getName());
            }
        }

        return rolesMap;
    }

    @Override
    public void addRoleToUser(String userId, String role) {
        setUserInRole(userId, role);
    }

    @Override
    @Transactional
    public void uploadFile(MultipartFile file) throws IOException {
        try (XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream())) {
            XSSFSheet sheet = workbook.getSheetAt(0);

            List<MakeUserDTO> userDataList = StreamSupport
                    .stream(((Iterable<Row>) sheet).spliterator(), false)
                    .skip(1)
                    .map(row -> {
                        MakeUserDTO userData = new MakeUserDTO();
                        userData.setFirstname(row.getCell(0).getStringCellValue());
                        userData.setLastname(row.getCell(1).getStringCellValue());
                        userData.setFunction(row.getCell(2).getStringCellValue());
                        userData.setUsername(row.getCell(3).getStringCellValue());
                        userData.setEmail(row.getCell(4).getStringCellValue());
                        userData.setRole(row.getCell(5).getStringCellValue());
                        return userData;
                    })
                    .toList();

            makeUsers(userDataList);
        }
    }

    @Override
    @Transactional
    public void deleteUser(String userId) {
        RealmResource realmResource = keycloak.realm("conferenceTool");
        UsersResource usersResource = realmResource.users();

        UserResource userResource = usersResource.get(userId);
        userResource.remove();
    }

    @NotNull
    private UserRepresentation getUserRepresentation(MakeUserDTO makeUserDTO) {
        UserRepresentation user = new UserRepresentation();
        user.setUsername(makeUserDTO.getUsername());
        user.setEmail(makeUserDTO.getEmail());
        user.setFirstName(makeUserDTO.getFirstname());
        user.setLastName(makeUserDTO.getLastname());
        user.setEmailVerified(true);
        user.setEnabled(true);

        Map<String, List<String>> attributes = new HashMap<>();
        attributes.put("function", Collections.singletonList(makeUserDTO.getFunction()));

        user.setAttributes(attributes);

        return user;
    }

    private void setUserInRole(String userId, String role) {
        RealmResource realmResource = keycloak.realm("conferenceTool");

        UserResource userResource = realmResource.users().get(userId);

        RoleRepresentation rr = realmResource.roles()
                .get(role).toRepresentation();

        userResource.roles().realmLevel()
                .add(Collections.singletonList(rr));
    }

    @Transactional
    protected void makeUsers(List<MakeUserDTO> userDataList) {
        for (MakeUserDTO makeUserDTO : userDataList) {
            try {
                createUser(makeUserDTO);
            } catch (MessagingException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            }
        }
    }

    public static String generatePassword (int length) {
        final char[] lowercase = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        final char[] uppercase = "ABCDEFGJKLMNPRSTUVWXYZ".toCharArray();
        final char[] numbers = "0123456789".toCharArray();
        final char[] symbols = "^$?!@#%&".toCharArray();
        final char[] allAllowed = "abcdefghijklmnopqrstuvwxyzABCDEFGJKLMNPRSTUVWXYZ0123456789^$?!@#%&".toCharArray();

        Random random = new SecureRandom();

        StringBuilder password = new StringBuilder();

        for (int i = 0; i < length-4; i++) {
            password.append(allAllowed[random.nextInt(allAllowed.length)]);
        }

        password.insert(random.nextInt(password.length()), lowercase[random.nextInt(lowercase.length)]);
        password.insert(random.nextInt(password.length()), uppercase[random.nextInt(uppercase.length)]);
        password.insert(random.nextInt(password.length()), numbers[random.nextInt(numbers.length)]);
        password.insert(random.nextInt(password.length()), symbols[random.nextInt(symbols.length)]);

        return password.toString();
    }
}
