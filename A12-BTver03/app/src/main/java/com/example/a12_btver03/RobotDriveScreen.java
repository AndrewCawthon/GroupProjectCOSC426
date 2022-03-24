package com.example.a12_btver03;

import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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
                } else if (lowerLimit >= 0) {
                    binding.sbPowerlow.setProgress(lowerLimit);
                }
            }
        });
        binding.driveUp.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view){
                /*possible feedback state
                final float[] NEGATIVE = {
                        -1.0f,     0,     0,    0, 255, // red
                        0, -1.0f,     0,    0, 255, // green
                        0,     0, -1.0f,    0, 255, // blue
                        0,     0,     0, 1.0f,   0  // alpha
                };

                binding.driveUp.setColorFilter(new ColorMatrixColorFilter(NEGATIVE));*/
                //insert drive forward
                return false;
            }
        });
        binding.driveRight.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view){
                //feedback state

                //insert drive right
                return false;
            }
        });
        binding.driveLeft.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view){
                //feedback state

                //insert drive left
                return false;
            }
        });
        binding.driveBack.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view){
                //feedback state
                //insert reverse
                return false;
            }
        });

    }
}
