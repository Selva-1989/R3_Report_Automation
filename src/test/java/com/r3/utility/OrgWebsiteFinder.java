package com.r3.utility;

import java.net.URL;
import java.util.*;

public class OrgWebsiteFinder {
    public static Set<String> WebsiteFinder_OrgCache(List<String> orgNameURListWithAllWebSites) {
        return findUniqueUrls(orgNameURListWithAllWebSites);
    }

    public static Set<String> findUniqueUrls(List<String> orgNameURListWithAllWebSites) {
        Set<String> uniqueUrls = new HashSet<>();

        for (String urlString : orgNameURListWithAllWebSites) {
            try {
                URL url = new URL(urlString);
                String mainUrl = url.getProtocol() + "://" + url.getHost() + (url.getPort() != -1 ? ":" + url.getPort() : "");
                uniqueUrls.add(mainUrl);
            } catch (Exception e) {
                // Handle malformed URLs or other exceptions
                e.printStackTrace();
            }
        }
        return uniqueUrls;
    }

    public static Set<String> findUniqueUrls(String orgNameURListWithAllWebSites) {
        // Convert the single string URL to a list and call the existing method
        return findUniqueUrls(Collections.singletonList(orgNameURListWithAllWebSites));
    }
}