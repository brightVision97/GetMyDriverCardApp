package com.rachev.getmydrivercardapp.views.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.Toast;

import com.rachev.getmydrivercardapp.R;
import com.rachev.getmydrivercardapp.views.camera.CameraActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.main_grid_view)
    GridLayout mainGridLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);


        setSingleEvent(mainGridLayout);
    }

    private void setSingleEvent(GridLayout mainGridLayout) {
        for (int i = 0; i < mainGridLayout.getChildCount(); i++) {
            CardView cardView = (CardView) mainGridLayout.getChildAt(i);
            int finalI = i;
            cardView.setOnClickListener(view -> {

                startActivity(new Intent(HomeActivity.this, CameraActivity.class));

            });

        }


    }
}
