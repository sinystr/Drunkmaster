package bg.stoykov.drunk.drunkmaster;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class PreferencesController {
    private SharedPreferences preferences;
    private static final int HOUR_IN_MILLISECONDS = 3600000;
    private static final String SHUTDOWN_ID = "shutdown";
    private static final String HOURS_ID = "hours";
    private static final String APPS_ID = "apps";

    PreferencesController(Context context){
        preferences = context.getSharedPreferences("Lock_info", 0);
    }

    public boolean isLockTimeNotOver(){
        return currentTime() < shutdownTime();

    }

    public void saveHourValue(int hours){
        SharedPreferences.Editor edit = preferences.edit();
        long shutdownTime = currentTime() + (hours * HOUR_IN_MILLISECONDS);
        edit.putLong(SHUTDOWN_ID, shutdownTime);
        edit.putInt(HOURS_ID, hours);
        edit.apply();

    }

    private long currentTime(){
        Date date = new Date();
        return date.getTime();

    }

    private long shutdownTime(){
        return preferences.getLong(SHUTDOWN_ID, 0);

    }

    int getHours(){
        return preferences.getInt(HOURS_ID, 0);
    }

    public void nullShutdownPrefTime(){
        SharedPreferences.Editor edit = preferences.edit();
        long shutdownTime = 0;
        edit.putLong(SHUTDOWN_ID, shutdownTime);
        edit.apply();
    }

    public void saveApps(ArrayList<String> apps){
        SharedPreferences.Editor edit = preferences.edit();
        Set<String> set = new HashSet<>();
        set.addAll(apps);
        edit.putStringSet(APPS_ID, set);
        edit.apply();
    }

    public Set<String> getApps(){
        return preferences.getStringSet(APPS_ID, null);
    }

    public long getShutdownTime(){
        return preferences.getLong(SHUTDOWN_ID, 0);
    }

}
