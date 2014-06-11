package com.example.splashscreen;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.io.InputStream;

import com.example.splashscreen.DatabaseHelper.User;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.ClipData.Item;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapterSlider extends ArrayAdapter<User> {
	static User userLobby = null;	
	DatabaseHelper dbHelper = new DatabaseHelper();
    private final Context context;
    private final List<User> itemsArrayList;
    InputStream inputStream = null;
	public MyAdapterSlider(Context context, List<User> rankingList) {
		super(context, R.layout.myfriends_item, rankingList);
		// TODO Auto-generated constructor stub
		this.context = context;
        
        this.itemsArrayList = rankingList;
	}

	
    
    @SuppressLint("ResourceAsColor")
	public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        
     // 2. Get rowView from inflater
        View rowView = inflater.inflate(R.layout.ranking_slider_layout, parent, false);
   
        
        
        
        
        
        /* set ranking number */
		TextView myrank = (TextView) rowView.findViewById(R.id.ranking_number);
		int rank = itemsArrayList.get(position).rank;
		myrank.setText("#" + rank);
		
		/* set image */
		ImageView myrank_image = (ImageView) rowView.findViewById(R.id.imageuser);
		TextView myrank_name = (TextView) rowView.findViewById(R.id.namefriend);
		if(position == 0){
			rowView.setBackgroundResource(R.color.GreenResult);
			
			

			
			myrank_name.setText("you");
			myrank_name.setTextColor(getContext().getResources().getColor(R.color.White));
			myrank.setTextColor(getContext().getResources().getColor(R.color.White));
		}
		myrank_name.setText(itemsArrayList.get(position).playerName);
		
		
		
		Bitmap bitmap = null;
		if(!(itemsArrayList.get(position).FbConnect.equals("0"))){
			String url = String.format(itemsArrayList.get(position).playerImage);
			try {
				inputStream = new URL(url).openStream();
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			bitmap = BitmapFactory.decodeStream(inputStream);
			ImageView fb_connection = (ImageView) rowView.findViewById(R.id.facebook_friend);
    		fb_connection.setBackgroundResource(R.drawable.facebook_round);
		}else{
			if(itemsArrayList.get(position).playerImage.contains("default")){
				bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.player_image);	
		}else{
		/**TODO add the path of the user image here and set the lobbyImage**/
		}
		}
    	//bitmap = getRoundedShape(bitmap);
		myrank_image.setImageBitmap(bitmap);
		
		/** set level **/
		TextView level = (TextView) rowView.findViewById(R.id.friend_level);
        long actualLevel = (long) (Math.pow(itemsArrayList.get(position).playerPoints, (1/1.7)));
        level.setText(Long.toString(actualLevel));
        
        /* set position */
		final TextView friendsPosition = (TextView) rowView.findViewById(R.id.friendPosition_slider);
		friendsPosition.setText(Integer.toString(position));
        
        rowView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int position = Integer.parseInt(friendsPosition.getText().toString());
				Intent i = new Intent(v.getContext(), StatsFriends.class);
				i.putExtra("ID", itemsArrayList.get(position).playerID);
				v.getContext().startActivity(i);
			}
		});

		
		
		
		
		
		
		
		
        
        
        
        
        
        
     
        // 5. retrn rowView
        return rowView;
    	
		
    }
    public Bitmap getRoundedShape(Bitmap scaleBitmapImage) {
        int targetWidth = 125;
        int targetHeight = 125;

        
        Bitmap targetBitmap = Bitmap.createBitmap(targetWidth,
                targetHeight, Bitmap.Config.ARGB_8888);
        BitmapFactory.Options opts = new BitmapFactory.Options();
		// opts.inJustDecodeBounds = true; 
		opts.inSampleSize=2; 
		opts.inPurgeable = true; // Tell to garbage collector that whether it needs free memory, the Bitmap can be cleared
		opts.inTempStorage = new byte[32 * 1024];
        Canvas canvas = new Canvas(targetBitmap);
        Path path = new Path();
        path.addCircle(
                ((float) targetWidth - 1) / 2,
                ((float) targetHeight - 1) / 2,
                (Math.min(((float) targetWidth), ((float) targetHeight)) / 2),
                Path.Direction.CCW);

        canvas.clipPath(path);
        
        canvas.drawBitmap(
                scaleBitmapImage,
                new Rect(0, 0,  scaleBitmapImage.getWidth(),  scaleBitmapImage
                        .getHeight()), new Rect(0, 0, targetWidth,
                        targetHeight), null);
        return targetBitmap;
    }
}

