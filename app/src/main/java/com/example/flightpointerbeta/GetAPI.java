package com.example.flightpointerbeta;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Scanner;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
//import jdk.jshell.spi.SPIResolutionException;
import org.apache.commons.codec.binary.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class GetAPI {
    private int radius;
    private double lat;
    private double longi;

    public GetAPI(int radi, double lat, double longi){
        radius = radi;
        this.lat = lat;
        this.longi = longi;
    }

    private Aircraft processJSON(String prettyJson) throws JSONException {
        /**Current JSON format:
         *          {"ac": [{aircraft 1}, {aircraft2},...,{aircraftN}],
         *                  "total": __,
         *                  "ctime": 1__,
         *                  "distmax": "__",
         *                  "ptime": __  }
         *  This is a huge JSONObject, with the following keys: ac,total,ctime,distmax,...
         *  "ac" is a JSONArray, containing more JSONObjects
         *  each JSONObject "aircraft n" where n is an integer, has their own values
         *                  **/


        JSONObject ac = new JSONObject(prettyJson);
        JSONArray ac2 = ac.getJSONArray("ac");
        int i = 0;

        /**This does not return the closest aircraft
         * We can find the closest aircraft based on long-lat of current location and aircraft location
         * using the Haversine formula
         * */
        double min = Double.MAX_VALUE;
        int near_index = 0;
        while (i <= 20){
            JSONObject obj = ac2.getJSONObject(i);
            double lat1 =  Double.parseDouble(obj.getString("lat"));
            double long1= Double.parseDouble(obj.getString("lon"));
            double distance = haversine(lat1,long1, MainActivity.lat, MainActivity.longi);
            if (distance < min && obj.getString("call").equals("") == false &&
                    (!obj.getString("spd").equals("") && Double.parseDouble(obj.getString("spd")) > 10)){

                min = distance;
                near_index = i;
            }
            i++;
        }
        Aircraft ans = new Aircraft(ac2.getJSONObject(near_index));
        return ans;


    }
    /***Haversine formula for finding distance between two location based on longitude and latitude
     * https://en.wikipedia.org/wiki/Haversine_formula
     * return unit kilometer
     * */

    public double haversine(double lat1, double lon1,
                            double lat2, double lon2)
    {
        // Haversine formula uses radian cause it's related with angles
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        // convert to radians
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        // apply formulae
        double a = Math.pow(Math.sin(dLat / 2), 2) +
                Math.pow(Math.sin(dLon / 2), 2) *
                        Math.cos(lat1) *
                        Math.cos(lat2);
        double rad = 6371;
        double c = 2 * Math.asin(Math.sqrt(a));
        return rad * c;
    }


    /** method for calling API
     * Using OKHTTP instead of UNIREST, unirest crashes app.
     * **/
    //getting data from ADSBX using the user-entered radius.
    public Aircraft getJSON() throws UnirestException, JSONException, IOException {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://adsbx-flight-sim-traffic.p.rapidapi.com/api/aircraft/json/lat/" +
                        this.lat +
                        "/lon/" +
                        this.longi +
                        "/dist/25/")
                .get()
                .addHeader("X-RapidAPI-Host", "adsbx-flight-sim-traffic.p.rapidapi.com")
                .addHeader("X-RapidAPI-Key", "9a977024b3msh661b55182095c6cp18eed5jsn8f12205dd548")
                .build();

        Response response = client.newCall(request).execute();
        return (processJSON(response.body().string()));

    }


    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

}
