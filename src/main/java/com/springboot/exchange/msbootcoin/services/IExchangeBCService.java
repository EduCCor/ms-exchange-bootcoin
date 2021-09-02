package com.springboot.exchange.msbootcoin.services;

import com.springboot.exchange.msbootcoin.documents.ExchangeDocument;
import reactor.core.publisher.Mono;

public interface IExchangeBCService extends ICrudService<ExchangeDocument, String>{

    Mono<ExchangeDocument> createExchange (ExchangeDocument exchangeDocument);

    Mono<ExchangeDocument> takeEnchage (String id, ExchangeDocument exchangeDocument);

}
