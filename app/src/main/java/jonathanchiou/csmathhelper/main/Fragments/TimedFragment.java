package jonathanchiou.csmathhelper.main.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jonathanchiou.csmathhelper.R;
import jonathanchiou.csmathhelper.main.Activities.TimedStatsActivity;
import jonathanchiou.csmathhelper.main.Utils.Constants;
import jonathanchiou.csmathhelper.main.Utils.ConversionFunctions;
import jonathanchiou.csmathhelper.main.Utils.SPHelper;
import jonathanchiou.csmathhelper.main.Utils.SnackbarHelper;
import jonathanchiou.csmathhelper.main.Utils.TimedProblemData;

public class TimedFragment extends Fragment {

    @Bind(R.id.parent) View parent;
    @Bind(R.id.finalcountup) Chronometer countup;
    @Bind(R.id.answer1) Button answer1;
    @Bind(R.id.answer2) Button answer2;
    @Bind(R.id.answer3) Button answer3;
    @Bind(R.id.answer4) Button answer4;
    @Bind(R.id.num1) TextView num1;
    @Bind(R.id.num2) TextView num2;
    @Bind(R.id.operation) TextView operation;

    private ArrayList<Integer> varianceArr; //for answers rng
    private ArrayList<Button> buttonList; //for deciding which button is the "answer"

    private Snackbar curSnackbar = null;
    private String solString = "";
    private int solved = 0;
    private long lastPause = 0;
    private boolean timerStatedOnCreate = false;
    private boolean done = false; //user has solved 15 problems

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timed, container, false);
        varianceArr = new ArrayList<Integer>(Arrays.asList(-2, -4, 2, 4, 6, 8)); //use different variance for large nums?
        ButterKnife.bind(this, view);
        buttonList = new ArrayList<Button>(Arrays.asList(answer1, answer2, answer3, answer4));
        countup.setBase(SystemClock.elapsedRealtime());
        timerStatedOnCreate = true;
        if (loadSavedProblem())
            return view;

        generateProblem();
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        lastPause = countup.getBase() - SystemClock.elapsedRealtime();
        countup.stop();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (timerStatedOnCreate)
            timerStatedOnCreate = false;
        else {
            countup.setBase(SystemClock.elapsedRealtime() + lastPause);
            countup.start();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (curSnackbar != null && curSnackbar.isShown())
            curSnackbar.dismiss();

        //I need to check if the user is done solving the problem set, or else problem data will always be saved
        //and will always load data from the 15th problem
        if (!done) {
            SPHelper.saveTimedProblem(getActivity(), countup.getBase() - SystemClock.elapsedRealtime(), num1.getText().toString(), num2.getText().toString(),
                    operation.getText().toString(), solString, buttonList);
        }
    }

    //Re-used most of the code from MathFragment. See that for more info about how I'm RNG-ing.
    public void generateProblem() {

        Random rand = new Random();

        int rando1 = rand.nextInt(120) + 8;
        int rando2;
        int result;

        int op = rand.nextInt(2);
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
        if (rand.nextInt(2) == 0)
            loadBinProblem(rando1, rando2, result);
        else
            loadHexProblem(rando1, rando2, result);
    }

    public boolean loadSavedProblem() {
        TimedProblemData data = SPHelper.getSavedTimedProblem(getActivity());
        if (data.getNum1().isEmpty() && data.getNum2().isEmpty())
            return false;

        ArrayList<String> answerSet = data.getAnswerSet();

        //countup.setText(data.getTime());
        num1.setText(data.getNum1());
        num2.setText(data.getNum2());
        operation.setText(data.getOp());
        solString = data.getResult();
        answer1.setText(answerSet.get(0));
        answer2.setText(answerSet.get(1));
        answer3.setText(answerSet.get(2));
        answer4.setText(answerSet.get(3));
        countup.setBase(SystemClock.elapsedRealtime() + data.getTime());
        countup.start();
        return true;
    }

    public void loadBinProblem(int rando1, int rando2, int result) {
        num1.setText(ConversionFunctions.decToBin(rando1));
        num2.setText(ConversionFunctions.decToBin(rando2));
        solString = ConversionFunctions.decToBin(result);
        loadAnswerSet(result, Constants.BINARY);
        countup.start();
    }

    public void loadHexProblem(int rando1, int rando2, int result) {
        num1.setText(ConversionFunctions.decToHex(rando1));
        num2.setText(ConversionFunctions.decToHex(rando2));
        solString = ConversionFunctions.decToHex(result);
        loadAnswerSet(result, Constants.HEX);
        countup.start();
    }

    //The fragment has 4 buttons, each button representing a possible solution
    //This function is to initialize the text on the 4 buttons to be possible solutions
    public void loadAnswerSet(int result, String mode) {
        Collections.shuffle(buttonList);
        Collections.shuffle(varianceArr); //use this small array for now
        if (mode.equals(Constants.BINARY)) {
            buttonList.get(0).setText(ConversionFunctions.decToBin(result));

            for (int i = 1; i < buttonList.size(); ++i)
                buttonList.get(i).setText(ConversionFunctions.decToBin(result + varianceArr.get(i)));
        }
        else if (mode.equals(Constants.HEX)) {
            buttonList.get(0).setText(ConversionFunctions.decToHex(result));

            for (int i = 1; i < buttonList.size(); ++i)
                buttonList.get(i).setText(ConversionFunctions.decToHex(result + varianceArr.get(i)));
        }
        else {
            Toast.makeText(getActivity(), "I dun goofed.", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick({R.id.answer1, R.id.answer2, R.id.answer3, R.id.answer4})
    public void onClick(View view) {
        String btnText = ((Button) view).getText().toString();
        if (btnText.equals(solString)) {
            ++solved;
            if (solved >= 1) {
                done = true;
                SPHelper.clearTimedProblemData(getActivity());
                Intent intent = new Intent(getActivity(), TimedStatsActivity.class);
                intent.putExtra(Constants.TIME_STR_KEY, countup.getText().toString());
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_left_out, R.anim.slide_left_in);
                return;
            }
            curSnackbar = SnackbarHelper.showSnackbar(getActivity(), ((ViewGroup) getView().getParent()), Constants.CORRECT, true);
        }
        else
            curSnackbar = SnackbarHelper.showSnackbar(getActivity(), ((ViewGroup) getView().getParent()), Constants.WRONG, false);

        generateProblem();
    }

}
