package com.comathuna.android.electroniccomponents;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryListFragment extends ListFragment {


    public CategoryListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String[] names = new String[Part.PartCategory.values().length];
        for (int i=0; i<names.length; i++) {
            names[i] = Part.sParts[i].getName();
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

}
