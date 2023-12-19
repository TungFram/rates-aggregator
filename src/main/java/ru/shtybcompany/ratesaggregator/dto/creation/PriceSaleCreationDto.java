package ru.shtybcompany.ratesaggregator.dto.creation;

import lombok.Data;

import java.util.UUID;

@Data
public class PriceSaleCreationDto implements CreationDto {
    UUID idOfPrice;
    Long sale;
}
