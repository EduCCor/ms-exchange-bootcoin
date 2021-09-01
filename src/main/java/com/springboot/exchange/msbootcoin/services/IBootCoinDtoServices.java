package com.springboot.exchange.msbootcoin.services;

import com.springboot.exchange.msbootcoin.documents.dto.BootCoinDocumentDto;
import reactor.core.publisher.Mono;

public interface IBootCoinDtoServices {

    Mono<BootCoinDocumentDto> findByCustomerIdentityNumber(String id);

    Mono<BootCoinDocumentDto> updateBootCoin(BootCoinDocumentDto bootCoinDocumentDto);

}
