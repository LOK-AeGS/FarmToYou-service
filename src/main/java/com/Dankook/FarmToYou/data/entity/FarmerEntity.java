package com.Dankook.FarmToYou.data.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Document(collection = "Farmer")
@Getter
@Setter
@Builder
@AllArgsConstructor
public class FarmerEntity {
    @Id
    private String id;
    private String name;
    private String phone;
    private String email;
    private String address;
}
