package cstjean.mobile.tp1remise2jayciplamondon.travail;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Bouton extends View implements View.OnClickListener {

    public Bouton(Context context) {
        super(context);
        init();
    }

    public Bouton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        // Set the background color to green
        setBackgroundColor(Color.GREEN);
        setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        // Handle click event here
        // Add your specific click handling logic
    }
}
