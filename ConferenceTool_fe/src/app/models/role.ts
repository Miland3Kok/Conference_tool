
export type Role = {
    role_id: number;
    roleName: Roles;
}

export enum Roles {
    ADMIN,
    ATTENDEE,
    SPEAKER,
    ORGANIZER
}