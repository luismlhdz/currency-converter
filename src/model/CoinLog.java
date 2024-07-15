package model;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CoinLog {

    private final Coin base;
    private final Coin target;
    private final BigDecimal conversionRate;
    private final Date dateConversion;

    public CoinLog(Coin base, Coin target, BigDecimal conversionRate, Date dateConversion) {
        this.base = base;
        this.target = target;
        this.conversionRate = conversionRate;
        this.dateConversion = dateConversion;
    }

    @Override
    public String toString() {
        String format = "dd/MM/yyyy' Hora: 'HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format);

        return "Monto: "+ base.getAmount() + " '" + base.getCode() + "' ('" + base.getDescription() + "')" +
                " equivale a Monto: " + target.getAmount() + " '" + target.getCode() + "' ('" + target.getDescription() + "')" +
                ", Tasa de conversión: "+ conversionRate + " Fecha :" + sdf.format(dateConversion) + ".";
    }

}