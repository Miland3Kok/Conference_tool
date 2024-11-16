import {User} from "./user";

export type Speaker = {
    speaker_id: number;
    user: User;
    bio: string;
    phone: string;
}