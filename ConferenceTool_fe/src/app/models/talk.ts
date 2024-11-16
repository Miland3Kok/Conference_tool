import {Speaker} from "./speaker";
import {TalkSpeaker} from "./talkSpeaker";

export type Talk = {
    talk_id: number;
    title: string;
    description: string;
    start_date: string;
    end_date: string;
    room: string;
    speakers: TalkSpeaker[];
}