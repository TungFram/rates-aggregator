package ru.shtybcompany.ratesaggregator.mappers.write;

public interface DtoToEntityMapper<DTO, E> {

    E dtoToEntity(DTO dto) throws IllegalArgumentException;
    Iterable<E> dtoToEntity(Iterable<DTO> dtos) throws IllegalArgumentException;
}
