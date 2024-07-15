package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;


public class ConfigurationAPI {

    private final String apiKey;
    private final String apiBaseUrl;

    public ConfigurationAPI() {
        ConfigurationAPI configurationAPI =this.getDataConfiguration();
        this.apiKey = configurationAPI.getApiKey();
        this.apiBaseUrl = configurationAPI.getApiBaseUrl();
    }

    public ConfigurationAPI(String apiKey, String apiBaseUrl) {
        this.apiKey = apiKey;
        this.apiBaseUrl = apiBaseUrl;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getApiBaseUrl() {
        return apiBaseUrl;
    }

    private ConfigurationAPI getDataConfiguration(){
        Properties properties = new Properties();
        String fileName = "configuration.properties";
        String rutaRelativa = ".\\src\\resources\\";
        ConfigurationAPI configurationAPI;
        try{
            properties.load(new FileInputStream(rutaRelativa + fileName));
            if(properties.get("API_KEY") != null &&  properties.get("API_BASE_URL") != null){
                configurationAPI = new ConfigurationAPI((String) properties.get("API_KEY"), (String) properties.get("API_BASE_URL"));
            }else{
                throw new RuntimeException("No existen los valores en el archivo de configuración");
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Archivo configuración no encontrado: "+ rutaRelativa +fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return configurationAPI;
    }
}