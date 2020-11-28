package db;

import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

/**
 *
 * @author felip
 */
public class validarStrings {



    //SCANNER PARA OBTENER EL INPUT
    private Scanner string;

    //VALIDAR QUE EL INPUT ES STRING
    public validarStrings() {
        string = new Scanner(System.in);
        string.useDelimiter("\n"); //Usado para nextLine()
        string.useLocale(Locale.US); // Para double
    }
    public String pedirString(String mensaje) {
        System.out.println(mensaje);
        String cadena = string.next();
        return cadena;
    }
    //VALIDAR INT POSITIVO
    public int pedirIntPositivo(String mensaje) {

        int num;
        do {
            try {
                System.out.println(mensaje);
                num = string.nextInt();
            } catch (InputMismatchException ex) {
                // En caso de error, el num se marca como uno negativo
                num = -1;
                string.next();
            }

            if (num < 0) {
                System.out.println("Error, introducce un numero entero positivo integer");
            }

        } while (num < 0);

        return num;
    }



}
