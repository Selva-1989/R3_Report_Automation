package com.r3.utility;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class CheckProviderName {
    public static  Map<Boolean, String> findProviderName (String firstName, String actualMiddleName,
                                                          String lastName, String credentials,  String webContent) {
        Map<Boolean, String> map = new HashMap<>();
        try {
            if (actualMiddleName.isEmpty() ||
                    actualMiddleName.equalsIgnoreCase("")||
                    actualMiddleName.equalsIgnoreCase(null)){
                actualMiddleName="";
            }
        } catch (NullPointerException e) {
            actualMiddleName="";
        }

        try {
            if (credentials.isEmpty() ||
                    credentials.equalsIgnoreCase("")||
                    credentials.equalsIgnoreCase(null)){
                credentials="";
            }
        } catch (NullPointerException e) {
            credentials="";
        }

        // Remove special characters from web content
        webContent = removeSpecialCharacters(webContent);

        // Remove special characters from combinations
        String[] combinations = {
                removeSpecialCharacters(firstName + " " + actualMiddleName + " " + lastName + " " + credentials),
                removeSpecialCharacters(firstName + " " + actualMiddleName + " " + lastName),
                removeSpecialCharacters(firstName + " " + lastName + " " + credentials),
                removeSpecialCharacters(firstName + " " + lastName)
        };

        boolean combinationFound = false;
        for (String combination : combinations) {
            if (webContent.toLowerCase().contains(combination.toLowerCase())) {
                combinationFound = true;
                System.out.println("Pass: Combination \"" + combination + "\" is present in the web content.");
                map.put(combinationFound, combination);
                break;
            }
        }

        if (!combinationFound) {
            System.out.println("Fail: ");
            map.put(combinationFound, "Provider Name NOT found in web content.");
        }
        return map;
    }

    private static String removeSpecialCharacters(String text) {
        // Define the regular expression pattern to remove special characters
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9\\s]");
        // Replace all special characters with empty string
        return pattern.matcher(text).replaceAll("");
    }
}