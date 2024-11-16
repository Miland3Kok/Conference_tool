package be.kdg.conference.service;

import be.kdg.conference.controller.dto.questionDTOs.*;
import be.kdg.conference.model.event_interaction.questions.Question;

import java.util.List;
import java.util.UUID;

public interface QuestionService {


    ClosedQuestionDTO createClosedQuestion(ClosedQuestionDTO questionDTO);

    ClosedQuestionDTO updateClosedQuestion(ClosedQuestionDTO questionDTO);

    Question getQuestionById(UUID id);

    OpenQuestionDTO createOpenQuestion(OpenQuestionDTO questionDTO);

    OpenQuestionDTO updateOpenQuestion(OpenQuestionDTO questionDTO);

    MultipleChoiceQuestionDTO createMultipleChoiceQuestion(MultipleChoiceQuestionDTO questionDTO);

    MultipleChoiceQuestionDTO updateMultipleChoiceQuestion(MultipleChoiceQuestionDTO questionDTO);

    UserAnswerDTO answerQuestion(UserAnswerDTO userAnswerDTO);

    List<QuestionDTO> getQuestionsByTalk(UUID id);

    void deleteQuestion(UUID id);

    boolean doesTalkHaveQuestions(UUID id);

    List<ClosedQuestionDTO> getAllClosedQuestions();

    List<OptionDTO> getOptionsByQuestion(UUID id);
}
