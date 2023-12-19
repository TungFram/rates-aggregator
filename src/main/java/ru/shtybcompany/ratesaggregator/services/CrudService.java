package ru.shtybcompany.ratesaggregator.services;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import ru.shtybcompany.ratesaggregator.dto.creation.CreationDto;
import ru.shtybcompany.ratesaggregator.dto.info.InfoDto;
import ru.shtybcompany.ratesaggregator.enities.DomainEntity;

import java.util.Optional;

public interface CrudService<
        E extends DomainEntity,
        CDTO extends CreationDto,
        IDTO extends InfoDto,
        ID> {

    // Create.
    E create(CDTO creationDto) throws EntityExistsException;
    Iterable<E> create(Iterable<CDTO> creationDtos) throws EntityExistsException;

    // Read.
    // Get.
    E getById(ID id) throws EntityNotFoundException;
    Iterable<E> getById(Iterable<ID> ids) throws EntityNotFoundException;
    Iterable<E> getAll();

    // Find.
    Optional<E> findById(ID id);
    Iterable<Optional<E>> findById(Iterable<ID> ids);

    // Check.
    Boolean existsById(ID id);
    Iterable<Boolean> existsById(Iterable<ID> ids);


    // Update.
    E update(E entity) throws EntityNotFoundException;

    Iterable<E> update(Iterable<E> entities) throws EntityNotFoundException;

    // Delete.
    void deleteById(ID id);
    void deleteById(Iterable<ID> ids);
    void deleteAll();



}
