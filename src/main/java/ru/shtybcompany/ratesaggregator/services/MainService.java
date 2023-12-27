package ru.shtybcompany.ratesaggregator.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.shtybcompany.ratesaggregator.dto.creation.TariffCreationDto;
import ru.shtybcompany.ratesaggregator.enities.TariffEntity;
import ru.shtybcompany.ratesaggregator.services.converters.MegafonTariffConverter;

import java.io.IOException;

@Service
public class MainService {

    private final Logger logger = LoggerFactory.getLogger(MainService.class);
    private final MegafonTariffConverter megafonTariffConverter;
    private final TariffCrudService tariffCrudService;

    @Autowired
    public MainService(MegafonTariffConverter megafonTariffConverter, TariffCrudService tariffCrudService) {
        this.megafonTariffConverter = megafonTariffConverter;
        this.tariffCrudService = tariffCrudService;
    }

    public Iterable<TariffEntity> getAndConvertTariffs() throws IOException {
        Iterable<TariffCreationDto> tariffDtos = this.megafonTariffConverter.parseHtmlToTariffs();
        Iterable<TariffEntity> entities = this.tariffCrudService.create(tariffDtos);
        return entities;
    }
}
