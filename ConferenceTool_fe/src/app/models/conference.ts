import {Talk} from "./talk";

export type Conference = {
    conference_id: number;
    name: string;
    description: string;
    start_date: string;
    end_date: string;
    location_id: string;
    talks: Talk[];
    active: boolean;
    floorPlanImage: string;
}