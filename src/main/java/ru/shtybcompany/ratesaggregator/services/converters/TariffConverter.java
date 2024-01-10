package ru.shtybcompany.ratesaggregator.services.converters;

import ru.shtybcompany.ratesaggregator.dto.creation.TariffCreationDto;

import java.io.IOException;
import java.util.Collection;

public interface TariffConverter {

    public Collection<? extends TariffCreationDto> convert2TariffDtos() throws IOException;
}
