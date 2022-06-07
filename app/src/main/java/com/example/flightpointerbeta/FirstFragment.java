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

import com.example.flightpointerbeta.databinding.FragmentFirstBinding;
import com.google.android.material.snackbar.Snackbar;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private TextView showCountTextView;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        View fragmentFirstLayout =
                inflater.inflate(R.layout.fragment_first,container,false);
        showCountTextView = fragmentFirstLayout.findViewById(R.id.editRadiusNumber);

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        //return binding.getRoot();
        return fragmentFirstLayout;

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /**
        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });
         **/


        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (showCountTextView.getText().toString().equals("")) {
                    Snackbar.make(view, "invalid radius, please choose radius between 0 and 25", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {

                    int radius = Integer.parseInt((showCountTextView.getText().toString()));


                    // need to add guard to check if user input is valid
                    if ((radius > 0 && radius <= 25)) {
                        FirstFragmentDirections.ActionFirstFragmentToSecondFragment action =
                                FirstFragmentDirections.actionFirstFragmentToSecondFragment(radius);

                        //transition between first fragment to second fragment.
                        NavHostFragment.findNavController(FirstFragment.this)
                                .navigate(action);
                    } else {
                        Snackbar.make(view, "invalid radius, please choose radius between 0 and 25", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }
            }


        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}