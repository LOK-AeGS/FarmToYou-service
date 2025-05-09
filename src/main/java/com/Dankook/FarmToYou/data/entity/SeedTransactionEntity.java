package com.Dankook.FarmToYou.data.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@Document(collection = "company_seed_transaction")
public class SeedTransactionEntity {
    @Id
    private ObjectId id;

    private Integer seedTransactionId;
    private Integer seedId;
    private Integer seedBuyerId;
    private Integer seedSales;
    private LocalDate seedSalesDate;
    private Integer seedTransactionStatus;

    private UserData userData; // ✅ 이 필드를 꼭 추가해야 Mongo에서 바인딩 가능

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserData {
        private Integer userId;
        private Integer userType;
        private String userName;
        private String userPhone;
        private String userEmail;
        private String userAddress;
        private String userAddressDetail;
    }
}
