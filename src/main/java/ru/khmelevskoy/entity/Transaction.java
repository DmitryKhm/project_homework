package ru.khmelevskoy.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinTable(name = "category_to_transaction",
            joinColumns = @JoinColumn(name = "transaction_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"))
    private Category categories;

    @Column(name = "transaction_value")
    private long value;

    @Column(name = "transaction_time")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "to_account_id")
    private Account accountTo;

    @ManyToOne
    @JoinColumn(name = "from_account_id")
    private Account accountFrom;

    public Category getCategory() {
        return categories;
    }

    public void setCategory(Category category) {
        this.categories = category;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Account getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(Account accountTo) {
        this.accountTo = accountTo;
    }

    public Account getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(Account accountFrom) {
        this.accountFrom = accountFrom;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "TransactionModel{" +
                "id=" + id +
                ", accountTo=" + accountTo +
                ", accountFrom=" + accountFrom +
                ", value=" + value +
                ", date=" + date +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return id == that.id && value == that.value && Objects.equals(accountTo, that.accountTo) && Objects.equals(accountFrom, that.accountFrom) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountTo, accountFrom, value, date);
    }
}