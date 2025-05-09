package com.Dankook.FarmToYou.data.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.repository.ExistsQuery;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
public class CompanyProfileResponse {
    Map<LocalDateTime,Integer> priceByMonth; //월, 판매수익
    Map<String,Integer> seedByCategory; //카테고리, 종자개수
}
