package com.coopmart.urbanfarming.urbanfarming.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.coopmart.urbanfarming.urbanfarming.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainMenuFragment extends Fragment {

    public static final String TAG = "Beranda";

//    @BindView(R.id.fmm_input_search) EditText searchBox;
//    @BindView(R.id.fmm_output_listview) ListView productList;
//    @BindView(R.id.fmm_static_error_message) TextView message;
//
//    private ArrayList<String[]> productData;
//
//    private class CustomArrayAdapter extends ArrayAdapter<String[]> {
//
//        private ArrayList<String[]> data;
//        private int lastPosition = -1;
//        private class ViewHolder {
//            public TextView name;
//            public TextView seller;
//            public TextView price;
//            public ImageView image;
//        }
//
//        public CustomArrayAdapter(Context context, ArrayList<String[]> data) {
//            super(context, R.layout.listview_product, data);
//            this.data = data;
//        }
//
//        @NonNull
//        @Override
//        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//            String[] data = getItem(position);
//            // Check if an existing view is being reused, otherwise inflate the view
//            MainMenuFragment.CustomArrayAdapter.ViewHolder viewHolder; // view lookup cache stored in tag
//
//            final View result;
//            viewHolder = new MainMenuFragment.CustomArrayAdapter.ViewHolder();
//            LayoutInflater inflater = LayoutInflater.from(getContext());
//            convertView = inflater.inflate(R.layout.listview_product, parent, false);
//            viewHolder.name = (TextView) convertView.findViewById(R.id.lp_output_product_name);
//            viewHolder.seller = (TextView) convertView.findViewById(R.id.lp_output_product_seller);
//            viewHolder.price = (TextView) convertView.findViewById(R.id.lp_output_product_price);
//            viewHolder.image = (ImageView) convertView.findViewById(R.id.lp_output_image);
//
//            result=convertView;
//            convertView.setTag(viewHolder);
//
//            Animation animation = AnimationUtils.loadAnimation(getContext(), (position > lastPosition) ? R.anim.fadein : R.anim.fadeout);
//            result.startAnimation(animation);
//            lastPosition = position;
//
//            viewHolder.name.setText(data[0]);
//            viewHolder.seller.setText(data[1]);
//            viewHolder.price.setText(data[2]);
//
//            // Return the completed view to render on screen
//            return convertView;
//        }
//    }

    public MainMenuFragment() {
        // Required empty public constructor
//        productData = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_menu, container, false);
        ButterKnife.bind(this, view);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(TAG);

//        displayListViewContent();

        return view;
    }

//    private void getPickupRequestData() {
//        productData.add(new String[] {"Kangkung Hijau", "Toko Ibu Susi", "Rp. 2000"});
//        productData.add(new String[] {"Wortel", "Toko Ibu Susi", "Rp. 10000"});
//        productData.add(new String[] {"Beras Setra Ramos", "Toko Ibu Susi", "Rp. 60000"});
//        productData.add(new String[] {"Telur Ayam", "Toko Ibu Nina", "Rp. 14000"});
//        productData.add(new String[] {"Pisang Raja", "Toko Ibu Nina", "Rp. 10000"});
//        productData.add(new String[] {"Tomat", "Toko Ibu Nina", "Rp. 6000"});
//    }
//
//    private void displayListViewContent() {
//        productData.clear();
//        getPickupRequestData();
//        if(productData.size() == 0) {
//            message.setVisibility(View.VISIBLE);
//            return;
//        }
//
//        ArrayAdapter adapter = new MainMenuFragment.CustomArrayAdapter(getActivity(), productData);
//        productList.setAdapter(adapter);
//        productList.setFooterDividersEnabled(true);
//        productList.setDividerHeight(1);
//    }
}
