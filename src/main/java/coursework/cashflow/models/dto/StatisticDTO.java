package coursework.cashflow.models.dto;

import coursework.cashflow.models.dao.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

@Data

// no validation, for statistic page

public class StatisticDTO {
    private List<Transaction> transactions;
    private List<Wallet> wallets;
    private List<Currency> currencies;
    private List<Category> categories;
    private List<Tag> tags;

    private List<TotalBalanceDTO> total;
}
