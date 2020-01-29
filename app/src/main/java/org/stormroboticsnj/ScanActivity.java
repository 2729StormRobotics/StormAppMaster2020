package org.stormroboticsnj;

import android.os.Bundle;
import android.util.Log;

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
        setContentView(R.layout.activity_scan);
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
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {
        Whoosh w;
        StormDao stormDao = db.stormDao();


        // Do something with the result here
        Log.v(TAG, rawResult.getText()); // Prints scan results
        Log.v(TAG, rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)

        String[] whoosh = rawResult.toString().split(Pattern.quote("|"));
        String[] whooshSub;

        for (int i = 0; i < whoosh.length; i++) {
            whooshSub = whoosh[i].split(Pattern.quote(","));
            w = new Whoosh();
            w.setTeam(Integer.parseInt(whooshSub[0]));
            w.setMatch(Integer.parseInt(whooshSub[1]));
            w.setAlliance(whoosh[2].equals("r"));
            w.setAPowerCell1(Integer.parseInt(whooshSub[3]));
            w.setAPowerCell2(Integer.parseInt(whooshSub[4]));
            w.setAPowerCell3(Integer.parseInt(whooshSub[5]));
            w.setAPowerCellPickup(Integer.parseInt(whooshSub[6]));
            w.setTPowerCell1(Integer.parseInt(whooshSub[7]));
            w.setTPowerCell2(Integer.parseInt(whooshSub[8]));
            w.setTPowerCell3(Integer.parseInt(whooshSub[9]));
            w.setTPowerCell3(Integer.parseInt(whooshSub[10]));
            w.setRotationControl(whooshSub[11].equals("y"));
            w.setPositionControl(whooshSub[12].equals("y"));
            w.setEPowerCell1(Integer.parseInt(whooshSub[13]));
            w.setEPowerCell2(Integer.parseInt(whooshSub[14]));
            w.setEPowerCell3(Integer.parseInt(whooshSub[15]));
            w.setLocations(whooshSub[16]);
            w.setEndgameOutcome(whooshSub[17]);
            stormDao.insertWhooshes(w);
        }
        /*try {
            Parser.parse(rawResult, this);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        // If you would like to resume scanning, call this method below:
        //mScannerView.resumeCameraPreview(this);

    }
}
