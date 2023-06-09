package coursework.cashflow.models.dao;

import coursework.cashflow.models.dto.TransactionDTO;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
public class Transaction {
    @Id
    @Column(name="transactionId")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "walletId", referencedColumnName = "walletId")
    private Wallet wallet;
    private String type; //(expenses or income)
    @ManyToOne
    @JoinColumn(name = "categoryId", referencedColumnName = "categoryId")
    private Category category;
    @ManyToOne
    @JoinColumn(name = "tagId", referencedColumnName = "tagId")
    private Tag tag;
    private Timestamp date;
    private String description;

    public Transaction() {}

    public Transaction(TransactionDTO transactionDTO) {
        this.type = transactionDTO.getType();
        this.date = transactionDTO.getDate();
        this.description = transactionDTO.getDescription();
    }
}
