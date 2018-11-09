package com.rachev.getmydrivercardapp.views.cardRequest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.rachev.getmydrivercardapp.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CardRequestActivity extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    @BindView(R.id.spinner)
    Spinner spinner;

    @BindView(R.id.nextButton)
    Button nextButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_request);

        ButterKnife.bind(this);

        spinner.setOnItemSelectedListener(this);

        List<String> categories = new ArrayList<>();
        categories.add("First card");
        categories.add("Exchange EU for BG");
        categories.add("Stolen/Lost/damaged");
        categories.add("Chaged adress/name/photo");
        categories.add("Renewal due to expired/suspended/withdrawn");

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, categories);

        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(categoryAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {

        }
    }
}
