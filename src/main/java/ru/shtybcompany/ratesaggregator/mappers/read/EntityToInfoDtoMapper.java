package ru.shtybcompany.ratesaggregator.mappers.read;

public interface EntityToInfoDtoMapper<E, IDTO> {

    IDTO entityToDto(E entity);
    Iterable<IDTO> entityToDto(Iterable<E> entity);
}
