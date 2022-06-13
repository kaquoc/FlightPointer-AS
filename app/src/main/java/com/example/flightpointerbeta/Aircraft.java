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
    double alt; //altitude in km

    public Aircraft(JSONObject aircraft,double di) {
        try{
            ac_callsign = aircraft.getString("call");
            ac_icao = aircraft.getString("icao");
            ac_reg = aircraft.getString("reg");
            ac_type = aircraft.getString("type");
            ac_lat  = Double.parseDouble(aircraft.getString("lat"));
            ac_long = Double.parseDouble(aircraft.getString("lon"));
            dist = di;
            alt = Double.parseDouble(aircraft.getString("alt"));


        }catch (JSONException e){

        }
    }
    /**calculating distance to user using pythagoras theorem and haversine formula*/
    public double distance_to_user(){
        //a^2 + b^2 = c^2
        double ans = Math.sqrt(Math.pow(this.dist,2) + Math.pow(this.alt,2));
        return ans;
    }
}
