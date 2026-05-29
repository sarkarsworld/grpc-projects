package org.example.grpcstocktradeclient.services;

import com.example.generated.stockTrade.StockResponse;
import com.example.generated.stockTrade.StockTradingServiceGrpc;
import com.example.generated.stockTrade.StockRequest;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Service;

@Service
public class StockTradeClientService {


    //------------- Server Stream grpc service call from client.
    private final StockTradingServiceGrpc.StockTradingServiceStub stockTradingServiceStub;

    public StockTradeClientService(StockTradingServiceGrpc.StockTradingServiceStub stockTradingServiceStub) {
        this.stockTradingServiceStub = stockTradingServiceStub;
    }

    public void getSubscribeStockPrice(String stockName){
        StockRequest stockRequest = StockRequest.newBuilder()
                .setStockName(stockName)
                .build();

        stockTradingServiceStub.subscribeStockPrice(stockRequest, new StreamObserver<StockResponse>() {
            @Override
            public void onNext(StockResponse stockResponse) {
                System.out.println("Stock Details :-");
                System.out.println("Stock Name="+stockResponse.getStockName() + " " +
                        "Stock Price="+stockResponse.getPrice() + " " +
                        "Time="+stockResponse.getTimestamp());
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("Error Occured - "+throwable);
            }

            @Override
            public void onCompleted() {
                System.out.println("Stock Trade server stream completed.");
            }
        });
    }




    /*
    //------------- Unary grpc service call from client.
    private final StockTradingServiceGrpc.StockTradingServiceBlockingStub stockTradingServiceBlockingStub;

    // Standard Spring constructor injection
    public StockTradeClientService(StockTradingServiceGrpc.StockTradingServiceBlockingStub stockTradingServiceBlockingStub) {
        this.stockTradingServiceBlockingStub = stockTradingServiceBlockingStub;
    }

    public StockResponse getStockPrice(String stockName) {
        StockRequest stockRequest = StockRequest.newBuilder()
                .setStockName(stockName)
                .build();

        return stockTradingServiceBlockingStub.getStockPrice(stockRequest);
    }

    */



}
