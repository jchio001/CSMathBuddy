package jonathanchiou.csmathhelper.main.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import jonathanchiou.csmathhelper.R;
import jonathanchiou.csmathhelper.main.OtherCode.Constants;

public class HomeFragment extends Fragment {

    @Bind(R.id.testCnt) TextView testCnt;
    @Bind(R.id.totalCnt) TextView totalCnt;
    @Bind(R.id.binCnt) TextView binCnt;
    @Bind(R.id.hexCnt) TextView hexCnt;

    SharedPreferences sp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parentViewGroup,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, parentViewGroup, false);
        ButterKnife.bind(this, rootView);
        sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        setUpCnt();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpCnt();
    }

    public void setUpCnt() {
        int totalSolved = sp.getInt(Constants.TOTAL_SOLVED_KEY, 0);
        totalCnt.setText(Constants.TOTAL_COUNT_STRING + Integer.toString(totalSolved));

        int testSolved = sp.getInt(Constants.TOTAL_SOLVED_TEST_KEY, 0);
        testCnt.setText(Constants.TOTAL_TEST_CNT_STRING + Integer.toString(testSolved));

        int binSolved = sp.getInt(Constants.TOTAL_SOLVED_BIN_KEY, 0);
        binCnt.setText(Constants.TOTAL_BIN_CNT_STRING + Integer.toString(binSolved));

        int hexSolved = sp.getInt(Constants.TOTAL_SOLVED_HEX_KEY, 0);
        hexCnt.setText(Constants.TOTAL_HEX_CNT_STRING + Integer.toString(hexSolved));
    }

}
