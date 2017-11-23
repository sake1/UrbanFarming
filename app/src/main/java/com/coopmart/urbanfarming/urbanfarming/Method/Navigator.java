package com.coopmart.urbanfarming.urbanfarming.Method;


/**
 * Created by sake on 08/06/17.
 */

public class Navigator {
    public static final int ERROR_CODE = -1;

    public static final int FROM_LOGIN_ACTIVITY = 0;
    public static final int REQUEST_PROFILE_EDIT = 1;
    public static final int FROM_SETTING_ACTIVITY = 2;
    public static final int FROM_HOME_ACTIVITY = 3;
    public static final int REQUEST_SIGNUP = 4;

    public static final int FROM_GOOGLE_MAPS_PICK_LOCATION = 5;
    public static final int FROM_GALLERY = 6;
    public static final int CODE_REQUEST_LOCATION = 7;
    public static final int CODE_REQUEST_EXTERNAL_STORAGE = 8;


//    public static void changeHomeActivityDisplayedFragment(Fragment intendedFragment, FragmentActivity fragment) {
//        /**
//         * TODO
//         * Add a prevention of any other kind of activity get passed to second param
//         */
//        FragmentManager fragmentManager = fragment.getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
//        fragmentTransaction.replace(R.id.h_fragment_frame, intendedFragment);
//        fragmentTransaction.commit();
//    }
}
