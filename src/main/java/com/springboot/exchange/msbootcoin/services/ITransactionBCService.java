package com.springboot.exchange.msbootcoin.services;

import com.springboot.exchange.msbootcoin.documents.dto.BootCoinDocumentDto;
import com.springboot.exchange.msbootcoin.documents.dto.TransactionBCDocumentDto;
import reactor.core.publisher.Mono;

public interface ITransactionBCService {

    Mono<TransactionBCDocumentDto> createTransaction(TransactionBCDocumentDto transactionBCDocumentDto);

}
