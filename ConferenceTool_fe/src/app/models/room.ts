import {Conference} from "./conference";
import {Talk} from "./talk";

export type Room = {
    room_id: string;
    name: string;
    conference_id: string;
    talks: Talk[];
}