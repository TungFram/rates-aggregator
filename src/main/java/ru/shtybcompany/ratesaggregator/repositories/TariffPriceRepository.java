package ru.shtybcompany.ratesaggregator.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shtybcompany.ratesaggregator.enities.TariffPriceEntity;

import java.util.UUID;

@Repository
public interface TariffPriceRepository extends JpaRepository<TariffPriceEntity, UUID> {
}
