package com.example.trackertesttask.ui;

import static com.example.trackertesttask.theme.util.DialogManager.showCustomAlertDialog;
import static com.example.trackertesttask.theme.util.ThemeManager.setCustomizedThemes;
import static com.example.trackertesttask.theme.util.ThemeStorage.getThemeColor;
import static com.example.trackertesttask.theme.util.ThemeStorage.setThemeColor;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trackertesttask.DataStorage;
import com.example.trackertesttask.R;
import com.mynameismidori.currencypicker.CurrencyPicker;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String CURRENCY_ID = "currency";
    ImageButton plusButton;
    TextView currency;
    PopupWindow settingsPopupWindow;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCustomizedThemes(this, getThemeColor(this));
        setContentView(R.layout.activity_main);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.empty_string);
        }
        plusButton = findViewById(R.id.plus_button);
        currency = findViewById(R.id.currency);
        plusButton.setOnClickListener(this);
        currency.setText(DataStorage.getDataFromStorage(getApplicationContext(), CURRENCY_ID));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(R.string.settings);
        menu.add(R.string.reset);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getTitle().equals(getString(R.string.settings))) {
            settingsPopupWindowShow(findViewById(android.R.id.content).getRootView());
        } else if (item.getTitle().equals(getString(R.string.reset))) {
            resetAlertDialogShow();
        }
        return super.onOptionsItemSelected(item);
    }

    public void resetAlertDialogShow() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.reset);
        builder.setMessage("Вы действительно хотите удалить все отмеченные часы?");
        builder.setPositiveButton(R.string.ok, (dialog, id) -> {
        });
        builder.setNegativeButton(R.string.cancel, (dialog, id) -> {
        });
        builder.create();
        builder.show();
    }

    public void settingsPopupWindowShow(View view) {
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View popupView = inflater.inflate(R.layout.settings_window, null);

        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        settingsPopupWindow = new PopupWindow(popupView, width, height, true);
        settingsPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }

    public void chooseColor(View view) {
        showCustomAlertDialog(this,
                chosenColor -> {
                    if (chosenColor.equals(getThemeColor(getApplicationContext()))) {
                        return;
                    }
                    setThemeColor(getApplicationContext(), chosenColor);
                    setCustomizedThemes(getApplicationContext(), chosenColor);
                    recreate();
                });
    }

    public void changePrice(View view) {
        settingsPopupWindow.dismiss();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = this.getLayoutInflater();
        view = inflater.inflate(R.layout.change_price_window, null);
        EditText editText = view.findViewById(R.id.edit_price_edit_text);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        builder.setView(view);
        builder.setNegativeButton(R.string.cancel,
                (dialog, id) -> dialog.cancel()).setPositiveButton(R.string.ok,
                (dialog, which) -> {
                    //txtView.setText(String.valueOf(rating.getRating()));
                    dialog.dismiss();
                });
        builder.create();
        builder.show();
    }

    public void changeCurrency(View view) {
        CurrencyPicker picker = CurrencyPicker.newInstance("Select Currency");
        picker.setListener((name, code, symbol, flagDrawableResID) -> {
            currency.setText(code);
            DataStorage.setDataToStorage(getApplicationContext(), code, CURRENCY_ID);
            picker.dismiss();
            settingsPopupWindow.dismiss();
        });
        picker.show(getSupportFragmentManager(), "CURRENCY_PICKER");
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
        hoursNumberPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
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
        minutesNumberPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
        });

        builder.setNegativeButton(R.string.cancel,
                (dialog, id) -> dialog.cancel()).setPositiveButton(R.string.ok,
                (dialog, which) -> {
                    //txtView.setText(String.valueOf(rating.getRating()));
                    dialog.dismiss();
                });


        builder.create();
        builder.show();
    }

    @Override
    public void onClick(View view) {
        plusButtonClick();
    }
}
