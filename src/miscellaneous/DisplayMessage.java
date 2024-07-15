package miscellaneous;

import java.util.Scanner;

public class DisplayMessage {

    public void showContinue(){
        Scanner userInput = new Scanner(System.in);

        System.out.print("\nPresiona la tecla 'enter' para continuar...");
        userInput.nextLine();

    }

    public Boolean errorInputData(String textOption, String textExit){
        Scanner userInput = new Scanner(System.in);

        var userInputData = "";
        var menuOption = "";
        boolean retryInput = Boolean.FALSE;

        while ((menuOption.compareToIgnoreCase("1") != 0) && (menuOption.compareToIgnoreCase("s") != 0)) {
            System.out.println("1.- " + textOption);
            System.out.println("S.- " + textExit);
            System.out.print("\nIngrese la opción deseada: ");
            userInputData = userInput.nextLine();
            menuOption = userInputData.trim().toLowerCase();
            switch (menuOption) {
                case "1":
                    retryInput = Boolean.TRUE;
                    break;
                case "s":
                    break;
                default:
                    System.out.println("La opción ingresada no es válida.\n");
            }
        }
        return retryInput;
    }

    public Boolean pageData(String textContinue, String textLine, String textStop, int count, int step){
        Scanner userInput = new Scanner(System.in);
        boolean continuePage = Boolean.TRUE;
        if((count % step) == 0){
            System.out.print(textContinue);
            var selectedOption = userInput.nextLine().trim().toUpperCase();
            if (selectedOption.equals(textStop)) {
                continuePage =  Boolean.FALSE;
            } else {
                System.out.println(textLine);
            }
        }else{
            System.out.println(textLine);
        }

        return continuePage;
    }
}