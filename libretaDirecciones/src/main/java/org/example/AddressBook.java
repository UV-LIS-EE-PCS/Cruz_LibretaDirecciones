package org.example;

import static org.example.Menu.delimiter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import org.jetbrains.annotations.NotNull;

public class AddressBook {

    public Scanner input = new Scanner(System.in);
    public ArrayList<AddressEntry> entries;

    public AddressBook(){
        entries = new ArrayList<>();
    }

    public void load(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length == 8) {
                    String firstName = parts[0].trim();
                    String lastName = parts[1].trim();
                    String street = parts[2].trim();
                    String city = parts[3].trim();
                    String state = parts[4].trim();
                    String zipCode = parts[5].trim();
                    String email = parts[6].trim();
                    String phone = parts[7].trim();

                    AddressEntry newEntry = new AddressEntry(firstName, lastName, street, city, state, zipCode, email, phone);
                    entries.add(newEntry);

                } else {
                    System.err.println("Error: formato de línea incorrecto en el archivo.");
                }
            }

            System.out.println("Datos cargados correctamente desde el archivo.");

        } catch (IOException e) {
            System.err.println("Error al cargar datos desde el archivo: " + e.getMessage());
        }
    }

    public void addAddressBook(){

        System.out.println("nombre: ");
        String firstName = input.nextLine();
        System.out.println("Apellido: ");
        String lastName= input.nextLine();
        System.out.println("Calle: ");
        String street = input.nextLine();
        System.out.println("ciudad: ");
        String city = input.nextLine();
        System.out.println("Estado: ");
        String state = input.nextLine();
        System.out.println("CP: ");
        String zipCode = input.nextLine();
        System.out.println("Email: ");
        String email = input.nextLine();
        System.out.println("Teláfono: ");
        String phone = input.nextLine();

        if (isValidInput(firstName, lastName, street, city, zipCode, email, phone)) {

            AddressEntry newAddress = new AddressEntry(firstName, lastName, street, city, state, zipCode, email, phone);
            entries.add(newAddress);
            System.out.println("¡Entrada agregada correctamente!");

        } else {
            System.out.println("Campos vacios. La entrada no fue agregada.");
        }
    }

    public void removeAddress() {
        if (entries.isEmpty()) {
            System.out.println("La lista de contactos está vacía.");
            return;
        }

        System.out.println("Ingrese las iniciales del apellido a eliminar:");
        String lastNameInitials = input.nextLine().trim();

        ArrayList<AddressEntry> matchingEntries = findMatchingEntries(lastNameInitials);

        if (matchingEntries.isEmpty()) {
            System.out.println("No se encontraron contactos para las iniciales de apellido proporcionadas.");
            return;
        }

        if (matchingEntries.size() == 1) {
            processUniqueMatch(matchingEntries.get(0));
        } else {
            processMultipleMatches(matchingEntries);
        }
    }

    private ArrayList<AddressEntry> findMatchingEntries(String lastName) {
        ArrayList<AddressEntry> matchingEntries = new ArrayList<>();
        for (AddressEntry entry : entries) {
            if (entry.getLastName().toLowerCase().startsWith(lastName.toLowerCase())) {
                matchingEntries.add(entry);
            }
        }
        return matchingEntries;
    }

    private void processUniqueMatch(AddressEntry entry) {
        System.out.println("Se encontró el siguiente contacto:");
        showEntry(entry);

        System.out.println("¿Desea eliminar este contacto? (y/n)");
        String option = input.nextLine();

        if (option.equalsIgnoreCase("y")) {
            entries.remove(entry);
            System.out.println("Contacto eliminado correctamente.");
        } else if (option.equalsIgnoreCase("n")) {
            System.out.println("No se ha eliminado el contacto.");
        } else {
            System.out.println("Opción no válida. No se ha eliminado el contacto.");
        }
    }

    private void processMultipleMatches(ArrayList<AddressEntry> matchingEntries) {
        System.out.println("Se encontraron múltiples contactos con el apellido proporcionado:");
        for (int i = 0; i < matchingEntries.size(); i++) {
            System.out.print((i + 1) + ":");
            showEntry(matchingEntries.get(i));
        }

        System.out.println("Ingrese el número del contacto que desea eliminar:");
        int contactNumber;
        try {
            contactNumber = Integer.parseInt(input.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Número no válido. No se ha eliminado el contacto.");
            return;
        }

        if (contactNumber < 1 || contactNumber > matchingEntries.size()) {
            System.out.println("Número no válido. No se ha eliminado el contacto.");
            return;
        }

        AddressEntry entryToRemove = matchingEntries.get(contactNumber - 1);
        System.out.println("Contacto seleccionado para eliminar:");
        showEntry(entryToRemove);

        System.out.println("¿Desea eliminar este contacto? (y/n)");
        String option = input.nextLine();

        if (option.equalsIgnoreCase("y")) {
            entries.remove(entryToRemove);
            System.out.println("Contacto eliminado correctamente.");
        } else if (option.equalsIgnoreCase("n")) {
            System.out.println("No se ha eliminado el contacto.");
        } else {
            System.out.println("Opción no válida. No se ha eliminado el contacto.");
        }
    }

    public static void showEntry(AddressEntry entry) {
        System.out.println(entry.toString());
    }

    public void searchAddress() {
        try {
            System.out.println("Ingrese el apellido del contacto a buscar:");
            String lastName = input.nextLine().trim();

            if (lastName.isEmpty()) {
                return;
            }

            boolean found = false;

            for (AddressEntry entry : entries) {
                if (entry.getLastName().startsWith(lastName)) {
                    System.out.println("Dirección encontrada:");
                    System.out.println(entry.toString());
                    found = true;
                }
            }

            if (!found) {
                System.out.println("No se encontró ninguna dirección para el apellido ingresado.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Error: Entrada no válida.");
        } catch (Exception e) {
            System.out.println("Ocurrió un error inesperado: " + e.getMessage());
        }
    }

    public static void showEntry(String lastName, @NotNull ArrayList<AddressEntry> entries){
        for (AddressEntry entry : entries) {
            if (entry.getLastName().equals(lastName)) {
                System.out.println(entry.toString());
            }
        }
    }

    public void getAddressBook() {
        int i = 1;

        if (entries.isEmpty()) {
            System.out.println("El libro de direcciones está vacío.");
        }
        else {
            System.out.println("Direcciones en el libro de direcciones:");

            for (AddressEntry entry : entries) {
                System.out.println(i + ": " + entry.toString());
                delimiter();
                i++;
            }
        }
    }

    private boolean isValidInput(String firstName, String lastName, String street, String city, String zipCode, String email, String phone) {
        return firstName != null && !firstName.isEmpty() &&
                lastName != null && !lastName.isEmpty() &&
                street != null && !street.isEmpty() &&
                city != null && !city.isEmpty() &&
                zipCode != null && !zipCode.isEmpty() &&
                email != null && !email.isEmpty() &&
                phone != null && !phone.isEmpty();
    }

    public void enter() {
        System.out.println("Presione Enter para continuar...");
        input.nextLine();
    }
}