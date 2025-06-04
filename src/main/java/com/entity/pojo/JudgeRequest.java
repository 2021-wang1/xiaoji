package com.entity.pojo;

import lombok.Data;

@Data
public class JudgeRequest {

    private Integer language_id;
    private String source_code;
    private String stdin;

}
