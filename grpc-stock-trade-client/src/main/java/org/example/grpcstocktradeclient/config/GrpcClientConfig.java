package org.example.grpcstocktradeclient.config;

import io.grpc.Channel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.grpc.client.GrpcChannelFactory;

import com.example.generated.stockTrade.StockTradingServiceGrpc;

@Configuration
public class GrpcClientConfig {

    @Bean
    public StockTradingServiceGrpc.StockTradingServiceBlockingStub stockTradingServiceBlockingStub(GrpcChannelFactory channelFactory) {
        // "stockTradeServer" matches the name configured in your application.yml
        Channel channel = channelFactory.createChannel("stockTradeServer");
        return StockTradingServiceGrpc.newBlockingStub(channel);
    }

}
