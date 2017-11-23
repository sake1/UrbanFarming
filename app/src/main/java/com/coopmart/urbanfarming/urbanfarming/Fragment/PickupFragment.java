package com.coopmart.urbanfarming.urbanfarming.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
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
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.coopmart.urbanfarming.urbanfarming.Activity.HomeActivity;
import com.coopmart.urbanfarming.urbanfarming.Activity.SettingActivity;
import com.coopmart.urbanfarming.urbanfarming.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class PickupFragment extends Fragment {

    public static final String TAG = "Jadwal Pengambilan Produk";

    @BindView(R.id.fp_no_list_label) TextView message;
    @BindView(R.id.fp_pickup_list) ListView listView;
    @BindView(R.id.fp_trigger_request) Button request;

    private ArrayList<String[]> pickupData;

    private class CustomArrayAdapter extends ArrayAdapter<String[]> {

        private ArrayList<String[]> data;
        private int lastPosition = -1;
        private class ViewHolder {
            public TextView session;
            public TextView time;
            public TextView status;
            public FrameLayout frame;
            public ImageView image;
        }

        public CustomArrayAdapter(Context context, ArrayList<String[]> data) {
            super(context, R.layout.listview_pickup_list, data);
            this.data = data;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            String[] data = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            PickupFragment.CustomArrayAdapter.ViewHolder viewHolder; // view lookup cache stored in tag

            final View result;

            if (convertView == null) {

                viewHolder = new PickupFragment.CustomArrayAdapter.ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.listview_pickup_list, parent, false);
                viewHolder.session = (TextView) convertView.findViewById(R.id.pl_output_session);
                viewHolder.time = (TextView) convertView.findViewById(R.id.pl_output_time);
                viewHolder.status = (TextView) convertView.findViewById(R.id.pl_output_status);
                viewHolder.frame = (FrameLayout) convertView.findViewById(R.id.pl_output_status_frame);
                viewHolder.image = (ImageView) convertView.findViewById(R.id.pl_output_status_picture);

                result=convertView;

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (PickupFragment.CustomArrayAdapter.ViewHolder) convertView.getTag();
                result=convertView;
            }

            Animation animation = AnimationUtils.loadAnimation(getContext(), (position > lastPosition) ? R.anim.fadein : R.anim.fadeout);
            result.startAnimation(animation);
            lastPosition = position;

            viewHolder.session.setText(data[0]);
            viewHolder.time.setText(data[1]);
            viewHolder.status.setText(data[2]);

            if(data[2].equals("REQUEST PENDING")) {
                viewHolder.frame.setBackgroundColor(getResources().getColor(R.color.yellowPending));
                viewHolder.image.setImageResource(R.drawable.clock_white_logo);
            } else if(data[2].equals("REQUEST ACCEPTED")) {
                viewHolder.frame.setBackgroundColor(getResources().getColor(R.color.blueAccepted));
                viewHolder.image.setImageResource(R.drawable.check_white_logo);
            } else {
                viewHolder.frame.setBackgroundColor(getResources().getColor(R.color.redDeclined));
                viewHolder.image.setImageResource(R.drawable.cross_white_logo);
            }
            // Return the completed view to render on screen
            return convertView;
        }
    }

    public PickupFragment() {
        pickupData = new ArrayList<>();
    }

    @OnClick(R.id.fp_trigger_request)
    public void pickupRequest() {
        ((HomeActivity) getActivity()).changeHomeActivityDisplayedFragment(new PickupRequestFragment(), true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pickup, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(TAG);
        ButterKnife.bind(this, view);

        displayListViewContent();

        return view;
    }

    private void getPickupRequestData() {
        pickupData.add(new String[] {"Pickup sesi 2", "Jam 14.00 - 17.00", "REQUEST PENDING"});
        pickupData.add(new String[] {"Pickup sesi 3", "Jam 19.00 - 21.00", "REQUEST ACCEPTED"});
        pickupData.add(new String[] {"Pickup sesi 1", "Jam 06.00 - 09.00", "REQUEST DECLINED"});
    }

    private void displayListViewContent() {
        pickupData.clear();
        getPickupRequestData();
        if(pickupData.size() == 0) {
            message.setVisibility(View.VISIBLE);
            return;
        }

        ArrayAdapter adapter = new PickupFragment.CustomArrayAdapter(getActivity(), pickupData);
        listView.setAdapter(adapter);
        listView.setFooterDividersEnabled(true);
        listView.setDividerHeight(1);
    }

}
