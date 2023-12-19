package ru.shtybcompany.ratesaggregator.mappers.write;


import org.javamoney.moneta.Money;
import org.mapstruct.*;
import org.springframework.stereotype.Component;
import ru.shtybcompany.ratesaggregator.dto.creation.TariffPriceCreateDto;
import ru.shtybcompany.ratesaggregator.enities.TariffPriceEntity;
import ru.shtybcompany.ratesaggregator.mappers.read.max.AbstractPriceSaleEntityToMaxInfoDtoMapper;
import ru.shtybcompany.ratesaggregator.mappers.read.min.AbstractTariffEntityToMinInfoDtoMapper;
import ru.shtybcompany.ratesaggregator.mappers.read.min.AbstractTariffPriceEntityToMinInfoDtoMapper;

import javax.money.CurrencyUnit;
import javax.money.Monetary;

@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        collectionMappingStrategy = CollectionMappingStrategy.TARGET_IMMUTABLE,
        uses = {
                AbstractTariffPriceEntityToMinInfoDtoMapper.class,
                AbstractTariffEntityToMinInfoDtoMapper.class,
                AbstractPriceSaleEntityToMaxInfoDtoMapper.class,
        })
public abstract class AbstractTariffPriceDtoToEntityMapper
        implements DtoToEntityMapper<TariffPriceCreateDto, TariffPriceEntity> {

    @Override
    @BeanMapping(ignoreByDefault = true)
    public TariffPriceEntity dtoToEntity(TariffPriceCreateDto tariffPriceCreateDto) throws IllegalArgumentException {
        CurrencyUnit currencyUnit = Monetary.getCurrency(tariffPriceCreateDto.getCurrency());
        Double price = tariffPriceCreateDto.getPrice();
        Money money = Money.of(price, currencyUnit);

        TariffPriceEntity tariffPrice = TariffPriceEntity.createBuilder()
                .withMoney(money)
                .build();

        return tariffPrice;
    }

    @Override
    public abstract Iterable<TariffPriceEntity> dtoToEntity(Iterable<TariffPriceCreateDto> tariffPriceCreateDtos) throws IllegalArgumentException;

}
