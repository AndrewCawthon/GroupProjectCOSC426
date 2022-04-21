package com.example.a12_btver03;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a12_btver03.databinding.DriveScreenBinding;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class RobotDriveScreen extends AppCompatActivity {
    DriveScreenBinding binding;
    private OutputStream cv_os = null;

    private BluetoothSocket cv_btSocket = null;

    private InputStream cv_is = null;
    private BluetoothDevice btDevice;
    private BluetoothAdapter cv_btInterface = null;
    private Set<BluetoothDevice> cv_pairedDevices = null;
    private BluetoothDevice cv_btDevice = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DriveScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



//        Bundle bundle = getIntent().getExtras();
//
//        if (bundle != null) {
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            btDevice = (BluetoothDevice) bundle.get("device");
//            cpf_connectToEV3(btDevice);
//        }

        MainActivity main = new MainActivity();
        btDevice = main.get_cv_btDevice();
        cv_btInterface = main.get_cv_btInterface();
        cv_btSocket = main.get_cv_btSocket();
        cv_is = main.get_cv_is();
        cv_os = main.get_cv_os();



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
        binding.driveUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                cpf_EV3MoveMotor();
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
            }
        });
        /*
        binding.driveUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(), "Forward", Toast.LENGTH_SHORT).show();
            }
        });
        */


        binding.driveRight.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view){
                //feedback state

                //insert drive right
                cpf_EV3MoveMotorRight();
                cpf_EV3MoveMotor();
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
                cpf_EV3MoveMotorLeft();
                cpf_EV3MoveMotor();
                return false;
            }
        });
        binding.driveLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(), "Left", Toast.LENGTH_SHORT).show();
            }
        });
        /*
        binding.driveBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                //feedback state
                //insert reverse
                cpf_EV3MoveMotorBackward();
                cpf_EV3MoveMotor();

                //return false;
            }
        });
        */
        binding.driveBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(), "Backward", Toast.LENGTH_SHORT).show();
                cpf_EV3MoveMotorBackward();
                cpf_EV3MoveMotor();
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
    private void cpf_EV3MoveMotor() {
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

            buffer[9] = (byte) 0x06;

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
            //binding.connectionTextView.setText("Error in MoveForward(" + e.getMessage() + ")");
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
    private void cpf_EV3MoveMotorLeft() {
        try {
            byte[] buffer = new byte[12];       //change once know size needed

            buffer[0] = (byte) (12-2);
            buffer[1] = 0;

            buffer[2] = 34;
            buffer[3] = 12;

            buffer[4] = (byte) 0x80;

            buffer[5] = 0;
            buffer[6] = 0;

            buffer[7] = (byte) 0xae;        //opcode

            buffer[8] = (byte) 0;       //layer 0006
            buffer[9] = (byte) 0x02;
            //buffer[10] = (byte) 0x81;
            //buffer[11] = (byte) 0xff; //-1 or ff



            cv_os.write(buffer);
            cv_os.flush();
        }
        catch (Exception e) {
            binding.connectionDriveTextView.setText("Error in MoveBackward(" + e.getMessage() + ")");
        }
    }


    //right
    private void cpf_EV3MoveMotorRight() {
        try {
            byte[] buffer = new byte[12];       //change once know size needed

            buffer[0] = (byte) (12-2);
            buffer[1] = 0;

            buffer[2] = 34;
            buffer[3] = 12;

            buffer[4] = (byte) 0x80;

            buffer[5] = 0;
            buffer[6] = 0;

            buffer[7] = (byte) 0xae;        //opcode

            buffer[8] = (byte) 0;       //layer 0006
            buffer[9] = (byte) 0x04;
            //buffer[10] = (byte) 0x81;
            //buffer[11] = (byte) 0xff; //-1 or ff



            cv_os.write(buffer);
            cv_os.flush();
        }
        catch (Exception e) {
            binding.connectionDriveTextView.setText("Error in MoveBackward(" + e.getMessage() + ")");
        }
    }

    @SuppressLint("MissingPermission")
    private void cpf_connectToEV3(BluetoothDevice bd) {
        try  {
            cv_btSocket = bd.createRfcommSocketToServiceRecord
                    (UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
            cv_btSocket.connect();
            //binding.secondaryTextView.setText("Connect to " + bd.getName() + " at " + bd.getAddress());
            //binding.bluetoothImageView.setImageResource(R.drawable.ic_action_bluetooth_on_symbol);
            cv_is = cv_btSocket.getInputStream();
            cv_os = cv_btSocket.getOutputStream();
        }
        catch (Exception e) {
            //binding.secondaryTextView.setText("Error interacting with remote device [" +
                   // e.getMessage() + "]");
        }
    }
}
