package com.springboot.exchange.msbootcoin.services.Impl;

import com.springboot.exchange.msbootcoin.documents.dto.BootCoinDocumentDto;
import com.springboot.exchange.msbootcoin.documents.dto.TransactionBCDocumentDto;
import com.springboot.exchange.msbootcoin.services.ITransactionBCService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class TransactionBCServiceImpl implements ITransactionBCService {

    private static final Logger log = LoggerFactory.getLogger(TransactionBCServiceImpl.class);

    @Autowired
    private WebClient.Builder clientBuilder;

    @Override
    public Mono<TransactionBCDocumentDto> createTransaction(TransactionBCDocumentDto transactionBCDocumentDto) {
        TransactionBCDocumentDto transactionDocument = TransactionBCDocumentDto.builder()
                .state(transactionBCDocumentDto.getState())
                .amountExchangeBC(transactionBCDocumentDto.getAmountExchangeBC())
                .amountExchangePen(transactionBCDocumentDto.getAmountExchangePen())
                .typeOfAccountSeller(transactionBCDocumentDto.getTypeOfAccountSeller())
                .customerIdentityNumberSeller(transactionBCDocumentDto.getCustomerIdentityNumberSeller())
                .ownerBCSeller(transactionBCDocumentDto.getOwnerBCSeller())
                .nroPhoneSeller(transactionBCDocumentDto.getNroPhoneSeller())
                .typeOfAccountBuyer(transactionBCDocumentDto.getTypeOfAccountBuyer())
                .customerIdentityNumberBuyer(transactionBCDocumentDto.getCustomerIdentityNumberBuyer())
                .ownerBCBuyer(transactionBCDocumentDto.getOwnerBCBuyer())
                .nroPhoneBuyer(transactionBCDocumentDto.getNroPhoneBuyer())
                .fechaTransaction(transactionBCDocumentDto.getFechaTransaction())
                .build();

        return clientBuilder
                .build()
                .post()
                .uri("http://localhost:8092/api/transaction-bc")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(transactionDocument)
                .retrieve()
                .bodyToMono(TransactionBCDocumentDto.class);
    }
}
