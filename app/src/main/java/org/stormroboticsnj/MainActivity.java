package org.stormroboticsnj;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.room.Room;

import com.google.android.material.navigation.NavigationView;
import com.opencsv.CSVWriter;

import org.stormroboticsnj.dao.StormDao;
import org.stormroboticsnj.models.Whoosh;
import org.stormroboticsnj.ui.DatabaseTools;
import org.stormroboticsnj.ui.MapFragment;
import org.stormroboticsnj.ui.display.DisplayFragment;
import org.stormroboticsnj.ui.display.whoosh.WhooshListFragment;
import org.stormroboticsnj.ui.rank.RankFragment;
import org.stormroboticsnj.ui.rank.team.TeamListFragment;

import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DisplayFragment.OnSearchListener, WhooshListFragment.OnListFragmentInteractionListener,
        RankFragment.OnSearchListener, TeamListFragment.OnListFragmentInteractionListener, DatabaseTools.OnFragmentInteractionListener,
        MapFragment.OnFragmentInteractionListener, ActivityCompat.OnRequestPermissionsResultCallback {

    public static final int CAMERA_REQUEST_CODE = 1;
    private AppBarConfiguration mAppBarConfiguration;
    private AppDatabase db;
    private String[] colNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigationview);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_scan, R.id.nav_display, R.id.nav_tools, R.id.nav_map, R.id.nav_privacy)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        /* get database, or build if it doesn't exist. This exact line must be included in the onCreate
        method of every Activity that uses the database. db can be a class-wide variable or local
        within onCreate. */
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "storm").allowMainThreadQueries().build(); //build database

        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey("CameraPermission")) {
            Toast.makeText(getApplicationContext(), "No Camera Permission", Toast.LENGTH_SHORT);
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
            }
        }

    }

    public String[] getColNames() {
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

    @Override
    public List<Whoosh> newSearchWL(String col, int val) {
        StormDao stormDao = db.stormDao();
        return stormDao.getByTeamNumber(val);
    }

    @Override
    public List<Whoosh> newSearch(boolean team, int val) {
        StormDao stormDao = db.stormDao();
        if (team) return stormDao.getByTeamNumber(val);
        else return stormDao.getByMatchNumber(val);
    }

    @Override
    public List<Whoosh> getAll() {
        StormDao stormDao = db.stormDao();
        return stormDao.getWhooshesByTeam();
    }

    @Override
    public void clearDatabase() {
        db.clearAllTables();
    }

    @Override
    public String dumpDatabase() {
        File exportDir = new File(Environment.getDataDirectory(), "");
        if (!exportDir.exists()) {
            exportDir.mkdirs();
        }

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
        String dateTime = df.format(Calendar.getInstance().getTime());

        File file = new File(exportDir, "scoutingradar-" + dateTime + ".csv");
        try {
            file.createNewFile();
            CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
            Cursor curCSV = db.query("SELECT * FROM " + "whooshes", null);
            csvWrite.writeNext(curCSV.getColumnNames());
            while (curCSV.moveToNext()) {
                //Which column you want to export
                String[] arrStr = new String[curCSV.getColumnCount()];
                for (int i = 0; i < curCSV.getColumnCount() - 1; i++)
                    arrStr[i] = curCSV.getString(i);
                csvWrite.writeNext(arrStr);
            }
            csvWrite.close();
            curCSV.close();
        } catch (Exception sqlEx) {
            Log.d("MainActivity dump", sqlEx.toString());
            return null;
        }
        return file.getName();
    }

    private void dump() {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        //mandatory implementation for MapFragment
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(getApplicationContext(), "Camera permission denied! Scanning will not work.", Toast.LENGTH_LONG).show();
            }
        }
    }
}
