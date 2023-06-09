package coursework.cashflow.controllers.api;

import coursework.cashflow.services.CurrencyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import coursework.cashflow.models.dao.Currency;

@Slf4j
@RestController
@RequestMapping("/api/currency")
@RequiredArgsConstructor
public class CurrencyControllerApi {
    private final CurrencyService currencyService;

    @PostMapping("/create")
    public void createCurrency(@RequestBody Currency currency) {
        currencyService.createCurrency(currency);
    }

    @PostMapping("/update")
    public void updateCurrency(@RequestBody Currency newCurrency) {
        currencyService.updateCurrency(newCurrency);
    }

    @PostMapping("/delete")
    public void deleteCurrency(@RequestBody Currency currency) {
        currencyService.deleteCurrency(currency);
    }
}
