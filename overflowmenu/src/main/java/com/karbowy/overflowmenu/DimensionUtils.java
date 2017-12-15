package com.karbowy.overflowmenu;


import android.content.Context;
import android.util.TypedValue;

class DimensionUtils {
    public static int applyDpToPx(Context context, int dp) {
       return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
}
