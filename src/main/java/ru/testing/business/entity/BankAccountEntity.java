package ru.testing.business.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "bank_account")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class BankAccountEntity {

    @Id
    String numberAccount;

    Currency currency;

    BigDecimal balance;

    public static String generationNumberAccount() {
        return UUID.randomUUID().toString().substring(0, 23).replaceAll("-", "");
    }

}
