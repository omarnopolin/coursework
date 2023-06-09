package coursework.cashflow.services;

import coursework.cashflow.models.dao.Currency;
import coursework.cashflow.models.dao.Wallet;
import coursework.cashflow.repositories.CurrencyRepo;
import coursework.cashflow.repositories.TransactionRepo;
import coursework.cashflow.repositories.WalletRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Slf4j
@RequiredArgsConstructor
public class CurrencyService {
    private final CurrencyRepo currencyRepo;
    private final TransactionRepo transactionRepo;
    private final WalletRepo walletRepo;

    public void createCurrency(@RequestBody Currency currency) {
        log.info("CREATE CURRENCY");
        currencyRepo.save(currency);
    }

    public void updateCurrency(@RequestBody Currency newCurrency) {
        Currency currency = currencyRepo.findById(newCurrency.getId());
        currency.setName(newCurrency.getName().equals("") ? currency.getName() : newCurrency.getName());
        currency.setTicker(newCurrency.getTicker() == 0 ? currency.getTicker() : newCurrency.getTicker());
        currencyRepo.save(currency);
    }

    @Transactional
    public void deleteCurrency(@RequestBody Currency currency) {
        log.info("DELETE CURRENCY");
        List<Wallet> wallets = walletRepo.findByCurrency_Id(currency.getId());
        wallets.forEach(wallet -> transactionRepo.deleteTransactionsByWallet_Id(wallet.getId()));
        walletRepo.deleteAll(wallets);
        currencyRepo.delete(currency);
    }
}
