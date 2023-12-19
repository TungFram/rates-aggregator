package ru.shtybcompany.ratesaggregator.dto.info.max;

import lombok.Data;
import ru.shtybcompany.ratesaggregator.dto.info.InfoDto;
import ru.shtybcompany.ratesaggregator.dto.info.min.TariffMinInfoDto;
import ru.shtybcompany.ratesaggregator.dto.info.min.TariffPriceMinInfoDto;

@Data
public class TariffPriceMaxInfoDto implements InfoDto {

    String id;
    String creationDateTime;

    Double number;
    String currency;

    TariffPriceMinInfoDto previousPrice;

    TariffMinInfoDto tariff;
    PriceSaleMaxInfoDto sale;
}
