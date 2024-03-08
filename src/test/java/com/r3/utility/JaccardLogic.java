package com.r3.utility;


import org.apache.commons.text.similarity.JaccardSimilarity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JaccardLogic {
    public static double maxScore = 0;
    public static String bestMatch = "";
    public static boolean searchLogic(double thresholdScore, String orgNameSting, String searchLinkHeadersString) {
        // Expected ORG Name
        String orgName = orgNameSting.toLowerCase();

        // Generate searchLinkHeaders
        String searchLinkHeader = searchLinkHeadersString.toLowerCase();
        //List<String> searchLinkHeaders = generateSearchLinkHeaders(searchLinkHeader);

        // Start String match operation
        boolean isMatched = performStringMatch(thresholdScore, orgName, searchLinkHeader);
        System.out.println("Is matched: " + isMatched);
        return isMatched;
    }

    // Method to remove words from a given string
    private static String removeWords(String input, List<String> wordsToRemove) {
        for (String word : wordsToRemove) {
            input = input.replaceAll("\\b" + word + "\\b", "");
        }
        return input.trim();
    }
    // Perform string match operation using fuzzy string matching
    public static boolean performStringMatch(double thresholdScore, String orgName, String searchLinkHeader) {
         maxScore = 0;
         bestMatch = "";

        // Word list to remove from the string
        List<String> wordList = new ArrayList<>(Arrays.asList("and", "or", "in", "for", "&", "of", "on", "is", "at", "has", "had", "a", "to"));
        // Remove words from the given string
        String cleanedORGNameString = removeWords(orgName, wordList);
        // String 2: first character of each word in the ORG Name
        StringBuilder string2Builder = new StringBuilder();
        String[] words = cleanedORGNameString.split("\\s+");
        for (String word : words) {
            string2Builder.append(word.charAt(0));
        }
        String firstCharOfEachOrgName = string2Builder.toString();//Expected string

        JaccardSimilarity jaccardSimilarity = new JaccardSimilarity();

        if (searchLinkHeader.length() != 0 ) {
            double score = jaccardSimilarity.apply(orgName.toLowerCase(), searchLinkHeader.toLowerCase())*100;
            if (score >= thresholdScore && score > maxScore) {
                maxScore = score;
                bestMatch = searchLinkHeader;
            }
        }

        /*if (searchLinkHeader.length() != 0) {
            String firstWordOfORGName = orgName.split("\\s+")[0];
            double score = jaroWinkler.apply(firstWordOfORGName.toLowerCase(),searchLinkHeader.toLowerCase())* 100;
            if (score >= thresholdScore && score > maxScore) {
                maxScore = score;
                bestMatch = searchLinkHeader;
            }
        }*/

        if (!bestMatch.isEmpty()) {
            System.out.println("Best Match: " + bestMatch + ", Score: " + maxScore);
        } else {
            System.out.println("No match found with score >= " + thresholdScore);
        }
        return maxScore >= thresholdScore;
    }
}