package ru.shtybcompany.ratesaggregator.services;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.shtybcompany.ratesaggregator.dto.creation.TariffCreationDto;
import ru.shtybcompany.ratesaggregator.dto.info.max.TariffMaxInfoDto;
import ru.shtybcompany.ratesaggregator.enities.TariffEntity;
import ru.shtybcompany.ratesaggregator.mappers.read.max.AbstractTariffEntityToMaxInfoDtoMapper;
import ru.shtybcompany.ratesaggregator.mappers.write.AbstractTariffDtoToEntityMapper;
import ru.shtybcompany.ratesaggregator.repositories.TariffRepository;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
//@Transactional
public class TariffCrudService implements CrudService<TariffEntity, TariffCreationDto, TariffMaxInfoDto, UUID> {

    private final Logger logger = LoggerFactory.getLogger(TariffCrudService.class);

    private final TariffRepository tariffRepository;
    private final AbstractTariffDtoToEntityMapper tariffDtoToEntityMapper;
    private final AbstractTariffEntityToMaxInfoDtoMapper tariffEntityToMaxInfoDtoMapper;


    @Autowired
    public TariffCrudService(
            TariffRepository tariffRepository,
            AbstractTariffDtoToEntityMapper tariffDtoToEntityMapper,
            AbstractTariffEntityToMaxInfoDtoMapper tariffEntityToMaxInfoDtoMapper
) {
        this.tariffRepository = tariffRepository;
        this.tariffDtoToEntityMapper = tariffDtoToEntityMapper;
        this.tariffEntityToMaxInfoDtoMapper = tariffEntityToMaxInfoDtoMapper;
    }


    @Override
    public TariffEntity create(TariffCreationDto creationDto) throws EntityExistsException {
        TariffEntity tariff;
        try {
            tariff = this.tariffDtoToEntityMapper.dtoToEntity(creationDto);
        }
        catch (IllegalArgumentException exception) {
            logger.debug("Warning! Creating tariff canceled: invalid argument. {}", creationDto);
            throw exception;
        }

        tariff = tariff.toBuilder()
                .withCreatedAt(LocalDateTime.now())
                .withUpdatedAt(LocalDateTime.now())
                .build();
        tariff = this.tariffRepository.save(tariff);
        return tariff;
    }

    @Override
    public Iterable<TariffEntity> create(Iterable<TariffCreationDto> creationDtos) throws EntityExistsException {
        var result = new LinkedList<TariffEntity>();
        for (TariffCreationDto creationDto : creationDtos) {
            TariffEntity tariff = this.create(creationDto);
            result.add(tariff);
        }

        return result;
    }

    @Override
    public TariffEntity getById(UUID uuid) throws EntityNotFoundException {
        TariffEntity tariff = this.tariffRepository.findById(uuid)
                .orElseThrow(EntityNotFoundException::new);

        return tariff;
    }

    @Override
    public Iterable<TariffEntity> getById(Iterable<UUID> uuids) throws EntityNotFoundException {
        var result = new LinkedList<TariffEntity>();
        for (UUID uuid : uuids) {
            TariffEntity tariff = this.getById(uuid);
            result.add(tariff);
        }

        return result;
    }

    @Override
    public Iterable<TariffEntity> getAll() {
        List<TariffEntity> allTariffs = this.tariffRepository.findAll();
        return allTariffs;
    }

    @Override
    public Optional<TariffEntity> findById(UUID uuid) {
        Optional<TariffEntity> optionalTariff = this.tariffRepository.findById(uuid);
        return optionalTariff;
    }

    @Override
    public Iterable<Optional<TariffEntity>> findById(Iterable<UUID> uuids) {
        var result = new LinkedList<Optional<TariffEntity>>();
        for (UUID uuid : uuids) {
            Optional<TariffEntity> optionalTariff = this.findById(uuid);
            result.add(optionalTariff);
        }

        return result;
    }

    @Override
    public Boolean existsById(UUID uuid) {
        Boolean exists = this.tariffRepository.existsById(uuid);
        return exists;
    }

    @Override
    public Iterable<Boolean> existsById(Iterable<UUID> uuids) {
        var result = new LinkedList<Boolean>();
        for (UUID uuid : uuids) {
            Boolean exists = this.existsById(uuid);
            result.add(exists);
        }

        return result;
    }

    @Override
    public TariffEntity update(TariffEntity tariffEntity) throws EntityNotFoundException {
        Optional<TariffEntity> foundTariffOpt = this.findById(tariffEntity.getTariffID());
        if (foundTariffOpt.isEmpty()) {
            throw new EntityNotFoundException("Tariff with id " + tariffEntity.getTariffID() + " does not exist!");
        }

        TariffEntity foundTariff = foundTariffOpt.get();
        foundTariff = foundTariff.toBuilder()
                .withTitle(tariffEntity.getTitle())
                .withDescription(tariffEntity.getDescription())
                .withUrl(tariffEntity.getUrl())
                .withSms(tariffEntity.getSms())
                .withMinutes(tariffEntity.getMinutes())
                .withGbs(tariffEntity.getGbs())
                .withIsArchived(tariffEntity.getIsArchived())
                .build();
        TariffEntity savedTariff = this.tariffRepository.save(foundTariff);
        return savedTariff;
    }

    @Override
    public Iterable<TariffEntity> update(Iterable<TariffEntity> entitiesToUpdate) throws EntityNotFoundException {
        var result = new LinkedList<TariffEntity>();
        for (TariffEntity tariffEntity : entitiesToUpdate) {
            TariffEntity updatedTariff = this.update(tariffEntity);
            result.add(updatedTariff);
        }

        return result;
    }

    @Override
    public void deleteById(UUID uuid) {
        this.tariffRepository.deleteById(uuid);
    }

    @Override
    public void deleteById(Iterable<UUID> uuids) {
        this.tariffRepository.deleteAllById(uuids);
    }

    @Override
    public void deleteAll() {
        this.tariffRepository.deleteAll();
    }
}
