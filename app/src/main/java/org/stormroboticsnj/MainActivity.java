package org.stormroboticsnj;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.room.Room;

import com.google.android.material.navigation.NavigationView;
import com.opencsv.*;

import org.stormroboticsnj.dao.StormDao;
import org.stormroboticsnj.models.Whoosh;
import org.stormroboticsnj.ui.display.DisplayFragment;
import org.stormroboticsnj.ui.display.whoosh.WhooshListFragment;
import org.stormroboticsnj.ui.rank.RankFragment;
import org.stormroboticsnj.ui.rank.team.TeamListFragment;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DisplayFragment.OnSearchListener, WhooshListFragment.OnListFragmentInteractionListener,
        RankFragment.OnSearchListener, TeamListFragment.OnListFragmentInteractionListener, DatabaseTools.OnFragmentInteractionListener {

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
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_display, R.id.nav_rank)
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

        StormDao stormdao = db.stormDao();
        Cursor cursor = stormdao.getCursor();
        cursor.moveToFirst();
        colNames = cursor.getColumnNames();

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
        new AlertDialog.Builder(this) //confirm with user
                .setTitle("Clear Data")
                .setMessage("Are you sure you want to completely clear the local database? This action is permanent.")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //clear database table
                        db.clearAllTables();
                        Toast.makeText(getApplicationContext(), "Database cleared", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //do nothing
                    }
                })
                .setIcon(R.mipmap.ic_launcher)
                .show();
    }

    @Override
    public void dumpDatabase() {
        new android.app.AlertDialog.Builder(MainActivity.this)
                .setTitle("Dump Database")
                .setMessage("Dump the database to a CSV file on the device.  Are you sure?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dump();
                        Toast.makeText(MainActivity.this, "Database Dumped", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }

    private void dump() {
        File exportDir = new File(Environment.getDataDirectory(), "");
        if (!exportDir.exists()) {
            exportDir.mkdirs();
        }

        File file = new File(exportDir, "stormexport" + ".csv");
        try {
            file.createNewFile();
            CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
            Cursor curCSV = db.query("SELECT * FROM " + "whooshes", null);
            csvWrite.writeNext(curCSV.getColumnNames());
            while (curCSV.moveToNext()) {
                //Which column you want to exprort
                String arrStr[] = new String[curCSV.getColumnCount()];
                for (int i = 0; i < curCSV.getColumnCount() - 1; i++)
                    arrStr[i] = curCSV.getString(i);
                csvWrite.writeNext(arrStr);
            }
            csvWrite.close();
            curCSV.close();
            Toast.makeText(getApplicationContext(), "Database Exported to csv", Toast.LENGTH_SHORT);
        } catch (Exception sqlEx) {

        }
    }
}
