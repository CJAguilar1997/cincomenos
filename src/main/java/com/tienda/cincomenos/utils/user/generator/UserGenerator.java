package com.tienda.cincomenos.utils.user.generator;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

public class UserGenerator {

    public static Map<String, String> generate(String nombre) {
        Map<String, String> userLogin = new HashMap<>();
        userLogin.put("username", generateUsername(nombre));
        userLogin.put("password", generatePassword());
        return userLogin;
    }


    private static String generateUsername(String nombre) {
        String[] nameInPieces = nombre.split(" ");
        StringBuilder username = new StringBuilder();
        username.append(nameInPieces[0] + ".");

        if (nameInPieces.length > 2) {
            username.append(nameInPieces[2]);
        } else {
            username.append(nameInPieces[1]);
        }
        return username.toString().toLowerCase();
    }

    
    private static String generatePassword() {

        final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        final int PASSWORD_LENGTH = 12;

        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            password.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }

        // Mezclar la contraseña para evitar patrones
        char[] passwordArray = password.toString().toCharArray();
        for (int i = passwordArray.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            char temp = passwordArray[index];
            passwordArray[index] = passwordArray[i];
            passwordArray[i] = temp;
        }

        return new String(passwordArray);
    }
}
