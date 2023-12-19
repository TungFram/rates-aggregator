package ru.shtybcompany.ratesaggregator.dto.info.max;

import lombok.Data;
import ru.shtybcompany.ratesaggregator.dto.info.InfoDto;

@Data
public class TariffMaxInfoDto implements InfoDto {

    String id;
    String creationDateTime;

    String title;
    String description;
    String url;

    int sms;
    int gbs;
    int minutes;

    Boolean isArchived;

    TariffPriceMaxInfoDto actualPrice;

}
