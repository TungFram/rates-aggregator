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
import java.util.List;


@Service
public class Tele2TariffConverter implements TariffConverter{

    private final Logger logger = LoggerFactory.getLogger(MegafonTariffConverter.class);

    private final String SOURCE_LINK = "https://spb.tele2.ru/tariffs";

    public List<TariffCreationDto> convert2TariffDtos() throws IOException {
        var result = new LinkedList<TariffCreationDto>();

        Document doc = Jsoup.connect(SOURCE_LINK).get();

        Elements tariffWrappers = doc.select(".tariff-card");

        for (Element tariffWrapper : tariffWrappers) {
            var tariffCreationDto = new TariffCreationDto();


            Element titleWrapperEl = tariffWrapper.selectFirst(".tariff-card__title");
            if (titleWrapperEl == null) {
                continue;
            }

            Element titleRefEl = tariffWrapper.selectFirst(".tariff-card__title-link");
            if (titleRefEl == null) {
                continue;
            }

            String tariffTitle = titleRefEl.ownText();
            String reference = titleRefEl.attr("abs:href");
            this.logger.warn("====================== " + tariffTitle);



            tariffCreationDto.setTitle(tariffTitle);
            tariffCreationDto.setDescription("");
            tariffCreationDto.setUrl(reference);
            tariffCreationDto.setArchived(false);

            Elements parameters = tariffWrapper.select(".tariff-card-parameter--block");
            for (Element parameter : parameters) {
                Element paramEl = parameter.selectFirst(".tariff-card-parameter__value");
                if (paramEl == null) {
                    continue;
                }

                String paramStr = paramEl.ownText();
                int paramValue = Integer.parseInt(paramStr);

                Element typeEl = parameter.selectFirst(".tariff-card-parameter__name");
                if (typeEl == null) {
                    continue;
                }

                String type = typeEl.ownText();

                switch (type) {
                    case "ГБ":
                        tariffCreationDto.setGbs(paramValue);
                        break;

                    case "МБ":
                        tariffCreationDto.setGbs(paramValue / 1024);

                    case "минут":
                        tariffCreationDto.setMinutes(paramValue);
                        break;

                    case "SMS":
                        tariffCreationDto.setSms(paramValue);
                        break;

                    default:
                        break;

                }
            }

//            for (Element parameter : parameters) {
//                String itemValue = parameter.select(".tariff-card-parameter__value").text().split(" ")[0];
//                int value = Integer.parseInt(itemValue);
//                String type = parameter.select(".tariff-card-parameter__name").text();
//
//                switch (type) {
//                    case "ГБ":
//                        tariffCreationDto.setGbs(value);
//                        break;
//                    case "минут":
//                        tariffCreationDto.setMinutes(value);
//                        break;
//                    case "SMS":
//                        tariffCreationDto.setSms(value);
//                        break;
//                }
//            }

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
