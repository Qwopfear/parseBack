package com.example.parser.config;

import com.example.parser.entity.WebAddress;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component
public class URLMapper {

    public final Map<WebAddress, String> mappedAddresses;

    public URLMapper() {
        mappedAddresses = new HashMap<>();
        mappedAddresses.put(WebAddress.EBAY, "www.ebay.com/sch/i.html?");
        mappedAddresses.put(WebAddress.OLX, "www.olx.pl");
        mappedAddresses.put(WebAddress.AMAZON, "www.amazon.pl");
    }





}
