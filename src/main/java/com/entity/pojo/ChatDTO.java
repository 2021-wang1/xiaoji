package com.entity.pojo;

import lombok.Data;

@Data
public class ChatDTO {

    //题目
    private String topic;

    //学生答案
    private String answer;

    //分值
    private Integer score;

    //参考答案
    private String reference;

    public static String getFormat(){
        return "{\"topic\":\"\",\"answer\":\"\",\"score\":10,\"reference\":\"\"}" +
                ",其中topic为题目,answer为学生的答案,score为这题的分值,reference为参考答案(不一定有);";
    }
}