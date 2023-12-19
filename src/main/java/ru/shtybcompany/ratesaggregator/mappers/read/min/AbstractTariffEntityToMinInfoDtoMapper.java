package ru.shtybcompany.ratesaggregator.mappers.read.min;

import org.mapstruct.*;
import org.springframework.stereotype.Component;
import ru.shtybcompany.ratesaggregator.dto.info.min.TariffMinInfoDto;
import ru.shtybcompany.ratesaggregator.enities.TariffEntity;
import ru.shtybcompany.ratesaggregator.mappers.read.EntityToInfoDtoMapper;

@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        collectionMappingStrategy = CollectionMappingStrategy.TARGET_IMMUTABLE,
        uses = {

        })
public abstract class AbstractTariffEntityToMinInfoDtoMapper
        implements EntityToInfoDtoMapper<TariffEntity, TariffMinInfoDto> {
    @Override
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id",     source = "entity.tariffID")
    @Mapping(target = "title",  source = "entity.title")
    @Mapping(target = "url",    source = "entity.url")
    public abstract TariffMinInfoDto entityToDto(TariffEntity entity);

    @Override
    public abstract Iterable<TariffMinInfoDto> entityToDto(Iterable<TariffEntity> entity);
}
