package ru.shtybcompany.ratesaggregator.dto.info.min;

import lombok.Data;
import ru.shtybcompany.ratesaggregator.dto.info.InfoDto;

@Data
public class TariffMinInfoDto implements InfoDto {

    String id;

    String title;
    String url;

}
