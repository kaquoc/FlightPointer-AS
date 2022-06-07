package com.example.flightpointerbeta;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.flightpointerbeta.databinding.FragmentSecondBinding;
import com.google.android.gms.maps.SupportMapFragment;
import com.mashape.unirest.http.exceptions.UnirestException;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class SecondFragment extends Fragment {

    //a Java public class that make use of GPS location and network location.


    private FragmentSecondBinding binding;
    private GetAPI api;
    String nearest_aircraft;

    TextView TVlongtitude;
    TextView TVlattitude;

    TextView tvFlight;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

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

        tvFlight= view.findViewById(R.id.Flight);
        this.api = new GetAPI(25,MainActivity.lat, MainActivity.longi);
        this.nearest_aircraft = "";

        /** we cannot perform network operation on the main thread, because of
         * “android.os.Network On Main Thread Exception error”
         * So we have to create a separate thread to handle any networking
         * operations
         */
        thread.start();

        /**Google Map View API key: AIzaSyCkIYQVZEDnnqoL3v-biK87kcECWRHRNfo   */




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
                    nearest_aircraft = "API call failed1";
                    Toast.makeText(getActivity(), "API call failed1", Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    nearest_aircraft = "API call failed2";
                    Toast.makeText(getActivity(), "API call failed2", Toast.LENGTH_LONG).show();
                } catch (Exception e){
                    nearest_aircraft = e.toString();
                    Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                }

                if (nearest_aircraft.equals("")){
                    tvFlight.setText("blank");
                }else{
                    tvFlight.setText(nearest_aircraft);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    });





}