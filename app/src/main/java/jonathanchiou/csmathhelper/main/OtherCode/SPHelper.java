package jonathanchiou.csmathhelper.main.OtherCode;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import java.util.PropertyResourceBundle;

import jonathanchiou.csmathhelper.main.Activities.MainActivity;

/**
 * Created by jman0_000 on 1/9/2016.
 */
public class SPHelper {

    public static boolean getCbState(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(Constants.SAVE_KEY, false);
    }

    public static void saveCbState(Context context, boolean value) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putBoolean(Constants.SAVE_KEY, value).apply();
    }

    public static void saveBinProblem(Context context, String val1, String val2, String result) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(Constants.BIN_KEY1, val1).apply();
        sp.edit().putString(Constants.BIN_KEY2, val2).apply();
        sp.edit().putString(Constants.BIN_RESULT_KEY, result).apply();
    }

    public static ProblemData getProblem(Context context, String key1, String key2, String key3, String mode) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String val1 = sp.getString(key1, "");
        String val2 = sp.getString(key2, "");
        String savedResult = sp.getString(key3, "");
        String modeVal = "";

        if (mode.equals(Constants.TEST_MODE))
            modeVal = sp.getString(Constants.BOTH_MODE_KEY, "");

        return new ProblemData(val1, val2, savedResult, modeVal);
    }

    public static void saveHexProblem(Context context, String val1, String val2, String result) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(Constants.HEX_KEY1, val1).apply();
        sp.edit().putString(Constants.HEX_KEY2, val2).apply();
        sp.edit().putString(Constants.HEX_RESULT_KEY, result).apply();
    }

    public static void saveBothProblem(Context context, String val1, String val2, String result, String mode2) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        Toast.makeText(context, "Mode2: " + mode2, Toast.LENGTH_SHORT).show();
        sp.edit().putString(Constants.BOTH_KEY1, val1).apply();
        sp.edit().putString(Constants.BOTH_KEY2, val2).apply();
        sp.edit().putString(Constants.BOTH_RESULT_KEY, result).apply();
        sp.edit().putString(Constants.BOTH_MODE_KEY, mode2).apply();
    }
}
