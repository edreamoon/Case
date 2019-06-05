package com.ware.img;

import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.appcompat.widget.AppCompatRadioButton;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.edreamoon.Utils;
import com.ware.R;

public class MRadioButton extends AppCompatRadioButton {

    private Drawable background;

    public MRadioButton(Context context) {
        super(context);
    }

    public MRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                background = getBackground();
//                setBackgroundColor(0x1a000000);
                setBackgroundDrawable(Utils.getDrawable(R.drawable.run_day_bg));
                break;
            case MotionEvent.ACTION_UP:
                setBackgroundDrawable(Utils.getDrawable(R.drawable.run_lab_bg));
                break;
//
//                case MotionEvent.ACTION_DOWN:
//                break;case MotionEvent.ACTION_DOWN:
//                break;
        }
        return super.onTouchEvent(event);
    }
}
