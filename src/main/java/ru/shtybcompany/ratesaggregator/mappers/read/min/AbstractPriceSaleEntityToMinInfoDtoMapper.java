package ru.shtybcompany.ratesaggregator.mappers.read.min;

import org.mapstruct.*;
import org.springframework.stereotype.Component;
import ru.shtybcompany.ratesaggregator.dto.info.min.PriceSaleMinInfoDto;
import ru.shtybcompany.ratesaggregator.enities.PriceSaleEntity;
import ru.shtybcompany.ratesaggregator.mappers.read.EntityToInfoDtoMapper;


@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        collectionMappingStrategy = CollectionMappingStrategy.TARGET_IMMUTABLE,
        uses = {

        })
public abstract class AbstractPriceSaleEntityToMinInfoDtoMapper
    implements EntityToInfoDtoMapper<PriceSaleEntity, PriceSaleMinInfoDto> {


    @Override
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id",     source = "entity.pricingSaleID")
    @Mapping(target = "sale",   source = "entity.sale")
    public abstract PriceSaleMinInfoDto entityToDto(PriceSaleEntity entity);

    @Override
    public abstract Iterable<PriceSaleMinInfoDto> entityToDto(Iterable<PriceSaleEntity> entity);
}
