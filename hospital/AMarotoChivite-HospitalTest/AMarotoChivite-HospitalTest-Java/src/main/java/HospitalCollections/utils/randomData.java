package HospitalCollections.utils;

import java.util.Random;

public class randomData {

    public static String randomDni() {

        final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final int LETTERS_LENGTH = LETTERS.length();

        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        // Generamos 8 random dígitos
        for (int i = 0; i < 8; i++) {
            sb.append(random.nextInt(10));
        }

        sb.append(LETTERS.charAt(random.nextInt(LETTERS_LENGTH)));

        return sb.toString();
    }
}
