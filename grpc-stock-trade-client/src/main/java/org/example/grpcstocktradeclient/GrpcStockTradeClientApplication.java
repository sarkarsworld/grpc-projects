package org.example.grpcstocktradeclient;

import org.example.grpcstocktradeclient.services.StockTradeClientService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GrpcStockTradeClientApplication implements CommandLineRunner {

    private final StockTradeClientService stockTradeClientService;

    public GrpcStockTradeClientApplication(StockTradeClientService stockTradeClientService) {
        this.stockTradeClientService = stockTradeClientService;
    }


    public static void main(String[] args) {
        SpringApplication.run(GrpcStockTradeClientApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Example for Unary gRPC call.
        // System.out.println("Stock Details : " + stockTradeClientService.getStockPrice("Apple"));

        // Example for Server Stream gRPC call.
        stockTradeClientService.getSubscribeStockPrice("Apple");
    }
}
