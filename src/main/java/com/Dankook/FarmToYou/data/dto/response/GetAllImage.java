package com.Dankook.FarmToYou.data.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class GetAllImage {
    List<String> urls;
}
