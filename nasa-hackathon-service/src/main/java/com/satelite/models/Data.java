package com.satelite.models;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class Data {
    
    private double latitude;
    
    private double longitude;
    
    private double brightTI4;
    
    private double scan;
    
    private double track;
    
    private String acquiredDateTime;
    
    private String satellite;
    
    private String confidence;
    
    private String version;
    
    private double brightTI5;
    
    private double frp;
    
    private String daynight;
}
