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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.coopmart.urbanfarming.urbanfarming.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {

    public static final String TAG = "Riwayat Transaksi";

    @BindView(R.id.fh_no_list_label) TextView message;
    @BindView(R.id.fh_transaction_list) ListView listView;

    private ArrayList<String[]> transactionData;

    private class CustomArrayAdapter extends ArrayAdapter<String[]> {

        private ArrayList<String[]> data;
        private int lastPosition = -1;
        private class ViewHolder {
            public TextView name;
            public TextView qty;
            public TextView price;
            public TextView time;
            public TextView point;
            public ImageView image;
        }

        public CustomArrayAdapter(Context context, ArrayList<String[]> data) {
            super(context, R.layout.listview_transaction_history, data);
            this.data = data;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            String[] data = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            HistoryFragment.CustomArrayAdapter.ViewHolder viewHolder; // view lookup cache stored in tag

            final View result;

            if (convertView == null) {

                viewHolder = new HistoryFragment.CustomArrayAdapter.ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.listview_transaction_history, parent, false);
                viewHolder.name = (TextView) convertView.findViewById(R.id.th_output_product_name);
                viewHolder.qty = (TextView) convertView.findViewById(R.id.th_output_product_qty);
                viewHolder.price = (TextView) convertView.findViewById(R.id.th_output_product_payment);
                viewHolder.time = (TextView) convertView.findViewById(R.id.th_output_timestamp);
                viewHolder.point = (TextView) convertView.findViewById(R.id.th_output_point);
                viewHolder.image = (ImageView) convertView.findViewById(R.id.th_product_picture);

                result=convertView;

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (HistoryFragment.CustomArrayAdapter.ViewHolder) convertView.getTag();
                result=convertView;
            }

            Animation animation = AnimationUtils.loadAnimation(getContext(), (position > lastPosition) ? R.anim.fadein : R.anim.fadeout);
            result.startAnimation(animation);
            lastPosition = position;

            viewHolder.name.setText(data[0]);
            viewHolder.qty.setText(data[1]);
            viewHolder.price.setText(data[2]);
            viewHolder.time.setText(data[3]);
            viewHolder.point.setText(data[4]);

            // Return the completed view to render on screen
            return convertView;
        }
    }

    public HistoryFragment() {
        transactionData = new ArrayList<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(TAG);
        ButterKnife.bind(this, view);

        displayListViewContent();

        return view;
    }

    private void getPickupRequestData() {
        transactionData.add(new String[] {"Bayam", "500 gr", "Rp. 2000", "07:12 15 Maret", "+20"});
        transactionData.add(new String[] {"Bawang Putih", "2000 gr", "Rp. 12000", "07:32 16 Maret", "+120"});
        transactionData.add(new String[] {"Bayam", "700 gr", "Rp. 2800",  "19:44 19 Maret", "+28"});
        transactionData.add(new String[] {"Bayam", "300 gr", "Rp. 1200",  "20:12 25 Maret", "+12"});
        transactionData.add(new String[] {"Bayam", "500 gr", "Rp. 2000",  "11:59 2 April", "+20"});
        transactionData.add(new String[] {"Bawang Putih", "1500 gr", "Rp. 9000", "11:59 2 April", "+90"});
    }

    private void displayListViewContent() {
        transactionData.clear();
        getPickupRequestData();
        if(transactionData.size() == 0) {
            message.setVisibility(View.VISIBLE);
            return;
        }

        ArrayAdapter adapter = new HistoryFragment.CustomArrayAdapter(getActivity(), transactionData);
        listView.setAdapter(adapter);
        listView.setFooterDividersEnabled(true);
        listView.setDividerHeight(1);
    }
}
