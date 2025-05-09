package com.Dankook.FarmToYou.data.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

//모든 예약 게시물을 다 보여줄 때 기능
@Setter
@Getter
public class GetAllPost {

//    private String name;
    private String title;
    private String content;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String imageUrl;
    private String farmerName;

}
