package com.example.trackertesttask.ui;

import static com.example.trackertesttask.util.theme.DialogManager.showCustomAlertDialog;
import static com.example.trackertesttask.util.theme.ThemeManager.setCustomizedThemes;
import static com.example.trackertesttask.util.DataStorage.getThemeColor;

import android.app.AlertDialog;
import android.content.Intent;
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

import com.example.trackertesttask.R;
import com.example.trackertesttask.model.WorkSession;
import com.example.trackertesttask.util.DataStorage;
import com.mynameismidori.currencypicker.CurrencyPicker;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String CURRENCY_ID = "currency";
    public static final String CURRENCY_PICKER = "currency picker";
    public static final String SELECT_CURRENCY_WINDOW_TITLE = "Select currency";
    public static final String SUM_PER_HOUR = "Sum per hour";
    public static final String SHARED_PREFERENCES_ID = "Theme";
    ImageButton plusButton;
    TextView currency;
    TextView sum;
    TextView detailButton;
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
        detailButton = findViewById(R.id.details_text_view);
        currency = findViewById(R.id.currency);
        sum = findViewById(R.id.sum_text_view);
        plusButton.setOnClickListener(this);
        detailButton.setOnClickListener(this);
        List<WorkSession> workSessions = DataStorage.getListFromStorage(this);
        setValueToSumTextView(workSessions);
        currency.setText(DataStorage.getDataFromStorage(this, CURRENCY_ID));

    }

    @Override
    protected void onResume() {
        super.onResume();
        List<WorkSession> workSessions = DataStorage.getListFromStorage(this);
        setValueToSumTextView(workSessions);
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
        builder.setMessage(R.string.are_you_you_want_delete_all_marked_hours);
        builder.setPositiveButton(R.string.ok, (dialog, id) -> {
            DataStorage.setListToStorage(this, new LinkedList<WorkSession>());
            setValueToSumTextView(DataStorage.getListFromStorage(this));
        });
        builder.setNegativeButton(R.string.cancel, null);
        builder.create();
        builder.show();
    }

    public void settingsPopupWindowShow(View view) {
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.settings_window, null);

        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        settingsPopupWindow = new PopupWindow(popupView, width, height, true);
        settingsPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }

    public void chooseColor(View view) {
        showCustomAlertDialog(this,
                chosenColor -> {
                    if (chosenColor.equals(getThemeColor(this))) {
                        return;
                    }
                    DataStorage.setDataToStorage(this, chosenColor, SHARED_PREFERENCES_ID);
                    setCustomizedThemes(this, chosenColor);
                    recreate();
                });
    }

    public void changePrice(View view) {
        if (settingsPopupWindow != null) {
            settingsPopupWindow.dismiss();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        view = inflater.inflate(R.layout.change_price_window, null);
        EditText editText = view.findViewById(R.id.edit_price_edit_text);
        TextView inputPricePerHour = view.findViewById(R.id.input_price_per_hour_tw);
        inputPricePerHour.setText(getString(R.string.input_price_per_hour) + getString(R.string.open_bracket) + DataStorage.getDataFromStorage(this, CURRENCY_ID) + getString(R.string.close_bracket));
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        builder.setView(view);
        builder.setNegativeButton(R.string.cancel,
                (dialog, id) -> dialog.cancel())
                .setPositiveButton(R.string.ok,
                        (dialog, id) -> {
                    String textFromEditText = String.valueOf(editText.getText());
                            if (!textFromEditText.equals("")) {
                                DataStorage.setDataToStorage(getApplicationContext(), textFromEditText, SUM_PER_HOUR);
                                setValueToSumTextView(DataStorage.getListFromStorage(getApplicationContext()));
                            }
                        });
        builder.create();
        builder.show();
    }

    public void changeCurrency(View view) {
        CurrencyPicker picker = CurrencyPicker.newInstance(SELECT_CURRENCY_WINDOW_TITLE);
        picker.setListener((name, code, symbol, flagDrawableResID) -> {
            currency.setText(code);
            DataStorage.setDataToStorage(this, code, CURRENCY_ID);
            picker.dismiss();
            settingsPopupWindow.dismiss();
        });
        picker.show(getSupportFragmentManager(), CURRENCY_PICKER);
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
                (dialog, id) -> {
                    double workTime = hoursNumberPicker.getValue() + (minutesNumberPicker.getValue() / 60f);
                    List<WorkSession> workSessions = saveWorkSession(workTime);
                    setValueToSumTextView(workSessions);
                });
        builder.create();
        builder.show();
    }

    private List<WorkSession> saveWorkSession(double workTime) {
        List<WorkSession> workSessions = DataStorage.getListFromStorage(this);
        Date date = new Date();
        WorkSession workSession = new WorkSession(workTime, date);
        workSessions.add(workSession);
        DataStorage.setListToStorage(this, workSessions);
        return workSessions;
    }

    private void setValueToSumTextView(List<WorkSession> workSessions) {
        TextView detailTextView = findViewById(R.id.details_text_view);
        String fromStorage = DataStorage.getDataFromStorage(this, SUM_PER_HOUR);
        if (fromStorage.equals(" ") || workSessions.isEmpty()) {
            sum.setText(R.string.zero);
            detailTextView.setVisibility(View.GONE);
        } else {
            double sumPerHour = Double.parseDouble(fromStorage);
            double totalSum = 0;
            for (WorkSession session : workSessions) {
                totalSum += (session.getDuration() * sumPerHour);
            }
            sum.setText(String.valueOf(totalSum));
            detailTextView.setVisibility(View.VISIBLE);
        }
    }

    public void detailButtonClick() {
        Intent myIntent = new Intent(MainActivity.this, DetailActivity.class);
        startActivity(myIntent);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.plus_button) {
            String fromStorage = DataStorage.getDataFromStorage(this, SUM_PER_HOUR);
            if (fromStorage.equals(" ") || fromStorage.equals("")) {
                changePrice(view);
            } else {
                plusButtonClick();
            }

        } else if (view.getId() == R.id.details_text_view) {
            detailButtonClick();
        }
    }
}
