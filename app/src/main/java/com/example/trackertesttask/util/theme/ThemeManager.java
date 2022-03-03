package com.example.trackertesttask.util.theme;

import android.content.Context;

import com.example.trackertesttask.R;

public class ThemeManager {
    public static final String PINK_THEME = "pink";
    public static final String YELLOW_THEME = "yellow";
    public static final String GREY_THEME = "grey";

    public static void setCustomizedThemes(Context context, String theme) {
        switch (theme) {
            case PINK_THEME:
                context.setTheme(R.style.AppTheme);
                break;
            case YELLOW_THEME:
                context.setTheme(R.style.YellowTheme);
                break;
            case GREY_THEME:
                context.setTheme(R.style.GreyTheme);
                break;
        }
    }
}
