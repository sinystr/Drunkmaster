package bg.stoykov.drunk.drunkmaster;

import java.util.ArrayList;
import java.util.HashSet;
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

    private ProgressDialog dialog;
    private AppAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apps);

        startThreadLoadApps();

        setListenerNextButton();


    }

    private void startThreadLoadApps(){
        new LoadDeviceApplications().execute();
    }

    private void setListenerNextButton(){
        Button nextStep = (Button) findViewById(R.id.btnAppsActivity);
        nextStep.setOnClickListener(this);
    }

    private void fillListView(ArrayList<AppInfo> appInfos){
        adapter = new AppAdapter(AppsActivity.this, R.layout.app_item ,appInfos);
        ListView mListView = (ListView) findViewById(R.id.lvApps);
        mListView.setAdapter(adapter);
    }

    private void showNoSelectedAppAlertToast(){
        Toast selectApps = Toast.makeText(AppsActivity.this, "Please select atleast 1 app!!", Toast.LENGTH_SHORT);
        selectApps.show();
    }

    private void saveAppsOnSharedPreferences(ArrayList<String> apps){
        SharedPreferences pref = getSharedPreferences("Lock_info", 0);
        SharedPreferences.Editor edit = pref.edit();
        Set<String> set = new HashSet<>();
        set.addAll(apps);
        edit.putStringSet("apps", set);
        edit.apply();
    }

    private void startTimeActivity(){
        Intent in = new Intent(this, TimeActivity.class);
        startActivity(in);
    }

    private void processCheckedApps(){
        // Getting the checked app package names
        ArrayList<String> apps = adapter.getSelected();

        if(apps.size() == 0) {
            showNoSelectedAppAlertToast();
        }else{
            saveAppsOnSharedPreferences(apps);
            startTimeActivity();

        }
    }

    private void showLoadingAppsDialog(){
        dialog = ProgressDialog.show(AppsActivity.this, "",
                "Loading Apps. Please wait...", true);
    }

    private void hideLoadingAppsDialog(){
        dialog.hide();
    }

    private void showSuccessToast(){
        Toast successMessage = Toast.makeText(AppsActivity.this, "All Applications loaded!", Toast.LENGTH_SHORT);
        successMessage.show();
    }

    private ArrayList<AppInfo> getAllApps(){
        ArrayList<AppInfo> appsInfoArray = new ArrayList<>();

        // We ask for array of apps on the device
        final PackageManager pm = getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo packageInfo : packages) {
            if(pm.getLaunchIntentForPackage(packageInfo.packageName)!= null &&
                    !pm.getLaunchIntentForPackage(packageInfo.packageName).equals("")) {

                try {
                    appsInfoArray.add(new AppInfo(pm.getApplicationLabel(packageInfo).toString(),
                            packageInfo.packageName, pm.getApplicationIcon(packageInfo.packageName)));

                } catch (NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        return appsInfoArray;
    }

    @Override
    public void onClick(View v) {
        processCheckedApps();
    }

    private class LoadDeviceApplications extends AsyncTask<Void, Void, ArrayList<AppInfo>>{

        @Override
        protected ArrayList<AppInfo> doInBackground(Void... params) {

            return getAllApps();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoadingAppsDialog();
        }

        @Override
        protected void onPostExecute(ArrayList<AppInfo> appInfos) {
            super.onPostExecute(appInfos);

            hideLoadingAppsDialog();
            fillListView(appInfos);
            showSuccessToast();


        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dialog.dismiss();
    }
}
