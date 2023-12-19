package ru.shtybcompany.ratesaggregator.mappers.read.max;

import org.mapstruct.*;
import org.springframework.stereotype.Component;
import ru.shtybcompany.ratesaggregator.dto.info.max.TariffPriceMaxInfoDto;
import ru.shtybcompany.ratesaggregator.enities.TariffPriceEntity;
import ru.shtybcompany.ratesaggregator.mappers.read.EntityToInfoDtoMapper;
import ru.shtybcompany.ratesaggregator.mappers.read.min.AbstractTariffEntityToMinInfoDtoMapper;
import ru.shtybcompany.ratesaggregator.mappers.read.min.AbstractTariffPriceEntityToMinInfoDtoMapper;

import java.util.List;


@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        collectionMappingStrategy = CollectionMappingStrategy.TARGET_IMMUTABLE,
        uses = {
                AbstractTariffPriceEntityToMinInfoDtoMapper.class,
                AbstractTariffEntityToMinInfoDtoMapper.class,
                AbstractPriceSaleEntityToMaxInfoDtoMapper.class,
        })
@Named("TariffPriceToMaxInfoDtoMapper")
public abstract class AbstractTariffPriceEntityToMaxInfoDtoMapper
        implements EntityToInfoDtoMapper<TariffPriceEntity, TariffPriceMaxInfoDto> {
    @Override
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "entity.priceID")
    // Implicit LocalDateTime to String conversion: https://mapstruct.org/documentation/stable/reference/html/#implicit-type-conversions
    @Mapping(target = "creationDateTime", source = "entity.createdAt", dateFormat = "dd.MM.yyyy HH:mm:ss")

    @Mapping(target = "previousPrice", source = "entity.previousPrice")
    @Mapping(target = "tariff",        source = "entity.tariff")
    @Mapping(target = "sale",          source = "entity.sale")
    public abstract TariffPriceMaxInfoDto entityToDto(TariffPriceEntity entity);

    @Override
    public abstract Iterable<TariffPriceMaxInfoDto> entityToDto(Iterable<TariffPriceEntity> entity);

    @AfterMapping
    public void mapMoneyPriceToDoubleAndString(
            TariffPriceEntity entity,
            @MappingTarget TariffPriceMaxInfoDto tariffDto) {
        double price = entity.getMoney().getNumber().doubleValueExact();
        String currency = entity.getMoney().getCurrency().getCurrencyCode();
        tariffDto.setNumber(price);
        tariffDto.setCurrency(currency);
    }

    // For AbstractTariffEntityToMaxInfoDtoMapper#actualPrice.
    @Named("PricesToCurrentPrice")
    public TariffPriceMaxInfoDto listOfPricesToOneCurrentPrice(List<TariffPriceEntity> prices) {
        TariffPriceEntity currTariffPrice = prices
                .stream()
                .filter(
                        price -> {
                            return price.getPreviousPrice() == null;
                        })
                .toList()
                .get(0);

        TariffPriceMaxInfoDto tariffPriceMaxInfoDto = this.entityToDto(currTariffPrice);
        return tariffPriceMaxInfoDto;
    }
}
