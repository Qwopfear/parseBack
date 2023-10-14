package com.example.parser.service;


import com.example.parser.config.URLMapper;
import com.example.parser.entity.ItemData;
import com.example.parser.entity.ParserObject;
import com.example.parser.entity.WebAddress;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ParseServiceImpl implements ParseService {


    private final URLMapper urlMapper;

    @Override
    public List<ItemData> parse(String urlToParse) {


        List<ItemData> response = new ArrayList<>();

        try {
            Document doc = Jsoup.connect(urlToParse).get();
            Elements links = doc.select("a");
            int index = 1;
            for (Element e : links) {
                if (e.hasClass("css-rc5s2u")) {
                    String link = e.attr("href");
                    String name = e.getElementsByTag("h6").first().text();
                    System.out.println("-----------------------------------");
                    String price = e.getElementsByTag("p").text();
                    ItemData item = new ItemData(
                            index
                            , name,
                            price,
                            // Make variable with site name
                            "www.olx.pl" + link

                    );
                    index++;
//                    System.out.println(e.text());
                    response.add(item);
                }
            }
            return response;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<ItemData> parseOlx(String webPage, String product) {
        List<ItemData> response = new ArrayList<>();

        try {
            Document doc = Jsoup.connect("https://" + webPage + "/q-" + product).get();
            Elements links = doc.select("a");
            int index = 1;
            for (Element e : links) {
                if (e.hasClass("css-rc5s2u")) {
                    String link = e.attr("href");
                    String name = e.getElementsByTag("h6").first().text();
                    String price = e.getElementsByTag("p").text();
                    ItemData item = new ItemData(
                            index,
                            name,
                            price,
                            // Make variable with site name
                            webPage + link

                    );
                    index++;

//                    System.out.println(e.text());
                    response.add(item);
                }

            }
            return response;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ItemData> parse(ParserObject parserObject) {
        List<ItemData> products = new ArrayList<>();
        for (WebAddress address : parserObject.getWebAddress()) {
            switch (address) {
                case OLX ->
                        products.addAll(parseOlx(urlMapper.mappedAddresses.get(address), parserObject.getProductName()));
                case EBAY ->
                        products.addAll(parseEbay(urlMapper.mappedAddresses.get(address), parserObject.getProductName()));
            }
        }

        return products;
    }

    private Collection<ItemData> parseEbay(String webPage, String product) {
        List<ItemData> response = new ArrayList<>();
        System.out.println(product);
        try {
            String url = "https://" + webPage + "_nkw=" + product;
            Document doc = Jsoup.connect(url).get();
            int index = 1;
            Elements links = doc.getElementsByClass("srp-results");
            for (Element e : links) {
                for (Element productCard : e.getElementsByTag("li")) {
                    String price = null;
                    String actualLink = null;
                    String name = null;
                    Element linkElement = productCard.getElementsByClass("s-item__link").first();
                    if (linkElement != null) {
                        actualLink = linkElement.attr("href");
                        Element nameElement = linkElement.getElementsByClass("s-item__title").first();
                        if (nameElement != null) {
                            name = nameElement.getElementsByTag("span").first().text();
                        }
                    }
                    Element priceElement = productCard.getElementsByClass("s-item__detail s-item__detail--primary").first();
                    if (priceElement != null) {
                        price = priceElement.getElementsByClass("s-item__price").first().text();
                    }
                    if (name != null && price != null) {
                        response.add(new ItemData(
                                index,
                                name,
                                price,
                                actualLink
                        ));
                        index++;
                    }
                }
            }
            return response;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Collection<ItemData> parseAmazon(String webAddress, String productName) {
        return null;
    }


}
