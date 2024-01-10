package ru.shtybcompany.ratesaggregator.services;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.shtybcompany.ratesaggregator.dto.creation.TariffCreationDto;
import ru.shtybcompany.ratesaggregator.enities.TariffEntity;
import ru.shtybcompany.ratesaggregator.mappers.write.AbstractTariffDtoToEntityMapper;
import ru.shtybcompany.ratesaggregator.mappers.write.AbstractTariffDtoToEntityMapperImpl;
import ru.shtybcompany.ratesaggregator.repositories.TariffRepository;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
public class TariffCrudService implements CrudService<TariffEntity, TariffCreationDto, UUID> {

    private final Logger logger = LoggerFactory.getLogger(TariffCrudService.class);

    private final TariffRepository tariffRepository;
    private final AbstractTariffDtoToEntityMapper tariffDtoToEntityMapper;


    @Autowired
    public TariffCrudService(
            TariffRepository tariffRepository,
            AbstractTariffDtoToEntityMapperImpl tariffDtoToEntityMapper
) {
        this.tariffRepository = tariffRepository;
        this.tariffDtoToEntityMapper = tariffDtoToEntityMapper;
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

//        int price = 0;
//        CurrencyUnit rubCurrency = Monetary.getCurrency("RUB");
//        var money = Money.of(price, rubCurrency);
//        var priceEntity = TariffPriceEntity.createBuilder()
//                .withTariff(tariff)
//                .withMoney(money)
//                .withPreviousPrice(null)
//                .build();

        tariff = tariff.toBuilder()
                .withCreatedAt(LocalDateTime.now())
                .withUpdatedAt(LocalDateTime.now())
//                .withPrice(priceEntity)
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
    public Collection<TariffEntity> getById(Collection<UUID> uuids) throws EntityNotFoundException {
        List<TariffEntity> tariffEntities = this.tariffRepository.findAllById(uuids);
        if (uuids.size() != tariffEntities.size()) {
            throw new EntityNotFoundException("Some of tariffs does not exist!");
        }

        return tariffEntities;
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
    public Boolean existsById(UUID uuid) {
        Boolean exists = this.tariffRepository.existsById(uuid);
        return exists;
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
