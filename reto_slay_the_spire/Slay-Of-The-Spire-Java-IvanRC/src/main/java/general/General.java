package general;

import java.util.Scanner;

public class General {
    private final static Scanner sc = new Scanner(System.in);
    public static int introducirNumero(int min, int max) {
        String numero = "";
        do{
            try{
                numero = sc.nextLine().trim();
                validarNumero(numero, min, max);
            }catch (Exception e){
                System.out.println(e.getMessage());
                numero = "";
            }
        }while(numero.equals(""));
        return Integer.parseInt(numero);
    }

    private static void validarNumero(String numero, int min, int max) {
        if(numero.isEmpty()){
            throw new IllegalArgumentException("Error al introducir número: El número no puede estar vacio, vuelva a probar:");
        }
        var regex = "-?[0-9]*";
        if(!numero.matches(regex)){
            throw new IllegalArgumentException("Error al introducir número: No ha introducido un número, vuelva a probar:");
        }
        int num = Integer.parseInt(numero);
        if(num < min || num > max){
            throw new IllegalArgumentException("Error al introducir número: El número debe estar entre "+min+" y "+max+", vuelva a probar:");
        }
    }
}
