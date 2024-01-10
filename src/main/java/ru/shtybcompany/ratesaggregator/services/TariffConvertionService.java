package ru.shtybcompany.ratesaggregator.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.shtybcompany.ratesaggregator.dto.creation.TariffCreationDto;
import ru.shtybcompany.ratesaggregator.services.converters.TariffConverter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TariffConvertionService {

    private final List<TariffConverter> converters;


    @Autowired
    public TariffConvertionService(
            List<TariffConverter> converters
    ) {
        this.converters = converters;
    }

    public ArrayList<TariffCreationDto> getConvertedTariffs() throws IOException {
        var allTariffDtosList = new ArrayList<TariffCreationDto>();

        for (TariffConverter converter :
                converters) {
            allTariffDtosList.addAll(converter.convert2TariffDtos());
        }

        return allTariffDtosList;
    }
}
