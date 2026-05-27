package org.example.grpcstocktradeserver.repository;

import org.example.grpcstocktradeserver.entity.Stocks;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StocksRepo extends JpaRepository<Stocks, Long> {


    Stocks findByStockName(String stockName);
}
