package com.r3.utility;

import java.net.URL;
import java.util.*;

public class OrgWebsiteFinder {
    public static String WebsiteFinder_OrgCache(List<String> orgNameURListWithAllWebSites) {
        Set<String> finalUniqueURLSet = findHighestOccurrenceSimilarWebsite(orgNameURListWithAllWebSites);

        // Print the final unique URLs
        for (String url : finalUniqueURLSet) {
            return url;
        }
        return null;
    }

    public static Set<String> findHighestOccurrenceSimilarWebsite(List<String> orgNameURListWithAllWebSites) {
        Map<String, Integer> domainOccurrences = new HashMap<>();
        Map<String, String> domainToUrlMap = new HashMap<>();

        for (String urlString : orgNameURListWithAllWebSites) {
            try {
                URL url = new URL(urlString);
                String domain = url.getHost(); // Include 'www' in the domain

                // Get the main URL till the first slash
                String mainUrl = url.getProtocol() + "://" + url.getHost() + (url.getPort() != -1 ? ":" + url.getPort() : "");

                // Update domain occurrences
                int count = domainOccurrences.getOrDefault(domain, 0);
                domainOccurrences.put(domain, count + 1);

                // Update domain to URL mapping
                domainToUrlMap.put(domain, mainUrl);
            } catch (Exception e) {
                // Handle malformed URLs or other exceptions
                e.printStackTrace();
            }
        }

        // Find the maximum occurrence
        int maxOccurrence = Collections.max(domainOccurrences.values());

        // Return URLs with the maximum occurrence
        Set<String> finalUniqueURLSet = new HashSet<>();
        for (Map.Entry<String, Integer> entry : domainOccurrences.entrySet()) {
            if (entry.getValue() == maxOccurrence) {
                finalUniqueURLSet.add(domainToUrlMap.get(entry.getKey()));
            }
        }

        return finalUniqueURLSet;
    }
}