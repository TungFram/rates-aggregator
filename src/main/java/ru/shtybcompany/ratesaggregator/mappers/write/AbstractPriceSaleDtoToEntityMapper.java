package ru.shtybcompany.ratesaggregator.mappers.write;

import org.mapstruct.*;
import org.springframework.stereotype.Component;
import ru.shtybcompany.ratesaggregator.dto.creation.PriceSaleCreationDto;
import ru.shtybcompany.ratesaggregator.enities.PriceSaleEntity;

import java.util.Collection;

@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        collectionMappingStrategy = CollectionMappingStrategy.TARGET_IMMUTABLE,
        uses = {

        })
public abstract class AbstractPriceSaleDtoToEntityMapper
    implements DtoToEntityMapper<PriceSaleCreationDto, PriceSaleEntity> {

    @Override
    @BeanMapping(ignoreByDefault = true)
    public PriceSaleEntity dtoToEntity(PriceSaleCreationDto priceSaleCreationDto) throws IllegalArgumentException {
        Long sale = priceSaleCreationDto.getSale();
        if (sale < 0) {
            throw new IllegalArgumentException("Sale can't be negative: " + sale);
        }

        PriceSaleEntity priceSaleEntity = PriceSaleEntity.createBuilder()
                .withSale(sale)
                .build();

        return priceSaleEntity;
    }

    @Override
    public abstract Collection<PriceSaleEntity> dtoToEntity(Iterable<PriceSaleCreationDto> priceSaleCreationDtos) throws IllegalArgumentException;



}