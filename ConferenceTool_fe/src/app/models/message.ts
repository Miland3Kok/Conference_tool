import {Speaker} from "./speaker";

export type MessageObject = {
    message_id: string,
    subject: string,
    message: string,
    important: boolean,
    date: Date,
    read: boolean,
    forOrganisation: boolean,
    conference_id: number,
    speaker_id: string
    speaker: Speaker
}
