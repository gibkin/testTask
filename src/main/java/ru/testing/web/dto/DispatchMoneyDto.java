package ru.testing.web.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DispatchMoneyDto {
    @NotBlank
    String sourceId;
    @NotBlank
    String targetId;
    @NotNull
    BigDecimal money;
}
