package coursework.cashflow.controllers.api;

import coursework.cashflow.models.dto.GeneralDTO;
import coursework.cashflow.models.dto.TransactionDTO;
import coursework.cashflow.models.dto.WalletDTO;
import coursework.cashflow.services.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/transaction")
@RequiredArgsConstructor
public class TransactionControllerApi {
    private final TransactionService transactionService;

    @GetMapping("/get")
    public GeneralDTO getTransactions(HttpServletRequest request) {
        return transactionService.getTransactionsByUsername(request.getUserPrincipal().getName());
    }

    @PostMapping("/create")
    public void createTransaction(@RequestBody TransactionDTO transaction) {
        transactionService.createTransaction(transaction);
    }

    @PostMapping("/update")
    public void updateTransaction(@RequestBody TransactionDTO transactionDTO) {
        transactionService.updateTransaction(transactionDTO);
    }

    @PostMapping("/delete")
    public void deleteTransaction(@RequestBody TransactionDTO transactionDTO) {
        transactionService.deleteTransaction(transactionDTO);
    }

    @PostMapping("/deleteAllByWallet")
    public void deleteAllTransactionByWallet(@RequestBody WalletDTO walletDTO) throws IOException {
        transactionService.deleteAllTransactionByWallet(walletDTO);
    }



}
