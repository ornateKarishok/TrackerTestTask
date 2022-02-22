package com.example.trackertesttask.ui;

import static com.example.trackertesttask.theme.util.DialogManager.showCustomAlertDialog;
import static com.example.trackertesttask.theme.util.ThemeManager.setCustomizedThemes;
import static com.example.trackertesttask.theme.util.ThemeStorage.getThemeColor;
import static com.example.trackertesttask.theme.util.ThemeStorage.setThemeColor;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trackertesttask.Countries;
import com.example.trackertesttask.CountriesListAdapter;
import com.example.trackertesttask.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SearchView.OnQueryTextListener {
    ImageButton plusButton;
    ListView list;
    CountriesListAdapter adapter;
    SearchView editSearch;
    String[] countriesNameList;
    ArrayList<Countries> arraylist = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCustomizedThemes(this, getThemeColor(this));
        setContentView(R.layout.activity_main);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.empty_string);
        }
        plusButton = findViewById(R.id.plus_button);
        plusButton.setOnClickListener(this);

        countriesNameList = new String[]{"Япония", "Китай", "Украина",
                "Россия", "Индонезия", "Индия", "Таиланд", "Филипины",
                "Сингапур", "Беларусия", "Чехия"};

        list = (ListView) findViewById(R.id.listview);

        for (String s : countriesNameList) {
            arraylist.add(new Countries(s));
        }
        adapter = new CountriesListAdapter(this, arraylist);
        list.setAdapter(adapter);
        editSearch = (SearchView) findViewById(R.id.search);
        editSearch.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.filter(newText);
        return false;
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
        } else {
            Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void settingsPopupWindowShow(View view) {
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams") View popupView = inflater.inflate(R.layout.settings_window, null);

        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
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

    public void changeCurrency(View view) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.select_currency_window);
        dialog.show();
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
