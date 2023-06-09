package coursework.cashflow.models.dto;

import lombok.Data;
import coursework.cashflow.models.dao.Currency;

@Data
public class CurrencyDTO {
    Iterable<Currency> currencies;
}
