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

    private String processJSON(String prettyJson) throws JSONException {
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
        //System.out.println(ac2.getJSONObject(1).getString("call"));
        //returning nearest aircraft callsign
        int i = 0;
        while (ac2.getJSONObject(i).getString("call").equals("")){
            i++;
        }
        return ac2.getJSONObject(i).getString("call");


    }


    /** method for calling API
     * Using OKHTTP instead of UNIREST, unirest crashes app.
     * **/
    //getting data from ADSBX using the user-entered radius.
    public String getJSON() throws UnirestException, JSONException, IOException {

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