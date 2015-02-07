package bg.stoykov.drunk.drunkmaster;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.Date;


public class Startup extends BroadcastReceiver {
    private Context mContext;
    public Startup() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context;
        if(currentTime() < shutdownTime()) {
            Intent in = new Intent(context, BlockingService.class);
            context.startService(in);
        }
    }

    long shutdownTime(){
        SharedPreferences pref = mContext.getSharedPreferences("Lock_info", 0);
        return pref.getLong("shutdowntime", 0);
    }

    long currentTime(){
        Date date = new Date();
        return date.getTime();
    }



}
