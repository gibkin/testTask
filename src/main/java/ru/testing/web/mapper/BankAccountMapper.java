package ru.testing.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.testing.web.dto.BankAccountCreateDto;
import ru.testing.web.dto.BankAccountGetDto;
import ru.testing.business.entity.BankAccountEntity;

import java.math.RoundingMode;
import java.util.List;

@Mapper(imports = {RoundingMode.class})
public interface BankAccountMapper {

    @Mapping(target = "numberAccount", expression = "java(BankAccountEntity.generationNumberAccount())")
    @Mapping(target = "balance",
            expression = "java(bankAccountDto.getBalance().setScale(bankAccountDto.getCurrency().getDefaultFractionDigits()," +
                    " RoundingMode.HALF_UP))")
    BankAccountEntity toEntity(BankAccountCreateDto bankAccountDto);

    BankAccountGetDto toDto(BankAccountEntity bankAccountEntity);

    List<BankAccountGetDto> toDtos(List<BankAccountEntity> bankAccountDtos);

}