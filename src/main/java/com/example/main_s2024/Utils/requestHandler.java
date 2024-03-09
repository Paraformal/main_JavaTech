package com.example.main_s2024.Utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class requestHandler {
    private String urlString;

    public requestHandler(String url){
        this.urlString = url;
    }

    public String getRequest(){
        if(urlString == null){
            return "No Url Specified";
        }

        try{
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer htmlResponse = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    htmlResponse.append(inputLine);
                }
                in.close();

                return "Response (HTML): " + htmlResponse.toString();
            } else {
                return "GET request not working";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Something went wrong! Please try again.";
    }
}
