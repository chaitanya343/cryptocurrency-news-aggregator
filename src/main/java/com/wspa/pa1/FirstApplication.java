package com.wspa.pa1;

import com.wspa.pa1.model.*;
import com.wspa.pa1.schemas.calculator.DivideResponse;
import com.wspa.pa1.soap.SOAPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@SpringBootApplication
public class FirstApplication {

	private static final Logger log= LoggerFactory.getLogger(FirstApplication.class);

	@Value("${apiKey.gnews}")
	private String gNewsApiKey;

	public static void main(String[] args) {
		SpringApplication.run(FirstApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public CommandLineRunner run(SOAPClient soapClient, RestTemplate restTemplate) throws Exception{
		return args -> {
			log.info("\nWelcome Crypto Currency Enthusiasts!");
			Scanner in = new Scanner(System.in);

			// Bored API
			MSG msg = restTemplate.getForObject("https://www.boredapi.com/api/activity", MSG.class);
			log.info("\nAre you bored?");
			String moo = in.nextLine();
			log.info("\nTry this -> "+msg.getActivity());

			// Calling REST service to get the exchange rates to USD of every currency
			RatesData rates = restTemplate.getForObject("https://api.coincap.io/v2/rates", RatesData.class);

			log.info("\nEnter the ISO Code of the currency of your interest eg INR, USD, CNY, etc. :");
			String userCurrCode = in.nextLine();

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

			// Calling REST service to get News by keyword
			log.info("\nEnter the name of the crypto currency to read news about:");
			String userCoinName = in.nextLine();

			String url = String.format("https://gnewsapi.net/api/search?q=%s&language=en&api_token=%s", userCoinName, gNewsApiKey);
			NewsData news = restTemplate.getForObject(url, NewsData.class);
			for (Article ar : news.getArticles().subList(0, 10)){
				log.info(ar.representation());
			}
		};
	}

}
