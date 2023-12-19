package ru.shtybcompany.ratesaggregator.mappers.read.min;

import org.mapstruct.*;
import org.springframework.stereotype.Component;
import ru.shtybcompany.ratesaggregator.dto.info.min.TariffPriceMinInfoDto;
import ru.shtybcompany.ratesaggregator.enities.TariffPriceEntity;
import ru.shtybcompany.ratesaggregator.mappers.read.EntityToInfoDtoMapper;

@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        collectionMappingStrategy = CollectionMappingStrategy.TARGET_IMMUTABLE,
        uses = {

        })
public abstract class AbstractTariffPriceEntityToMinInfoDtoMapper
        implements EntityToInfoDtoMapper<TariffPriceEntity, TariffPriceMinInfoDto> {
    @Override
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "entity.priceID")
    public abstract TariffPriceMinInfoDto entityToDto(TariffPriceEntity entity);

    @Override
    public abstract Iterable<TariffPriceMinInfoDto> entityToDto(Iterable<TariffPriceEntity> entity);

    @AfterMapping
    public void mapMoneyPriceToDoubleAndString(
            TariffPriceEntity entity,
            @MappingTarget TariffPriceMinInfoDto tariffDto) {
        double price = entity.getMoney().getNumber().doubleValueExact();
        String currency = entity.getMoney().getCurrency().getCurrencyCode();
        tariffDto.setNumber(price);
        tariffDto.setCurrency(currency);
    }

}
