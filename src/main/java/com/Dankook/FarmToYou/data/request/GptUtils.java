package com.Dankook.FarmToYou.data.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GptUtils {
    public static final String info = "아래는 씨앗에 대한 기록들이야 이 기록들을 바탕으로 사용 가이드를 작성해줘\n";

    public static final String seedName = "씨앗 이름: ";
    public static final String researchDate = "관찰 기간: ";
    public static final String imageUrl = "이 씨앗의 이미지야: ";
    public static final String content = "연구 기록: ";

}

