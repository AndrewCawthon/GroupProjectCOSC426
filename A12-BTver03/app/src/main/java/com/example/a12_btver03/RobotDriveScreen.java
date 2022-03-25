package com.example.a12_btver03;

import android.content.Intent;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a12_btver03.databinding.DriveScreenBinding;

import java.util.Objects;

public class RobotDriveScreen extends AppCompatActivity {
    DriveScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DriveScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if(getIntent() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


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

        binding.driveUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(), "Forward", Toast.LENGTH_SHORT).show();
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
        binding.driveRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(), "Right", Toast.LENGTH_SHORT).show();
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
        binding.driveLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(), "Left", Toast.LENGTH_SHORT).show();
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

        binding.driveBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(), "Backward", Toast.LENGTH_SHORT).show();
            }
        });

        binding.connectImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lv_it = new Intent(RobotDriveScreen.this, MainActivity.class);
                startActivity(lv_it);
            }
        });

    }
}
