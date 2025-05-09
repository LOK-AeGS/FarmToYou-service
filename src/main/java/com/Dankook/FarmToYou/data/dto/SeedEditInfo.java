package com.Dankook.FarmToYou.data.dto;

import lombok.*;
//seed Id로 받기
@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SeedEditInfo {
    private String seedName;
    private String category;
    private String seedNumber;
    private int price;
    private String title;
    private String content;
    
}
