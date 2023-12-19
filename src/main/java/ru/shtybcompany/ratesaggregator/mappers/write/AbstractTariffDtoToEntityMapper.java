package ru.shtybcompany.ratesaggregator.mappers.write;

import org.mapstruct.*;
import org.springframework.stereotype.Component;
import ru.shtybcompany.ratesaggregator.dto.creation.TariffCreationDto;
import ru.shtybcompany.ratesaggregator.enities.TariffEntity;


@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        collectionMappingStrategy = CollectionMappingStrategy.TARGET_IMMUTABLE,
        uses = {

        })
public abstract class AbstractTariffDtoToEntityMapper
    implements DtoToEntityMapper<TariffCreationDto, TariffEntity> {
    @Override
    @BeanMapping(ignoreByDefault = true)
    public TariffEntity dtoToEntity(TariffCreationDto tariffCreationDto) throws IllegalArgumentException {
        // TODO: validation layer in mappers

        TariffEntity tariff = TariffEntity.createBuilder()
                .withTitle(tariffCreationDto.getTitle())
                .withDescription(tariffCreationDto.getDescription())
                .withUrl(tariffCreationDto.getUrl())
                .withSms(tariffCreationDto.getSms())
                .withMinutes(tariffCreationDto.getMinutes())
                .withGbs(tariffCreationDto.getGbs())
                .withIsArchived(tariffCreationDto.isArchived())
                .build();
        return tariff;
    }

    @Override
    public abstract Iterable<TariffEntity> dtoToEntity(Iterable<TariffCreationDto> tariffCreationDtos) throws IllegalArgumentException;

}
