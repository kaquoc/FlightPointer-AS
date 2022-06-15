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
            ac_icao =  aircraft.getString("icao").equals("") ? "unavailable" :aircraft.getString("icao") ;
            ac_reg = aircraft.getString("reg").equals("") ? "unavailable" :aircraft.getString("reg") ;
            ac_type = aircraft.getString("type").equals("") ? "unavailable" :aircraft.getString("type") ;
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

    public String getAc_callsign() {
        return ac_callsign;
    }

    public void setAc_callsign(String ac_callsign) {
        this.ac_callsign = ac_callsign;
    }

    public String getAc_icao() {
        return ac_icao;
    }

    public void setAc_icao(String ac_icao) {
        this.ac_icao = ac_icao;
    }

    public String getAc_reg() {
        return ac_reg;
    }

    public void setAc_reg(String ac_reg) {
        this.ac_reg = ac_reg;
    }

    public String getAc_type() {
        return ac_type;
    }

    public void setAc_type(String ac_type) {
        this.ac_type = ac_type;
    }

    public double getAc_lat() {
        return ac_lat;
    }

    public void setAc_lat(double ac_lat) {
        this.ac_lat = ac_lat;
    }

    public double getAc_long() {
        return ac_long;
    }

    public void setAc_long(double ac_long) {
        this.ac_long = ac_long;
    }

    public double getDist() {
        return dist;
    }

    public void setDist(double dist) {
        this.dist = dist;
    }

    public double getAlt() {
        return alt;
    }

    public void setAlt(double alt) {
        this.alt = alt;
    }
}
