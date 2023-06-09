package coursework.cashflow.services;

import coursework.cashflow.models.dao.Users;
import coursework.cashflow.models.dao.Wallet;
import coursework.cashflow.models.dto.WalletCurrencyDTO;
import coursework.cashflow.models.dto.WalletDTO;
import coursework.cashflow.repositories.CurrencyRepo;
import coursework.cashflow.repositories.TransactionRepo;
import coursework.cashflow.repositories.UserRepo;
import coursework.cashflow.repositories.WalletRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepo walletRepo;
    private final TransactionRepo transactionRepo;
    private final UserRepo userRepo;
    private final CurrencyRepo currencyRepo;

    public WalletCurrencyDTO getWalletsAndCurrency(String username) {
        try {
            Users user = userRepo.findByLogin(username);
            return WalletCurrencyDTO.builder()
                    .wallets(walletRepo.findByUser_Id(user.getId()))
                    .currencies(currencyRepo.findAll())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void createWallet(WalletDTO wallet, String username) {
        log.info("CREATE WALLET");

        Wallet newWallet = Wallet.builder()
                .name(wallet.getName())
                .currency(currencyRepo.findById(wallet.getCurrencyId()))
                .balance(wallet.getBalance())
                .user(userRepo.findByLogin(username))
                .build();

        walletRepo.save(newWallet);

    }

    public void updateWallet(WalletDTO newWallet) {
        log.info("UPDATE WALLET");

        Wallet wallet = walletRepo.findById(newWallet.getId())
                .orElseThrow(NoSuchElementException::new);
        wallet.setName(newWallet.getName().equals("") ? wallet.getName() : newWallet.getName());

        walletRepo.save(wallet);

    }

    @Transactional
    public void deleteWallet(WalletDTO wallet) {
        log.info("DELETE WALLET");
        transactionRepo.deleteTransactionsByWallet_Id(wallet.getId());
        walletRepo.delete(walletRepo.findById(wallet.getId())
                .orElseThrow(NoSuchElementException::new)
        );
    }
}
