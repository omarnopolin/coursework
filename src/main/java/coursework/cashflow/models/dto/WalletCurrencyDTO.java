package coursework.cashflow.models.dto;

import lombok.*;
import coursework.cashflow.models.dao.Currency;
import coursework.cashflow.models.dao.Wallet;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class WalletCurrencyDTO {
    private List<Wallet> wallets;
    private List<Currency> currencies;
}
