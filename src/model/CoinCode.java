package model;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CoinCode {

    private final Map<String,String> codes;

    public CoinCode(List<List<String>> codes) {
        this.codes = new LinkedHashMap<>();
        for (List<String> sublist : codes) {
            if (this.codes.put(sublist.getFirst(), sublist.getLast()) != null) {
                throw new IllegalStateException("Llave duplicada.");
            }
        }
    }

    public Map<String, String> getCodes() {
        return codes;
    }

}