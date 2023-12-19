package ru.shtybcompany.ratesaggregator.dto.creation;

import lombok.Data;

import java.util.UUID;

@Data
public class TariffPriceCreateDto implements CreationDto {
    UUID idOfTariff;
    String currency;
    Double price;
}
