package com.Dankook.FarmToYou.data.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ImageReceive {
    private String imageName;
    private MultipartFile multipartFile;
}
