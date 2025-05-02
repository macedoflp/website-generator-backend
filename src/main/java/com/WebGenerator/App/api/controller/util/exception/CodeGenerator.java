package com.WebGenerator.App.api.controller.util.exception;

import java.util.Random;

public class CodeGenerator {

    public static String generateCode(int digits) {
        if (digits <= 0) {
            throw new IllegalArgumentException("A quantidade de dígitos deve ser maior que zero.");
        }

        Random random = new Random();
        int lowerBound = (int) Math.pow(10, digits - 1);
        int upperBound = (int) Math.pow(10, digits) - 1;

        // Caso digits == 1, o lowerBound deve ser 0
        if (digits == 1) {
            lowerBound = 0;
        }

        int code = lowerBound + random.nextInt(upperBound - lowerBound + 1);
        return String.valueOf(code);
    }
}
