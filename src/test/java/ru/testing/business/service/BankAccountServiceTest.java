package ru.testing.business.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.testing.business.entity.BankAccountEntity;
import ru.testing.testUtils.BaseTest;
import ru.testing.testUtils.TestDataUtils;
import ru.testing.web.dto.BankAccountCreateDto;
import ru.testing.web.dto.BankAccountGetDto;
import ru.testing.web.dto.DispatchMoneyDto;
import ru.testing.web.error.BankAccountException;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.testing.testUtils.TestDataUtils.*;

@Transactional
public class BankAccountServiceTest extends BaseTest {

    @Autowired
    private BankAccountService service;

    @Autowired
    private TestDataUtils testDataUtils;

    @Test
    public void create() {
        BankAccountCreateDto dto = testDataUtils.generationCreateDto(BALANCE1, CURRENCY1);
        BankAccountGetDto bankAccountGetDto = service.create(dto);
        assertNotNull(bankAccountGetDto);
    }

    @Test
    public void getAll() {
        testDataUtils.generateBankAccountEntities();
        List<BankAccountGetDto> bankAccounts = service.getAll();
        assertNotNull(bankAccounts);
        assertFalse(bankAccounts.isEmpty());
    }

    @Test
    public void dispatchMoneyOK() {
        BankAccountEntity sourceBankAccount = testDataUtils.generateBankAccountEntity(BALANCE2, CURRENCY1);
        BankAccountEntity targetBankAccount = testDataUtils.generateBankAccountEntity(BALANCE3, CURRENCY2);
        BigDecimal money = BigDecimal.valueOf(21425);
        DispatchMoneyDto dispatchMoneyDto = testDataUtils.generationDispatchMoneyDto(sourceBankAccount.getNumberAccount(),
                targetBankAccount.getNumberAccount(), money);
        service.dispatchMoney(dispatchMoneyDto);
        List<BankAccountGetDto> bankAccounts = service.getAll();
        assertNotNull(bankAccounts);
        assertEquals(2, bankAccounts.size());
        assertEquals(BigDecimal.valueOf(0.23), bankAccounts.get(0).getBalance());
        assertEquals(BigDecimal.valueOf(21426.43), bankAccounts.get(1).getBalance());
    }

    @Test
    public void dispatchMoneyError() {
        BankAccountEntity sourceBankAccount = testDataUtils.generateBankAccountEntity(BALANCE2, CURRENCY1);
        BankAccountEntity targetBankAccount = testDataUtils.generateBankAccountEntity(BALANCE3, CURRENCY2);
        BigDecimal money = BigDecimal.valueOf(30000);
        DispatchMoneyDto dispatchMoneyDto = testDataUtils.generationDispatchMoneyDto(sourceBankAccount.getNumberAccount(),
                targetBankAccount.getNumberAccount(), money);
        try {
            service.dispatchMoney(dispatchMoneyDto);
        } catch (BankAccountException ex) {
            assertEquals("Your account has insufficient funds", ex.getMessage());
            return;
        }
        fail();
    }
}