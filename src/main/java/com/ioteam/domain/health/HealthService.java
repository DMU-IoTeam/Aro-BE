package com.ioteam.domain.health;

import com.ioteam.domain.health.dto.*;
import com.ioteam.domain.health.entity.*;
import com.ioteam.domain.health.repository.*;
import com.ioteam.domain.user.entity.User;
import com.ioteam.domain.user.repository.UserRepository;
import com.ioteam.global.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HealthService {
    private final HealthQuestionRepository questionRepository;
    private final HealthAnswerRepository answerRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long createQuestion(Long guardianId, QuestionRequest request) {
        User guardian = userRepository.findById(guardianId)
            .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));
        HealthQuestion question = HealthQuestion.builder()
            .guardian(guardian)
            .questionText(request.getQuestionText())
            .options(request.getOptions())
            .build();
        return questionRepository.save(question).getId();
    }

    @Transactional
    public void updateQuestion(Long guardianId, Long questionId, QuestionRequest request) {
        HealthQuestion question = questionRepository.findById(questionId)
            .orElseThrow(() -> new EntityNotFoundException("질문을 찾을 수 없습니다."));
        if (!question.getGuardian().getId().equals(guardianId)) {
            throw new AccessDeniedException("해당 질문을 수정할 권한이 없습니다.");
        }
        question.update(request.getQuestionText(), request.getOptions());
    }

    @Transactional
    public void deleteQuestion(Long guardianId, Long questionId) {
        HealthQuestion question = questionRepository.findById(questionId)
            .orElseThrow(() -> new EntityNotFoundException("질문을 찾을 수 없습니다."));
        if (!question.getGuardian().getId().equals(guardianId)) {
            throw new AccessDeniedException("해당 질문을 삭제할 권한이 없습니다.");
        }
        questionRepository.deleteById(questionId);
    }

    @Transactional(readOnly = true)
    public List<QuestionResponse> getQuestions(Long seniorId) {
        User senior = userRepository.findById(seniorId)
            .orElseThrow(() -> new EntityNotFoundException("피보호자를 찾을 수 없습니다."));
        if (senior.getGuardian() == null) {
            return List.of();
        }
        return questionRepository.findAllByGuardianId(senior.getGuardian().getId())
            .stream().map(QuestionResponse::new).collect(Collectors.toList());
    }

    @Transactional
    public void createAnswers(Long seniorId, List<AnswerRequest> requests) {
        User senior = userRepository.findById(seniorId)
            .orElseThrow(() -> new EntityNotFoundException("피보호자를 찾을 수 없습니다."));

        List<HealthAnswer> answers = requests.stream().map(req -> {
            HealthQuestion question = questionRepository.findById(req.getQuestionId())
                .orElseThrow(() -> new EntityNotFoundException("질문을 찾을 수 없습니다."));
            return HealthAnswer.builder()
                .senior(senior)
                .question(question)
                .answerText(req.getAnswerText())
                .checkDate(LocalDate.now())
                .build();
        }).collect(Collectors.toList());

        answerRepository.saveAll(answers);
    }

    @Transactional(readOnly = true)
    public List<AnswerResponse> getAnswers(Long guardianId, Long seniorId, LocalDate startDate, LocalDate endDate) {
        User senior = userRepository.findById(seniorId)
            .orElseThrow(() -> new EntityNotFoundException("피보호자를 찾을 수 없습니다."));
        if (senior.getGuardian() == null || !senior.getGuardian().getId().equals(guardianId)) {
            throw new AccessDeniedException("해당 피보호자의 기록을 조회할 권한이 없습니다.");
        }
        return answerRepository.findAllBySeniorIdAndCheckDateBetween(seniorId, startDate, endDate)
            .stream().map(AnswerResponse::new).collect(Collectors.toList());
    }
}