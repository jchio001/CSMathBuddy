package jonathanchiou.csmathhelper.main.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jonathanchiou.csmathhelper.R;
import jonathanchiou.csmathhelper.main.OtherCode.Constants;
import jonathanchiou.csmathhelper.main.OtherCode.ConversionFunctions;

public class MathFragment extends Fragment {

    @Bind(R.id.num1) TextView num1;
    @Bind(R.id.num2) TextView num2;
    @Bind(R.id.operation) TextView operation;
    @Bind(R.id.answer) EditText answer;
    @Bind(R.id.answer1) EditText answer1;

    private String mode;
    private String mode2; //identifier for test mode
    private String solString; //Used to store hex/bin string that represents the answer
    private SharedPreferences defaultSp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parentViewGroup,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_math, parentViewGroup, false);
        ButterKnife.bind(this, rootView);
        mode = getArguments().getString(Constants.MODE_KEY);
        mode2 = mode;

        ButterKnife.bind(this, rootView);

        defaultSp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        if (mode2.equals(Constants.BINARY_MODE))
            answer.setVisibility(View.VISIBLE);
        else if (mode2.equals(Constants.HEX_MODE))
            answer1.setVisibility(View.VISIBLE);

        generateNumbers();
        return rootView;
    }


    public void generateNumbers() {
        answer.setText("");
        answer1.setText("");
        Random rand = new Random(); //Need to initialize the RNGesus

        //Intialize problem. Note to self: check subtraction;
        int rando1 = rand.nextInt(120) + 8;
        int rando2;
        int result;

        int op = rand.nextInt(2);
        //For subtraction, check that num1 > num2 by at least 4!
        if (op == 0) {
            rando2 = rand.nextInt(126) + 2;
            result = rando1 + rando2;
            operation.setText("+");
        }
        else {
            rando2 = rand.nextInt(rando1 - 5) + 2;
            result = rando1 - rando2;
            operation.setText("-");
        }

        if (mode.equals(Constants.BINARY_MODE))
            loadBinProblem(rando1, rando2, result);
        else if (mode.equals(Constants.HEX_MODE)) {
            loadHexProblem(rando1, rando2, result);
        }
        else if (mode.equals(Constants.TEST_MODE)) {
            loadMixed(rand, rando1, rando2, result);
        }
        else
            Toast.makeText(getActivity(), mode, Toast.LENGTH_SHORT).show();
        //Have numbers now, so we set the TextViews to have the numbers of the problem
    }

    ///Bin works!
    public void loadBinProblem(int rando1, int rando2, int result) {
        num1.setText(ConversionFunctions.decToBin(rando1));
        num2.setText(ConversionFunctions.decToBin(rando2));
        solString = ConversionFunctions.decToBin(result);
        //answer.setText(ConversionFunctions.decToBin(sum));
    }

    public void loadHexProblem(int rando1, int rando2, int result) {
        num1.setText(ConversionFunctions.decToHex(rando1));
        num2.setText(ConversionFunctions.decToHex(rando2));
        solString = ConversionFunctions.decToHex(result);
        //Toast.makeText(this, solString, Toast.LENGTH_SHORT).show();
    }

    //don't know if mixed works yet. To be tested!
    public void loadMixed(Random rand, int rando1, int rando2, int result) {
        int problemTypeSeed = rand.nextInt(2);
        //Toast.makeText(this, "TEST MODE: " + Integer.toString(problemTypeSeed), Toast.LENGTH_SHORT).show();
        if (problemTypeSeed == 0) {
            mode2 = Constants.BINARY_MODE;
            answer.setVisibility(View.VISIBLE);
            answer1.setVisibility(View.GONE);
            loadBinProblem(rando1,rando2, result);
        }
        else {
            mode2 = Constants.HEX_MODE;
            answer.setVisibility(View.GONE);
            answer1.setVisibility(View.VISIBLE);
            loadHexProblem(rando1,rando2, result);
        }
    }

    public void refresh() {
        Toast.makeText(getActivity(), "Correct!", Toast.LENGTH_SHORT).show();
        int num = defaultSp.getInt(Constants.TOTAL_SOLVED_KEY, 0) + 1;
        defaultSp.edit().putInt(Constants.TOTAL_SOLVED_KEY, num).apply();
        if (mode.equals(Constants.BINARY_MODE)) {
            num = defaultSp.getInt(Constants.TOTAL_SOLVED_BIN_KEY, 0) + 1;
            defaultSp.edit().putInt(Constants.TOTAL_SOLVED_BIN_KEY, num).apply();
        }
        else if (mode.equals(Constants.HEX_MODE)) {
            num = defaultSp.getInt(Constants.TOTAL_SOLVED_HEX_KEY, 0) + 1;
            defaultSp.edit().putInt(Constants.TOTAL_SOLVED_HEX_KEY, num).apply();
        }
        else {
            num = defaultSp.getInt(Constants.TOTAL_SOLVED_TEST_KEY, 0) + 1;
            defaultSp.edit().putInt(Constants.TOTAL_SOLVED_TEST_KEY, num).apply();
        }
        generateNumbers();
    }

    @OnClick(R.id.Submit)
    public void onSubmit() {
        String tmp;
        //Using 2 diff Textviews for input!
        if (mode2.equals(Constants.BINARY_MODE))
            tmp = answer.getText().toString();
        else
            tmp = answer1.getText().toString();

        if (tmp.equals(solString)) {
            refresh();
        }
        else
            Toast.makeText(getActivity(), "Wrong!", Toast.LENGTH_SHORT).show();
    }

}
