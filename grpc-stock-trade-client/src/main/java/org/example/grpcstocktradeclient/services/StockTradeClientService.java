package org.example.grpcstocktradeclient.services;

import com.example.generated.stockTrade.StockResponse;
import com.example.generated.stockTrade.StockTradingServiceGrpc;
import com.example.generated.stockTrade.StrockRequest;
import org.springframework.stereotype.Service;

@Service
public class StockTradeClientService {

    private final StockTradingServiceGrpc.StockTradingServiceBlockingStub stockTradingServiceBlockingStub;

    // Standard Spring constructor injection
    public StockTradeClientService(StockTradingServiceGrpc.StockTradingServiceBlockingStub stockTradingServiceBlockingStub) {
        this.stockTradingServiceBlockingStub = stockTradingServiceBlockingStub;
    }

    public StockResponse getStockPrice(String stockName) {

        StrockRequest strockRequest = StrockRequest.newBuilder()
                .setStockName(stockName)
                .build();

        return stockTradingServiceBlockingStub.getStockPrice(strockRequest);
    }



}
