package com.ioteam.domain.health.dto;
import lombok.Getter;
@Getter
public class AnswerRequest {
    private Long questionId;
    private String answerText;
}