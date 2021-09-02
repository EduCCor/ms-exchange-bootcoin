package com.springboot.exchange.msbootcoin.services.Impl;

import com.springboot.exchange.msbootcoin.documents.ExchangeDocument;
import com.springboot.exchange.msbootcoin.documents.dto.TransactionBCDocumentDto;
import com.springboot.exchange.msbootcoin.repositories.ExchangeBCRepository;
import com.springboot.exchange.msbootcoin.services.IBootCoinDtoServices;
import com.springboot.exchange.msbootcoin.services.IExchangeBCService;
import com.springboot.exchange.msbootcoin.services.ITransactionBCService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

@Service
public class ExchangeBCServiceImpl implements IExchangeBCService {

    private static final Logger log = LoggerFactory.getLogger(ExchangeBCServiceImpl.class);

    private static final double COMPRA = 5.5;

    private static final double VENTA = 6.1;

    @Autowired
    private ExchangeBCRepository exchangeBCRepository;

    @Autowired
    private IBootCoinDtoServices bootCoinDtoServices;

    @Autowired
    private ITransactionBCService transactionBCService;

    @Override
    public Mono<ExchangeDocument> create(ExchangeDocument o) {
        return exchangeBCRepository.save(o);
    }

    @Override
    public Flux<ExchangeDocument> findAll() {
        return exchangeBCRepository.findAll();
    }

    @Override
    public Mono<ExchangeDocument> findById(String s) {
        return exchangeBCRepository.findById(s);
    }

    @Override
    public Mono<ExchangeDocument> update(ExchangeDocument o) {
        return exchangeBCRepository.findById(o.getId()).flatMap(c -> {
            return exchangeBCRepository.save(c);
        });
    }

    @Override
    public Mono<Void> delete(ExchangeDocument o) {
        return exchangeBCRepository.delete(o);
    }

    @Override
    public Mono<ExchangeDocument> createExchange(ExchangeDocument exchangeDocument) {
        return bootCoinDtoServices.findByCustomerIdentityNumber(exchangeDocument.getCustomerIdentityNumberSeller()).flatMap(c -> {
            System.out.println("bc" + c);
            if(c.getId() == null){
                log.info("No se puede hacer un deposito, no existe la cuenta de BootCoin");
                return Mono.just(ExchangeDocument.builder().build());
            }

            exchangeDocument.setTypeOfAccountSeller(c.getTypeOfAccount());
            exchangeDocument.setOwnerBCSeller(c.getOwnerBootCoin());
            exchangeDocument.setNroPhoneSeller(c.getNroPhone());
            exchangeDocument.setAmountPenSeller(exchangeDocument.getAmountBitCoinSeller()*VENTA);

            c.setAmountBitCoin(c.getAmountBitCoin() - exchangeDocument.getAmountBitCoinSeller());

            return bootCoinDtoServices.updateBootCoin(c).flatMap(d -> {
                return exchangeBCRepository.save(exchangeDocument);
            });
        });
    }

    @Override
    public Mono<ExchangeDocument> takeEnchage(String id, ExchangeDocument exchangeDocument) {
        return exchangeBCRepository.findById(id).flatMap(c -> {
            System.out.println(c);

            return bootCoinDtoServices.findByCustomerIdentityNumber(exchangeDocument.getCustomerIdentityNumberBuyer()).flatMap(d -> {
                c.setOwnerBCBuyer(d.getOwnerBootCoin());
                c.setNroPhoneBuyer(d.getNroPhone());
                c.setTypeOfAccountBuyer(d.getTypeOfAccount());
                c.setAmountBitCoinBuyer(c.getAmountBitCoinSeller());
                c.setAmountPenBuyer(c.getAmountPenSeller());
                c.setCustomerIdentityNumberBuyer(d.getCustomerIdentityNumber());
                c.setFechaExchange(new Date());

                d.setAmountBitCoin(d.getAmountBitCoin() + c.getAmountBitCoinSeller());

                return bootCoinDtoServices.updateBootCoin(d).flatMap( e -> {
                    TransactionBCDocumentDto transactionDoc = new TransactionBCDocumentDto();
                    transactionDoc.setState("PENDING");
                    transactionDoc.setAmountExchangeBC(c.getAmountBitCoinSeller());
                    transactionDoc.setAmountExchangePen(c.getAmountPenSeller());
                    transactionDoc.setTypeOfAccountSeller(c.getTypeOfAccountSeller());
                    transactionDoc.setCustomerIdentityNumberSeller(c.getCustomerIdentityNumberSeller());
                    transactionDoc.setOwnerBCSeller(c.getOwnerBCSeller());
                    transactionDoc.setNroPhoneSeller(c.getNroPhoneSeller());
                    transactionDoc.setTypeOfAccountBuyer(c.getTypeOfAccountBuyer());
                    transactionDoc.setCustomerIdentityNumberBuyer(c.getCustomerIdentityNumberBuyer());
                    transactionDoc.setOwnerBCBuyer(c.getOwnerBCBuyer());
                    transactionDoc.setNroPhoneBuyer(c.getNroPhoneBuyer());
                    transactionDoc.setFechaTransaction(new Date());

                    return transactionBCService.createTransaction(transactionDoc).flatMap(f -> {
                        return exchangeBCRepository.save(c);
                    });
                });
            });
        });
    }
}
