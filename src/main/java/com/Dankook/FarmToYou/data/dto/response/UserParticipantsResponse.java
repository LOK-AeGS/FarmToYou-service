package com.Dankook.FarmToYou.data.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class UserParticipantsResponse {

    private List<Map<String, LocalDateTime>> matchingList; // <PostId, 매칭 날짜>
}
