package model.validations;

import java.time.LocalDate;

/**
 * Esta clase proporciona métodos para validar información de usuarios.
 * 
 * Autor: angelvintos
 */
public class UserDataValidations {

    public static boolean checkId(int typeDoc, String id) {
        // Solo admitimos NIF, así que si el tipo de documento no es 1, es incorrecto
        // Esto se podra usar si la persona no tiene DNI y tiene NIE o pasaporte, para asi cambiar la validacion del formato
        if (typeDoc != 1) {
            return false;
        }
        // Validamos el formato del NIF (DNI)
        // Comprobamos si tiene 9 caracteres
        return id.length() == 9;
    }


    public static boolean checkFormatDate(String date) {
        // Utilizamos una expresión regular para verificar el formato de la fecha
        return date.matches("\\d{2}/\\d{2}/\\d{4}");
    }


    public static int calculateAge(String birthDate) {
        // Extraemos el año de nacimiento 
        int birthYear = Integer.parseInt(birthDate.substring(6));
        
        // Obtenemos el año actual
        int currentYear = LocalDate.now().getYear();
     
        // Calculamos la diferencia entre el año actual y el año de nacimiento para obtener la edad
        return currentYear - birthYear;
    }


    public static boolean checkPostalCode(String zip) {
        // Validamos el código postal
        // Esperamos que tenga 5 caracteres y que todos sean numeros
        return zip.length() == 5 && zip.chars().allMatch(Character::isDigit);
    }

    public static boolean isNumeric(String str) {
        // Validamos que han introducido numeros
        return str.chars().allMatch(Character::isDigit);
    }
    

    public static boolean isAlphabetic(String str) {
        // Comprobamos si todos los caracteres son letras
        return str.chars().allMatch(Character::isLetter);
    } 
    

    public static boolean checkEmail(String email) {
        // Validamos el formato del correo electrónico
        // Tendra que contener "@" y que termine en ".com", ".es".
        return email.contains("@") && (email.endsWith(".com") || email.endsWith(".es")); // Se pueden agregar más dominios según sea necesario
    }


    public static boolean checkName(String name) {
        // Validamos el nombre: esperamos una longitud mínima de 2 caracteres y que no contenga números
        return name.length() >= 2 && !name.chars().anyMatch(Character::isDigit);
    }
}
