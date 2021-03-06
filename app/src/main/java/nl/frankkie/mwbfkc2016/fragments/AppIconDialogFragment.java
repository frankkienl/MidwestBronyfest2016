package nl.frankkie.mwbfkc2016.fragments;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import nl.frankkie.mwbfkc2016.R;
import nl.frankkie.mwbfkc2016.util.Util;

/**
 * Created by fbouwens on 1-2-2016.
 */
public class AppIconDialogFragment extends DialogFragment {

    public static final int[] ICONS = {R.drawable.ic_launcher_mwbfkc2016_web};
    public static final int[] ICONS_mipmap = {R.mipmap.ic_launcher};

    public AppIconDialogFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout rootView = (LinearLayout) inflater.inflate(R.layout.dialog_pickicon, container, false);
        for (int i = 0; i < ICONS.length; i++) {
            String iconName = "Icon " + (i + 1);
            final int id = i;
            View row = inflater.inflate(R.layout.dialog_pickicon_row, rootView, false);
            ((ImageView) row.findViewById(R.id.pickicon_image)).setImageResource(ICONS[i]);
            ((TextView) row.findViewById(R.id.pickicon_name)).setText(iconName);
            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeAppIcon(getActivity(), id);
                    //remove dialog
                    dismiss();
                }
            });
            rootView.addView(row);
        }
        getDialog().setTitle("Pick Icon");
        return rootView;
    }

    public static void changeAppIcon(Context context, int choiceIndex) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putInt("app_icon", ICONS[choiceIndex]);
        prefsEditor.putInt("app_icon_mipmap", ICONS_mipmap[choiceIndex]);
        prefsEditor.commit();
        //
        PackageManager pm = context.getPackageManager();
        for (int i = 0; i < ICONS.length; i++) {
            ComponentName componentName = new ComponentName(context.getPackageName(), context.getPackageName() + ".activities.Splash" + (i + 1) + "Activity");
            if (i == choiceIndex) {
                //enable this one
                pm.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
            } else {
                //disable this one
                pm.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
            }
        }
        //
        if (context instanceof Activity) {
            try {
                Snackbar.make(((Activity) context).findViewById(R.id.container), R.string.about_changedicon, Snackbar.LENGTH_LONG).show();
            } catch (Exception e) {
                Util.sendACRAReport("AppIconDialogFragment.changeAppIcon", e);
            }
        }
    }
}
