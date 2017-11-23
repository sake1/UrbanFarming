package com.coopmart.urbanfarming.urbanfarming.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coopmart.urbanfarming.urbanfarming.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CatalogFragment extends Fragment {

    public static final String TAG = "Katalog";

    public CatalogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_catalog, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(TAG);
        return view;
    }

}
