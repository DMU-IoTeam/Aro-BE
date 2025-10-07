package com.ioteam.domain.health.dto;
import com.ioteam.domain.health.entity.HealthQuestion;
import lombok.Getter;
@Getter
public class QuestionResponse {
    private Long id;
    private String questionText;
    private String options;
    public QuestionResponse(HealthQuestion question) {
        this.id = question.getId();
        this.questionText = question.getQuestionText();
        this.options = question.getOptions();
    }
}