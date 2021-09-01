package com.springboot.exchange.msbootcoin.documents;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Document(collection = "exchange")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeDocument {

    @Id
    private String id;

    @Field(name = "state")
    private String state;

    @Field(name = "typeOfAccountSeller")
    private String typeOfAccountSeller;

    @Field(name = "customerIdentityNumberSeller")
    @Indexed(unique = true)
    private String customerIdentityNumberSeller;

    @Indexed(unique = true)
    @Field(name = "ownerBCSeller")
    private String ownerBCSeller;

    @Indexed(unique = true)
    @Field(name = "nroPhoneSeller")
    private String nroPhoneSeller;

    private Double amountBitCoinSeller;

    private Double amountPenSeller;

    @Field(name = "customerIdentityNumberBuyer")
    @Indexed(unique = true)
    private String customerIdentityNumberBuyer;

    @Indexed(unique = true)
    @Field(name = "ownerBCBuyer")
    private String ownerBCBuyer;

    @Indexed(unique = true)
    @Field(name = "nroPhoneBuyer")
    private String nroPhoneBuyer;

    private Double amountBitCoinBuyer;

    private Double amountPenBuyer;

    @Field(name = "typeOfAccountBuyer")
    private String typeOfAccountBuyer;

    private Date fechaExchange;
}
