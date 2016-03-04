package edu.uta.cse.personaltutorservice;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by Mousa Almotairi & Funda Karapinar on 3/3/2016.
 */
public class registerTutorService_Fragment extends Fragment {

    View rootView;
    SeekBar priceSeekBar;
    TextView pricePerHour;
    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.registertutorservice_layout, container, false);

        pricePerHour = (TextView) rootView.findViewById(R.id.labelPrice);
        priceSeekBar = (SeekBar)rootView.findViewById(R.id.priceSeekBar);

        priceSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int currentProgress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                 currentProgress = progress;
                String priceText = String.valueOf((int)progress) + " USD";
                pricePerHour.setText(priceText.trim());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {


            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                String priceText = String.valueOf((int)currentProgress) + " USD";
                pricePerHour.setText(priceText.trim());

            }
        });
        return rootView;
    }
}
