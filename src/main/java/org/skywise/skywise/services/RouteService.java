package org.skywise.skywise.services;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.skywise.skywise.api.models.AirportAPIResponse;

import java.io.IOException;

public class RouteService {
    private final String API_KEY = "QV26nYD5s7dFJHMHic2I92DWavZtOodT8F16MH37";
    private final String BASE_URL = "https://api.flightplandatabase.com";
    private final String PLAN_URL = "/plan";
    private final String AIRPORT_URL = "/nav/airport/";
    private final String PLAN_GENERATOR_URL = "/auto/generate";
    private final String WEATHER_URL = "/weather/";

    public RouteService() {

    }

    private String getRoutePlanUrl() {
        return BASE_URL + PLAN_URL;
    }

    private String getAirportUrl(String ICAO) {
        return BASE_URL + AIRPORT_URL + ICAO;
    }

    private String getGenerateURL() {
        return BASE_URL + PLAN_GENERATOR_URL;
    }

    private String getWeatherURL(String ICAO) {
        return BASE_URL + WEATHER_URL + ICAO;
    }

    public JSONObject generateRoutePlan() {
        OkHttpClient client = new OkHttpClient();

        AirportAPIResponse departureAirport = getAirportByICAO("LKTB");
        AirportAPIResponse arrivalAirport = getAirportByICAO("LKPR");

        JSONObject departureAirportJson = new JSONObject();
        departureAirportJson.put("type", departureAirport.getType());
        departureAirportJson.put("ident", departureAirport.getIdent());
        departureAirportJson.put("name", departureAirport.getName());
        departureAirportJson.put("lat", departureAirport.getLat());
        departureAirportJson.put("lon", departureAirport.getLon());
        departureAirportJson.put("alt", 0);
        departureAirportJson.put("via", JSONObject.NULL);

        JSONObject arrivalAirportJson = new JSONObject();
        arrivalAirportJson.put("type", arrivalAirport.getType());
        arrivalAirportJson.put("ident", arrivalAirport.getIdent());
        arrivalAirportJson.put("name", arrivalAirport.getName());
        arrivalAirportJson.put("lat", arrivalAirport.getLat());
        arrivalAirportJson.put("lon", arrivalAirport.getLon());
        arrivalAirportJson.put("alt", 0);
        arrivalAirportJson.put("via", JSONObject.NULL);

        JSONArray nodesJsonArray = new JSONArray();
        nodesJsonArray.put(departureAirportJson);
        nodesJsonArray.put(arrivalAirportJson);

        JSONObject routeJsonObject = new JSONObject();
        routeJsonObject.put("nodes", nodesJsonArray);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("fromICAO", "LKPR");
        jsonObject.put("toICAO", "LKTB");
        jsonObject.put("fromName", "Praha");
        jsonObject.put("toName", "Brno");
        jsonObject.put("route", routeJsonObject);

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(jsonObject.toString(), JSON);

        Request request = new Request.Builder()
                .url(getRoutePlanUrl())
                .header("Authorization", "Basic " + API_KEY)
                .post(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String output = response.body().string();
            System.out.println(output);
            return new JSONObject(output);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public AirportAPIResponse getAirportByICAO(String ICAO) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(getAirportUrl(ICAO))
                .header("Authorization", "Basic " + API_KEY)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String output = response.body().string();
            JSONObject jsonObject = new JSONObject(output);
            String name = jsonObject.getString("name");
            float lat = jsonObject.getFloat("lat");
            float lon = jsonObject.getFloat("lon");

            return new AirportAPIResponse(
                    "APT",
                    ICAO,
                    name,
                    lat,
                    lon,
                    0,
                    null
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getRoute() {
        String dep = "LKPR";
        String arr = "EDDM";
        OkHttpClient client = new OkHttpClient();

        JSONObject jsonRequestObject = new JSONObject();
        jsonRequestObject.put("fromICAO", dep);
        jsonRequestObject.put("toICAO", arr);

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody requestBody = RequestBody.create(jsonRequestObject.toString(), JSON);

        Request request = new Request.Builder()
                .url(getGenerateURL())
                .header("Authorization", "Basic " + API_KEY)
                .post(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String output = response.body().string();
            return output;
            //JSONObject jsonObject = new JSONObject(output);
            //return jsonObject;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
