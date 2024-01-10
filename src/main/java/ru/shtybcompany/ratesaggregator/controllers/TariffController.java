package ru.shtybcompany.ratesaggregator.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shtybcompany.ratesaggregator.dto.creation.TariffCreationDto;
import ru.shtybcompany.ratesaggregator.dto.info.max.TariffMaxInfoDto;
import ru.shtybcompany.ratesaggregator.enities.TariffEntity;
import ru.shtybcompany.ratesaggregator.mappers.read.max.AbstractTariffEntityToMaxInfoDtoMapper;
import ru.shtybcompany.ratesaggregator.mappers.read.max.AbstractTariffEntityToMaxInfoDtoMapperImpl;
import ru.shtybcompany.ratesaggregator.services.MainService;
import ru.shtybcompany.ratesaggregator.services.TariffCrudService;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE,
        value = "/${api.prefix}/${api.currentVersion}" +
                "/")
public class TariffController implements CrudController<TariffMaxInfoDto, TariffCreationDto, UUID> {

    private final MainService mainService;
    private final TariffCrudService tariffCrudService;
    private final AbstractTariffEntityToMaxInfoDtoMapper tariffEntityToMaxInfoDtoMapper;

    @Autowired
    public TariffController(
            MainService mainService,
            TariffCrudService tariffCrudService,
            AbstractTariffEntityToMaxInfoDtoMapperImpl tariffEntityToMaxInfoDtoMapper
    ) {
        this.mainService = mainService;
        this.tariffCrudService = tariffCrudService;
        this.tariffEntityToMaxInfoDtoMapper = tariffEntityToMaxInfoDtoMapper;
    }

    @GetMapping("/warm")
    public ResponseEntity<Iterable<TariffEntity>> warmDB() throws IOException {
        Iterable<TariffEntity> tariffs = this.mainService.getAndConvertTariffs();
        return ResponseEntity.ok(tariffs);
    }


    @Override
    @PostMapping("/")
    public ResponseEntity<TariffMaxInfoDto> createByDto(@RequestBody TariffCreationDto dtoInfoToSave) {
        TariffEntity createdTariff = this.tariffCrudService.create(dtoInfoToSave);
        TariffMaxInfoDto tariffInfo = this.tariffEntityToMaxInfoDtoMapper.entityToDto(createdTariff);
        return ResponseEntity.ok(tariffInfo);
    }

    @Override
    @GetMapping("/")
    public ResponseEntity<Iterable<TariffMaxInfoDto>> getAll() {
        Iterable<TariffEntity> tariffs = this.tariffCrudService.getAll();
        Iterable<TariffMaxInfoDto> tariffInfos = this.tariffEntityToMaxInfoDtoMapper.entityToDto(tariffs);
        return ResponseEntity.ok(tariffInfos);
    }

    @Override
    @GetMapping("/{tariffId}")
    public ResponseEntity<TariffMaxInfoDto> getById(@PathVariable UUID tariffId) {
        TariffEntity foundEntity = this.tariffCrudService.getById(tariffId);
        TariffMaxInfoDto entityInfo = this.tariffEntityToMaxInfoDtoMapper.entityToDto(foundEntity);
        return ResponseEntity.ok(entityInfo);
    }

    @Override
    @PutMapping("/{tariffId}")
    public ResponseEntity<TariffMaxInfoDto> updateByDto(
            @PathVariable UUID tariffId,
            @RequestBody TariffCreationDto dtoInfoToUpdate) {
        final Optional<TariffEntity> tariffEntityOptional = this.tariffCrudService.findById(tariffId);
        if (tariffEntityOptional.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        final TariffEntity updatedTariff = this.tariffCrudService.update(tariffEntityOptional.get());

        // No content: https://stackoverflow.com/questions/2342579/http-status-code-for-update-and-delete
        return ResponseEntity.noContent().build();
    }

    @Override
    @DeleteMapping("/{tariffId}")
    public ResponseEntity<Integer> deleteById(@PathVariable UUID tariffId) {
        this.tariffCrudService.deleteById(tariffId);
        return ResponseEntity.noContent().build();
    }

    @Override
    @DeleteMapping("/")
    public ResponseEntity<Void> deleteAll() {
        this.tariffCrudService.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
