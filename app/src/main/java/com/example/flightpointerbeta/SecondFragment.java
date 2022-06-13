package com.example.flightpointerbeta;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.flightpointerbeta.databinding.FragmentSecondBinding;
import com.google.android.gms.maps.MapView;
import com.mashape.unirest.http.exceptions.UnirestException;


import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;


public class SecondFragment extends Fragment{

    //a Java public class that make use of GPS location and network location.


    private FragmentSecondBinding binding;
    private GetAPI api;
    JSONObject nearest_aircraft;

    TextView TVlongtitude;
    TextView TVlattitude;

    TextView flight_callsign;
    TextView flight_reg;
    TextView flight_type;
    TextView distance;

    MapView MVmapView;

    int PERMISSION_ID = 44;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        /**Implementing Google Service map API, API key is in Note*/

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();



    }




    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int radius = SecondFragmentArgs.fromBundle(getArguments()).getRadiusArgs();
        TextView radiusView = view.getRootView().findViewById(R.id.radius_fragment2);
        radiusView.setText(String.valueOf(radius));

        TVlongtitude = view.findViewById(R.id.Longitude);
        TVlattitude = view.findViewById(R.id.Lattitude);

        TVlongtitude.setText(Double.toString(MainActivity.longi));
        TVlattitude.setText(Double.toString(MainActivity.lat));

        /** First basic step, displaying top 10 closest aircraft in the vicinity using RESTAPI
         * First need to get user current location longitude, lattitude
         * From there we can call RESTAPI to get aircraft in the vicinity.
         * **/

        flight_callsign = view.findViewById(R.id.call_sign);
        flight_reg = view.findViewById(R.id.reg);
        flight_type = view.findViewById(R.id.aircraft_type);
        distance = view.findViewById(R.id.distance);


        this.api = new GetAPI(25,MainActivity.lat, MainActivity.longi);
        this.nearest_aircraft = new JSONObject();

        /** we cannot perform network operation on the main thread, because of
         * “android.os.Network On Main Thread Exception error”
         * So we have to create a separate thread to handle any networking
         * operations
         */
        thread.start();






        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }





        Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            try  {
                try {
                    nearest_aircraft = api.getJSON();
                } catch (UnirestException e) {

                    Toast.makeText(getActivity(), "API call failed1", Toast.LENGTH_LONG).show();
                } catch (JSONException e) {

                    Toast.makeText(getActivity(), "API call failed2", Toast.LENGTH_LONG).show();
                } catch (Exception e){

                    Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                }

                if (nearest_aircraft.equals("")){
                    flight_callsign.setText("blank");
                }else{
                    flight_callsign.setText("aircraft callsign: " + nearest_aircraft.getString("call"));
                    flight_reg.setText("aircraft registration "+ nearest_aircraft.getString("reg"));
                    flight_type.setText("aircraft type: "+ nearest_aircraft.getString("type"));
                    double dist = api.haversine(MainActivity.lat,MainActivity.longi,
                            Double.parseDouble(nearest_aircraft.getString("lat")),
                            Double.parseDouble(nearest_aircraft.getString("lon")));
                    distance.setText("aircraft distance to you: " + dist + " km");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    });


}