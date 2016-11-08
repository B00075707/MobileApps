package com.comathuna.android.electroniccomponents;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class TopLevelActivity extends AppCompatActivity implements PartListFragment.PartListListener {

    private static final boolean LOCAL_LOGD = true;
    private static final String TAG = "TopLevelActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_level);
        if (LOCAL_LOGD) Log.d(TAG, "onCreate() called");
    }

    @Override
    public void itemClicked(long id) {
//        View fragmentContainer = findViewById(R.id.fragment_container);
//        if (fragmentContainer != null) {
        if (getResources().getBoolean(R.bool.is_landscape)) {
            FragmentManager fm = getSupportFragmentManager();
            //PartDetailFragment fragment = (PartDetailFragment)fm.findFragmentById(R.id.fragment_container);

            PartDetailFragment fragment = new PartDetailFragment();
            fragment.setPart(id);
            fm.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        } else {
            Intent intent = new Intent(this, PartActivity.class);
            intent.putExtra(PartActivity.EXTRA_PART_ID, (int)id);
            startActivity(intent);
        }
        if (LOCAL_LOGD) Log.d(TAG, "itemClicked() called");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        if (LOCAL_LOGD) Log.d(TAG, "onSaveInstanceState() called");
    }

    @Override
    public void onStart() {
        super.onStart();
        if (LOCAL_LOGD) Log.d(TAG, "onStart() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        if (LOCAL_LOGD) Log.d(TAG, "onPause() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        if (LOCAL_LOGD) Log.d(TAG, "onResume() called");
    }

    @Override
    public void onStop() {
        super.onStop();
        if (LOCAL_LOGD) Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (LOCAL_LOGD) Log.d(TAG, "onDestroy() called");
    }

}
