
export type Question = {
    id: string;
    content: string;
    talkId: string;
    correctAnswer: string;
    falseAnswer: string;
    options: Option[];
    type: string;
}

export type OpenQuestion = {
    talkId: string;
    content: string;
}

export type ClosedQuestion = {
    talkId: string;
    content: string;
    correctAnswer: string;
    falseAnswer: string;
}

export type MultipleChoiceQuestion = {
    talkId: string;
    content: string;
    options: Option[];
}

export type Option = {
    optionText: string;
    isCorrect: boolean;
}