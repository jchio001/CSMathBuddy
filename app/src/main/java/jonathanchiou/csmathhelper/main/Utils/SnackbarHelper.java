package jonathanchiou.csmathhelper.main.Utils;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import jonathanchiou.csmathhelper.R;

/**
 * Created by jman0_000 on 1/21/2016.
 */
public class SnackbarHelper {
    public static Snackbar showSnackbar(Context context, View parent, String message, boolean isCorrect) {
        Snackbar snackbar = Snackbar.make(parent, message, Snackbar.LENGTH_LONG);
        View rootView = snackbar.getView();
        if (isCorrect)
            snackbar.getView().setBackgroundColor(context.getResources().getColor(R.color.green));
        else
            snackbar.getView().setBackgroundColor(context.getResources().getColor(R.color.red));
        TextView tv = (TextView) rootView.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(context.getResources().getColor(R.color.white));
        snackbar.show();
        return snackbar;
    }
}
