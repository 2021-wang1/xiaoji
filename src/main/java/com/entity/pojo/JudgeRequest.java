package com.entity.pojo;

import lombok.Data;

@Data
public class JudgeRequest {

    // 语言
    private Integer language_id = 71;
    // 源代码
    private String source_code;
    // 输入
    private String stdin = "";

}
