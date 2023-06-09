package coursework.cashflow.services;

import coursework.cashflow.models.UsersDetails;
import coursework.cashflow.models.dao.Transaction;
import coursework.cashflow.models.dao.Users;
import coursework.cashflow.models.dao.Wallet;
import coursework.cashflow.models.dto.StatisticDTO;
import coursework.cashflow.models.dto.TotalBalanceDTO;
import coursework.cashflow.repositories.TransactionRepo;
import coursework.cashflow.repositories.WalletRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final TransactionRepo transactionRepo;
    private final WalletRepo walletRepo;

    @GetMapping("/get30")
    public StatisticDTO getTransactionsForLast30Days(Integer walletId, String startDate, String endDate) throws ParseException {

        Users user = ((UsersDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();

        List<Transaction> transactions = null;
        if (walletId == 0) {
            transactions = transactionRepo.findByWallet_User_IdAndDateBetween(
                    user.getId(),
                    new Timestamp(stringToMillis(startDate)),
                    new Timestamp(stringToMillis(endDate) + 24 * 60 * 60 * 1000));
        } else {
            transactions = transactionRepo.findByWallet_IdAndWallet_User_IdAndDateBetween(
                    walletId, user.getId(),
                    new Timestamp(stringToMillis(startDate)),
                    new Timestamp(stringToMillis(endDate) + 24 * 60 * 60 * 1000));
        }
        List<Wallet> wallets = walletRepo.findByUser_Id(user.getId());

        List<TotalBalanceDTO> total = (walletId == 0)
                ? countTotal(wallets, transactions)
                : countTotal(
                wallets.stream()
                        .filter(w -> w.getId().intValue() == walletId)
                        .collect(Collectors.toList()), transactions);

        return StatisticDTO.builder()
                .transactions(transactions)
                .wallets(wallets)
                .total(total)
                .build();
    }

    private long stringToMillis(String income) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse(income);
        return date.getTime();
    }

    private List<TotalBalanceDTO> countTotal(List<Wallet> wallets, List<Transaction> transactions) {
        List<TotalBalanceDTO> total = new ArrayList<>();

        for (Wallet w : wallets) {
            BigDecimal totalIncomes = new BigDecimal(0);
            BigDecimal totalExpenses = new BigDecimal(0);

            for (Transaction t : transactions) {
                if (t.getWallet().equals(w)) {
                    if (t.getType().equals("income")) {
                        totalIncomes = totalIncomes.add(t.getTag().getPrice());
                    } else {
                        totalExpenses = totalExpenses.add(t.getTag().getPrice());
                    }
                }
            }
            total.add(TotalBalanceDTO.builder()
                    .wallet(w)
                    .totalIncomes(totalIncomes)
                    .totalExpenses(totalExpenses)
                    .totalBalance(totalIncomes.subtract(totalExpenses))
                    .build());
        }

        return total;
    }

}
