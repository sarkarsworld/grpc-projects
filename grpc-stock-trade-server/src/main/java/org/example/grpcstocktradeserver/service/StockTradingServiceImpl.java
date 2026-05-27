package org.example.grpcstocktradeserver.service;

import com.example.generated.stockTrade.StockResponse;
import com.example.generated.stockTrade.StockTradingServiceGrpc;
import com.example.generated.stockTrade.StrockRequest;
import io.grpc.stub.StreamObserver;
import org.example.grpcstocktradeserver.entity.Stocks;
import org.example.grpcstocktradeserver.repository.StocksRepo;
import org.springframework.grpc.server.service.GrpcService;

@GrpcService
public class StockTradingServiceImpl extends StockTradingServiceGrpc.StockTradingServiceImplBase {

    private final StocksRepo stocksRepo;

    public StockTradingServiceImpl(StocksRepo stocksRepo) {
        this.stocksRepo = stocksRepo;
    }

    @Override
    public void getStockPrice(StrockRequest request, StreamObserver<StockResponse> responseObserver) {

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
}
