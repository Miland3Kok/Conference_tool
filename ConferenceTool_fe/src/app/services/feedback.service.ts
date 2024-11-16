import {Injectable} from "@angular/core";
import {UserToken} from "../security-config/userToken.service";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../../environments/environment";

@Injectable({
    providedIn: 'root'
})

export class FeedbackService {
    private API_URL: string = environment.backendConfig.url + 'api/feedback'
    private userToken: string | undefined;

    constructor(userToken: UserToken, private http: HttpClient) {
        userToken.getUserToken().then(token => {
            this.userToken = token;
        });
    }

    createFeedback(feedback: any) {
        return fetch(`${this.API_URL}/create`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + this.userToken
            },
            body: JSON.stringify(feedback)
        });
    }

    userHasGivenFeedback(talkId: number, userId: string) {
        const headers = new HttpHeaders({
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + this.userToken
        });

        return this.http.get<boolean>(`${this.API_URL}/userHasGivenFeedback?userId=${userId}&talkId=${talkId}`, {headers: headers});
    }

    getRatingOfTalk(talkId: number) {
        return this.http.get<number>(`${this.API_URL}/getRatingOfTalk?talkId=${talkId}`);
    }
}
