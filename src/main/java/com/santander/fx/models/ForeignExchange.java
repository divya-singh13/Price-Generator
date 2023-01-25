package com.santander.fx.models;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ForeignExchange {
    private String id;
    private String instrumentName;
    private double bidPrice;
    private double askPrice;
    private LocalDateTime timeStamp;


}
