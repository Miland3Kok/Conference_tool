import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {ClosedQuestion, MultipleChoiceQuestion, OpenQuestion, Option, Question} from "../models/question";
import {BehaviorSubject, Subject, tap} from 'rxjs';

@Injectable({
    providedIn: 'root'
})

export class QuestionService {


    private API_URL = 'http://localhost:8000/api/question'
    private ANSWER_URL = 'http://localhost:8000/api/answer'

    questionCreated = new Subject<void>();

    constructor(private http: HttpClient) { }

    getQuestionById(id: string | null | undefined) {
        return this.http.get<Question>(`${this.API_URL}/${id}`);
    }

    getQuestionsByTalk(talkId: string | undefined) {
        return this.http.get<Question[]>(`${this.API_URL}/getQuestionsByTalk/${talkId}`);
    }




    createOpenQuestion(openQuestion: OpenQuestion) {
        return this.http.post<Question>(`${this.API_URL}/open`, openQuestion);
    }

    createClosedQuestion(closedQuestion: ClosedQuestion) {
        return this.http.post<Question>(`${this.API_URL}/closed`, closedQuestion);
    }

    createMultipleChoiceQuestion(multipleChoiceQuestion: MultipleChoiceQuestion) {
        return this.http.post<Question>(`${this.API_URL}/multiple-choice`, multipleChoiceQuestion);
    }


    deleteQuestion(questionId: string) {
        return this.http.delete<Question>(`${this.API_URL}/delete/${questionId}`);
    }


    updateOpenQuestion(openQuestion: Question) {
        return this.http.post<Question>(`${this.API_URL}/open`, openQuestion).pipe(tap(() => this.questionCreated.next()));

    }

    doesTalkHaveQuestions(talkId: string) {
        return this.http.get<boolean>(`${this.API_URL}/doesTalkHaveQuestions/${talkId}`);
    }

    updateClosedQuestion(closedQuestion: Question) {
        return this.http.post<Question>(`${this.API_URL}/closed`, closedQuestion).pipe(tap(() => this.questionCreated.next()));
    }

    updateMultipleChoiceQuestion(multipleChoiceQuestion: Question) {
        return this.http.post<Question>(`${this.API_URL}/multiple-choice`, multipleChoiceQuestion).pipe(tap(() => this.questionCreated.next()));
    }

    getOptionsByQuestionId(questionId: string) {
        return this.http.get<Option[]>(`${this.API_URL}/getOptionsByQuestion/${questionId}`);
    }

    createAnswer(answer: { userAnswerId: string, userId: string, questionId: string, answer: string }) {
        return this.http.post(`${this.API_URL}/answer`, answer);
    }

    getAnswersByQuestionId(questionId: string) {
        return this.http.get(`${this.ANSWER_URL}/getAllUserAnswersOfQuestion/${questionId}`);
    }
}