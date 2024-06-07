package org.example;

import java.util.Scanner;
import static org.example.Menu.displayMenu;

public class AddressBookApplication {
    public static void main(String[] args) {

        try (Scanner input = new Scanner(System.in)) {
            AddressBook addressBook = new AddressBook();

            String choice;

            while(true) {
                displayMenu();
                choice = input.nextLine();

                switch (choice.toLowerCase()) {
                    case "a":
                        System.out.println("Ingrese el nombre del archivo:");
                        String fileName = input.nextLine();
                        addressBook.load(fileName);
                        addressBook.enter();
                        break;
                    case "b":
                        addressBook.addAddressBook();
                        addressBook.enter();
                        break;
                    case "c":
                        addressBook.removeAddress();
                        addressBook.enter();
                        break;
                    case "d":
                        addressBook.searchAddress();
                        addressBook.enter();
                        break;
                    case "e":
                        addressBook.getAddressBook();
                        addressBook.enter();
                        break;
                    case "f":
                        System.exit(0);
                    default:
                        System.out.println("Ingrese una opción válida.");
                        addressBook.enter();
                }
            }
        }
    }
}