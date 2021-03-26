package com.wspa.pa1.controller;

import com.wspa.pa1.form.Form;
import com.wspa.pa1.model.*;
import com.wspa.pa1.schemas.calculator.DivideResponse;
import com.wspa.pa1.soap.SOAPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/form")
public class PageController implements WebMvcConfigurer {

    private static final Logger log= LoggerFactory.getLogger(PageController.class);

    @Value("${apiKey.gnews}")
    private String gNewsApiKey;

    @Autowired
    private SOAPClient soapClient;

    @GetMapping(value = { "", "/" })
    public String ratesList(Model model) {
        RestTemplate restTemplate = new RestTemplate();
        // Calling REST service to get the exchange rates to USD of every currency
        RatesData rates = restTemplate.getForObject("https://api.coincap.io/v2/rates", RatesData.class);
        model.addAttribute("rates", rates.getData());
        model.addAttribute("form", new Form());
        return "form/list";
    }

    @GetMapping("/create")
    public String createHomeworkForm(Form form, Model model) {
        return "form/create";
    }

    @PostMapping(value = { "/create" })
    public String display(Form form, Model model) {
        RestTemplate restTemplate = new RestTemplate();

        RatesData rates = restTemplate.getForObject("https://api.coincap.io/v2/rates", RatesData.class);
        model.addAttribute("rates", rates.getData());
        String userCurrCode = form.getCurrIsoCode();
        List<Rate> userSelectedCurrRateList = rates.getData().stream().filter(r -> r.getSymbol().equals(userCurrCode)).collect(Collectors.toList());
        Double userCurrExchangeRateToUSD = Double.parseDouble(userSelectedCurrRateList.get(0).getRateUsd());
        int denominator = (int) (userCurrExchangeRateToUSD*1000);

        // Calling REST service to get the list crypto currencies and rates in USD
        log.info("Details of Top crypto currencies:");
        CoinData coins = restTemplate.getForObject("https://api.coincap.io/v2/assets", CoinData.class);
        for (Coin co : coins.getCoins().subList(0, 10)){
            int numerator = (int) (Double.parseDouble(co.getPriceUsd())*1000);
            // Calling SOAP service to divide (Crypto Price in USD) by (Exchange Rate of selected currency to USD)
            DivideResponse response = soapClient.divide(numerator, denominator);
            if(response.getDivideResult() > 0) {
                co.setConvertedPrice(response.getDivideResult());
                log.info(co.representation(userCurrCode));
            }
        }
        model.addAttribute("coins", coins.getCoins().stream().filter(c -> c.getConvertedPrice()>0).collect(Collectors.toList()));

        // Calling REST service to get News by keyword
        log.info("\nEnter the name of the crypto currency to read news about:");
        String userCoinName = form.getCryptoCurr();

        String url = String.format("https://gnewsapi.net/api/search?q=%s&language=en&api_token=%s", userCoinName, gNewsApiKey);
        NewsData news = restTemplate.getForObject(url, NewsData.class);
        for (Article ar : news.getArticles().subList(0, 10)){
            log.info(ar.representation());
        }
        model.addAttribute("articles", news.getArticles());

        return "form/view";
    }
}
