package com.satelite.models;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Point {
    
    private Double latitude;
    
    private Double longitude;
}
