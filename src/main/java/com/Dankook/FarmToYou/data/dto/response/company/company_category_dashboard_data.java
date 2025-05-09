package com.Dankook.FarmToYou.data.dto.response.company;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/*
{
    data: [4, 3, 2, 1, 5, 6, 2, 9],
    category: ['곡류', '두류', '채소류', '과수류', '약용식물', '사료작물', '화훼류']
} // 카테고리 대시보드 데이터 for 기업 마이페이지
 */
@Getter
@Setter
public class company_category_dashboard_data {
    List<Integer> data;
    List<String > category;
}
