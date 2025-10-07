package com.ioteam.domain.health.dto;
import com.ioteam.domain.health.entity.HealthAnswer;
import lombok.Getter;
import java.time.LocalDate;
@Getter
public class AnswerResponse {
    private Long questionId;
    private String questionText;
    private String answerText;
    private LocalDate checkDate;
    public AnswerResponse(HealthAnswer answer) {
        this.questionId = answer.getQuestion().getId();
        this.questionText = answer.getQuestion().getQuestionText();
        this.answerText = answer.getAnswerText();
        this.checkDate = answer.getCheckDate();
    }
}