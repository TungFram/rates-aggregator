package ru.shtybcompany.ratesaggregator.mappers.read.max;

import org.mapstruct.*;
import org.springframework.stereotype.Component;
import ru.shtybcompany.ratesaggregator.dto.info.max.TariffMaxInfoDto;
import ru.shtybcompany.ratesaggregator.enities.TariffEntity;
import ru.shtybcompany.ratesaggregator.mappers.read.EntityToInfoDtoMapper;

@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        collectionMappingStrategy = CollectionMappingStrategy.TARGET_IMMUTABLE,
        uses = {
//            AbstractTariffPriceEntityToMaxInfoDtoMapper.class,
        })
public abstract class AbstractTariffEntityToMaxInfoDtoMapper implements EntityToInfoDtoMapper<TariffEntity, TariffMaxInfoDto> {

    @Override
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "entity.tariffID")
    // Implicit LocalDateTime to String conversion: https://mapstruct.org/documentation/stable/reference/html/#implicit-type-conversions
    @Mapping(target = "creationDateTime", source = "entity.createdAt", dateFormat = "dd.MM.yyyy HH:mm:ss")

    @Mapping(target = "title",          source = "entity.title")
    @Mapping(target = "description",    source = "entity.description")
    @Mapping(target = "url",            source = "entity.url")
    @Mapping(target = "sms",            source = "entity.sms")
    @Mapping(target = "gbs",            source = "entity.gbs")
    @Mapping(target = "minutes",        source = "entity.minutes")
    @Mapping(target = "isArchived",     source = "entity.isArchived")
//    @Mapping(target = "actualPrice",    source = "entity.prices",
//            qualifiedByName = { "TariffPriceToMaxInfoDtoMapper", "PricesToCurrentPrice" })
    public abstract TariffMaxInfoDto entityToDto(TariffEntity entity);

    @Override
    public abstract Iterable<TariffMaxInfoDto> entityToDto(Iterable<TariffEntity> entity);
}
