package com.example.parser.conroller;



import com.example.parser.entity.ItemData;
import com.example.parser.entity.ParserObject;
import com.example.parser.service.ParseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {


    private final ParseService parseService;

    @GetMapping("")
    public ResponseEntity<List<ItemData>> parse(
            @RequestParam String urlToParse
    ) {
        return ResponseEntity.ok(parseService.parse(urlToParse));
    }

    @GetMapping("/parse")
    public ResponseEntity<List<ItemData>> parsePage(
            @RequestBody ParserObject parserObjects

    ) {

        System.out.println(parserObjects);
        return ResponseEntity.ok(parseService.parse(parserObjects));
    }


}
