package com.example.esempioapplication;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class GradientFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_gradient, container, false);

        // Get the widgets reference from XML layout
        final ImageView iv = root.findViewById(R.id.iv);
        Button btn = root.findViewById(R.id.btn);

        // Set a click listener for Button widget
        btn.setOnClickListener(v -> {
            // Initialize a new GradientDrawable
            GradientDrawable gd = new GradientDrawable();

            // Set the color array to draw gradient
            gd.setColors(new int[]{
                    Color.RED,
                    Color.GREEN,
                    Color.YELLOW,
                    Color.CYAN
            });

            // Set the GradientDrawable gradient type linear gradient
            gd.setGradientType(GradientDrawable.LINEAR_GRADIENT);

            // Set GradientDrawable shape is a rectangle
            gd.setShape(GradientDrawable.RECTANGLE);

            // Set 3 pixels width solid blue color border
            gd.setStroke(3, Color.BLUE);

            // Set GradientDrawable width and in pixels
            gd.setSize(450, 150); // Width 450 pixels and height 150 pixels

            // Set GradientDrawable as ImageView source image
            iv.setImageDrawable(gd);
        });
        return root;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
}
