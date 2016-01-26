package jonathanchiou.csmathhelper.main.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import jonathanchiou.csmathhelper.R;
import jonathanchiou.csmathhelper.main.Events.ReturnHome;
import jonathanchiou.csmathhelper.main.Utils.Constants;

public class TimedStatsActivity extends AppCompatActivity {

    @Bind(R.id.timetaken) TextView timeTaken;

    private String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timed_stats);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        EventBus.getDefault().post(new ReturnHome(Constants.SWITCH_TO_HOME));

        time = getIntent().getStringExtra(Constants.TIME_STR_KEY);
        timeTaken.setText(Constants.TIME_TAKEN + time);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_right_out, R.anim.slide_right_in);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home)
            finish();

        return true;
    }
}
