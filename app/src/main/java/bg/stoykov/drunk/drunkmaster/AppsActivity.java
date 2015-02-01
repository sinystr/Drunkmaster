package bg.stoykov.drunk.drunkmaster;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


public class AppsActivity extends DrunkenMasterActionBarActivity implements View.OnClickListener {

    private ListView mListView;
    private ProgressDialog dialog;
    private AppAdapter adapter;
    private Button nextStep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apps);

        // Activating the thread that loads all apps
        // and puts them in the list view
        new LoadDeviceApplications().execute();

        nextStep = (Button)findViewById(R.id.btnAppsActivity);
        nextStep.setOnClickListener(this);

    }

    private void fillList(ArrayList<AppInfo> appInfos){
        adapter = new AppAdapter(AppsActivity.this, R.layout.app_item ,appInfos);
        mListView = (ListView) findViewById(R.id.lvApps);
        mListView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        ArrayList<String> apps = adapter.getSelected();
        if(apps.size() == 0) {
            Toast selectApps = Toast.makeText(AppsActivity.this, "Please select atleast 1 app!!", Toast.LENGTH_SHORT);
            selectApps.show();
        }else{
            SharedPreferences pref = getSharedPreferences("Lock_info", 0);
            SharedPreferences.Editor edit = pref.edit();
            Set<String> set = new HashSet<>();
            set.addAll(apps);
            edit.putStringSet("apps", set);
            edit.apply();

            Intent in = new Intent(this, TimeActivity.class);
            startActivity(in);


        }
    }

    private class LoadDeviceApplications extends AsyncTask<Void, Void, ArrayList<AppInfo>>{

        @Override
        protected ArrayList<AppInfo> doInBackground(Void... params) {
            ArrayList<AppInfo> appInfo = new ArrayList<>();

            // We ask for array of apps on the device
            final PackageManager pm = getPackageManager();
            List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

            for (ApplicationInfo packageInfo : packages) {
                if(pm.getLaunchIntentForPackage(packageInfo.packageName)!= null &&
                        !pm.getLaunchIntentForPackage(packageInfo.packageName).equals("")) {

                    try {
                        appInfo.add(new AppInfo(pm.getApplicationLabel(packageInfo).toString(),
                                packageInfo.packageName, pm.getApplicationIcon(packageInfo.packageName)));

                    } catch (NameNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }

            return appInfo;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(AppsActivity.this, "",
                    "Loading Apps. Please wait...", true);
        }

        @Override
        protected void onPostExecute(ArrayList<AppInfo> appInfos) {
            super.onPostExecute(appInfos);
            fillList(appInfos);

            dialog.hide();
            Toast successMessage = Toast.makeText(AppsActivity.this, "All Applications loaded!", Toast.LENGTH_SHORT);
            successMessage.show();

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dialog.dismiss();
    }
}
