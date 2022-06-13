package com.example.flightpointerbeta;

import org.json.JSONException;
import org.json.JSONObject;

class Aircraft {
    String ac_callsign;
    String ac_icao;
    String ac_reg;
    String ac_type;
    double ac_lat;
    double ac_long;
    double dist;
    double alt; //altitude in ft

    public Aircraft(JSONObject aircraft,double di) {
        try{
            ac_callsign = aircraft.getString("call");
            ac_icao = aircraft.getString("icao");
            ac_reg = aircraft.getString("reg");
            ac_type = aircraft.getString("type");
            ac_lat  = Double.parseDouble(aircraft.getString("lat"));
            ac_long = Double.parseDouble(aircraft.getString("lon"));
            dist = di;

        }catch (JSONException e){

        }
    }
    /**calculating distance to user using pythagoras theorem and haversine formula*/
    public double distance_to_user(){

    }
}
