package com.r3.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NearestProviderPhoneNumber {
    static Map<String,String> resultMap = new HashMap<>();
    static String Result = null;;
    static String Message = null;
    public static Map<String,String> findProviderPhoneNumberLogic(String providerName, String expectedPhoneNumber, String webPageContent) {
        // Call the method to match provider and find duplicate phone numbers
        Map<String, List<Integer>> matchedPhoneNumbers = findDuplicatePhoneNumbers(providerName, webPageContent);
        // Find the nearest phone number associated with the provider name
        String nearestPhoneNumber = findNearestPhoneNumber(providerName, webPageContent);
        if (!matchedPhoneNumbers.isEmpty()) {
            System.out.println("Matched Phone Numbers:");
            for (Map.Entry<String, List<Integer>> entry : matchedPhoneNumbers.entrySet()) {
                String phoneNumber = entry.getKey();
                List<Integer> distances = entry.getValue();
                System.out.println("Phone: " + phoneNumber + ", Distances: " + distances);
            }

            // Find the phone number with the minimum distance
            String nearestPhoneNumberFromDuplicates = findNearestPhoneNumber(matchedPhoneNumbers);
            int minDistance = matchedPhoneNumbers.get(nearestPhoneNumberFromDuplicates).stream().min(Integer::compareTo).orElse(-1);

            // Check if the nearest phone number matches the expected phone number
            if (nearestPhoneNumberFromDuplicates.equals(expectedPhoneNumber)) {
                Result = "PROVPHONE";
                Message = ("The given number " + expectedPhoneNumber + " is the provider's phone number and also the nearest phone number from the provider name.");
                resultMap.put(Result,Message);
            } else {
                Result = "NOTPROVPHONE";
                Message = ("The nearest phone number from the provider name (" + nearestPhoneNumberFromDuplicates + ") is not the expected phone number (" + expectedPhoneNumber + ").");
                resultMap.put(Result,Message);
            }
        } else {
            System.out.println("No matched phone numbers found for the provider.");

            if (nearestPhoneNumber != null) {
                if (nearestPhoneNumber.equals(expectedPhoneNumber)) {
                    Result = "PROVPHONE";
                    Message = ("The given number " + expectedPhoneNumber + " is the provider's phone number and also the nearest phone number from the provider name.");
                    resultMap.put(Result,Message);
                } else {
                    Result = "NOTPROVPHONE";
                    Message = ("The nearest phone number from the provider name (" + nearestPhoneNumber + ") is not the expected phone number (" + expectedPhoneNumber + ").");
                    resultMap.put(Result,Message);
                }
            } else {
                System.out.println("FAIL: No phone number associated with the provider name found.");
            }
        }
        return resultMap;
    }

    public static Map<String, List<Integer>> findDuplicatePhoneNumbers(String providerName, String content) {
        // Define patterns for extracting provider name and phone numbers
        Pattern providerPattern = Pattern.compile(providerName);
        Pattern phonePattern = Pattern.compile("(\\d{3}-\\d{3}-\\d{4})");

        // Match provider name
        Matcher providerMatcher = providerPattern.matcher(content);
        Map<String, List<Integer>> matchedPhoneNumbers = new HashMap<>();

        while (providerMatcher.find()) {
            // Match all phone numbers associated with the provider
            Matcher phoneMatcher = phonePattern.matcher(content);
            int providerIndex = providerMatcher.start();

            while (phoneMatcher.find()) {
                String phoneNumber = phoneMatcher.group(1);
                int phoneIndex = phoneMatcher.start();
                int distance = Math.abs(providerIndex - phoneIndex);

                // Add the phone number and its distance to the map
                matchedPhoneNumbers.computeIfAbsent(phoneNumber, k -> new ArrayList<>()).add(distance);
            }
        }

        return matchedPhoneNumbers;
    }

    public static String findNearestPhoneNumber(String providerName, String content) {
        // Define patterns for extracting provider name and phone numbers
        Pattern phonePattern = Pattern.compile("(\\d{3}-\\d{3}-\\d{4})");

        // Match all phone numbers associated with the provider name
        Matcher phoneMatcher = phonePattern.matcher(content);
        int nearestDistance = Integer.MAX_VALUE;
        String nearestPhoneNumber = null;
        int providerIndex = content.indexOf(providerName);

        while (phoneMatcher.find()) {
            String phoneNumber = phoneMatcher.group(1);
            int phoneIndex = phoneMatcher.start();
            int distance = Math.abs(providerIndex - phoneIndex);

            // Update nearest phone number if the distance is smaller
            if (distance < nearestDistance) {
                nearestDistance = distance;
                nearestPhoneNumber = phoneNumber;
            }
        }

        return nearestPhoneNumber;
    }

    public static String findNearestPhoneNumber(Map<String, List<Integer>> matchedPhoneNumbers) {
        // Find the phone number with the minimum distance
        String nearestPhoneNumber = null;
        int minDistance = Integer.MAX_VALUE;

        for (Map.Entry<String, List<Integer>> entry : matchedPhoneNumbers.entrySet()) {
            List<Integer> distances = entry.getValue();
            for (Integer distance : distances) {
                if (distance < minDistance) {
                    minDistance = distance;
                    nearestPhoneNumber = entry.getKey();
                }
            }
        }

        return nearestPhoneNumber;
    }
}