package bg.stoykov.drunk.drunkmaster;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


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

        ViewHolder holder = null;

        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater)context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.app_item, null);

            holder = new ViewHolder();
            holder.mProfileImageView = (ImageView) convertView.findViewById(R.id.ivAppItem);
            holder.mAppName = (TextView) convertView.findViewById(R.id.tvAppItem);
            holder.mCheckboxBlock = (CheckBox) convertView.findViewById(R.id.cbAppItem);
            convertView.setTag(holder);

        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        AppInfo app = appList.get(position);
        holder.mAppName.setText(app.getName());
        holder.mCheckboxBlock.setChecked(app.isSelected());
        holder.mProfileImageView.setImageDrawable(app.getPhoto());

        return convertView;

    }

}