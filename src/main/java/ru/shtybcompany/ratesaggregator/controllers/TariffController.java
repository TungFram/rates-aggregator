package ru.shtybcompany.ratesaggregator.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shtybcompany.ratesaggregator.enities.TariffEntity;
import ru.shtybcompany.ratesaggregator.services.MainService;

import java.io.IOException;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE,
        value = "/${api.prefix}/${api.currentVersion}" +
                "/")
public class TariffController {

    private final MainService mainService;

    @Autowired
    public TariffController(
            MainService mainService
    ) {
        this.mainService = mainService;
    }

    @GetMapping("/")
    public ResponseEntity<Iterable<TariffEntity>> getAll() throws IOException {
        Iterable<TariffEntity> tariffs = this.mainService.getAndConvertTariffs();
        return ResponseEntity.ok(tariffs);
    }
}
