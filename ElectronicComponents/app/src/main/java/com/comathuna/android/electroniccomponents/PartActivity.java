package com.comathuna.android.electroniccomponents;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PartActivity extends AppCompatActivity {
    public static final String EXTRA_PART_ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part);

        FragmentManager fm = getSupportFragmentManager();
        PartDetailFragment fragment = (PartDetailFragment)fm.findFragmentById(R.id.detail_frag);
        int partId = (int) getIntent().getExtras().get(EXTRA_PART_ID);
        fragment.setPart(partId);
    }
}
