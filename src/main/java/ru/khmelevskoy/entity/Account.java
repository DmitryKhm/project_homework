package ru.khmelevskoy.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "account_name")
    private String accName;

    @Column(name = "account_value")
    private BigDecimal accValue;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAccName() {
        return accName;
    }

    public void setAccName(String accName) {
        this.accName = accName;
    }

    public BigDecimal getAccValue() {
        return accValue;
    }

    public void setAccValue(BigDecimal accValue) {
        this.accValue = accValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id == account.id && accValue.equals(account.accValue) && Objects.equals(accName, account.accName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accName, accValue);
    }
}