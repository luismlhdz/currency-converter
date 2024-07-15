package model;

import java.math.BigDecimal;
import java.util.Map;

public record CoinConversionRate(String baseCode, Map<String, BigDecimal> conversionRates) {

}