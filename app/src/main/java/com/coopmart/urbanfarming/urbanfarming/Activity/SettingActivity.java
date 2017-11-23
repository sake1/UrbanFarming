package com.coopmart.urbanfarming.urbanfarming.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.coopmart.urbanfarming.urbanfarming.Fragment.PickupFragment;
import com.coopmart.urbanfarming.urbanfarming.Method.Navigator;
import com.coopmart.urbanfarming.urbanfarming.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingActivity extends AppCompatActivity {

    private static final String TAG = "Pengaturan";

    @BindView(R.id.as_list) ListView settingList;

    private class CustomArrayAdapter extends ArrayAdapter<String> {

        private String[] data;
        private int lastPosition = -1;
        private class ViewHolder {
            public TextView title;
        }

        public CustomArrayAdapter(Context context, String[] data) {
            super(context, R.layout.listview_setting, data);
            this.data = data;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            String data = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            ViewHolder viewHolder; // view lookup cache stored in tag

            final View result;

            if (convertView == null) {

                viewHolder = new ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.listview_setting, parent, false);
                viewHolder.title = (TextView) convertView.findViewById(R.id.ls_listview_title);

                result=convertView;

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
                result=convertView;
            }

            Animation animation = AnimationUtils.loadAnimation(getContext(), (position > lastPosition) ? R.anim.fadein : R.anim.fadeout);
            result.startAnimation(animation);
            lastPosition = position;

            viewHolder.title.setText(data);
            // Return the completed view to render on screen
            return convertView;
        }
    }
    private class CustomOnItemClickListener implements AdapterView.OnItemClickListener {

        private static final int LOG_OUT_ITEM = 0;

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if(position == LOG_OUT_ITEM) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.putExtra("flag", Navigator.FROM_SETTING_ACTIVITY);
                startActivity(intent);
                finish();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        getSupportActionBar().setTitle(TAG);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        displayListViewContent();
    }

    private void displayListViewContent() {
        settingList.setAdapter(new CustomArrayAdapter(this, new String[]{"Log Out"}));
        settingList.setFooterDividersEnabled(true);
        settingList.setDividerHeight(1);
        settingList.setOnItemClickListener(new CustomOnItemClickListener());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
