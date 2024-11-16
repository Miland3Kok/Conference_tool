package be.kdg.conference.controller;

import be.kdg.conference.controller.dto.questionDTOs.*;
import be.kdg.conference.model.event_interaction.questions.ClosedQuestion;
import be.kdg.conference.model.event_interaction.questions.MultipleChoiceQuestion;
import be.kdg.conference.model.event_interaction.questions.OpenQuestion;
import be.kdg.conference.model.event_interaction.questions.Question;
import be.kdg.conference.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/question")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getQuestionById(@PathVariable UUID id) {
        log.info("Retrieving question with ID: {}", id);
        Question question = questionService.getQuestionById(id);
        if (question instanceof ClosedQuestion) {
            return ResponseEntity.ok(new ClosedQuestionDTO(question));
        } else if (question instanceof MultipleChoiceQuestion) {
            return ResponseEntity.ok(new MultipleChoiceQuestionDTO(question));
        } else if (question instanceof OpenQuestion) {
            return ResponseEntity.ok(new OpenQuestionDTO(question));
        }
        return ResponseEntity.notFound().build();
    }


    @PostMapping("/closed")
    public ClosedQuestionDTO createClosedQuestion(@RequestBody ClosedQuestionDTO questionDTO) {
        return questionService.createClosedQuestion(questionDTO);
    }

    @PutMapping("/closed")
    public ResponseEntity<?> updateClosedQuestion(@RequestBody ClosedQuestionDTO questionDTO) {
        ClosedQuestionDTO closedQuestionDTO = questionService.updateClosedQuestion(questionDTO);
        return ResponseEntity.ok(closedQuestionDTO);
    }

    @PostMapping("/open")
    public OpenQuestionDTO createOpenQuestion(@RequestBody OpenQuestionDTO questionDTO) {
        return questionService.createOpenQuestion(questionDTO);
    }

    @PutMapping("/open")
    public ResponseEntity<?> updateOpenQuestion(@RequestBody OpenQuestionDTO questionDTO) {
        OpenQuestionDTO openQuestionDTO = questionService.updateOpenQuestion(questionDTO);
        return ResponseEntity.ok(openQuestionDTO);
    }

    @PostMapping("/multiple-choice")
    public MultipleChoiceQuestionDTO createMultipleChoiceQuestion(@RequestBody MultipleChoiceQuestionDTO questionDTO) {
        return questionService.createMultipleChoiceQuestion(questionDTO);
    }

    @PutMapping("/multiple-choice")
    public ResponseEntity<?> updateMultipleChoiceQuestion(@RequestBody MultipleChoiceQuestionDTO questionDTO) {
        MultipleChoiceQuestionDTO multipleChoiceQuestionDTO = questionService.updateMultipleChoiceQuestion(questionDTO);
        return ResponseEntity.ok(multipleChoiceQuestionDTO);
    }

    @GetMapping("doesTalkHaveQuestions/{id}")
    public boolean doesTalkHaveQuestions(@PathVariable UUID id) {
        return questionService.doesTalkHaveQuestions(id);
    }

    @PostMapping("/answer")
    public UserAnswerDTO answerQuestion(@RequestBody UserAnswerDTO userAnswerDTO) {
        return questionService.answerQuestion(userAnswerDTO);
    }

    @GetMapping("/getQuestionsByTalk/{id}")
    public List<QuestionDTO> getQuestionsByTalk(@PathVariable UUID id) {
        return questionService.getQuestionsByTalk(id);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteQuestion(@PathVariable UUID id) {
        questionService.deleteQuestion(id);
    }

    @GetMapping("/getAllQuestions")
    public List<ClosedQuestionDTO> getAllClosedQuestions() {
        List<ClosedQuestionDTO> fd = questionService.getAllClosedQuestions();
        return fd;
    }

    @GetMapping("/getOptionsByQuestion/{id}")
    public List<OptionDTO> getOptionsByQuestion(@PathVariable UUID id) {
        return questionService.getOptionsByQuestion(id);
    }
}
