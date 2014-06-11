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
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class RankingAdapter extends ArrayAdapter<User> {
	static User userLobby = null;	
	DatabaseHelper dbHelper = new DatabaseHelper();
    private final Context context;
    private final List<User> itemsArrayList;
    InputStream inputStream = null;
    Typeface roboto_black = null;
    Typeface roboto_black_italic = null;
    Typeface roboto_medium_italic = null;
	public RankingAdapter(Context context, List<User> rankingList) {
		super(context, R.layout.myfriends_item, rankingList);
		// TODO Auto-generated constructor stub
		this.context = context;
        
        this.itemsArrayList = rankingList;
        
        roboto_black = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Black.ttf");
		roboto_black_italic = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-BlackItalic.ttf");
		roboto_medium_italic = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-MediumItalic.ttf");
	}

	
    
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        
     // 2. Get rowView from inflater
        View rowView = inflater.inflate(R.layout.ranking_item, parent, false);
   
        
        
        
        
        
        /* set ranking number */
		TextView myrank = (TextView) rowView.findViewById(R.id.ranking_number);
		int rank = itemsArrayList.get(position).rank;
		myrank.setText(Integer.toString(rank));
		
		
        
     // 3. Get the two text view from the rowView
        TextView playerName = (TextView) rowView.findViewById(R.id.friend_name);
        TextView club = (TextView) rowView.findViewById(R.id.friend_club);
        ImageView imageuser = (ImageView) rowView.findViewById(R.id.friend_img);
        TextView level = (TextView) rowView.findViewById(R.id.friend_level);
        
        
        
        
     /** set name of the friend **/
        playerName.setText(itemsArrayList.get(position).playerName);
        playerName.setTypeface(roboto_black_italic);
        
        /* set user image */
        
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
			
			
			/* set fb connected icon */
			ImageView fb = (ImageView) rowView.findViewById(R.id.friend_fb);
			fb.setImageResource(R.drawable.facebook_round);
		}else{
			if(itemsArrayList.get(position).playerImage.contains("default")){
				
				bitmap = BitmapFactory.decodeResource(rowView.getResources(), R.drawable.player_image);
			}else{
			/**TODO add the path of the user image here and set the lobbyImage**/
			}
		}
        
        imageuser.setImageBitmap(bitmap);
        
        /*set level */
        long actualLevel = (long) (Math.pow(itemsArrayList.get(position).playerPoints, (1/1.7)));
		level.setText(Long.toString(actualLevel));
		level.setBackgroundResource(R.drawable.red_circle);
		level.setTypeface(roboto_medium_italic);
       
        
        /* set club */
		club.setText(itemsArrayList.get(position).club);
		club.setTypeface(roboto_medium_italic);
		
		/* set position */
		final TextView friendsPosition = (TextView) rowView.findViewById(R.id.friendPosition);
		friendsPosition.setText(Integer.toString(position));
		friendsPosition.setTypeface(roboto_black);
        
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
    
}

