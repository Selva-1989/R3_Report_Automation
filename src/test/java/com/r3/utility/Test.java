package com.r3.utility;


public class Test {
    public static void main(String[] args) {
        String input = "dfwd deesd wwdff";
        String answer = extractAbbreviation(input);
        System.out.println("Answer: " + answer);
    }

    private static String extractAbbreviation(String input) {
        StringBuilder abbreviation = new StringBuilder();

        // Split the input string into words
        String[] words = input.split("\\s+");

        // Extract the first two characters of the first word
        if (words.length > 0) {
            abbreviation.append(words[0].substring(0, Math.min(2, words[0].length())).toUpperCase());

            // Concatenate the first character of subsequent words
            for (int i = 1; i < words.length; i++) {
                abbreviation.append(words[i].charAt(0));
            }
        }

        return abbreviation.toString();
    }
}