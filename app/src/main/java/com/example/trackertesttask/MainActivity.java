package com.example.trackertesttask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener {
    ImageButton menuButton;
    ImageButton plusButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        menuButton = findViewById(R.id.menu_button);
        menuButton.setOnClickListener(this);
        plusButton = findViewById(R.id.plus_button);
        plusButton.setOnClickListener(this);

    }

    @SuppressLint("ClickableViewAccessibility")
    public void onButtonShowPopupWindowClick(View view) {

        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.menu_window, null);

        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int WindowWidth = size.x;
        popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, WindowWidth - 500, menuButton.getHeight());

        popupView.setOnTouchListener((v, event) -> {
            popupWindow.dismiss();
            return true;
        });
    }

    public void plusButtonClick() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.select_time_window, null);
        builder.setView(view);
        NumberPicker hoursNumberPicker = (NumberPicker) view.findViewById(R.id.hours_number_picker);
        NumberPicker minutesNumberPicker = (NumberPicker) view.findViewById(R.id.minutes_number_picker);
        String[] hoursValue = new String[49];
        for (int i = 0; i < 49; i++) {
            hoursValue[i] = String.valueOf(i);
        }
        hoursNumberPicker.setMinValue(0);
        hoursNumberPicker.setMaxValue(hoursValue.length - 1);


        hoursNumberPicker.setDisplayedValues(hoursValue);
        hoursNumberPicker.setWrapSelectorWheel(false);
        hoursNumberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            }
        });

        String[] minutesValue = new String[59];
        for (int i = 0; i < 59; i++) {
            minutesValue[i] = String.valueOf(i);
        }
        minutesNumberPicker.setMinValue(0);
        minutesNumberPicker.setMaxValue(minutesValue.length - 1);


        minutesNumberPicker.setDisplayedValues(minutesValue);

        for (int i = 0; i < 60; i++) {
            minutesNumberPicker.setValue(i);
        }
        minutesNumberPicker.setWrapSelectorWheel(false);
        minutesNumberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            }
        });

        builder.setNegativeButton(R.string.cancel,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).setPositiveButton(R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //txtView.setText(String.valueOf(rating.getRating()));
                        dialog.dismiss();
                    }
                });


        builder.create();
        builder.show();
    }


    @Override
    public void onClick(View view) {
        if (view == menuButton) {
            onButtonShowPopupWindowClick(view);
        } else if (view == plusButton) {
            plusButtonClick();
        }
    }
}
