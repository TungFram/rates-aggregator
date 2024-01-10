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
public class BeelineTariffConverter implements TariffConverter {

    private final Logger logger = LoggerFactory.getLogger(MegafonTariffConverter.class);

    private final String SOURCE_LINK = "https://spb.beeline.ru/customers/products/mobile/tariffs/";

    public List<TariffCreationDto> convert2TariffDtos() throws IOException {
        var result = new LinkedList<TariffCreationDto>();

        Document doc = Jsoup.connect(SOURCE_LINK).get();

        Elements tariffWrappers = doc.select(".E9tSER");
        for (Element tariffWrapper : tariffWrappers) {
            var tariffCreationDto = new TariffCreationDto();

            Element refEl = tariffWrapper.selectFirst(".d0ZXHD");
            String reference = refEl.attr("abs:href");


            String tariffTitle = tariffWrapper.selectFirst(".QpPCUG").ownText();
            String tariffDesc = doc.selectFirst(".xlJ9gj").ownText();

            tariffCreationDto.setTitle(tariffTitle);
            tariffCreationDto.setDescription(tariffDesc);
            tariffCreationDto.setUrl(reference);
            tariffCreationDto.setArchived(false);

            Elements detailItems = tariffWrapper.select(".value--default");
            for (Element detailItem : detailItems) {
                Element element = detailItem.selectFirst(".JcQa1x");
                if (element == null) {
                    continue;
                }

                String itemValue = element.ownText();
                int value = Integer.parseInt(itemValue);
                String type = detailItem.selectFirst(".xQvJV2").ownText();

                switch (type) {
                    case "ГБ":
                        tariffCreationDto.setGbs(value);
                        break;
                    case "мин":
                        tariffCreationDto.setMinutes(value);
                        break;
                }
            }

            tariffCreationDto.setSms(0);

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
