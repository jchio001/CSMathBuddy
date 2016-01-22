package jonathanchiou.csmathhelper.main.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import jonathanchiou.csmathhelper.R;
import jonathanchiou.csmathhelper.main.Fragments.AlertDialogFragment;
import jonathanchiou.csmathhelper.main.Fragments.HomeFragment;
import jonathanchiou.csmathhelper.main.Fragments.MathFragment;
import jonathanchiou.csmathhelper.main.Fragments.TimedFragment;
import jonathanchiou.csmathhelper.main.Utils.Constants;

public class MainActivity extends AppCompatActivity{

    @Bind(R.id.left_drawer) ListView drawerList;
    @Bind(R.id.drawer_layout) DrawerLayout myDrawerLayout;

    private static MenuItem menuItem;
    private ActionBarDrawerToggle myDrawerToggle;
    private ArrayAdapter<String> myAdapter;


    Context context;
    SharedPreferences sp;
    android.support.v7.app.ActionBar actionBar;
    String[] drawerArr = {Constants.HOME, Constants.BINARY, Constants.HEX, Constants.MIXED, Constants.TIMED};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        context = this;

        //set up drawer's contents
        myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, drawerArr);
        drawerList.setAdapter(myAdapter);
        drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                loadFragment(id, position, view);
            }
        });

        //action bar stuff
        actionBar = getSupportActionBar();
        actionBar.setTitle(Constants.HOME);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_drawer);
        myDrawerToggle = new ActionBarDrawerToggle(this, myDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View view) {
                super.onDrawerOpened(view);
                invalidateOptionsMenu();
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }

            public void onDrawerStateChanged(int newState) {
                if (newState == DrawerLayout.STATE_DRAGGING) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }
            }

        };

        myDrawerLayout.setDrawerListener(myDrawerToggle);
        myDrawerToggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.main, new HomeFragment(), Constants.HOME).commit();
        }
    }

    public void loadFragment(long id, int position, View view) {
        //hide keyboard
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        Bundle bundle = new Bundle();
        Fragment mathFrag = new MathFragment();
        mathFrag.setArguments(bundle);
        String tag;
        if (id == 0) {
            myDrawerLayout.closeDrawer(Gravity.LEFT);
            actionBar.setTitle(Constants.HOME);
            getSupportFragmentManager().beginTransaction().replace(R.id.main, new HomeFragment(), Constants.HOME).commit();
            return;
        }
        else if (id == 1) {
            bundle.putString(Constants.MODE_KEY, Constants.BINARY_MODE);
            tag = Constants.BINARY;
        }
        else if (id == 2) {
            bundle.putString(Constants.MODE_KEY, Constants.HEX_MODE);
            tag = Constants.HEX;
        }
        else if (id == 3) {
            bundle.putString(Constants.MODE_KEY, Constants.MIXED_MODE);
            tag = Constants.MIXED;
        }
        else if (id == 4) {
            myDrawerLayout.closeDrawer(Gravity.LEFT);
            actionBar.setTitle(Constants.TIMED);
            getSupportFragmentManager().beginTransaction().replace(R.id.main, new TimedFragment(), Constants.TIMED).commit();
            return;
        }
        else {
            Toast.makeText(getApplicationContext(), drawerArr[position], Toast.LENGTH_SHORT).show();
            myDrawerLayout.closeDrawer(Gravity.LEFT);
            return;
        }
        myDrawerLayout.closeDrawer(Gravity.LEFT);
        actionBar.setTitle(tag);
        getSupportFragmentManager().beginTransaction().replace(R.id.main, mathFrag, tag).commit();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        myDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        myDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        //note to self: newer version of android uses SupportFragmentManager
        Fragment myFragment = getSupportFragmentManager().findFragmentByTag(Constants.HOME);
        if (myDrawerLayout.isDrawerOpen(GravityCompat.START))
            myDrawerLayout.closeDrawer(Gravity.LEFT);
        else if (myFragment != null && myFragment.isVisible())
            super.onBackPressed();
        else {
            getSupportFragmentManager().beginTransaction().replace(R.id.main, new HomeFragment(), Constants.HOME).commit();
            actionBar.setTitle(Constants.HOME);
        }
    }

    public static MenuItem getMenuItem() {
        return menuItem;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menuItem = menu.findItem(R.id.refresh);
        menuItem.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            if (myDrawerLayout.isDrawerOpen(GravityCompat.START))
                myDrawerLayout.closeDrawer(Gravity.LEFT);
            else {
                View view = getCurrentFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                myDrawerLayout.openDrawer(Gravity.LEFT);
            }
        }
        else if (id == R.id.action_settings) {
            android.support.v4.app.FragmentManager fm = this.getSupportFragmentManager();
            AlertDialogFragment fragment = new AlertDialogFragment();
            fragment.show(fm, Constants.SETTINGS);
        }

        return super.onOptionsItemSelected(item);
    }
}
