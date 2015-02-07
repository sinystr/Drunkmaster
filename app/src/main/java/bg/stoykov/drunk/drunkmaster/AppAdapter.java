package bg.stoykov.drunk.drunkmaster;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;

public class AppAdapter extends ArrayAdapter<AppInfo> {

    private ArrayList<AppInfo> appList;
    private Context context;

    public AppAdapter(Context context, int textViewResourceId,
                           ArrayList<AppInfo> appList) {
        super(context, textViewResourceId, appList);
        this.appList = new ArrayList<>();
        this.appList.addAll(appList);
        this.context = context;
    }

    private class ViewHolder {
        ImageView mProfileImageView;
        TextView mAppName;
        CheckBox mCheckboxBlock;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = convertView;

        if (rowView == null) {
            // Inflating the xml view
            LayoutInflater vi = (LayoutInflater)context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            rowView = vi.inflate(R.layout.app_item, null);

            // Creating a new ViewHolder and filling it
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.mProfileImageView = (ImageView) rowView.findViewById(R.id.ivAppItem);
            viewHolder.mAppName = (TextView) rowView.findViewById(R.id.tvAppItem);

            CheckBox checkBox;
            checkBox = (CheckBox) rowView.findViewById(R.id.cbAppItem);
            viewHolder.mCheckboxBlock = checkBox;

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v ;
                    AppInfo app = (AppInfo) cb.getTag();
                    app.setSelected( cb.isChecked() );
                }
            });

            rowView.setTag(viewHolder);

        }

        ViewHolder holder = (ViewHolder) rowView.getTag();
        AppInfo app = appList.get(position);
        holder.mAppName.setText(app.getName());
        holder.mCheckboxBlock.setChecked(app.isSelected());
        holder.mProfileImageView.setImageDrawable(app.getPhoto());

        // Binding the current app to the checkbox
        // ( need this for the click listener )
        AppInfo currentApp = appList.get(position);
        holder.mCheckboxBlock.setTag(currentApp);

        return rowView;

    }

    public ArrayList<String> getSelected(){
        ArrayList<String> filteredAppList = new ArrayList<>();
        if(appList.size() > 0) {
            for (AppInfo app : appList) {
                if (app.isSelected()) {
                    filteredAppList.add(app.getPackageName());
                }
            }
        }
        return filteredAppList;
    }

}