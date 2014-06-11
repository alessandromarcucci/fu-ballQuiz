package com.example.splashscreen;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.ClipData.Item;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.splashscreen.DatabaseHelper;
import com.example.splashscreen.DatabaseHelper.User;
 
public class MyAdapter extends ArrayAdapter<Item> {
		static User userLobby = null;	
		DatabaseHelper dbHelper = new DatabaseHelper();
        private final Context context;
        private final ArrayList<Item> itemsArrayList;
        String premium = null;
        InputStream inputStream = null;
        Typeface roboto_black = null;
        Typeface roboto_medium_italic = null;
        Typeface roboto_black_italic = null;
        public MyAdapter(Context context, ArrayList<Item> itemsArrayList) {
        	
        	
            super(context, R.layout.list_item, itemsArrayList);
            
            this.context = context;
            
            this.itemsArrayList = itemsArrayList;
            
            roboto_black = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Black.ttf");
    		roboto_black_italic = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-BlackItalic.ttf");
    		roboto_medium_italic = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-MediumItalic.ttf");
        }
 
        @SuppressLint("NewApi")
		@Override
        public View getView(int position, View convertView, ViewGroup parent) {
 
            
        	// 1. Create inflater 
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            
            
 
            // 2. Get rowView from inflater
            View rowView = inflater.inflate(R.layout.myfriends_item, parent, false);
            
            // 3. Get the two text view from the rowView
            TextView playerName = (TextView) rowView.findViewById(R.id.friend_name);
            TextView club = (TextView) rowView.findViewById(R.id.friend_club);
            ImageView imageuser = (ImageView) rowView.findViewById(R.id.friend_img);
            TextView level = (TextView) rowView.findViewById(R.id.friend_level);
 
            // 4. Set the text for textView 
            String i = (String) itemsArrayList.get(position).getText();
            userLobby = dbHelper.getUser(Integer.parseInt(i));
            
            
            /** set name of the friend **/
            playerName.setText(userLobby.playerName);
            playerName.setTypeface(roboto_black_italic);
            
            club.setText(userLobby.club);
            club.setTypeface(roboto_medium_italic);
            
            level.setTypeface(roboto_medium_italic);
            
            /* set user image */
            
            Bitmap bitmap = null;
            if(!(userLobby.FbConnect.equals("0"))){
    			String url = String.format(userLobby.playerImage);
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
    			ImageView fb = (ImageView) rowView.findViewById(R.id.facebook_friend);
    			fb.setImageResource(R.drawable.facebook_round);
    		}else{
    			if(userLobby.playerImage.contains("default")){
    				
    				bitmap = BitmapFactory.decodeResource(rowView.getResources(), R.drawable.player_image);
    			}else{
    			/**TODO add the path of the user image here and set the lobbyImage**/
    			}
    		}
            //bitmap = getRoundedShape(bitmap);
            imageuser.setImageBitmap(bitmap);
            
            /*set level */
            long actualLevel = (long) (Math.pow(userLobby.playerPoints, (1/1.7)));
    		level.setText(Long.toString(actualLevel));
    		level.setBackgroundResource(R.drawable.red_circle);
           
            
            
    		
    		/* set position */
    		final TextView friendsPosition = (TextView) rowView.findViewById(R.id.friendPosition);
    		friendsPosition.setText(Integer.toString(position));
    		
    		/** set challenge button **/
    		Button challengeFriends = (Button) rowView.findViewById(R.id.challengeFriends);
    		challengeFriends.setOnClickListener(new View.OnClickListener() {
    			
    			@Override
    			public void onClick(View v) {
    				// TODO Auto-generated method stub
    				int position = Integer.parseInt(friendsPosition.getText().toString());
    				int id_send = FragmentContainer.userLobby.playerID;
    				int id_rec = userLobby.playerID;
    				String category = "";
    				if (!(dbHelper.alreadyAMatch(id_send, id_rec))){
    					dbHelper.setUsersToMatch(id_send, id_rec, category);
    					Toast.makeText(getContext(), "new game created VS. " + Integer.toString(id_rec), Toast.LENGTH_SHORT).show();
    				}else{
    					Toast.makeText(getContext(), "already a match opened with " + Integer.toString(id_rec), Toast.LENGTH_SHORT).show();
    				}
    				Intent i = new Intent(v.getContext(), FragmentContainer.class);
    				
    				
        			
        			i.putExtra("ID", FragmentContainer.userLobby.playerID);
        			v.getContext().startActivity(i);
    			}
    		});
    		
    		rowView.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					int position = Integer.parseInt(friendsPosition.getText().toString());
					Intent i = new Intent(v.getContext(), StatsFriends.class);
					i.putExtra("ID", userLobby.playerID);
					v.getContext().startActivity(i);
				}
			});
    		
            return rowView;
        }
        
        
}