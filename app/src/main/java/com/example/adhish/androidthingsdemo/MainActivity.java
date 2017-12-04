package com.example.adhish.androidthingsdemo;

import android.app.Activity;
import android.os.Bundle;

import com.google.android.things.contrib.driver.button.Button;
import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManagerService;

import java.io.IOException;


public class MainActivity extends Activity{

    Button button;
    private Gpio ledGpio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PeripheralManagerService service = new PeripheralManagerService();


        try {
            ledGpio = service.openGpio("BCM21");
            ledGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
            button = new Button("BCM6", Button.LogicState.PRESSED_WHEN_LOW);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        button.setOnButtonEventListener(new Button.OnButtonEventListener() {
            @Override
            public void onButtonEvent(Button button, boolean pressed) {
                try {
                    ledGpio.setValue(pressed);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        try {
            button.close();
            ledGpio.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
