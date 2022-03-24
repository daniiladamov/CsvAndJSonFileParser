package com.adamov.test.parsing.models;

import lombok.Data;

@Data
public class ModelOut {
    private static int count=1;
    private final int id=count++;
    private int orderId;
    private double amount;
    private String currency;
    private String comment;
    private String filename;
    private long line;
    private String result;
}
