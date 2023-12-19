package ru.shtybcompany.ratesaggregator.dto.info.max;

import lombok.Data;
import ru.shtybcompany.ratesaggregator.dto.info.InfoDto;
import ru.shtybcompany.ratesaggregator.dto.info.min.TariffPriceMinInfoDto;

@Data
public class PriceSaleMaxInfoDto implements InfoDto {

    String id;
    String creationDateTime;

    long sale;
    TariffPriceMinInfoDto price;
}
