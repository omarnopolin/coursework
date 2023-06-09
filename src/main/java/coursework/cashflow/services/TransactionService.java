package coursework.cashflow.services;

import coursework.cashflow.models.dao.Tag;
import coursework.cashflow.models.dao.Transaction;
import coursework.cashflow.models.dao.Users;
import coursework.cashflow.models.dao.Wallet;
import coursework.cashflow.models.dto.GeneralDTO;
import coursework.cashflow.models.dto.TransactionDTO;
import coursework.cashflow.models.dto.WalletDTO;
import coursework.cashflow.repositories.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionService {

    private final UserRepo userRepo;
    private final WalletRepo walletRepo;
    private final CurrencyRepo currencyRepo;
    private final TransactionRepo transactionRepo;
    private final CategoryRepo categoryRepo;
    private final TagRepo tagRepo;


    public GeneralDTO getTransactionsByUsername(String username) {
        Users user = userRepo.findByLogin(username);

        return GeneralDTO.builder()
                .transactions(transactionRepo.findByWallet_User_Id(user.getId()))
                .wallets(walletRepo.findByUser_Id(user.getId()))
                .currencies(currencyRepo.findAll())
                .categories(categoryRepo.findAll())
                .tags(tagRepo.findAll())
                .build();
    }

    @Transactional
    public void createTransaction(TransactionDTO transactionDto) {
            log.info("Create transaction");

            changeWalletBalance(transactionDto);

            Transaction transaction = new Transaction(transactionDto);
            transaction.setCategory(categoryRepo.findById(transactionDto.getCategoryId()));
            transaction.setWallet(walletRepo.findById(transactionDto.getWalletId())
                    .orElseThrow(NoSuchElementException::new));
            transaction.setTag(tagRepo.findById(transactionDto.getTagId()));
            transactionRepo.save(transaction);
    }

    @Transactional
    public void updateTransaction(@RequestBody TransactionDTO newTransaction) {
            log.info("Update transaction");
            Transaction transaction = transactionRepo.findById(newTransaction.getId());
            transaction.setCategory(newTransaction.getCategoryId() == 0 ? transaction.getCategory() : categoryRepo.findById(newTransaction.getCategoryId()));
            transaction.setDate(newTransaction.getDate() == null ? transaction.getDate() : newTransaction.getDate());
            transaction.setDescription(newTransaction.getDescription().equals("") ? transaction.getDescription() : newTransaction.getDescription());
            recountBalanceIfTypeChanged(newTransaction, transaction);
            recountBalanceIfTagChanged(newTransaction, transaction);
            transactionRepo.save(transaction);
    }

    @Transactional
    public void deleteTransaction(@RequestBody TransactionDTO transaction){
            log.info("Delete one transaction");
            changeWalletBalanceOnDelete(transaction);
            transactionRepo.deleteById(transaction.getId());
    }

    @Transactional
    public void deleteAllTransactionByWallet(@RequestBody WalletDTO wallet) throws IOException {
            log.info("Delete all transactions with their wallet");
            log.info(wallet.toString());
            transactionRepo.deleteTransactionsByWallet_Id(wallet.getId());
            walletRepo.deleteById(wallet.getId());
    }

    private void changeWalletBalance(TransactionDTO transaction) {
        Wallet wallet = walletRepo.findById(transaction.getWalletId())
                .orElseThrow(NoSuchElementException::new);
        Tag tag = tagRepo.findById(transaction.getTagId());

        BigDecimal priceAccordingToWalletCurrency = tag.getPrice().multiply(
                BigDecimal.valueOf(wallet.getCurrency().getTicker()));

        BigDecimal countNewBalance = (transaction.getType().equals("income")) ?
                wallet.getBalance().add(priceAccordingToWalletCurrency) :
                wallet.getBalance().subtract(priceAccordingToWalletCurrency);
        wallet.setBalance(countNewBalance);
        walletRepo.save(wallet);
    }

    private void changeWalletBalanceOnDelete(TransactionDTO transaction) {
        Wallet wallet = walletRepo.findById(transaction.getWalletId())
                .orElseThrow(NoSuchElementException::new);
        Tag tag = tagRepo.findById(transaction.getTagId());

        BigDecimal priceAccordingToWalletCurrency = tag.getPrice().multiply(
                BigDecimal.valueOf(wallet.getCurrency().getTicker()));

        BigDecimal countNewBalance = (transaction.getType().equals("income")) ?
                wallet.getBalance().subtract(priceAccordingToWalletCurrency) :
                wallet.getBalance().add(priceAccordingToWalletCurrency);
        wallet.setBalance(countNewBalance);
        walletRepo.save(wallet);
    }

    private void recountBalanceIfTypeChanged(TransactionDTO newTransaction, Transaction transaction) {
        if (!newTransaction.getType().equals("") &&
                !newTransaction.getType().equals(transaction.getType())) {

            transaction.setType(newTransaction.getType());
            Wallet wallet = transaction.getWallet();
            Tag tag = transaction.getTag();

            BigDecimal priceAccordingToWalletCurrency = tag.getPrice()
                    .multiply(BigDecimal.valueOf(wallet.getCurrency().getTicker()))
                    .multiply(BigDecimal.valueOf(2));

            BigDecimal countNewBalance = (transaction.getType().equals("income")) ?
                    wallet.getBalance().add(priceAccordingToWalletCurrency) :
                    wallet.getBalance().subtract(priceAccordingToWalletCurrency);
            wallet.setBalance(countNewBalance);
            walletRepo.save(wallet);
        }
    }

    private void recountBalanceIfTagChanged(TransactionDTO newTransaction, Transaction transaction) {
        if (newTransaction.getTagId() != 0 &&
                !newTransaction.getTagId().equals(transaction.getTag().getId())) {

            Wallet wallet = transaction.getWallet();
            Tag oldTag = transaction.getTag();
            Tag newTag = tagRepo.findById(newTransaction.getTagId());

            transaction.setTag(newTag);

            BigDecimal oldPriceAccordingToWalletCurrency = oldTag.getPrice()
                    .multiply(BigDecimal.valueOf(wallet.getCurrency().getTicker()));
            BigDecimal newPriceAccordingToWalletCurrency = newTag.getPrice()
                    .multiply(BigDecimal.valueOf(wallet.getCurrency().getTicker()));

            BigDecimal countNewBalance = (transaction.getType().equals("income")) ?
                    wallet.getBalance()
                            .subtract(oldPriceAccordingToWalletCurrency)
                            .add(newPriceAccordingToWalletCurrency) :
                    wallet.getBalance()
                            .add(oldPriceAccordingToWalletCurrency)
                            .subtract(newPriceAccordingToWalletCurrency);
            wallet.setBalance(countNewBalance);
            walletRepo.save(wallet);
        }
    }

}
