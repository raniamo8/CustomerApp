package customerapp.models.customerapp;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class FragmentManagerHelper {
    private static final long MIN_CLICK_INTERVAL = 200;
    private static long mLastClickTime;

    public static boolean isClickValid() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - mLastClickTime < MIN_CLICK_INTERVAL) {
            return false;
        }
        mLastClickTime = currentTime;
        return true;
    }

    public static void replace(FragmentManager fragmentManager, int frameId, Fragment fragment) {
        if (!isClickValid()) {
            return;
        }

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
