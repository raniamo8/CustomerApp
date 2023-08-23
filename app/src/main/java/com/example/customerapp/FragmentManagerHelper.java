package com.example.customerapp;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class FragmentManagerHelper {

    public static void replace(FragmentManager fragmentManager, int frameId, Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(frameId, fragment);
        fragmentTransaction.commit();
    }

    public static void goToFragment(FragmentManager fragmentManager, int frameId, Fragment fragment, int enterAnim, int exitAnim, boolean addToBackStack) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(enterAnim, exitAnim);
        transaction.replace(frameId, fragment);
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    public static void goBackToPreviousFragment(FragmentManager fragmentManager) {
        if (fragmentManager != null) {
            fragmentManager.popBackStack();
        }
    }
}
