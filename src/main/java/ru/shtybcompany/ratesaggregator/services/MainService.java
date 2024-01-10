package ru.shtybcompany.ratesaggregator.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.shtybcompany.ratesaggregator.dto.creation.TariffCreationDto;
import ru.shtybcompany.ratesaggregator.enities.TariffEntity;

import java.io.IOException;
import java.util.ArrayList;

@Service
public class MainService {

    private final Logger logger = LoggerFactory.getLogger(MainService.class);
    private final TariffConvertionService tariffConvertionService;
    private final TariffCrudService tariffCrudService;

    @Autowired
    public MainService(
            TariffConvertionService tariffConvertionService,
            TariffCrudService tariffCrudService
    ) {
        this.tariffConvertionService = tariffConvertionService;
        this.tariffCrudService = tariffCrudService;
    }

    public Iterable<TariffEntity> getAndConvertTariffs() throws IOException {
        ArrayList<TariffCreationDto> tariffs = this.tariffConvertionService.getConvertedTariffs();
        Iterable<TariffEntity> savedEntities = this.tariffCrudService.create(tariffs);
        return savedEntities;
    }
}
