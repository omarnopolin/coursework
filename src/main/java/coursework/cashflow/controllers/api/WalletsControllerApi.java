package coursework.cashflow.controllers.api;

import coursework.cashflow.models.dto.WalletCurrencyDTO;
import coursework.cashflow.models.dto.WalletDTO;
import coursework.cashflow.services.WalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import coursework.cashflow.models.dao.Wallet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/api/wallets")
@RequiredArgsConstructor
public class WalletsControllerApi {
    private final WalletService walletService;

    @GetMapping("/get")
    public WalletCurrencyDTO getWalletsAndCurrency(HttpServletRequest request) {
        return walletService.getWalletsAndCurrency(request.getUserPrincipal().getName());
    }

    @PostMapping("/create")
    public void postCreateWallet(@RequestBody WalletDTO wallet, HttpServletRequest request) {
        walletService.createWallet(wallet, request.getUserPrincipal().getName());
    }

    @PostMapping("/update")
    public void postUpdateWallets(@RequestBody WalletDTO newWallet) {
        walletService.updateWallet(newWallet);
    }

    @PostMapping("/delete")
    public void deleteWallet(@RequestBody WalletDTO wallet) {
        walletService.deleteWallet(wallet);
    }

}
