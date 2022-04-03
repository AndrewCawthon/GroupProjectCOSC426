package com.example.a12_btver03;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.a12_btver03.databinding.ActivityMainBinding;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import java.io.InputStream;
import java.io.OutputStream;

//ver03 control robot and sound
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private final String CV_ROBOTNAME = "EV3";
    private BluetoothAdapter cv_btInterface = null;
    private Set<BluetoothDevice> cv_pairedDevices = null;
    private BluetoothDevice cv_btDevice = null;
    private BluetoothSocket cv_btSocket = null;

    private InputStream cv_is = null;
    private OutputStream cv_os = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        // Need grant permission once per install
        cpf_checkBTPermissions();

        binding.connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(), "Connect", Toast.LENGTH_SHORT).show();
                cpf_connectToEV3(cv_btDevice);
                //binding.bluetoothImageView.setImageResource(R.drawable.ic_action_bluetooth_on_symbol);
            }
        });

        binding.closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(), "Close", Toast.LENGTH_SHORT).show();
            }
        });


        binding.tiltOnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(), "Tilt On", Toast.LENGTH_SHORT).show();
                cpf_EV3MoveMotorBackward();
                cpf_EV3MoveMotorForward();
            }
        });

        binding.tiltOffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(), "Tilt Off", Toast.LENGTH_SHORT).show();
            }
        });

        binding.driveImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lv_it = new Intent(MainActivity.this, RobotDriveScreen.class);
                lv_it.putExtra("device", cv_btDevice);
                //lv_it.putExtra("socket", cv_btSocket);
                startActivity(lv_it);
            }
        });

    }

    private void cpf_checkBTPermissions() {
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.BLUETOOTH_SCAN) == PackageManager.PERMISSION_GRANTED) {
            binding.connectionTextView.setText("BLUETOOTH_SCAN already granted.\n");
        } else {
            binding.connectionTextView.setText("BLUETOOTH_SCAN NOT granted.\n");
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_DENIED) {
            binding.secondaryTextView.setText("BLUETOOTH_CONNECT NOT granted.\n");
        } else {
            binding.secondaryTextView.setText("BLUETOOTH_CONNECT already granted.\n");
            binding.bluetoothImageView.setImageResource(R.drawable.ic_action_bluetooth_on_symbol);
        }
    }


    // Overriding onCreateoptionMenu() to make Option menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflating menu by overriding inflate() method of MenuInflater class.
        //Inflating here means parsing layout XML to views.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    //Overriding onOptionsItemSelected to perform event on menu items
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        //Toast.makeText(this, "You chose : " + menuItem.getTitle(), Toast.LENGTH_SHORT).show();
        switch (menuItem.getItemId()) {
            case R.id.menu_first: cpf_requestBTPermissions();
                return true;
            case R.id.menu_second: cv_btDevice = cpf_locateInPairedBTList(CV_ROBOTNAME);
                return true;
            case R.id.menu_third: cpf_connectToEV3(cv_btDevice);
                return true;
            case R.id.menu_fourth:
                cpf_EV3MoveMotor();
                cpf_EV3MoveMotorForward();
                return true;
            case R.id.menu_fifth: cpf_EV3PlayTone();
                return true;
            case R.id.menu_sixth: cpf_disconnFromEV3(cv_btDevice);
                return true;
            case R.id.menu_seventh:
                cpf_EV3MoveMotorBackward();
                cpf_EV3MoveMotorBackwards();
                return true;
            case R.id.menu_eighth:
                cpf_EV3MoveMotorLeft();
                cpf_EV3MoveMotorLefts();
                return true;
            case R.id.menu_ninth:
                cpf_EV3MoveMotorRight();
                cpf_EV3MoveMotorRights();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    @SuppressLint("MissingPermission")
    private BluetoothDevice cpf_locateInPairedBTList(String name) {
        BluetoothDevice lv_bd = null;
        try {
            cv_btInterface = BluetoothAdapter.getDefaultAdapter();
            cv_pairedDevices = cv_btInterface.getBondedDevices();
            Iterator<BluetoothDevice> lv_it = cv_pairedDevices.iterator();
            while (lv_it.hasNext())  {
                lv_bd = lv_it.next();
                if (lv_bd.getName().equalsIgnoreCase(name)) {
                    binding.connectionTextView.setText(name + " is in paired list");
                    return lv_bd;
                }
            }
            binding.connectionTextView.setText(name + " is NOT in paired list");
        }
        catch (Exception e) {
            binding.connectionTextView.setText("Failed in findRobot() " + e.getMessage());
        }
        return null;
    }

    @SuppressLint("MissingPermission")
    private void cpf_connectToEV3(BluetoothDevice bd) {
        try  {
            cv_btSocket = bd.createRfcommSocketToServiceRecord
                    (UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
            cv_btSocket.connect();
            binding.secondaryTextView.setText("Connect to " + bd.getName() + " at " + bd.getAddress());
            binding.bluetoothImageView.setImageResource(R.drawable.ic_action_bluetooth_on_symbol);
            cv_is = cv_btSocket.getInputStream();
            cv_os = cv_btSocket.getOutputStream();
        }
        catch (Exception e) {
            binding.secondaryTextView.setText("Error interacting with remote device [" +
                    e.getMessage() + "]");
        }
    }

    @SuppressLint("MissingPermission")
    private void cpf_disconnFromEV3(BluetoothDevice bd) {
        try {
            cv_btSocket.close();
            cv_is.close();
            cv_os.close();
            binding.secondaryTextView.setText(bd.getName() + " is disconnect " );
            binding.bluetoothImageView.setImageResource(R.drawable.ic_action_bluetooth_off);
        } catch (Exception e) {
            binding.secondaryTextView.setText("Error in disconnect -> " + e.getMessage());
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
            binding.connectionTextView.setText("Error in MoveForward(" + e.getMessage() + ")");
        }
    }

    private void cpf_EV3MoveMotorBackwards() {
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
            binding.connectionTextView.setText("Error in MoveForward(" + e.getMessage() + ")");
        }
    }

    private void cpf_EV3MoveMotorLefts() {
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
            binding.connectionTextView.setText("Error in MoveForward(" + e.getMessage() + ")");
        }
    }

    private void cpf_EV3MoveMotorRights() {
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
            binding.connectionTextView.setText("Error in MoveForward(" + e.getMessage() + ")");
        }
    }

    private void cpf_EV3PlayTone() {
        try {
            byte[] buffer = new byte[17];       // 0x9 command length

            buffer[0] = (byte) (17-2);
            buffer[1] = 0;

            //1200 xxxx 8000 00AE 0006 8132 0082 8403 82B4 0001
            //bbbb mmmm tthh hhcc cccc cccc cccc cccc cccc cccc

            //0F00 xxxx 8000 0094 0181 0282 E803 82E8 03

            //arbitrary numbers for now
            buffer[2] = 34;
            buffer[3] = 12;

            buffer[4] = (byte) 0x80;

            buffer[5] = 0;
            buffer[6] = 0;

            buffer[7] = (byte) 0x94;
            buffer[8] = (byte) 0x01;

            buffer[9] = (byte) 0x81;
            buffer[10] = (byte) 0x02;

            buffer[11] = (byte) 0x82;
            buffer[12] = (byte) 0xE8;
            buffer[13] = (byte) 0x03;

            buffer[14] = (byte) 0x82;
            buffer[15] = (byte) 0xE8;
            buffer[16] = (byte) 0x03;

            cv_os.write(buffer);
            cv_os.flush();
        }
        catch (Exception e) {
            binding.connectionTextView.setText("Error in Play Tone(" + e.getMessage() + ")");
        }
    }

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
            //.setText("Error in MoveBackward(" + e.getMessage() + ")");
        }
    }

    private void cpf_EV3MoveMotor() {
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
            buffer[11] = (byte) 0x01; //-1 or ff



            cv_os.write(buffer);
            cv_os.flush();
        }
        catch (Exception e) {
            //.setText("Error in MoveBackward(" + e.getMessage() + ")");
        }
    }

    // https://www.geeksforgeeks.org/android-how-to-request-permissions-in-android-application/
    private void cpf_requestBTPermissions() {
        // We can give any value but unique for each permission.
        final int BLUETOOTH_SCAN_CODE = 100;
        final int BLUETOOTH_CONNECT_CODE = 101;

        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.BLUETOOTH_SCAN) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.BLUETOOTH_SCAN},
                    BLUETOOTH_SCAN_CODE);
        } else {
            Toast.makeText(MainActivity.this,
                    "BLUETOOTH_SCAN already granted", Toast.LENGTH_SHORT).show();
        }

        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.BLUETOOTH_CONNECT},
                    BLUETOOTH_CONNECT_CODE);
        } else {
            Toast.makeText(MainActivity.this,
                    "BLUETOOTH_CONNECT already granted", Toast.LENGTH_SHORT).show();
            binding.bluetoothImageView.setImageResource(R.drawable.ic_action_bluetooth_on_symbol);
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
            binding.connectionTextView.setText("Error in MoveBackward(" + e.getMessage() + ")");
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
            binding.connectionTextView.setText("Error in MoveBackward(" + e.getMessage() + ")");
        }
    }
}