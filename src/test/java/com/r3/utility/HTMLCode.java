package com.r3.utility;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTMLCode {
    static String html;

    public static String get(String Url) throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.custom().setRedirectStrategy(new LaxRedirectStrategy()).build()) {
            HttpGet httpGet = new HttpGet(Url);
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    HttpEntity entity = response.getEntity();
                    html = EntityUtils.toString(entity);
                } else {
                    System.err.println("HTTP Error: " + statusCode);
                    URL urlObj = new URL(Url);
                    HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
                    connection.setRequestMethod("GET");
                    BufferedReader reader = new BufferedReader(new InputStreamReader((connection.getInputStream())));
                    StringBuilder htmlBuilder = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        htmlBuilder.append(line);
                    }
                    html = htmlBuilder.toString();
                    reader.close();
                }
            }
            return new String(html);
        }
    }
}


/*
try {
            URL urlObj = new URL(Url);
            HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader((connection.getInputStream())));
            htmlBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                htmlBuilder.append(line);
            }
            reader.close();
        } catch (IOException e) {
            try (CloseableHttpClient httpClient = HttpClients.custom().setRedirectStrategy(new LaxRedirectStrategy()).build()) {
                HttpGet httpGet = new HttpGet(Url);
                try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                    int statusCode = response.getStatusLine().getStatusCode();
                    if (statusCode == 200) {
                        HttpEntity entity = response.getEntity();
                        String html = EntityUtils.toString(entity);
                        htmlBuilder.append(html);
                    } else {
                        System.err.println("HTTP Error: " + statusCode);
                    }
                } catch (IOException | NullPointerException e1) {
                    e1.printStackTrace();

                }
            }
        }
 */
