package com.springboot.exchange.msbootcoin.handler;

import com.springboot.exchange.msbootcoin.documents.ExchangeDocument;
import com.springboot.exchange.msbootcoin.services.IExchangeBCService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class ExchangeHandler {

    private static final Logger log = LoggerFactory.getLogger(ExchangeHandler.class);

    @Autowired
    private IExchangeBCService exchangeBCService;

    public Mono<ServerResponse> findAll(ServerRequest request){
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(exchangeBCService.findAll(), ExchangeDocument.class);
    }

    public Mono<ServerResponse> createExchangeBC(ServerRequest request){

        Mono<ExchangeDocument> exchangeDocumentMono = request.bodyToMono(ExchangeDocument.class);

        return exchangeDocumentMono.flatMap(exchangeRequest -> exchangeBCService.createExchange(exchangeRequest))
                .flatMap( c -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(c)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> takeExchangeBC(ServerRequest request){
        Mono<ExchangeDocument> exchangeDocument = request.bodyToMono(ExchangeDocument.class);
        String id = request.pathVariable("id");

        return exchangeDocument.flatMap(ebc -> exchangeBCService.takeEnchage(id, ebc))
                .flatMap( c -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(c))
                ).switchIfEmpty(ServerResponse.badRequest().build());

    }
}
