package bg.stoykov.drunk.drunkmaster;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class BlockingService extends Service {

    private long mShutdownTime;
    private Handler handler = new Handler();
    private ActivityManager mActivityManager;
    private ArrayList<String> mAppList = new ArrayList<>();

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
        startServiceAsForegroundService();

    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private Runnable runnable = new Runnable() {

        @Override
        public void run() {
            if(mAppList.contains(getCurrentActivityPackageName())){
                exitBlockedApp();
            }
            tryToShutdownService();
            handler.postDelayed(this, 500);
        }
    };

    private void initShutdownTime(){
        PreferencesController controller = new PreferencesController(this);
        this.mShutdownTime = controller.getShutdownTime();
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
        PreferencesController controller = new PreferencesController(this);
        for (String app : controller.getApps()) {
            mAppList.add(app);
        }
    }

    private void initActivityManager() {
        mActivityManager = (ActivityManager) BlockingService.this
                .getSystemService(Context.ACTIVITY_SERVICE);
    }

    private void startServiceAsForegroundService(){
        Notification notification = new Notification(R.drawable.ic_launcher,
                getString(R.string.drunkmaster_activated),
                System.currentTimeMillis());
        Intent notificationIntent = new Intent(this, BlockingService.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        notification.setLatestEventInfo(this, "Drunkmaster",
                "Activated!", pendingIntent);
        startForeground(101, notification);
    }

    private String getCurrentActivityPackageName(){

        String packageName;

        if(Build.VERSION.SDK_INT > 20){
            packageName = mActivityManager.getRunningAppProcesses().get(0).processName;
        }
        else{
            packageName = mActivityManager.getRunningTasks(1).get(0).baseActivity.getPackageName();
        }

        return packageName;
    }

    private void toastBlockedApp(){
        Toast.makeText(getApplicationContext(), getString(R.string.app_locked),
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
        PreferencesController controller = new PreferencesController(this);
        controller.nullShutdownPrefTime();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
        Toast.makeText(getApplicationContext(), getString(R.string.apps_unlocked),
                Toast.LENGTH_SHORT).show();
    }
}
