package com.lqm.tomatoit.util;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.util.Log;

/**
 * autour: lqm
 * desc:
 */
public class ActivityUtils {

    private static final String TAG = "ActivityUtils";

    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                             @NonNull Fragment fragment, int frameId) {
        addFragmentToActivity(fragmentManager, fragment, frameId, true);
    }

    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                             @NonNull Fragment fragment, int frameId, boolean addToBackStack) {
        if (fragment.isAdded()) {
            Log.w(TAG, "addFragmentToActivity: fragment is added:" + fragment.getClass().getName());
            return;
        }
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(frameId, fragment);
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
    }
}

