package com.comathuna.android.electroniccomponents;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PartDetailFragment extends Fragment {

    private long partId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_part_detail, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        View view= getView();
        if (view != null) {
            TextView title = (TextView) view.findViewById(R.id.textTitle);
            Part part = Part.sParts[(int) partId];
            title.setText(part.getName());
            TextView description = (TextView) view.findViewById(R.id.textDescription);
            description.setText(part.getDescription());
        }
    }

    public void setPart(long id){
        this.partId = id;
    }
}