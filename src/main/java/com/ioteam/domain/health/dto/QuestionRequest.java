package com.ioteam.domain.health.dto;
import lombok.Getter;

@Getter
public class QuestionRequest {
    private String questionText;
    private String options;
}