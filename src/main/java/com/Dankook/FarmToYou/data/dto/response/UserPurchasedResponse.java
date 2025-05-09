package com.Dankook.FarmToYou.data.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class UserPurchasedResponse {

    private List<Map<String, LocalDateTime>> parchasedList; // <SeedId, 날짜>

}
