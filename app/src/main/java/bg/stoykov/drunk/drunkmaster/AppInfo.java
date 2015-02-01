package bg.stoykov.drunk.drunkmaster;

import android.graphics.drawable.Drawable;

public class AppInfo {

    private String mName;
    private String mPackageName;
    private Drawable mPhoto;
    boolean mSelected = false;

    public AppInfo(String name, String packageName, Drawable photo) {
        this.mName = name;
        this.mPackageName = packageName;
        this.mPhoto = photo;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getPackageName() {
        return mPackageName;
    }

    public void setPackageName(String packageName) {
        this.mPackageName = packageName;
    }

    public Drawable getPhoto() {
        return mPhoto;
    }

    public void setPhotoUrl(Drawable photo) {
        this.mPhoto = photo;
    }

    public boolean isSelected() {
        return mSelected;
    }

    public void setSelected(boolean selected) {
        this.mSelected = selected;
    }

}