package com.example.splashscreen;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.CheckedTextView;

public class CheckedTextViewC extends CheckedTextView {

    public CheckedTextViewC(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }
    public CheckedTextViewC(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }
    public CheckedTextViewC(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }
    public void setTypeface(Typeface tf, int style) {
        if(!this.isInEditMode()){
        Typeface normalTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-MediumItalic.ttf");
        Typeface boldTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-MediumItalic.ttf");

        if (style == Typeface.BOLD) {
            super.setTypeface(boldTypeface/*, -1*/);
        } else {
            super.setTypeface(normalTypeface/*, -1*/);
        }
        }

    }
}