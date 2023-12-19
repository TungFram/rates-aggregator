package ru.shtybcompany.ratesaggregator.dto.creation;

import lombok.Data;

@Data
public class TariffCreationDto implements CreationDto {

    String title;
    String description;
    String url;

    int sms;
    int gbs;
    int minutes;

    boolean isArchived;

}
