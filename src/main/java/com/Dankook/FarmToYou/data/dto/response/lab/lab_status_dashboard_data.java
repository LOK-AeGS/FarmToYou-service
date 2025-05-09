package com.Dankook.FarmToYou.data.dto.response.lab;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/*
export const lab_status_dashboard_data =
{
    data: [4, 3, 2, 1, 5],
    category: ['연구중', '모집 중', '성공', '실패', '중단']
} // 연구 상태 대시보드 데이터 for 연구소 마이페이지
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class lab_status_dashboard_data {
    List<Integer> data;
    List<String> category;
}
