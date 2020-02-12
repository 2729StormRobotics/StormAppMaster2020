package org.stormroboticsnj;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.stormroboticsnj.dao.StormDao;
import org.stormroboticsnj.models.Whoosh;

import java.lang.ref.WeakReference;
import java.util.regex.Pattern;

public class ResultTask extends AsyncTask<String, Integer, Boolean> {
    AppDatabase db;
    WeakReference<Context> context;

    public ResultTask(AppDatabase db, Context context) {
        super();
        this.db = db;
        this.context = new WeakReference<>(context);
    }

    protected Boolean doInBackground(String... scan) {

        Whoosh w;

        StormDao stormDao = db.stormDao();

        String[] whoosh = scan[0].split(Pattern.quote("|"));
        String[] whooshSub;

        try {

            for (int i = 0; i < whoosh.length; i++) {
                whooshSub = whoosh[i].split(Pattern.quote(","));
                w = new Whoosh();
                w.setTeam(Integer.parseInt(whooshSub[0]));
                w.setMatch(Integer.parseInt(whooshSub[1]));
                w.setAlliance(whooshSub[2].equals("r"));
                w.setAPowerCell1(Integer.parseInt(whooshSub[3]));
                w.setAPowerCell2(Integer.parseInt(whooshSub[4]));
                w.setAPowerCell3(Integer.parseInt(whooshSub[5]));
                w.setAPowerCellPickup(Integer.parseInt(whooshSub[6]));
                w.setTPowerCell1(Integer.parseInt(whooshSub[7]));
                w.setTPowerCell2(Integer.parseInt(whooshSub[8]));
                w.setTPowerCell3(Integer.parseInt(whooshSub[9]));
                w.setRotationControl(whooshSub[10].equals("y"));
                w.setPositionControl(whooshSub[11].equals("y"));
                w.setEPowerCell1(Integer.parseInt(whooshSub[12]));
                w.setEPowerCell2(Integer.parseInt(whooshSub[13]));
                w.setEPowerCell3(Integer.parseInt(whooshSub[14]));
                w.setLocations(whooshSub[15]);
                w.setEndgameOutcome(whooshSub[16]);
                w.setDefenseSecs(Integer.parseInt(whooshSub[17]));
                stormDao.insertWhooshes(w);
            }

        } catch (Exception e) {
            Log.d("Failed", e.toString());

        }
        return true;
    }

    protected void onProgressUpdate(Integer... progress) {
        //setProgressPercent(progress[0]);
    }

    protected void onPostExecute(Boolean result) {
        //showDialog("Downloaded " + result + " bytes");
//        Toast t = new Toast(context);
//        t.makeText()
        if (context.get() != null) {
            Toast.makeText(context.get(), result ? "QR Scanned Successfully" : "Scan Unsucessful", Toast.LENGTH_SHORT).show();
        }
    }
}