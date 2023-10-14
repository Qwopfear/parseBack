package com.example.parser.service;


import com.example.parser.entity.ItemData;
import com.example.parser.entity.ParserObject;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ParseService {


    List<ItemData> parse(String urlToParse);


    List<ItemData> parse(ParserObject parserObjects);
}
