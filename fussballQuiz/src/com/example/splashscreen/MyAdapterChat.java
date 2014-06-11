package com.example.splashscreen;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.ClipData.Item;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.splashscreen.DatabaseHelper;
import com.example.splashscreen.DatabaseHelper.User;
import com.example.splashscreen.DatabaseHelper.chatMessage;

 
public class MyAdapterChat extends ArrayAdapter<chatMessage> {
		static User user = null;	
		DatabaseHelper dbHelper = new DatabaseHelper();
        private final Context context;
        private final ArrayList<chatMessage> itemsArrayList;
        String premium = null;
        InputStream inputStream = null;
        public MyAdapterChat(Context context, ArrayList<chatMessage> itemsArrayList) {
        	
        	
            super(context, R.layout.list_item, itemsArrayList);
            
            this.context = context;
            
            this.itemsArrayList = itemsArrayList;
        }
 
        @SuppressLint("NewApi")
		@Override
        public View getView(int position, View convertView, ViewGroup parent) {
 
            
        	// 1. Create inflater 
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            
            
 
            // 2. Get rowView from inflater
            View rowView = inflater.inflate(R.layout.chat_item, parent, false);
            
            // 3. Get the two text view from the rowView
            TextView comment = (TextView) rowView.findViewById(R.id.comment);
            comment.setText(itemsArrayList.get(position).message);
            
            RelativeLayout commentLine = (RelativeLayout) rowView.findViewById(R.id.commentline);
            TextView comment_timestamp = (TextView) rowView.findViewById(R.id.comment_timestamp);
            comment_timestamp.setText(itemsArrayList.get(position).timestamp);
            LinearLayout wrapper = (LinearLayout) rowView.findViewById(R.id.wrapper);
            commentLine.setBackgroundResource(((itemsArrayList.get(position).senderId) == FragmentContainer.userLobby.playerID) ? R.drawable.bubble_yellow : R.drawable.bubble_green);
    		if(itemsArrayList.get(position).senderId != FragmentContainer.userLobby.playerID){
    			/** set player image and eventual fb connection icon for the challenger**/
            	ImageView challengerchat = (ImageView) rowView.findViewById(R.id.challengerchat);
            	Bitmap bitmap = null;
            	if(!(StatsFriends.user.FbConnect.equals("0"))){
        			String url = String.format(StatsFriends.user.playerImage);
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
        			ImageView fb_connection = (ImageView) rowView.findViewById(R.id.facebookchat);
            		fb_connection.setBackgroundResource(R.drawable.facebook_round);
        		}else{
        			if(StatsFriends.user.playerImage.contains("default")){
        				bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.player_image);	
        				
        			}else{
        			/**TODO add the path of the user image here and set the lobbyImage**/
        			}
        		}
            	//bitmap = getRoundedShape(bitmap);
    			challengerchat.setImageBitmap(bitmap);
    			
    			/** set the round icon **/
    			ImageView round = (ImageView) rowView.findViewById(R.id.round_active);
    			round.setBackgroundResource(R.drawable.avatar_circle);
    			
    			/** set the round icon for the level **/
    			RelativeLayout backlevel = (RelativeLayout) rowView.findViewById(R.id.levelBack);
    			backlevel.setBackgroundResource(R.drawable.red_circle);
    			
    			/** set the level icon **/
    			TextView levelchat_text = (TextView) rowView.findViewById(R.id.levelchat_text);
    			long actualLevel = (long) (Math.pow(StatsFriends.user.playerPoints, (1/1.7)));
    			levelchat_text.setText(Long.toString(actualLevel));
    		}
            wrapper.setGravity( ((itemsArrayList.get(position).senderId) == FragmentContainer.userLobby.playerID)? Gravity.LEFT : Gravity.RIGHT);
            
            
            // 5. return rowView
            return rowView;
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
      	  sourceBitmap.getHeight()), new Rect(0, 0, targetWidth, targetHeight), null);
      	
      	  
      	  final int width=targetBitmap.getWidth(),height=targetBitmap.getHeight();
      	  
     
      	  return targetBitmap;
      	 }
}