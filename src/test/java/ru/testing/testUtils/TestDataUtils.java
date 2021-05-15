package ru.testing.testUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.testing.business.dao.BankAccountRepository;
import ru.testing.business.entity.BankAccountEntity;
import ru.testing.web.dto.BankAccountCreateDto;
import ru.testing.web.dto.DispatchMoneyDto;
import ru.testing.web.mapper.BankAccountMapper;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;

@Component
public class TestDataUtils {
    public static final BigDecimal BALANCE1 = BigDecimal.valueOf(145.2422);
    public static final BigDecimal BALANCE2 = BigDecimal.valueOf(21425.2342);
    public static final BigDecimal BALANCE3 = BigDecimal.valueOf(1.43);
    public static final Currency CURRENCY1 = Currency.getInstance("USD");
    public static final Currency CURRENCY2 = Currency.getInstance("RUR");
    public static final Currency CURRENCY3 = Currency.getInstance("JPY");


    @Autowired
    private BankAccountRepository repository;

    @Autowired
    private BankAccountMapper mapper;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public BankAccountEntity generateBankAccountEntity(BigDecimal balance, Currency currency) {
        return repository.save(mapper.toEntity(generationCreateDto(balance, currency)));
    }

    public BankAccountCreateDto generationCreateDto(BigDecimal balance, Currency currency) {
        BankAccountCreateDto dto = new BankAccountCreateDto();
        dto.setBalance(balance);
        dto.setCurrency(currency);
        return dto;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<BankAccountEntity> generateBankAccountEntities() {
        BankAccountEntity dto1 = generateBankAccountEntity(BALANCE1, CURRENCY1);
        BankAccountEntity dto2 = generateBankAccountEntity(BALANCE2, CURRENCY2);
        BankAccountEntity dto3 = generateBankAccountEntity(BALANCE3, CURRENCY3);
        return Arrays.asList(dto1, dto2, dto3);
    }

    public DispatchMoneyDto generationDispatchMoneyDto(String sourceId, String targetId, BigDecimal money){
        DispatchMoneyDto dto = new DispatchMoneyDto();
        dto.setSourceId(sourceId);
        dto.setTargetId(targetId);
        dto.setMoney(money);
        return dto;
    }
}
