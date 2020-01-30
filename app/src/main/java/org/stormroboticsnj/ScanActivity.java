package org.stormroboticsnj;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.google.zxing.Result;

import org.stormroboticsnj.dao.StormDao;
import org.stormroboticsnj.models.Whoosh;

import java.util.regex.Pattern;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private static final String TAG = "";
    private ZXingScannerView mScannerView;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "storm").allowMainThreadQueries().build(); //build database

    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();// Stop camera on pause
    }

    @Override
    public void handleResult(final Result rawResult) {
        ResultTask rt = new ResultTask(db, getApplicationContext());
        rt.execute(rawResult.toString());
        onBackPressed();
    }


}


