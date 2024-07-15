package services;

import com.google.gson.Gson;
import util.ConfigurationAPI;
import dto.CoinCodeDTO;
import dto.CoinConversionRateDTO;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

public class CoinData {

    private static final ConfigurationAPI configurationAPI = new ConfigurationAPI();

    public Optional<CoinCodeDTO> allCoin(){
        URI uriAddress = URI.create( configurationAPI.getApiBaseUrl() + configurationAPI.getApiKey() + "/codes" );

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder().uri(uriAddress).build();

        try {
            HttpResponse<String> response = client
                    .send(request,HttpResponse.BodyHandlers.ofString());
            int statusCode = response.statusCode();
            if (statusCode < 200 || statusCode >= 300) {
                throw new Exception("Error en el servicio para obtener las monedas disponibles (código): " + statusCode+".");
            }
            return Optional.of(new Gson().fromJson(response.body(), CoinCodeDTO.class ));

        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }

    }

    public Optional<CoinConversionRateDTO> allCoinConversion(String currencyCode){

        URI uriAddress = URI.create(configurationAPI.getApiBaseUrl() + configurationAPI.getApiKey() + "/latest/" + currencyCode.trim().toUpperCase());

        try (HttpClient client = HttpClient.newHttpClient()) {

            HttpRequest request = HttpRequest.newBuilder().uri(uriAddress).build();

            try {
                HttpResponse<String> response = client
                        .send(request, HttpResponse.BodyHandlers.ofString());
                int statusCode = response.statusCode();
                if (statusCode < 200 || statusCode >= 300) {
                    throw new Exception("Error en el servicio para obtener la conversión entre monedas disponibles (código): " + statusCode+".");
                }
                return Optional.of(new Gson().fromJson(response.body(), CoinConversionRateDTO.class));

            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }
}