package com.comathuna.android.electroniccomponents;

//import android.app.Fragment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class PartDetailFragment extends Fragment {

    private static final String PART_ID_KEY_INDEX = "part_id_index";
    private static final boolean LOCAL_LOGD = true;
    private static final String TAG = "PartDetailFragment";
    private long mPartId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (LOCAL_LOGD) Log.d(TAG, "onCreate() called");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mPartId = savedInstanceState.getLong(PART_ID_KEY_INDEX);
        }
        if (LOCAL_LOGD) Log.d(TAG, "onCreateView() called");
        return inflater.inflate(R.layout.fragment_part_detail, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (LOCAL_LOGD) Log.d(TAG, "onStart() called");
        View view= getView();
        if (view != null) {
            Part part = Part.sParts[(int) mPartId];

            TextView title = (TextView) view.findViewById(R.id.textTitle);
            title.setText(part.getName());

            TextView description = (TextView) view.findViewById(R.id.textDescription);
            description.setText(part.getDescription());

            ImageView image = (ImageView) view.findViewById(R.id.imagePartIcon);
            image.setImageResource(part.getImageResourceId());
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putLong(PART_ID_KEY_INDEX, mPartId);
        if (LOCAL_LOGD) Log.d(TAG, "onSaveInstanceState() called");
    }

    public void setPart(long id){
        this.mPartId = id;
        if (LOCAL_LOGD) Log.d(TAG, "setPart() called");
    }
}