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
import java.util.Iterator;
import java.util.Set;

public class BlockingService extends Service {

    private Handler handler = new Handler();
    private ActivityManager mActivityManager;
    private ArrayList<String> appList = new ArrayList<>();

    public BlockingService() {
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

            handler.postDelayed(this, 500);
        }
    };


    void fillAppList(){
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

    void startForegroundService(){
        Notification notification = new Notification(R.drawable.ic_launcher, "Drunkmaster",
                System.currentTimeMillis());
        Intent notificationIntent = new Intent(this, BlockingService.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        notification.setLatestEventInfo(this, "Service",
                "Activated!", pendingIntent);
        startForeground(101, notification);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initActivityManager();
        fillAppList();
        // Start checking
        handler.postDelayed(runnable, 0);
        startForegroundService();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //Date date = new Date(System.currentTimeMillis());
        //Toast.makeText(this, "Time: " + date.getTime(), Toast.LENGTH_LONG).show();
        return START_STICKY;
    }

    public String getCurrentActivityPackageName(){

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


}
