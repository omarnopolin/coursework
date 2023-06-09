package coursework.cashflow.controllers.api;

import coursework.cashflow.models.dto.StatisticDTO;
import coursework.cashflow.repositories.TransactionRepo;
import coursework.cashflow.repositories.WalletRepo;
import coursework.cashflow.models.UsersDetails;
import coursework.cashflow.services.StatisticsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.bind.annotation.*;
import coursework.cashflow.models.dao.Transaction;
import coursework.cashflow.models.dao.Users;
import coursework.cashflow.models.dao.Wallet;
import coursework.cashflow.models.dto.TotalBalanceDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/stat")
@RequiredArgsConstructor
public class StatisticsControllerApi {
    private final StatisticsService statisticsService;

    @GetMapping("/get30")
    public StatisticDTO getTransactionsForLast30Days(@RequestParam(value = "walletId") Integer walletId,
                                                     @RequestParam(value = "startDate") String startDate,
                                                     @RequestParam(value = "endDate") String endDate) throws ParseException {
        return statisticsService.getTransactionsForLast30Days(walletId, startDate, endDate);
    }
}
