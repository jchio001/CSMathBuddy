package jonathanchiou.csmathhelper.main.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import jonathanchiou.csmathhelper.main.Activities.MainActivity;
import jonathanchiou.csmathhelper.main.OtherCode.Constants;
import jonathanchiou.csmathhelper.main.OtherCode.ConversionFunctions;
import jonathanchiou.csmathhelper.main.OtherCode.ProblemData;
import jonathanchiou.csmathhelper.main.OtherCode.SPHelper;

public class MathFragment extends Fragment {

    @Bind(R.id.num1) TextView num1;
    @Bind(R.id.num2) TextView num2;
    @Bind(R.id.operation) TextView operation;
    @Bind(R.id.answer) EditText answer;
    @Bind(R.id.answer1) EditText answer1;

    private String mode;
    private String mode2; //identifier for test mode
    private String solString = ""; //Used to store hex/bin string that represents the answer
    private SharedPreferences defaultSp;
    private MenuItem refresh;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parentViewGroup,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_math, parentViewGroup, false);
        ButterKnife.bind(this, rootView);
        MainActivity.getMenuItem().setVisible(true);
        setHasOptionsMenu(true);
        mode = getArguments().getString(Constants.MODE_KEY);
        mode2 = mode;

        ButterKnife.bind(this, rootView);

        defaultSp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        if (mode2.equals(Constants.BINARY_MODE))
            answer.setVisibility(View.VISIBLE);
        else if (mode2.equals(Constants.HEX_MODE))
            answer1.setVisibility(View.VISIBLE);

        if (SPHelper.getCbState(getActivity()) == true) {
            if (setUpResume())
                return rootView;
        }
        generateNumbers();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mode.equals(Constants.BINARY_MODE))
            SPHelper.saveBinProblem(getActivity(), num1.getText().toString(), num2.getText().toString(), solString,
                    operation.getText().toString());
        else if (mode.equals(Constants.HEX_MODE))
            SPHelper.saveHexProblem(getActivity(), num1.getText().toString(), num2.getText().toString(), solString,
                    operation.getText().toString());
        else if(mode.equals(Constants.MIXED_MODE)) {
            //Toast.makeText(getActivity(), "Math: " + mode2, Toast.LENGTH_SHORT).show();
            SPHelper.saveBothProblem(getActivity(), num1.getText().toString(), num2.getText().toString(), solString, mode2,
                    operation.getText().toString());
        }
        else {
            Toast.makeText(getActivity(), "Something's broken", Toast.LENGTH_SHORT).show();
        }
    }


    public void generateNumbers() {
        answer.setText("");
        answer1.setText("");
        Random rand = new Random(); //Need to initialize the RNGesus

        //Initialize problem. Note to self: check subtraction;
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
        else if (mode.equals(Constants.MIXED_MODE)) {
            loadMixed(rand, rando1, rando2, result);
        }
        else
            Toast.makeText(getActivity(), "Mode is messed up: " + mode, Toast.LENGTH_SHORT).show();
        //Have numbers now, so we set the TextViews to have the numbers of the problem
    }

    //If resuming the problem is successful, return true
    //else, return false.
    public boolean setUpResume() {
        ProblemData data;
        if (mode.equals(Constants.BINARY_MODE))
            return resumeBinProblem();
        else if (mode.equals(Constants.HEX_MODE))
            return resumeHexProblem();
        else if (mode.equals(Constants.MIXED_MODE))
            return resumeBothProblem();
        else
            Toast.makeText(getActivity(), "Failed to load problem", Toast.LENGTH_SHORT).show();

        return false;
    }

    public boolean resumeBinProblem() {
        ProblemData data = SPHelper.getProblem(getActivity(), Constants.BIN_KEY1, Constants.BIN_KEY2,
                Constants.BIN_RESULT_KEY, Constants.BIN_OP);

        String tmp1 = data.getNum1();
        String tmp2 = data.getNum2();
        String tmp3 = data.getOp();
        if (tmp1.equals("") && tmp2.equals(""))
            return false;

        num1.setText(tmp1);
        num2.setText(tmp2);
        operation.setText(tmp3);
        answer.setVisibility(View.VISIBLE);
        answer1.setVisibility(View.GONE);
        solString = data.getResult();
        return true;
    }

    public boolean resumeHexProblem() {
        ProblemData data = SPHelper.getProblem(getActivity(), Constants.HEX_KEY1, Constants.HEX_KEY2,
                Constants.HEX_RESULT_KEY, Constants.HEX_OP);

        String tmp1 = data.getNum1();
        String tmp2 = data.getNum2();
        String tmp3 = data.getOp();

        if (tmp1.equals("") && tmp2.equals(""))
            return false;

        num1.setText(tmp1);
        num2.setText(tmp2);
        operation.setText(tmp3);
        answer.setVisibility(View.GONE);
        answer1.setVisibility(View.VISIBLE);
        solString = data.getResult();
        return true;
    }

    public boolean resumeBothProblem () {
        ProblemData data = SPHelper.getProblem(getActivity(), Constants.MIXED_KEY1, Constants.MIXED_KEY2,
                Constants.MIXED_RESULT_KEY, Constants.MIXED_OP);

        String tmp1 = data.getNum1();
        String tmp2 = data.getNum2();
        String tmp3 = data.getOp();

        if (tmp1.equals("") && tmp2.equals(""))
            return false;

        num1.setText(tmp1);
        num2.setText(tmp2);
        operation.setText(tmp3);
        mode2 = data.getMode();
        if (mode2.equals(Constants.BINARY_MODE)) {
            answer.setVisibility(View.VISIBLE);
            answer1.setVisibility(View.GONE);
        }
        else if (mode2.equals(Constants.HEX_MODE)) {
            answer.setVisibility(View.GONE);
            answer1.setVisibility(View.VISIBLE);
        }
        else {
            //Toast.makeText(getActivity(), "Math: " + mode2, Toast.LENGTH_SHORT).show();
        }
        solString = data.getResult();
        return true;
    }

    public void loadBinProblem(int rando1, int rando2, int result) {
        num1.setText(ConversionFunctions.decToBin(rando1));
        num2.setText(ConversionFunctions.decToBin(rando2));
        solString = ConversionFunctions.decToBin(result);
    }

    public void loadHexProblem(int rando1, int rando2, int result) {
        num1.setText(ConversionFunctions.decToHex(rando1));
        num2.setText(ConversionFunctions.decToHex(rando2));
        solString = ConversionFunctions.decToHex(result);
    }

    public void loadMixed(Random rand, int rando1, int rando2, int result) {
        int problemTypeSeed = rand.nextInt(2);
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

    public void updateCount() {
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
            updateCount();
        }
        else
            Toast.makeText(getActivity(), "Wrong! " + solString, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        refresh = menu.findItem(R.id.refresh);
        refresh.setVisible(true);
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                generateNumbers();
            default:
                break;
        }
        return true;
    }

}
