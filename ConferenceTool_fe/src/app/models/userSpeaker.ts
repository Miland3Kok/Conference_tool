import {Role} from "./role";

export type UserSpeaker = {
    user_id: number;
    firstName: string;
    lastName: string;
    office_function: string;
    roles: Role[];
    speaker_id: number;
    bio: string;
    phone: string;
    profilePicture: string;
}