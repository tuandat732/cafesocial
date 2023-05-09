package com.example.cafesocial.view.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.cafesocial.R;

public class StoreProgressBar extends TableRow {
    private TextView title;
    private ProgressBar progressBar;
    private TextView rate;

    public StoreProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.component_store_progress_bar, this, true);

        title = findViewById(R.id.title);
        progressBar = findViewById(R.id.progressBar);
        rate = findViewById(R.id.rate);
    }


    public void setData(String titleString, Integer progress) {
        title.setText(titleString);
        progressBar.setProgress(progress);
        rate.setText(Integer.toString(progress));
    }

}
