package jonathanchiou.csmathhelper.main.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jonathanchiou.csmathhelper.R;

public class TimedFragment extends Fragment {

    @Bind(R.id.finalcountup) Chronometer countup;
    private ArrayList<Integer> varianceArr; //for answers

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timed, container, false);
        varianceArr = new ArrayList<Integer>(Arrays.asList(-2, -4, 2, 4, 6));
        ButterKnife.bind(this, view);
        countup.start();
        return view;
    }

    @OnClick({R.id.answer1, R.id.answer2, R.id.answer3, R.id.answer4})
    public void onClick(View view) {
        String btnText = ((Button) view).getText().toString();
    }

}
