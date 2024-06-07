package org.example;

public class Menu {

    public static void displayMenu(){
        delimiter();
        System.out.println("Elige una opción del menú");
        System.out.println("a) Carga de archivo\n"
                            + "b) Agregar\n"
                            + "c) Eliminar\n"
                            + "d) Buscar\n"
                            + "e) Mostrar\n"
                            + "f) Salir");
        delimiter();
    }
    public static void delimiter(){
        System.out.println("==============================");
    }

}
