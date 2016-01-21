package jonathanchiou.csmathhelper.main.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;

import butterknife.Bind;
import butterknife.ButterKnife;
import jonathanchiou.csmathhelper.R;

public class TimedFragment extends Fragment {

    @Bind(R.id.finalcountup) Chronometer countup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timed, container, false);
        ButterKnife.bind(this, view);
        countup.start();
        return view;
    }

}
