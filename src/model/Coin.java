package model;

import java.math.BigDecimal;

public class Coin {

    private String code;
    private String description;
    private BigDecimal amount;

    public Coin() {
        super();
    }

    public Coin(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Código: '" + code + '\'' +
                " - Descripción: '" + description + '\'';
    }
}