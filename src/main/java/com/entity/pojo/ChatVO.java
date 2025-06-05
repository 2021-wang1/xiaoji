package com.entity.pojo;

import lombok.Data;

@Data
public class ChatVO {

    //得分
    private Float score;

    //原因
    private String cause;

    public static String getFormat() {
        return "{\"score\":0.0,\"cause\":\"\"},其中score为你评出的得分情况,cause为给出这个分数的原因,少于100字";
    }
}