package ru.testing.web.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BankAccountGetDto extends BankAccountCreateDto {
    String numberAccount;
}
