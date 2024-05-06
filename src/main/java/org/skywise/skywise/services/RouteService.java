package org.skywise.skywise.services;

import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;

public class RouteService {
    private final String API_KEY;
    private final String BASE_URL = "https://api.flightplandatabase.com";
    private final String WEATHER_URL = "/weather/";

    public RouteService() {
        this.API_KEY = System.getenv("METAR_API_KEY");
    }

    private String getWeatherURL(String ICAO) {
        return BASE_URL + WEATHER_URL + ICAO;
    }

    public String getWeather(String ICAO) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(getWeatherURL(ICAO))
                .header("Authorization", "Basic " + API_KEY)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String output = response.body().string();
            JSONObject jsonObject = new JSONObject(output);
            return jsonObject.getString("METAR");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
