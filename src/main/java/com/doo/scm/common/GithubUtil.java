package com.doo.scm.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.doo.scm.github.controller.request.GithubRequest;

public class GithubUtil {
    
    private static String host = "http://localhost:4000";
    public static String api(GithubRequest dto) {
        try {
            URL url = new URL(host + "/api/v1" + dto.getUrl());
            HttpURLConnection connection;
            connection = (HttpURLConnection) url.openConnection();
            
            connection.setRequestMethod(dto.getMethod());
            connection.setRequestProperty("Content-Type", "application/json");
            
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
