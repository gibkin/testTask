package ru.testing.business.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.testing.business.dao.BankAccountRepository;
import ru.testing.business.entity.BankAccountEntity;
import ru.testing.business.service.BankAccountService;
import ru.testing.web.dto.BankAccountCreateDto;
import ru.testing.web.dto.BankAccountGetDto;
import ru.testing.web.dto.DispatchMoneyDto;
import ru.testing.web.error.BankAccountException;
import ru.testing.web.error.ResourceNotFoundException;
import ru.testing.web.mapper.BankAccountMapper;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BankAccountServiceImpl implements BankAccountService {
    private static final String YOUR_ACCOUNT_HAS_INSUFFICIENT_FUNDS = "Your account has insufficient funds";
    private final BankAccountRepository repository;
    private final BankAccountMapper bankAccountMapper;

    @Transactional
    @Override
    public BankAccountGetDto create(BankAccountCreateDto bankAccountDto) {
        return bankAccountMapper.toDto(repository.saveAndFlush(bankAccountMapper.toEntity(bankAccountDto)));
    }

    @Override
    public List<BankAccountGetDto> getAll() {
        return bankAccountMapper.toDtos(repository.findAll());
    }

    private BankAccountEntity getById(String id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @Transactional
    @Override
    public void dispatchMoney(DispatchMoneyDto dispatchMoneyDto) {
        BankAccountEntity sourceBankAccount = getById(dispatchMoneyDto.getSourceId());
        BigDecimal money = dispatchMoneyDto.getMoney();
        BigDecimal sourceBalance = sourceBankAccount.getBalance();
        if (sourceBalance.compareTo(money) < 0) {
            log.error(YOUR_ACCOUNT_HAS_INSUFFICIENT_FUNDS);
            throw new BankAccountException(YOUR_ACCOUNT_HAS_INSUFFICIENT_FUNDS);
        }
        BigDecimal newBalanceSource = sourceBalance.subtract(money);
        sourceBankAccount.setBalance(newBalanceSource);
        BankAccountEntity targetBankAccount = getById(dispatchMoneyDto.getTargetId());
        BigDecimal newBalanceTarget = targetBankAccount.getBalance().add(money);
        targetBankAccount.setBalance(newBalanceTarget);
    }
}
