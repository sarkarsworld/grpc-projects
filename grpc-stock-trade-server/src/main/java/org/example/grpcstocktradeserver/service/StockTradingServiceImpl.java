package org.example.grpcstocktradeserver.service;

import com.example.generated.stockTrade.StockResponse;
import com.example.generated.stockTrade.StockTradingServiceGrpc;
import com.example.generated.stockTrade.StockRequest;
import io.grpc.stub.StreamObserver;
import org.example.grpcstocktradeserver.entity.Stocks;
import org.example.grpcstocktradeserver.repository.StocksRepo;
import org.springframework.grpc.server.service.GrpcService;

import java.time.Instant;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@GrpcService
public class StockTradingServiceImpl extends StockTradingServiceGrpc.StockTradingServiceImplBase {

    private final StocksRepo stocksRepo;

    public StockTradingServiceImpl(StocksRepo stocksRepo) {
        this.stocksRepo = stocksRepo;
    }

    // Example of Unary Request and Response using gRPC.
    @Override
    public void getStockPrice(StockRequest request, StreamObserver<StockResponse> responseObserver) {

        // Get stock-name from StockRequest.
        String stockName = request.getStockName();

        // get stock details from DB by the stock-name.
        Stocks stock = stocksRepo.findByStockName(stockName);

        // build a StockResponse object to be returned back to the calling method.
        StockResponse stockResponse = StockResponse.newBuilder()
                .setStockName(stock.getStockName())
                .setPrice(stock.getPrice())
                .setTimestamp(stock.getLastUpdated().toString())
                .build();

        // send back gRPC response
        responseObserver.onNext(stockResponse);

        // mark the stream completed.
        responseObserver.onCompleted();


    }

    @Override
    public void subscribeStockPrice(StockRequest request, StreamObserver<StockResponse> responseObserver) {

        // Get stock-name from StockRequest.
        String stockName = request.getStockName();

        // Send hardcoded simulate stream of responses.
        try {
            for (int i=0; i<10; i++){
                // Create object of StockResponse.
                StockResponse stockResponse = StockResponse.newBuilder()
                        .setStockName(stockName)
                        .setPrice(new Random().nextDouble(200))
                        .setTimestamp(Instant.now().toString())
                        .build();

                // send the response object via responseObserver and sleep for 1 second.
                responseObserver.onNext(stockResponse);
                TimeUnit.SECONDS.sleep(1);

            }

            // Mark the tasks as completed.
            responseObserver.onCompleted();
        }catch (Exception e){
            responseObserver.onError(e);
        }

    }

}
