package bg.stoykov.drunk.drunkmaster;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;



public class Startup extends BroadcastReceiver {

    public Startup() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        PreferencesController controller = new PreferencesController(context);
        if(controller.isLockTimeNotOver()) {
            Intent in = new Intent(context, BlockingService.class);
            context.startService(in);
        }
    }




}
