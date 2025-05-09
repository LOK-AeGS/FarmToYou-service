package com.Dankook.FarmToYou.data.dto.gptapi;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

//쓸려고 했는데 공식문서랑 달라서 지금 못 쓰는 중
@Getter
@Setter
public class
TextGenerationResponse {
    private String id;
    private String object;
    private long created;
    private String model;
    private List<Choice> choices;
    private Usage usage;
    @Getter
    @Setter
    public class Choice {
        private int index;
        private Message message;
        private String finish_reason;
    }
    @Getter
    @Setter
    public class Message {
        private String role;
        private String content;
    }
    @Getter
    @Setter
    public class Usage {
        private int prompt_tokens;
        private int completion_tokens;
        private int total_tokens;
    }
}
