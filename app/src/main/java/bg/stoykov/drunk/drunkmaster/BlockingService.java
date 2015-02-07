package bg.stoykov.drunk.drunkmaster;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class BlockingService extends Service {

    private long mShutdownTime;
    private Handler handler = new Handler();
    private ActivityManager mActivityManager;
    private ArrayList<String> appList = new ArrayList<>();

    public BlockingService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initShutdownTime();
        initActivityManager();
        fillBlockedAppList();
        // Start checking loop
        handler.postDelayed(runnable, 0);
        startForegroundService();

    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private Runnable runnable = new Runnable() {

        @Override
        public void run() {
            if(appList.contains(getCurrentActivityPackageName())){
                exitBlockedApp();
            }
            tryToShutdownService();
            handler.postDelayed(this, 500);
        }
    };

    private void initShutdownTime(){
        SharedPreferences pref = getSharedPreferences("Lock_info", 0);
        this.mShutdownTime = pref.getLong("shutdowntime", 0);
        //Date date = new Date();
        //this.mShutdownTime = date.getTime() + 1 * 60000;
    }

    private void tryToShutdownService(){
        Date date = new Date();
        long currentTime = date.getTime();
        if(currentTime > mShutdownTime){
            stopSelf();
            nullShutdownPrefTime();
        }
    }

    private void fillBlockedAppList(){
        SharedPreferences pref = getSharedPreferences("Lock_info", 0);
        Set<String> set = pref.getStringSet("apps", null);
        for (Iterator<String> it = set.iterator(); it.hasNext(); ) {
            String f = it.next();
            appList.add(f);
        }
    }

    private void initActivityManager() {
        mActivityManager = (ActivityManager) BlockingService.this
                .getSystemService(Context.ACTIVITY_SERVICE);
    }

    private void startForegroundService(){
        Notification notification = new Notification(R.drawable.ic_launcher, "Drunkmaster activated!",
                System.currentTimeMillis());
        Intent notificationIntent = new Intent(this, BlockingService.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        notification.setLatestEventInfo(this, "Drunkmaster",
                "Activated!", pendingIntent);
        startForeground(101, notification);
    }

    private String getCurrentActivityPackageName(){

        String mPackageName;

        if(Build.VERSION.SDK_INT > 20){
            mPackageName = mActivityManager.getRunningAppProcesses().get(0).processName;
        }
        else{
            mPackageName = mActivityManager.getRunningTasks(1).get(0).baseActivity.getPackageName();
        }

        return mPackageName;
    }

    private void toastBlockedApp(){
        Toast.makeText(getApplicationContext(), "This app is locked!",
                Toast.LENGTH_SHORT).show();
    }

    private void goToHomeScreen(){
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void exitBlockedApp(){
        goToHomeScreen();
        toastBlockedApp();
    }

    void nullShutdownPrefTime(){
        SharedPreferences pref = getSharedPreferences("Lock_info", 0);
        SharedPreferences.Editor edit = pref.edit();
        long shutdownTime = 0;
        edit.putLong("shutdowntime", shutdownTime);
        edit.apply();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(getApplicationContext(), "Apps are now unlocked!!",
                Toast.LENGTH_SHORT).show();
    }
}
