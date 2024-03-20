/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.console;

import model.validations.UserDataValidations;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int choice;

            do {
                System.out.println("Menú:");
                System.out.println("1. Validar ID");
                System.out.println("2. Validar formato de fecha");
                System.out.println("3. Calcular edad");
                System.out.println("4. Validar código postal");
                System.out.println("5. Validar si es numérico");
                System.out.println("6. Validar si es alfabético");
                System.out.println("7. Validar correo electrónico");
                System.out.println("8. Validar nombre");
                System.out.println("0. Salir");

                System.out.print("Ingrese su elección: ");
                choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        System.out.print("Ingrese el tipo de documento (1 para NIF): ");
                        int typeDoc = scanner.nextInt();
                        System.out.print("Ingrese el ID: ");
                        String id = scanner.next();
                        boolean idValid = UserDataValidations.checkId(typeDoc, id);
                        System.out.println("ID válido: " + idValid);
                        break;
                    case 2:
                        System.out.print("Ingrese una fecha (dd/mm/yyyy): ");
                        String date = scanner.next();
                        boolean dateValid = UserDataValidations.checkFormatDate(date);
                        System.out.println("Formato de fecha válido: " + dateValid);
                        break;
                    case 3:
                        System.out.print("Ingrese la fecha de nacimiento (dd/mm/yyyy): ");
                        String birthDate = scanner.next();
                        int age = UserDataValidations.calculateAge(birthDate);
                        System.out.println("Edad calculada: " + age);
                        break;
                    case 4:
                        System.out.print("Ingrese el código postal: ");
                        String zip = scanner.next();
                        boolean zipValid = UserDataValidations.checkPostalCode(zip);
                        System.out.println("Código postal válido: " + zipValid);
                        break;
                    case 5:
                        System.out.print("Ingrese un valor: ");
                        String numericStr = scanner.next();
                        boolean isNumeric = UserDataValidations.isNumeric(numericStr);
                        System.out.println("Es numérico: " + isNumeric);
                        break;
                    case 6:
                        System.out.print("Ingrese un valor: ");
                        String alphabeticStr = scanner.next();
                        boolean isAlphabetic = UserDataValidations.isAlphabetic(alphabeticStr);
                        System.out.println("Es alfabético: " + isAlphabetic);
                        break;
                    case 7:
                        System.out.print("Ingrese un correo electrónico: ");
                        String email = scanner.next();
                        boolean emailValid = UserDataValidations.checkEmail(email);
                        System.out.println("Correo electrónico válido: " + emailValid);
                        break;
                    case 8:
                        System.out.print("Ingrese un nombre: ");
                        String name = scanner.next();
                        boolean nameValid = UserDataValidations.checkName(name);
                        System.out.println("Nombre válido: " + nameValid);
                        break;
                    case 0:
                        System.out.println("Saliendo del programa...");
                        break;
                    default:
                        System.out.println("Opción no válida. Inténtelo de nuevo.");
                        break;
                }
            } while (choice != 0);
        }
    }
}
