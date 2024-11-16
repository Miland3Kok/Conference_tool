package be.kdg.conference.service.implementation;

import be.kdg.conference.controller.dto.questionDTOs.*;
import be.kdg.conference.model.event_interaction.questions.*;
import be.kdg.conference.repository.*;
import be.kdg.conference.service.QuestionService;
import be.kdg.conference.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final ClosedQuestionRepository closedQuestionRepository;
    private final MultipleChoiceQuestionRepository multipleChoiceQuestionRepository;

    private final OpenQuestionRepository openQuestionRepository;

    private final UserAnswerRepository userAnswerRepository;
    private final UserService userService;

    private final OptionRepository optionRepository;



    public QuestionServiceImpl(QuestionRepository questionRepository,
                               ClosedQuestionRepository closedQuestionRepository,
                               MultipleChoiceQuestionRepository multipleChoiceQuestionRepository,
                               OpenQuestionRepository openQuestionRepository,
                               UserAnswerRepository userAnswerRepository,
                               UserService userService,
                               OptionRepository optionRepository) {
        this.questionRepository = questionRepository;
        this.closedQuestionRepository = closedQuestionRepository;
        this.multipleChoiceQuestionRepository = multipleChoiceQuestionRepository;
        this.openQuestionRepository = openQuestionRepository;
        this.userAnswerRepository = userAnswerRepository;
        this.userService = userService;
        this.optionRepository = optionRepository;
    }

    @Override
    public Question getQuestionById(UUID id) {
        Optional<Question> question = questionRepository.findById(id);
        if (question.isEmpty()) {
            throw new RuntimeException("Question not found");
        }
        return question.get();
    }


    @Override
    public ClosedQuestionDTO createClosedQuestion(ClosedQuestionDTO questionDTO) {
        ClosedQuestion question = new ClosedQuestion(questionDTO);
        question.setQuestionType(QuestionType.CLOSED);
        closedQuestionRepository.save(question);
        return questionDTO;
    }

    @Override
    public ClosedQuestionDTO updateClosedQuestion(ClosedQuestionDTO questionDTO) {
        ClosedQuestion closedQuestion = (ClosedQuestion) questionRepository.findById(questionDTO.getId()).orElseThrow();
        closedQuestion.setCorrectAnswer(questionDTO.getCorrectAnswer());
        closedQuestion.setFalseAnswer(questionDTO.getFalseAnswer());
        closedQuestion.setContent(questionDTO.getContent());
        ClosedQuestion saved = closedQuestionRepository.save(closedQuestion);
        return new ClosedQuestionDTO(saved);
    }

    @Override
    public OpenQuestionDTO createOpenQuestion(OpenQuestionDTO questionDTO) {
        OpenQuestion question = new OpenQuestion(questionDTO);
        question.setQuestionType(QuestionType.OPEN);
        questionRepository.save(question);
        return questionDTO;
    }

    @Override
    public OpenQuestionDTO updateOpenQuestion(OpenQuestionDTO questionDTO) {
        OpenQuestion question = (OpenQuestion) questionRepository.findById(questionDTO.getId()).orElseThrow();
        question.setContent(questionDTO.getContent());
        OpenQuestion saved = questionRepository.save(question);
        return new OpenQuestionDTO(saved);
    }

    @Override
    public MultipleChoiceQuestionDTO createMultipleChoiceQuestion(MultipleChoiceQuestionDTO questionDTO) {
        List<Option> options = questionDTO.getOptions().stream().map(optionDTO -> {
            Option option = new Option(optionDTO);
            optionRepository.save(option);
            return option;
        }).collect(Collectors.toList());

        MultipleChoiceQuestion question = new MultipleChoiceQuestion(questionDTO);
        question.setQuestionType(QuestionType.MULTIPLE_CHOICE);
        question.setOptionTexts(options);

        MultipleChoiceQuestion savedQuestion = multipleChoiceQuestionRepository.save(question);

        options.forEach(option -> option.setMultipleChoiceQuestion(savedQuestion));

        optionRepository.saveAll(options);

        return new MultipleChoiceQuestionDTO(savedQuestion);
    }

    @Override
    public MultipleChoiceQuestionDTO updateMultipleChoiceQuestion(MultipleChoiceQuestionDTO questionDTO) {
        MultipleChoiceQuestion question = (MultipleChoiceQuestion) questionRepository.findById(questionDTO.getId()).orElseThrow();
        question.setContent(questionDTO.getContent());

        List<Option> updatedOptions = new ArrayList<>();
        for (OptionDTO optionDTO : questionDTO.getOptions()) {
            Option option;
            if (optionDTO.getId() != null) {
                option = optionRepository.findById(optionDTO.getId()).orElseThrow();
                option.setOptionText(optionDTO.getOptionText());
            } else {
                option = new Option(optionDTO);
            }
            updatedOptions.add(option);
        }

        List<Option> optionsToRemove = new ArrayList<>();
        for (Option option : question.getOptionTexts()) {
            if (!updatedOptions.contains(option)) {
                optionsToRemove.add(option);
            }
        }
        question.getOptionTexts().removeAll(optionsToRemove);

        question.setOptionTexts(updatedOptions);

        question.setQuestionType(QuestionType.MULTIPLE_CHOICE);
        MultipleChoiceQuestion saved = multipleChoiceQuestionRepository.save(question);

        return new MultipleChoiceQuestionDTO(saved);
    }



    @Override
    public UserAnswerDTO answerQuestion(UserAnswerDTO userAnswerDTO) {
        UserAnswer userAnswer = new UserAnswer();
        userAnswer.setUser(userService.getUserById(userAnswerDTO.getUserId()));
        userAnswer.setQuestion(questionRepository.findById(userAnswerDTO.getQuestionId()).orElseThrow());
        userAnswer.setAnswer(userAnswerDTO.getAnswer());
        userAnswerRepository.save(userAnswer);
        return userAnswerDTO;
    }


    @Override
    public List<QuestionDTO> getQuestionsByTalk(UUID id) {
        List<QuestionDTO> questions = new ArrayList<>();

        closedQuestionRepository.findAllByTalkId(id).forEach(question -> questions.add(new QuestionDTO(question)));
        openQuestionRepository.findAllByTalkId(id).forEach(question -> questions.add(new QuestionDTO(question)));
        multipleChoiceQuestionRepository.findAllByTalkId(id).forEach(question -> questions.add(new QuestionDTO(question)));

        return questions;
    }

    @Override
    public void deleteQuestion(UUID id) {
            questionRepository.deleteById(id);
    }

    @Override
    public boolean doesTalkHaveQuestions(UUID id) {
        return !questionRepository.findAllByTalkId(id).isEmpty();
    }

    @Override
    public List<ClosedQuestionDTO> getAllClosedQuestions() {
        return closedQuestionRepository.findAll().stream().map(ClosedQuestionDTO::new).collect(Collectors.toList());
    }

    @Override
    public List<OptionDTO> getOptionsByQuestion(UUID id) {
        return optionRepository.findAllByMultipleChoiceQuestionId(id).stream().map(OptionDTO::new).collect(Collectors.toList());
    }
}
