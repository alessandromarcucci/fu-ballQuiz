package com.example.splashscreen;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.InputStream;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
 
public class OptionFragment extends Fragment {
    
	InputStream inputStream = null;
	
    public OptionFragment(){}
     
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
  
        View rootView = inflater.inflate(R.layout.account_layout, container, false);
        return rootView;
        
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    	


    }
    
    public Bitmap getRoundedShape(Bitmap scaleBitmapImage) {
    	  // TODO Auto-generated method stub
    	  int targetWidth = 500;
    	  int targetHeight = 500;
    	  Bitmap targetBitmap = Bitmap.createBitmap(targetWidth, targetHeight,Bitmap.Config.ARGB_8888);
    	  
    	  Canvas canvas = new Canvas(targetBitmap);
    	  Path path = new Path();
    	  path.addCircle(((float) targetWidth - 1) / 2,
    	  ((float) targetHeight - 1) / 2, (Math.min(((float) targetWidth), ((float) targetHeight)) / 2),  Path.Direction.CCW);
    	  canvas.clipPath(path);
    	  Bitmap sourceBitmap = scaleBitmapImage;
    	  canvas.drawBitmap(sourceBitmap, new Rect(0, 0, sourceBitmap.getWidth(),
    	  sourceBitmap.getHeight()), new Rect(0, 0, targetWidth,
    	  targetHeight), null);
    	  return targetBitmap;
    	 }
}
