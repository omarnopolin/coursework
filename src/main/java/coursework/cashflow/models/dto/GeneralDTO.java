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
public class GeneralDTO {
    private List<Transaction> transactions;
    private List<Wallet> wallets;
    private List<Currency> currencies;
    private List<Category> categories;
    private List<Tag> tags;
}
