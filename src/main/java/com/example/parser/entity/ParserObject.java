package com.example.parser.entity;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class ParserObject {

    private String productName;
    private List<WebAddress> webAddress;
}
