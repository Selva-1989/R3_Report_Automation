package com.r3.utility;

import me.xdrop.fuzzywuzzy.FuzzySearch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Fuzzy {
    public static double maxScore = 0;
    public static String bestMatch = "";
    public static boolean searchLogic(double thresholdScore, String orgNameSting, String searchLinkHeadersString,
                                      String trueOrgName, String trueLinkHeader) {
        // Expected ORG Name
        String orgName = orgNameSting.toLowerCase();

        // Generate searchLinkHeaders
        String searchLinkHeader = searchLinkHeadersString.toLowerCase();
        //List<String> searchLinkHeaders = generateSearchLinkHeaders(searchLinkHeader);

        // Start String match operation
        boolean isMatched = performStringMatch(thresholdScore, orgName, searchLinkHeader, trueOrgName, trueLinkHeader);
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

    private static String extractFirstWordTwoCharAndFirstCharOfEachWord(String input) {
        StringBuilder abbreviation = new StringBuilder();
        // Split the input string into words
        String[] words = input.split("\\s+");
        // Extract the first two characters of the first word
        if (words.length > 0) {
            abbreviation.append(words[0].substring(0, Math.min(2, words[0].length())).toLowerCase());
            // Concatenate the first character of subsequent words
            for (int i = 1; i < words.length; i++) {
                abbreviation.append(words[i].charAt(0));
            }
        }
        return abbreviation.toString();
    }

    // Perform string match operation using fuzzy string matching
    public static boolean performStringMatch(double thresholdScore, String orgName, String searchLinkHeader,
                                             String trueOrgName, String trueLinkHeader) {
         maxScore = 0;
         bestMatch = "";
        String FirstWordOrgName = trueOrgName.split("\\s")[0].toLowerCase(); //actual string1
        String firstWordOfLinkHeader = trueLinkHeader.split("\\s")[0].
                                                        replace(".com", "").
                                                        replace(".in", "").
                                                        replace(".org", "").
                                                        replace(".edu", "").
                                                        replace(".net", "").
                                                        replace(".", "").
                                                        replace("-", "").
                                                        replace(" ", "").
                                                        replace("https://www.", "").
                                                        replace("www.", "");;

        // Word list to remove from the string
        List<String> wordList = new ArrayList<>(Arrays.asList("and", "or", "in", "for", "&", "of", "on", "is", "at", "has", "had", "a", "to"));
        // Remove words from the given string
        String cleanedORGNameString = removeWords(trueOrgName, wordList);
        // String 2: first character of each word in the ORG Name
        StringBuilder string2Builder = new StringBuilder();
        String[] words = cleanedORGNameString.split("\\s+");
        for (String word : words) {
            string2Builder.append(word.charAt(0));
        }
        String firstCharOfEachOrgName = string2Builder.toString();//Expected string

        // first character of each word in the Link Header
        String cleanedLinkeHeaderString = removeWords(trueLinkHeader, wordList);
        StringBuilder string2BuilderLinkHeader = new StringBuilder();
        String[] wordsLinkHeaderArray = cleanedLinkeHeaderString.toLowerCase().split("\\s+");
        for (String word : wordsLinkHeaderArray) {
            string2BuilderLinkHeader.append(word.charAt(0));
        }
        String firstCharOfEachWordLinkHeader = string2BuilderLinkHeader.toString();

        String FirstWordTwoCharAndFirstCharOfEachWordLinkHeader = extractFirstWordTwoCharAndFirstCharOfEachWord(trueLinkHeader);


        double wholeFirstWordMatchScore1 = FuzzySearch.ratio(FirstWordOrgName.toLowerCase(), firstWordOfLinkHeader.toLowerCase());
        double wholeFirstWordMatchScore2 = FuzzySearch.ratio(firstCharOfEachOrgName.toLowerCase(), firstWordOfLinkHeader.toLowerCase());
        double wholeFirstWordMatchScore3 = FuzzySearch.ratio(FirstWordOrgName.toLowerCase(), firstCharOfEachWordLinkHeader.toLowerCase());
        double wholeFirstWordMatchScore4 = FuzzySearch.ratio(FirstWordOrgName.toLowerCase(), FirstWordTwoCharAndFirstCharOfEachWordLinkHeader.toLowerCase());
        if ((wholeFirstWordMatchScore1 >=85 || wholeFirstWordMatchScore2 >=60 ||
             wholeFirstWordMatchScore3 >=60 || wholeFirstWordMatchScore4 >=60) &&
             searchLinkHeader.length() != 0) {
            double score = FuzzySearch.ratio(orgName.toLowerCase(), searchLinkHeader.toLowerCase());
            if (score >= thresholdScore && score > maxScore) {
                maxScore = score;
                bestMatch = searchLinkHeader;
            }
        }

        if (!bestMatch.isEmpty()) {
            System.out.println("Best Match: " + bestMatch + ", Score: " + maxScore);
        } else {
            System.out.println("No match found with score >= " + thresholdScore);
        }
        return maxScore >= thresholdScore;
    }
}