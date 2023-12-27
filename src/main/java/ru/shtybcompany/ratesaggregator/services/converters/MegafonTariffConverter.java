package ru.shtybcompany.ratesaggregator.services.converters;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.shtybcompany.ratesaggregator.dto.creation.TariffCreationDto;

import java.io.IOException;
import java.util.LinkedList;


@Service
public class MegafonTariffConverter {

    private final Logger logger = LoggerFactory.getLogger(MegafonTariffConverter.class);

    public Iterable<TariffCreationDto> parseHtmlToTariffs() throws IOException {
        var result = new LinkedList<TariffCreationDto>();

        Document doc = Jsoup.connect("https://spb.megafon.ru/tariffs/all/").get();

        Elements tariffWrappers = doc.select(".tariffs-carousel-v3__card-wrapper");
        for (Element tariffWrapper : tariffWrappers) {
            var tariffCreationDto = new TariffCreationDto();

            Element titleRefEl = tariffWrapper.selectFirst(".tariffs-card-header-v3__title-link");
            String reference = titleRefEl.attr("abs:href");

            Document tariffInfo;
            try {
                tariffInfo = Jsoup.connect(reference).get();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }


            Element tariffBanner = tariffInfo.selectFirst(".tariffs-detail-banner__banner");
            String tariffTitle = tariffBanner.child(0).text();
            String tariffDesc = tariffBanner.child(1).text();

            tariffCreationDto.setTitle(tariffTitle);
            tariffCreationDto.setDescription(tariffDesc);
            tariffCreationDto.setUrl(reference);
            tariffCreationDto.setArchived(false);

            Elements detailItems = tariffInfo.select(".tariffs-detail-short-base-item");
            for (Element detailItem : detailItems) {
                Elements itemValueEl = detailItem.select(".tariffs-detail-short-base-item__value");
                String itemValue = itemValueEl.text();
                int value = Integer.parseInt(itemValue.split(" ")[0]);
                String type = itemValue.split(" ")[1];

                switch (type) {
                    case "ГБ":
                        tariffCreationDto.setGbs(value);
                        break;
                    case "минут":
                        tariffCreationDto.setMinutes(value);
                        break;
                    case "SMS":
                        tariffCreationDto.setSms(value);
                        break;
                }
            }

//            String priceStr = tariffInfo.selectFirst(".tariffs-price__price_current").text();
//            int price = Integer.parseInt(priceStr.split(" ")[0]);
//            CurrencyUnit rubCurrency = Monetary.getCurrency("RUB");
//            var money = Money.of(price, rubCurrency);
//            var priceEntity = TariffPriceEntity.createBuilder()
//                    .withTariff(tariff)
//                    .withMoney(money)
//                    .build();


            result.add(tariffCreationDto);
        }

        return result;
    }
}
