package com.example.trackertesttask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.TimePickerDialog;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
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

        TimePickerDialog.OnTimeSetListener myTimeListener = (view, hourOfDay, minute) -> Toast.makeText(getApplicationContext(), hourOfDay + " " + minute, Toast.LENGTH_LONG).show();

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, myTimeListener, 0, 0, true);
        timePickerDialog.setTitle("Choose hour:");
        timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        timePickerDialog.show();
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
