package jonathanchiou.csmathhelper.main.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.speech.RecognitionService;
import android.widget.Button;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

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
        sp.edit().putString(Constants.MIXED_KEY1, val1).apply();
        sp.edit().putString(Constants.MIXED_KEY2, val2).apply();
        sp.edit().putString(Constants.MIXED_RESULT_KEY, result).apply();
        sp.edit().putString(Constants.MIXED_MODE_KEY, mode2).apply();
        sp.edit().putString(Constants.MIXED_OP, op).apply();
    }

    //so everything but getting modeVal is working. Somewhere, there was an improper write
    //fetch problem
    public static ProblemData getProblem(Context context, String num1_key, String num2_key, String result_key, String op_key) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String val1 = sp.getString(num1_key, "");
        String val2 = sp.getString(num2_key, "");
        String savedResult = sp.getString(result_key, "");
        String modeVal = sp.getString(Constants.MIXED_MODE_KEY, "");
        String op = sp.getString(op_key, "");

        return new ProblemData(val1, val2, modeVal, savedResult, op);
    }

    //Timed mode Functions
    public static void saveTimedProblem(Context context, int solved, int total, Long time, String num1, String num2, String op, String result,
                                   ArrayList<Button> answerSet) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putInt(Constants.TIMED_SOLVED, solved).apply();
        sp.edit().putInt(Constants.TIMED_TOTAL, total).apply();
        sp.edit().putLong(Constants.TIMED_TIME, time).apply();
        sp.edit().putString(Constants.TIMED_NUM1, num1).apply();
        sp.edit().putString(Constants.TIMED_NUM2, num2).apply();
        sp.edit().putString(Constants.TIMED_OP, op).apply();
        sp.edit().putString(Constants.TIMED_RESULT, result).apply();

        for (int i = 0; i < answerSet.size(); ++i) {
            sp.edit().putString(Constants.TIMED_SOLUTION +
                    Integer.toString(i), answerSet.get(i).getText().toString()).apply();
        }
    }

    public static TimedProblemData getSavedTimedProblem(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        int solved = sp.getInt(Constants.TIMED_SOLVED, 0);
        int total = sp.getInt(Constants.TIMED_TOTAL, 0);
        long time = sp.getLong(Constants.TIMED_TIME, 0);
        String num1 = sp.getString(Constants.TIMED_NUM1, "");
        String num2 = sp.getString(Constants.TIMED_NUM2, "");
        String op = sp.getString(Constants.TIMED_OP, "");
        String result = sp.getString(Constants.TIMED_RESULT, "");
        ArrayList<String> solutionSet = new ArrayList<String>();

        for (int i = 0; i < 4; ++i)
            solutionSet.add(sp.getString(Constants.TIMED_SOLUTION + Integer.toString(i), ""));

        return new TimedProblemData(solved, total, time, num1, num2, op, result, solutionSet);
    }

    public static void clearTimedProblemData(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putInt(Constants.TIMED_SOLVED, 0).apply();
        sp.edit().putLong(Constants.TIMED_TIME, 0).apply();
        sp.edit().putString(Constants.TIMED_NUM1, "").apply();
        sp.edit().putString(Constants.TIMED_NUM2, "").apply();
        sp.edit().putString(Constants.TIMED_OP, "").apply();
        sp.edit().putString(Constants.TIMED_RESULT, "").apply();

        for (int i = 0; i < 4; ++i) {
            sp.edit().putString(Constants.TIMED_SOLUTION +
                    Integer.toString(i), "").apply();
        }
    }

}
