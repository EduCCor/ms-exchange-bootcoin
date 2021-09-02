package com.springboot.exchange.msbootcoin.documents.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionBCDocumentDto {

    @Id
    private String id;

    private String state;

    private Double amountExchangeBC;

    private Double amountExchangePen;

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


    @Field(name = "typeOfAccountBuyer")
    private String typeOfAccountBuyer;

    @Field(name = "customerIdentityNumberBuyer")
    @Indexed(unique = true)
    private String customerIdentityNumberBuyer;

    @Indexed(unique = true)
    @Field(name = "ownerBCBuyer")
    private String ownerBCBuyer;

    @Indexed(unique = true)
    @Field(name = "nroPhoneBuyer")
    private String nroPhoneBuyer;

    private Date fechaTransaction;
}
