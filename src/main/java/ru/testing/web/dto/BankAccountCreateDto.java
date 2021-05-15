package ru.testing.web.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Currency;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class BankAccountCreateDto {
    @NotNull
    Currency currency;
    @NotNull
    BigDecimal balance;
}
