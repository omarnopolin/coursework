package coursework.cashflow.models.dao;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

@Data
@Entity
public class Wallet {
    @Id
    @Column(name="walletId")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private String name;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "currencyId", referencedColumnName = "currencyId")
    private Currency currency;
    private BigDecimal balance;
//    private byte icon;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private Users user;

//    public Wallet() {}
//
//    public Wallet(WalletDTO wallet) {
//        this.name = wallet.getName();
//        this.balance = wallet.getBalance();
//    }
}
