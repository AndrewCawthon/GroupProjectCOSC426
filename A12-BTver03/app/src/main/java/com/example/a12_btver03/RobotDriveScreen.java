package com.example.a12_btver03;

import android.os.Bundle;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a12_btver03.databinding.DriveScreenBinding;

public class RobotDriveScreen extends AppCompatActivity {
    DriveScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DriveScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.sbPowerhigh.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int lowerLimit = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                lowerLimit = progress;
                binding.tvPowerhigh.setText("Power: " + lowerLimit);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (lowerLimit <= 100) {
                    binding.sbPowerhigh.setProgress(lowerLimit);
                }
                else if (lowerLimit >= 0) {
                    binding.sbPowerhigh.setProgress(lowerLimit);
                }
            }
        });

        binding.sbPowerlow.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int lowerLimit = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                lowerLimit = progress;
                binding.tvPowerlow.setText("Power: " + lowerLimit);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (lowerLimit <= 100) {
                    binding.sbPowerlow.setProgress(lowerLimit);
                }
                else if (lowerLimit >= 0) {
                    binding.sbPowerlow.setProgress(lowerLimit);
                }
            }
        });
    }
}
