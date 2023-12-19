package ru.shtybcompany.ratesaggregator.dto.info.min;

import lombok.Data;
import ru.shtybcompany.ratesaggregator.dto.info.InfoDto;

@Data
public class PriceSaleMinInfoDto implements InfoDto {

    String id;
    long sale;
}
