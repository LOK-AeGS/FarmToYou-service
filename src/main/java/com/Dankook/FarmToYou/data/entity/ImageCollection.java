package com.Dankook.FarmToYou.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "image")
@Getter
@Setter
@Builder
@AllArgsConstructor
public class ImageCollection {

    @Id
    private String id;

    private String imageName;
    private String imageUrl;

}