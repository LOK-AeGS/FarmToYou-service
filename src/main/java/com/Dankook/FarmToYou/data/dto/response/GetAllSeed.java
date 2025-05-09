package com.Dankook.FarmToYou.data.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//모든 Seed줄 때 Response
public class GetAllSeed {

    private String name;
    private int price;
    private String numberOfSeed;
    private String category;
    private String way;
    private String caution;

}
