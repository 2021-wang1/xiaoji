package com.entity.pojo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Model {
    private String model = "Qwen/Qwen3-8B";
    private Boolean stream = false;
    private Integer max_tokens = 512;
    private Boolean enable_thinking = false;
    private Integer thinking_budget = 4096;
    private Float min_p = 0.05F;
    private Float temperature = 0.7F;
    private Float top_p = 0.7F;
    private Integer top_k = 50;
    private Float frequency_penalty = 0.5F;
    private Integer n = 1;
    private List<String> stop = new ArrayList<>();
    private List<Messages> messages = new ArrayList<>();

    @Data
    public static class Messages{

        private String role = "user";
        private String content;

        public Messages(String content) {
            this.content = content;
        }
    }

}