package com.doo.scm.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.doo.scm.gitea.controller.request.GiteaRequest;

public class GiteaUtil {
    
    // private String token = "0719b661ddf4a542793802a6748b865939189afd";

    public static String getCommits() {
        URL url;
        try {
            url = new URL("http://localhost:4000/api/v1/repos/doo/test");
            HttpURLConnection connection;
            connection = (HttpURLConnection) url.openConnection();
            
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            // connection.setRequestProperty("User-Agent", USER_AGENT);
            // connection.setDoOutput(true);
            
            // DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
            // outputStream.writeBytes("dd");
            // outputStream.flush();
            // outputStream.close();
            
            // int responseCode = connection.getResponseCode();
            
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer stringBuffer = new StringBuffer();
            String inputLine;
            
            while ((inputLine = bufferedReader.readLine()) != null)  {
                stringBuffer.append(inputLine);
            }
            bufferedReader.close();
            
            String response = stringBuffer.toString();
            System.out.println(response);
            return response;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String api(GiteaRequest dto) {
        try {
            URL url = new URL("http://localhost:4000/api/v1" + dto.getUrl());
            HttpURLConnection connection;
            connection = (HttpURLConnection) url.openConnection();
            
            connection.setRequestMethod(dto.getMethod());
            connection.setRequestProperty("Content-Type", "application/json");
            
            // DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
            // outputStream.writeBytes("dd");
            // outputStream.flush();
            // outputStream.close();
            
            // int responseCode = connection.getResponseCode();
            
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer stringBuffer = new StringBuffer();
            String inputLine;
            
            while ((inputLine = bufferedReader.readLine()) != null)  {
                stringBuffer.append(inputLine);
            }
            bufferedReader.close();
            
            String response = stringBuffer.toString();
            return response;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
