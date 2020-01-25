package org.stormroboticsnj;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.zxing.Result;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import android.view.Menu;
import android.widget.Toast;

import org.stormroboticsnj.dao.StormDao;
import org.stormroboticsnj.models.Whoosh;
import org.stormroboticsnj.ui.display.DisplayFragment;
import org.stormroboticsnj.ui.whoosh.WhooshListFragment;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler, DisplayFragment.OnSearchListener, WhooshListFragment.OnListFragmentInteractionListener {

    private AppBarConfiguration mAppBarConfiguration;
    private AppDatabase db;
    private String[] colNames;

//    private ZXingScannerView mScannerView;

//    public void scanQR(){
//        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
//        setContentView(mScannerView);
//        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
//        mScannerView.startCamera();
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_display, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        /* get database, or build if it doesn't exist. This exact line must be included in the onCreate
        method of every Activity that uses the database. db can be a class-wide variable or local
        within onCreate. */
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "stormdb").allowMainThreadQueries().build(); //build database

        StormDao stormdao = db.stormDao();
        Cursor cursor = stormdao.getCursor();
        cursor.moveToFirst();
        colNames = cursor.getColumnNames();

    }

    public String[] getColNames(){
        return this.colNames;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public List<Whoosh> getData(String col, int val) {
        /* List<Whoosh> output = new ArrayList();

        Whoosh first = new Whoosh(2729, 1);
        first.setScore(1);
        Whoosh second = new Whoosh(2729, 2);
        second.setScore(28);

        output.add(first);
        output.add(second); */ //testing purposes
        StormDao stormDao = db.stormDao();
        return stormDao.filterWhooshes(col, val);
        //return output;
    }

    @Override
    public List<Whoosh> newSearchWL(String col, int val) {
        StormDao stormDao = db.stormDao();
        return stormDao.filterWhooshes(col, val);
    }

    @Override
    public List<Whoosh> newSearch(String col, int val) {
        /* List<Whoosh> output = new ArrayList();

        Whoosh first = new Whoosh(1490, 3);
        first.setScore(8);
        Whoosh second = new Whoosh(1490, 4);
        second.setScore(35);

        output.add(first);
        output.add(second); */ //testing purposes
        StormDao stormDao = db.stormDao();
        return stormDao.filterWhooshes(col, val);

        //return output;
    }

    public void handleResult(Result rawResult) {
        Toast t = new Toast(this);
        t.setText(rawResult.toString());
        t.show();
    }
}
