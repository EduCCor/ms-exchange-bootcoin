package com.springboot.exchange.msbootcoin.repositories;

import com.springboot.exchange.msbootcoin.documents.ExchangeDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ExchangeBCRepository extends ReactiveMongoRepository<ExchangeDocument, String> {
}
