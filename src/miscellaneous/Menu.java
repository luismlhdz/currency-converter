package miscellaneous;

import services.CoinData;
import model.Coin;
import model.CoinCode;
import model.CoinConversionRate;
import dto.CoinConversionRateDTO;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class Menu {

    private final DisplayMessage displayMessage = new DisplayMessage();
    private final ConversionLog conversionLog = new ConversionLog();
    private final CoinData coinData = new CoinData();

    public void mainMenuCurrency() {

        Scanner userInput = new Scanner(System.in);
        CoinCode codes = supportedCodes();
        Coin origin = new Coin();
        Coin target = new Coin();
        CoinConversionRate coinConversionRate = null;
        var userInputOption = "";
        var menuOption = "";

        while (menuOption.compareToIgnoreCase("s") != 0) {
            System.out.println("***** Menú Conversión Moneda. *****");
            System.out.println("""
                    1.- Mostrar lista de códigos disponibles.
                    2.- Ingresar monto.
                    3.- Ingresar código de moneda origen.
                    4.- Ingresar código de moneda destino.
                    5.- Realizar conversión.
                    6.- Consultar historial.
                    7.- Limpiar datos.
                    S.- Salir.
                    ***********************************""");
            System.out.print("Ingrese la opción deseada: ");
            userInputOption = userInput.nextLine();
            menuOption = userInputOption.trim().toLowerCase();
            switch (menuOption) {

                case "1":
                    System.out.println("\n+++Códigos Disponibles+++");
                    if (codes == null) {
                        codes = supportedCodes();
                    }
                    menuCoinCodeDetail(codes);
                    displayMessage.showContinue();
                    break;

                case "2":
                    System.out.println("\n+++Monto Origen.+++");
                    origin.setAmount(menuCoinAmount());
                    if(origin.getAmount().compareTo(new BigDecimal("0")) > 0){
                        System.out.println("Monto: " + origin.getAmount() );
                    }
                    displayMessage.showContinue();
                    break;

                case "3":
                    System.out.println("\n+++Código Moneda Origen.+++");
                    if (codes == null) {
                        codes = supportedCodes();
                        if(codes == null){
                            System.out.println("Error al obtener códigos.");
                            displayMessage.showContinue();
                            break;
                        }
                    }
                    Optional<Coin> originCode = menuCoinCode(codes);
                    if(originCode.isPresent()){
                        origin.setCode(originCode.get().getCode());
                        origin.setDescription(originCode.get().getDescription());
                        System.out.println(origin);
                    }else{
                        System.out.println("No se guardaron datos de moneda.");
                        origin.setCode(null);
                        origin.setDescription(null);
                    }
                    displayMessage.showContinue();
                    break;

                case "4":
                    System.out.println("\n+++Código Moneda Destino.+++");
                    if (codes == null) {
                        codes = supportedCodes();
                        if(codes == null){
                            System.out.println("Error al obtener códigos.");
                            displayMessage.showContinue();
                            break;
                        }
                    }
                    Optional<Coin> targetCode = menuCoinCode(codes);
                    if(targetCode.isPresent()){
                        target.setCode(targetCode.get().getCode());
                        target.setDescription(targetCode.get().getDescription());
                        System.out.println(target);
                    }else{
                        System.out.println("No se guardaron datos de moneda.");
                        target.setCode(null);
                        target.setDescription(null);
                    }
                    displayMessage.showContinue();
                    break;

                case "5":
                    System.out.println("\n+++Conversión de Moneda.+++");
                    if(origin.getCode()!= null){
                        Optional<CoinConversionRateDTO>  coinConversionRateDTO = Optional.empty();
                        if (coinConversionRate != null){
                            if(origin.getCode().compareToIgnoreCase(coinConversionRate.baseCode()) != 0){
                                coinConversionRateDTO = getConversionRate(origin);
                            }
                        }else{
                            coinConversionRateDTO = getConversionRate(origin);
                        }
                        if(coinConversionRateDTO.isPresent()){
                            coinConversionRate = new CoinConversionRate(coinConversionRateDTO.get().base_code(), coinConversionRateDTO.get().conversion_rates());
                        }
                        conversionCoin(origin, target, coinConversionRate);
                    }else{
                        System.out.println("\nLa moneda de origen es requerida par realizar el proceso de conversión.");
                    }

                    displayMessage.showContinue();
                    break;

                case "6":
                    System.out.println("\n++Historial conversiones.++");
                    try {
                        conversionLog.readLog();
                    } catch (RuntimeException e) {
                        System.out.println("No se encontró historial de conversiones.");
                    }
                    displayMessage.showContinue();
                    break;

                case "7":
                    System.out.println("\n+++Limpieza de datos+++");
                    codes = supportedCodes();
                    origin = new Coin();
                    target = new Coin();
                    coinConversionRate = null;
                    displayMessage.showContinue();
                    break;

                case "s":
                    System.out.println("\n*****Saliendo de Menú Conversión Moneda.*****");
                    break;

                default:
                    System.out.println("La opción ingresada no es válida.");
                    displayMessage.showContinue();
            }
        }

    }

    private CoinCode supportedCodes(){
        CoinData coinData = new CoinData();
        CoinCode codes = null;
        var codesData = coinData.allCoin();
        if(codesData.isPresent()){
            codes = new CoinCode(codesData.get().supported_codes());
        }
        return codes;
    }

    private BigDecimal menuCoinAmount() {
        Scanner userInput = new Scanner(System.in);
        BigDecimal currencyAmount = new BigDecimal("0");
        var userInputData = "";
        var errorAmount = Boolean.TRUE;

        while (errorAmount) {
            System.out.print("\nIngresa el monto (ejemplo: 123.99):");
            userInputData = userInput.nextLine();
            try {
                currencyAmount = new BigDecimal(userInputData.trim());
                errorAmount = Boolean.FALSE;
            } catch (NumberFormatException e) {
                System.out.println("\nEl monto ingresada no es válida: " + userInputData.trim() + "\n");
                errorAmount = displayMessage.errorInputData("Ingresar monto.", "Salir a Menú Conversión Moneda.");
            }
        }
        return currencyAmount;
    }

    private Optional<Coin> menuCoinCode(CoinCode coinCode) {

        Optional<Coin> coin = Optional.empty();
        Scanner userInput = new Scanner(System.in);
        var userInputData = "";
        var code = "";
        var errorCode = Boolean.TRUE;

        while (errorCode) {
            System.out.print("\nIngresa el código de la moneda (ejemplo: MXN): ");
            userInputData = userInput.nextLine();
            code = userInputData.trim().toUpperCase();

            //validar código
            if (coinCode.getCodes().containsKey(code)) {
                coin =  Optional.of(new Coin(code,coinCode.getCodes().get(code)));
                errorCode = Boolean.FALSE;
            }else{
                System.out.println("El código ingresado no es válido:" + code + "\n");
                errorCode = displayMessage.errorInputData("Ingresar código.", "Salir a Menú Conversión Moneda.");
            }
        }

        return coin;
    }

    private void menuCoinCodeDetail(CoinCode coinCode){
        var count = 1;
        var step = 16;
        boolean continuePage;
        for(var entry : coinCode.getCodes().entrySet()){
            continuePage = displayMessage.pageData("\nOprime la tecla 'c' para continuar con la lista códigos o la tecla 's' para dejar de mostrar códigos:"
                    , "Código: " + entry.getKey() + " --- Detalle: " + entry.getValue(),
                    "S", count, step);
            if(!continuePage){
                break;
            }
            count += 1;
        }
    }

    private Optional<CoinConversionRateDTO> getConversionRate (Coin origin){
        return coinData.allCoinConversion(origin.getCode());
    }

    private void conversionCoin(Coin origin, Coin target, CoinConversionRate coinConversionRate){
        if(origin.getCode() != null && !origin.getCode().isBlank()){
            if(coinConversionRate != null){
                if(target.getCode() != null && !target.getCode().isBlank()){
                    if (coinConversionRate.conversionRates().containsKey(target.getCode())) {
                        if(origin.getAmount() != null && origin.getAmount().compareTo(new BigDecimal("0"))> 0) {
                            BigDecimal conversionAmount = origin.getAmount().multiply(coinConversionRate.conversionRates().get(target.getCode())).setScale(2, RoundingMode.CEILING);
                            target.setAmount(conversionAmount);
                            try {
                                conversionLog.saveLog(origin, target, coinConversionRate.conversionRates().get(target.getCode()));
                                System.out.println("Conversión de " + origin.getCode() + "(" + origin.getDescription()  +")" + " --> " + target.getCode() + "(" + target.getDescription() +")");
                                System.out.println( origin.getAmount().setScale(2, RoundingMode.CEILING) + " " + origin.getCode() + " equivale a " + target.getAmount().setScale(2, RoundingMode.CEILING) + " " + target.getCode());
                            }catch (RuntimeException e){
                                System.out.println("Error al generar archivo de historial.");
                            }
                        }else{
                            System.out.println("Es necesario el monto a convertir.");
                        }
                    }else{
                        System.out.println("No esta el código en las divisas disponibles.");

                    }
                }else{
                    System.out.println("La moneda de destino es requerida par realizar el proceso de conversión.");
                }
            }else{
                System.out.println("No hay datos de conversión para la moneda origen");
            }
        }else{
            System.out.println("La moneda de origen es requerida par realizar el proceso de conversión.");
        }
    }

}