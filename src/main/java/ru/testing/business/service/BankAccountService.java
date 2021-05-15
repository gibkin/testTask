package ru.testing.business.service;

import ru.testing.web.dto.BankAccountCreateDto;
import ru.testing.web.dto.BankAccountGetDto;
import ru.testing.web.dto.DispatchMoneyDto;

import java.util.List;

public interface BankAccountService {
    BankAccountGetDto create(BankAccountCreateDto bankAccountDto);

    List<BankAccountGetDto> getAll();

    void dispatchMoney(DispatchMoneyDto dispatchMoneyDto);
}
