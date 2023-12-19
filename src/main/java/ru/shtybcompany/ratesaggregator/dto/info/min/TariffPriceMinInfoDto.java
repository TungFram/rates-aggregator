package ru.shtybcompany.ratesaggregator.dto.info.min;

import lombok.Data;
import ru.shtybcompany.ratesaggregator.dto.info.InfoDto;

@Data
public class TariffPriceMinInfoDto implements InfoDto {

    String id;

    Double number;
    String currency;
}
