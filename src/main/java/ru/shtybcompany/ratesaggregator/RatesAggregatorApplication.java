package ru.shtybcompany.ratesaggregator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class RatesAggregatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(RatesAggregatorApplication.class, args);
    }

}
