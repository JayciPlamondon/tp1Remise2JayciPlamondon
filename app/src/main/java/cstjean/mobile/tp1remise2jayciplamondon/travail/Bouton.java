package cstjean.mobile.tp1remise2jayciplamondon.travail;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

public class Bouton extends FrameLayout implements View.OnClickListener {

    public Bouton(Context context) {
        super(context);
        init();
    }

    public Bouton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        // Set the background color to green
        setBackgroundColor(Color.GREEN);

        // Set click listener for the Bouton
        setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        // Handle click event for Bouton here
        // Add your specific click handling logic
    }
}

