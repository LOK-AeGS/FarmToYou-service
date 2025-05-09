package com.Dankook.FarmToYou.data.dto.response.farmer;

import java.util.List;

public class ExpReviewResponse {
    private int expId;
    private int expReviewId;
    private int expReviewerId;
    private String expReviewerName;
    private ExpReviewDesc expReviewDesc;
    private int expReviewRate;
    private List<String> expReviewImage;
    private String expReviewDate;

    public static class ExpReviewDesc {
        private String title;
        private String content;
        // getters and setters
    }
    // getters and setters
} 