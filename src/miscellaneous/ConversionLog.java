package miscellaneous;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Coin;
import model.CoinLog;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

public class ConversionLog {

    private final String RUTA_ARCHIVO = ".\\src\\resources\\";
    private final String NAME_FILE = "conversion.log";

    public void saveLog(Coin base, Coin target, BigDecimal conversionRate) {
        Gson gson = new GsonBuilder().create();
        Date currentDate = new Date();
        CoinLog coinLog = new CoinLog(base, target, conversionRate, currentDate);
        try {
            FileWriter fileWriter = new FileWriter(RUTA_ARCHIVO + NAME_FILE, true);
            fileWriter.write(gson.toJson(coinLog) +  System.lineSeparator());
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void readLog() {
        CoinLog coinLog;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        DisplayMessage displayMessage = new DisplayMessage();
        try {
            BufferedReader bufferedReader = new BufferedReader( new FileReader(RUTA_ARCHIVO + NAME_FILE));
            String line;
            boolean continuePage = Boolean.TRUE;
            var count = 1;
            var step = 16;
            while((line = bufferedReader.readLine()) != null && continuePage){
                coinLog = gson.fromJson(line, CoinLog.class);
                continuePage = displayMessage.pageData("\nOprime la tecla 'c' para continuar con el historial de conversiones o la tecla 's' para dejar de mostrar los registros:"
                        , coinLog.toString(),
                        "S", count, step);
                count += 1;
            }
            bufferedReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}