package com.springboot.exchange.msbootcoin.services.Impl;

import com.springboot.exchange.msbootcoin.documents.dto.BootCoinDocumentDto;
import com.springboot.exchange.msbootcoin.services.IBootCoinDtoServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
public class BootCoinDtoServiceImpl implements IBootCoinDtoServices {

    private static final Logger log = LoggerFactory.getLogger(BootCoinDtoServiceImpl.class);

    @Autowired
    private WebClient.Builder clientBuilder;

    @Override
    public Mono<BootCoinDocumentDto> findByCustomerIdentityNumber(String id) {
        return clientBuilder
                .build()
                .get()
                .uri("http://localhost:8090/api/bootcoin/number/" + id)
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(clientResponse -> clientResponse.bodyToMono(BootCoinDocumentDto.class))
                .doOnNext(c -> log.info("Account BootCoin", c.getCustomerIdentityNumber()))
                .switchIfEmpty(Mono.just(BootCoinDocumentDto.builder().id(null).build()));
    }

    @Override
    public Mono<BootCoinDocumentDto> updateBootCoin(BootCoinDocumentDto bootCoinDocumentDto) {
        BootCoinDocumentDto bcDocument = BootCoinDocumentDto.builder()
                .id(bootCoinDocumentDto.getId())
                .amountBitCoin(bootCoinDocumentDto.getAmountBitCoin())
                .build();

        return clientBuilder
                .build()
                .put()
                .uri("http://localhost:8090/api/bootcoin/" + bootCoinDocumentDto.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(bcDocument)
                .retrieve()
                .bodyToMono(BootCoinDocumentDto.class);
    }
}
