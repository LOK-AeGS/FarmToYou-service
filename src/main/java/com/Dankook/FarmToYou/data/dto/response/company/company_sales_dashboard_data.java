package com.Dankook.FarmToYou.data.dto.response.company;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/*
{
    data: [1000000, 2000000, 3000000, 4000000, 5000000, 6000000, 7000000, 8000000],
    month: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월']
} // 판매 대시보드 데이터 for 기업 마이페이지지
 */
@Getter
@Setter
public class company_sales_dashboard_data {
    List<Integer> data;//: [1000000, 2000000, 3000000, 4000000, 5000000, 6000000, 7000000, 8000000],
    List<String> month ;//: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월']

}
