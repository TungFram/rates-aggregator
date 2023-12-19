package ru.shtybcompany.ratesaggregator.mappers.read.max;

import org.mapstruct.*;
import org.springframework.stereotype.Component;
import ru.shtybcompany.ratesaggregator.dto.info.max.PriceSaleMaxInfoDto;
import ru.shtybcompany.ratesaggregator.enities.PriceSaleEntity;
import ru.shtybcompany.ratesaggregator.mappers.read.EntityToInfoDtoMapper;
import ru.shtybcompany.ratesaggregator.mappers.read.min.AbstractTariffPriceEntityToMinInfoDtoMapper;

@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        collectionMappingStrategy = CollectionMappingStrategy.TARGET_IMMUTABLE,
        uses = {
                AbstractTariffPriceEntityToMinInfoDtoMapper.class,
        })
public abstract class AbstractPriceSaleEntityToMaxInfoDtoMapper
    implements EntityToInfoDtoMapper<PriceSaleEntity, PriceSaleMaxInfoDto> {
    @Override
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "entity.pricingSaleID")
    // Implicit LocalDateTime to String conversion: https://mapstruct.org/documentation/stable/reference/html/#implicit-type-conversions
    @Mapping(target = "creationDateTime", source = "entity.createdAt", dateFormat = "dd.MM.yyyy HH:mm:ss")

    @Mapping(target = "sale",   source = "entity.sale")
    @Mapping(target = "price",  source = "entity.price")
    public abstract PriceSaleMaxInfoDto entityToDto(PriceSaleEntity entity);

    @Override
    public abstract Iterable<PriceSaleMaxInfoDto> entityToDto(Iterable<PriceSaleEntity> entity);
}
