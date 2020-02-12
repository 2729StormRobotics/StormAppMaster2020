package org.stormroboticsnj;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.room.Room;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private static final String TAG = "";
    private ZXingScannerView mScannerView;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //make sure we still have permission to use the camera
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(ScanActivity.this, MainActivity.class);
            intent.putExtra("CameraPermission", 0);
            startActivity(intent);
        }

        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);
        List<BarcodeFormat> formats = new ArrayList<>();
        formats.add(BarcodeFormat.QR_CODE);
        mScannerView.setFormats(formats);

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
        Intent intent = new Intent(ScanActivity.this, MainActivity.class);
        startActivity(intent);
    }


}


