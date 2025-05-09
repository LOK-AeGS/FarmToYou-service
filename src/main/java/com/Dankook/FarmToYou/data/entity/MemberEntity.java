package com.Dankook.FarmToYou.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Document(collection = "Member")
@Getter
@Setter
@Builder
@AllArgsConstructor
public class MemberEntity {
    @Id
    private String memberId;
    private String memberName;
    private String memberPhoneNumber;
    private String memberEmail;
    private String memberAddress;
    private List<Map<String, LocalDateTime>> matchingList; // <PostId, 매칭 날짜>
    private List<Map<String, LocalDateTime>> parchasedList; // <SeedId, 날짜>

}
