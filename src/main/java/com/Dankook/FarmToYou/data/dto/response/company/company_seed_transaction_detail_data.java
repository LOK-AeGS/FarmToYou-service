package com.Dankook.FarmToYou.data.dto.response.company;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.apache.catalina.User;

import java.time.LocalDate;

/*

export const company_seed_transaction_detail_data = { // 거래 상세 데이터
    seed_transaction_id: 1, // 거래 번호
    seed_id: 1, // 품종 번호
    seed_buyer_id: 1, // 구매자 번호
    seed_sales: 10, // 구매 수량
    seed_sales_date: '2025-01-01',
    seed_transaction_status: 0, // 0: 배송중, 1: 거래확정
}

 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class company_seed_transaction_detail_data {
    private int seed_transaction_id;//: 1, // 거래 번호
    private int seed_id; //: 1, // 품종 번호
    private int seed_sales ;//: 10, // 구매 수량
    @JsonFormat(pattern = "yyyy-MM-dd") // JSON → LocalDate 자동 변환
    private LocalDate seed_sales_date;
    private int seed_total_pay;
    private int getSeed_transaction_status;
    private String seed_transaction_payment;
    private UserData userData;
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserData{
        private int user_id;//: 1,
        private int user_type;//: 0,
        private String user_name;//: '홍길동',
        private String user_phone;//: '010-1234-5678',ㄴ
        private String user_email;//: 'hong@gmail.com',
        private String user_address;//: '',
        private String user_address_detail;//: '',
    }
}
