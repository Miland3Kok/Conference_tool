package be.kdg.conference.controller;

import be.kdg.conference.controller.dto.questionDTOs.UserAnswerDTO;
import be.kdg.conference.service.implementation.UserAnswerServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/answer")
public class AnswerController {
    private final UserAnswerServiceImpl userAnswerService;

    public AnswerController(UserAnswerServiceImpl userAnswerService) {
        this.userAnswerService = userAnswerService;
    }

    @GetMapping("/getAllUserAnswersOfQuestion/{questionId}")
    public ResponseEntity<List<UserAnswerDTO>> getAllUserAnswersOfQuestion(@PathVariable String questionId) {
        try {
           List<UserAnswerDTO> userAnswers = userAnswerService.getUserAnswersOfQuestion(UUID.fromString(questionId));
            return ResponseEntity.ok().body(userAnswers);
        } catch (Exception e) {
            log.warn(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
