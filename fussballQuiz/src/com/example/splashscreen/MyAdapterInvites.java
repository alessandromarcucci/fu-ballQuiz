package com.example.splashscreen;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.ClipData.Item;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.splashscreen.DatabaseHelper;
import com.example.splashscreen.DatabaseHelper.Match;
import com.example.splashscreen.DatabaseHelper.User;


public class MyAdapterInvites extends ArrayAdapter<Match>{
	
	protected static final Context MyAdapterPending = null;
	DatabaseHelper dbHelper = new DatabaseHelper();
	private final Context context;
    private final ArrayList<Match> itemsArrayList;
    ArrayList<Match> arrayPending = InvitesFragment.arrayPending;
    ArrayList<Match> arrayMatch = InvitesFragment.arrayMatch;
    final ListView listView = InvitesFragment.listView;
    User myuser = FragmentContainer.userLobby;
    static User selectedUser = null;
    static int pos = 0;
    View rowView = null;
    InputStream inputStream = null;
    String[] categories = {"Bundesliga", "Euro/World Cups", "European Cups", "Foreign Leagues", "Miscellaneous", "National Teams"};
    Typeface roboto_black = null;
    Typeface roboto_medium_italic = null;
    Typeface roboto_black_italic = null;
	public MyAdapterInvites(Context context, ArrayList<Match> itemsArrayList)  {
		
		super(context,R.layout.pendingmatch_layout, itemsArrayList);
		
		// TODO Auto-generated constructor stub
		this.context = context;
		this.itemsArrayList = itemsArrayList;
		
	}
	
	@SuppressLint("NewApi")
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
		
		
		
        	//Create inflater 
        	LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        	roboto_black = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Black.ttf");
    		roboto_black_italic = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-BlackItalic.ttf");
    		roboto_medium_italic = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-MediumItalic.ttf");
    		if((itemsArrayList.get(position).state == 1) && (itemsArrayList.get(position).IDsender_match == myuser.playerID)){
            	
            	rowView = inflater.inflate(R.layout.pendingsender, parent, false);
            	
            	RelativeLayout subcategories_title = (RelativeLayout) rowView.findViewById(R.id.subcategories1_title);
            	subcategories_title.setVisibility(View.GONE);
            	
            	
            	
            	
            	
            	
            	
            	Log.w("positions", Integer.toString(position));
            	
            	/* set challenger image and fb icon */
            	ImageView challenger_img = (ImageView) rowView.findViewById(R.id.challenger_sender);
            	Bitmap bitmap = null;
                if(!(InvitesFragment.challengerList.get(position).FbConnect.equals("0"))){
        			String url = String.format(InvitesFragment.challengerList.get(position).playerImage);
        			try {
        				inputStream = new URL(url).openStream();
        			} catch (MalformedURLException e1) {
        				// TODO Auto-generated catch block
        				e1.printStackTrace();
        			} catch (IOException e1) {
        				// TODO Auto-generated catch block
        				e1.printStackTrace();
        			}
        			BitmapFactory.Options opts = new BitmapFactory.Options();
        			// opts.inJustDecodeBounds = true; 
        			opts.inSampleSize=2;   
        			
        			bitmap = BitmapFactory.decodeStream(inputStream);
        			
        			
        			/* set fb connected icon */
        			ImageView fbconnected = (ImageView) rowView.findViewById(R.id.challenger_sender_fb);
        			fbconnected.setImageResource(R.drawable.facebook_round);
        		}else{
        			if(InvitesFragment.challengerList.get(position).playerImage.contains("default")){
        				BitmapFactory.Options opts = new BitmapFactory.Options();
            			// opts.inJustDecodeBounds = true; 
            			opts.inSampleSize=2; 
        				bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.player_image);
        			}else{
        			/**TODO add the path of the user image here and set the lobbyImage**/
        			}
        		}
                //bitmap = getRoundedShape(bitmap);
                challenger_img.setImageBitmap(bitmap);
                
                bitmap = null;
                
                /* set level */
                TextView sender_level = (TextView) rowView.findViewById(R.id.challenger_sender_level);
                long actualLevel = (long) (Math.pow(InvitesFragment.challengerList.get(position).playerPoints, (1/1.7)));
                sender_level.setText(Long.toString(actualLevel));
                sender_level.setTypeface(roboto_medium_italic);
                
                /*set club */
                TextView sender_club = (TextView) rowView.findViewById(R.id.challenger_sender_club);
                sender_club.setText(InvitesFragment.challengerList.get(position).club);
                sender_club.setTypeface(roboto_medium_italic);
                
                /* set name */
                TextView sender_name = (TextView) rowView.findViewById(R.id.challenger_sender_name);
                sender_name.setText(InvitesFragment.challengerList.get(position).playerName);
               	sender_name.setTypeface(roboto_black_italic);
                
               	/* set category */
            	TextView category = (TextView) rowView.findViewById(R.id.challenger_sender_category);
            	category.setText(itemsArrayList.get(position).category);
            	category.setTypeface(roboto_medium_italic);
            	
            	
            	/* set rounded progress */
                ProgressBar progress_user = (ProgressBar) rowView.findViewById(R.id.progress_user);
                progress_user.setProgress((int)actualLevel);
                
                Resources res = context.getResources();
                Rect bounds = progress_user.getProgressDrawable().getBounds();
                
                if(actualLevel > 0){
                	progress_user.setProgressDrawable(res.getDrawable(R.drawable.progress_bar_user_low_level));
                	progress_user.setSecondaryProgress(100);
                }
                
                if(actualLevel > 33) {
                	progress_user.setProgressDrawable(res.getDrawable(R.drawable.progress_bar_user_medium_level));
                	progress_user.setSecondaryProgress(100);
                	
                }
                if(actualLevel > 66){
                	progress_user.setProgressDrawable(res.getDrawable(R.drawable.progress_bar_user_high_level));
                	progress_user.setSecondaryProgress(100);
                }
               	
                final TextView pos_pending = (TextView) rowView.findViewById(R.id.position_pending1);
            	Button deleteMatch = (Button) rowView.findViewById(R.id.deny_button);
            	deleteMatch.setBackgroundResource(R.drawable.lobby_reject);
            	pos_pending.setText(Integer.toString(position));
            	
            	
            	deleteMatch.setOnClickListener(new View.OnClickListener() {
    				
    				@Override
    				public void onClick(View v) {
    					
    					pos = Integer.parseInt((String) pos_pending.getText());
    					dbHelper.deleteMatch(itemsArrayList.get(pos).IDmatch);
    					
    					
    					
    					
    					/*Intent i = new Intent(v.getContext(), Lobby.class);
    					i.putExtra(Login.EXTRA_MESSAGE, myuser.playerID);
    					v.getContext().startActivity(i);*/
    					
    				}
    			});
            }else if((itemsArrayList.get(position).state == 1) && (itemsArrayList.get(position).IDreceiver_match == myuser.playerID)){
            	rowView = inflater.inflate(R.layout.pendingreceiver, parent, false);
            	RelativeLayout subcategories_title = (RelativeLayout) rowView.findViewById(R.id.subcategories1_title);
            	subcategories_title.setVisibility(View.GONE);
            	
            	
            	
            	
            	
            	
            	/* set challenger image and fb icon */
            	ImageView challenger_img = (ImageView) rowView.findViewById(R.id.challenger_receiver);
            	Bitmap bitmap = null;
                if(!(InvitesFragment.challengerList.get(position).FbConnect.equals("0"))){
        			String url = String.format(InvitesFragment.challengerList.get(position).playerImage);
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
        			ImageView fbconnected = (ImageView) rowView.findViewById(R.id.challenger_receiver_fb);
        			fbconnected.setImageResource(R.drawable.facebook_round);
        		}else{
        			if(InvitesFragment.challengerList.get(position).playerImage.contains("default")){
        				
        				bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.player_image);
        			}else{
        			/**TODO add the path of the user image here and set the lobbyImage**/
        			}
        		}
                //bitmap = getRoundedShape(bitmap);
                challenger_img.setImageBitmap(bitmap);
               

                bitmap = null;
               
                /* set level */
                TextView receiver_level = (TextView) rowView.findViewById(R.id.challenger_receiver_level);
                long actualLevel = (long) (Math.pow(InvitesFragment.challengerList.get(position).playerPoints, (1/1.7)));
                receiver_level.setText(Long.toString(actualLevel));
                receiver_level.setTypeface(roboto_medium_italic);
                
                /*set club */
                TextView receiver_club = (TextView) rowView.findViewById(R.id.challenger_receiver_club);
                receiver_club.setText(InvitesFragment.challengerList.get(position).club);
                receiver_club.setTypeface(roboto_medium_italic);
                /* set name */
                TextView receiver_name = (TextView) rowView.findViewById(R.id.challenger_receiver_name);
                receiver_name.setText(InvitesFragment.challengerList.get(position).playerName);
                receiver_name.setTypeface(roboto_black_italic);
                
                /* set category */
            	TextView category = (TextView) rowView.findViewById(R.id.challenger_receiver_category);
            	category.setText(itemsArrayList.get(position).category);
            	category.setTypeface(roboto_medium_italic);
            	
            	
            	/* set rounded progress */
                ProgressBar progress_user = (ProgressBar) rowView.findViewById(R.id.progress_user);
                progress_user.setProgress((int)actualLevel);
                
                Resources res = context.getResources();
                Rect bounds = progress_user.getProgressDrawable().getBounds();
                
                if(actualLevel > 0){
                	progress_user.setProgressDrawable(res.getDrawable(R.drawable.progress_bar_user_low_level));
                	progress_user.setSecondaryProgress(100);
                }
                
                if(actualLevel > 33) {
                	progress_user.setProgressDrawable(res.getDrawable(R.drawable.progress_bar_user_medium_level));
                	progress_user.setSecondaryProgress(100);
                	
                }
                if(actualLevel > 66){
                	progress_user.setProgressDrawable(res.getDrawable(R.drawable.progress_bar_user_high_level));
                	progress_user.setSecondaryProgress(100);
                }
            	//TextView vsreceiver = (TextView) rowView.findViewById(R.id.vsreceiver);
            	//TextView datereceiver = (TextView) rowView.findViewById(R.id.datereceiver);
            	final TextView pos_pending = (TextView) rowView.findViewById(R.id.position_pending2);
            	pos_pending.setText(Integer.toString(position));
            	//vsreceiver.setText("VS player " + selectedUser.playerName);
            	//datereceiver.setText(itemsArrayList.get(position).creation);
            	Button accept = (Button) rowView.findViewById(R.id.accept_button);
            	accept.setBackgroundResource(R.drawable.lobby_accept);
            	Button dontaccept = (Button) rowView.findViewById(R.id.deny_button);
            	dontaccept.setBackgroundResource(R.drawable.lobby_reject);
            	
            	
            	
            	
            	
            	dontaccept.setOnClickListener(new View.OnClickListener() {
    				
    				@Override
    				public void onClick(View v) {
    					pos = Integer.parseInt((String) pos_pending.getText());
    					dbHelper.deleteMatch(itemsArrayList.get(pos).IDmatch);
    					Log.w("oyea", Integer.toString(itemsArrayList.get(pos).IDmatch));
    					Intent i = new Intent(v.getContext(), FragmentContainer.class);
    					i.putExtra(Login.EXTRA_MESSAGE, myuser.playerID);
    					v.getContext().startActivity(i);
    					
    				}
    			});
            	
            	accept.setOnClickListener(new View.OnClickListener() {
            		
            		@Override
            		public void onClick(View v){
            			pos = Integer.parseInt((String) pos_pending.getText());
            			dbHelper.startMatch(itemsArrayList.get(pos).IDmatch);
            			Log.w("o yea", "you accepted");
            			dbHelper.fillQuestions(itemsArrayList.get(pos).IDmatch);
            			
            			Intent i = new Intent(v.getContext(), Game.class);
            			Log.w("idmatch - 1" , Integer.toString(itemsArrayList.get(pos).IDmatch));
            			i.putExtra("IDEXTRA", Integer.toString(itemsArrayList.get(pos).IDmatch));
            			v.getContext().startActivity(i);
            		}
            	});
			
		}
        
        //return rowView
        return rowView;
    }
	
	static void shuffleArray(int[] ar)
	  {
	    Random rnd = new Random();
	    for (int i = ar.length - 1; i > 0; i--)
	    {
	      int index = rnd.nextInt(i + 1);
	      // Simple swap
	      int a = ar[index];
	      ar[index] = ar[i];
	      ar[i] = a;
	    }
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
                new Rect(0, 0, scaleBitmapImage.getWidth(), scaleBitmapImage
                        .getHeight()), new Rect(0, 0, targetWidth,
                        targetHeight), null);
        return targetBitmap;
    }
	
	
	
	



}
