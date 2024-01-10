package ru.shtybcompany.ratesaggregator.controllers;

import org.springframework.http.ResponseEntity;
import ru.shtybcompany.ratesaggregator.dto.creation.CreationDto;
import ru.shtybcompany.ratesaggregator.dto.info.InfoDto;

public interface CrudController<IDTO extends InfoDto, CDTO extends CreationDto, ID> {

    // Create.
    public ResponseEntity<IDTO> createByDto(CDTO dtoInfoToSave);

    // Read.
    public ResponseEntity<Iterable<IDTO>> getAll();
    public ResponseEntity<IDTO> getById(ID id);

    // Update.
    public ResponseEntity<IDTO> updateByDto(ID tariffId, CDTO dtoInfoToUpdate);

    // Delete.
    public ResponseEntity<Integer> deleteById(ID id);
    public ResponseEntity<Void> deleteAll();
}