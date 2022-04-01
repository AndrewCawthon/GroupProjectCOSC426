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

import java.io.OutputStream;
import java.util.Objects;

public class RobotDriveScreen extends AppCompatActivity {
    DriveScreenBinding binding;
    private OutputStream cv_os = null;

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
                cpf_EV3MoveMotorForward();
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
                cpf_EV3MoveMotorBackward();
                cpf_EV3MoveMotorForward();
                return false;
            }
        });

        binding.driveBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(), "Backward", Toast.LENGTH_SHORT).show();
                cpf_EV3MoveMotorBackward();
                cpf_EV3MoveMotorForward();
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
    private void cpf_EV3MoveMotorForward() {
        try {
            byte[] buffer = new byte[20];       // 0x12 command length

            buffer[0] = (byte) (20-2);
            buffer[1] = 0;

            buffer[2] = 34;
            buffer[3] = 12;

            buffer[4] = (byte) 0x80;

            buffer[5] = 0;
            buffer[6] = 0;

            buffer[7] = (byte) 0xae;
            buffer[8] = 0;

            buffer[9] = (byte) 0x02;

            buffer[10] = (byte) 0x81;
            buffer[11] = (byte) 0x32;

            buffer[12] = 0;

            buffer[13] = (byte) 0x82;
            buffer[14] = (byte) 0x84;
            buffer[15] = (byte) 0x03;

            buffer[16] = (byte) 0x82;
            buffer[17] = (byte) 0xB4;
            buffer[18] = (byte) 0x00;

            buffer[19] = 1;

            cv_os.write(buffer);
            cv_os.flush();
        }
        catch (Exception e) {
            binding.connectionDriveTextView.setText("Error in MoveForward(" + e.getMessage() + ")");
        }
    }

    //opOutput_Polarity  set polarity to -1
    //layer
    //nos
    //pol
    private void cpf_EV3MoveMotorBackward() {
        try {
            byte[] buffer = new byte[12];       //change once know size needed

            buffer[0] = (byte) (12-2);
            buffer[1] = 0;

            buffer[2] = 34;
            buffer[3] = 12;

            buffer[4] = (byte) 0x80;

            buffer[5] = 0;
            buffer[6] = 0;

            buffer[7] = (byte) 0xa7;        //opcode

            buffer[8] = (byte) 0;       //layer 0006
            buffer[9] = (byte) 0x06;
            buffer[10] = (byte) 0x81;
            buffer[11] = (byte) 0xff; //-1 or ff



            cv_os.write(buffer);
            cv_os.flush();
        }
        catch (Exception e) {
            binding.connectionDriveTextView.setText("Error in MoveBackward(" + e.getMessage() + ")");
        }
    }

    //opRI8 (or 16 or 32)...left
    //0x2C
    //source1
    //source2
    //destination


    //right
}
