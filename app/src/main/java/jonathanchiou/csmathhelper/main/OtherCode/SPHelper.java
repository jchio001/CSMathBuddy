package jonathanchiou.csmathhelper.main.OtherCode;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by jman0_000 on 1/9/2016.
 */
public class SPHelper {

    //cb SharedPreference methods
    public static boolean getCbState(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(Constants.SAVE_KEY, false);
    }

    public static void saveCbState(Context context, boolean value) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putBoolean(Constants.SAVE_KEY, value).apply();
    }

    //Saving problem methods
    public static void saveBinProblem(Context context, String val1, String val2, String result, String op) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(Constants.BIN_KEY1, val1).apply();
        sp.edit().putString(Constants.BIN_KEY2, val2).apply();
        sp.edit().putString(Constants.BIN_RESULT_KEY, result).apply();
        sp.edit().putString(Constants.BIN_OP, op).apply();
    }

    public static void saveHexProblem(Context context, String val1, String val2, String result, String op) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(Constants.HEX_KEY1, val1).apply();
        sp.edit().putString(Constants.HEX_KEY2, val2).apply();
        sp.edit().putString(Constants.HEX_RESULT_KEY, result).apply();
        sp.edit().putString(Constants.HEX_OP, op).apply();
    }

    public static void saveBothProblem(Context context, String val1, String val2, String result, String mode2, String op) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        //Toast.makeText(context, "Mode2: " + mode2, Toast.LENGTH_SHORT).show();
        sp.edit().putString(Constants.BOTH_KEY1, val1).apply();
        sp.edit().putString(Constants.BOTH_KEY2, val2).apply();
        sp.edit().putString(Constants.BOTH_RESULT_KEY, result).apply();
        sp.edit().putString(Constants.BOTH_MODE_KEY, mode2).apply();
        sp.edit().putString(Constants.BOTH_OP, op).apply();
    }

    //so everything but getting modeVal is working. Somewhere, there was an improper write
    //fetch problem
    public static ProblemData getProblem(Context context, String num1_key, String num2_key, String result_key, String op_key) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String val1 = sp.getString(num1_key, "");
        String val2 = sp.getString(num2_key, "");
        String savedResult = sp.getString(result_key, "");
        String modeVal = sp.getString(Constants.BOTH_MODE_KEY, "");
        String op = sp.getString(op_key, "");

        return new ProblemData(val1, val2, modeVal, savedResult, op);
    }

}
