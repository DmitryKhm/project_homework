package ru.khmelevskoy.dto;

import java.math.BigDecimal;
import java.util.Objects;


public class AccountDTO {

    private long id;

    private long userId;

    private String accName;

    private BigDecimal accValue;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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
        AccountDTO that = (AccountDTO) o;
        return id == that.id && userId == that.userId && Objects.equals(accName, that.accName) && Objects.equals(accValue, that.accValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, accName, accValue);
    }
}