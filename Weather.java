package com.codetech.weather;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;  // Add org.json library for JSON parsing



import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import org.json.JSONObject;
import java.util.Scanner;

public class Weather {
    
    private static final String API_KEY = "033dd878e3fd10d2ad9fadc57bc88349"; // Replace with your OpenWeatherMap API key
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather?q=";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter city name: ");
        String city = scanner.nextLine();
        scanner.close();

        String url = BASE_URL + city + "&appid=" + API_KEY + "&units=metric";

        try {
            String response = fetchWeatherData(url);
            if (response != null) {
                parseAndDisplayWeather(response);
            } else {
                System.out.println("Failed to fetch weather data.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static String fetchWeatherData(String url) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return response.body();
        } else {
            System.out.println("Error: HTTP " + response.statusCode());
            return null;
        }
    }

    private static void parseAndDisplayWeather(String response) {
        JSONObject jsonObject = new JSONObject(response);
        
        String cityName = jsonObject.getString("name");
        JSONObject main = jsonObject.getJSONObject("main");
        double temperature = main.getDouble("temp");
        int humidity = main.getInt("humidity");
        JSONObject weather = jsonObject.getJSONArray("weather").getJSONObject(0);
        String description = weather.getString("description");

        System.out.println("Weather Data for: " + cityName);
        System.out.println("Temperature: " + temperature + "°C");
        System.out.println("Humidity: " + humidity + "%");
        System.out.println("Condition: " + description);
    }
}




