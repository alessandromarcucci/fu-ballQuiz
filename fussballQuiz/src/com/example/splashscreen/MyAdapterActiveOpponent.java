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


public class MyAdapterActiveOpponent extends ArrayAdapter<Item>{
	
	protected static final Context MyAdapterPending = null;
	DatabaseHelper dbHelper = new DatabaseHelper();
	private final Context context;
    private final ArrayList<Item> itemsArrayList;
    ArrayList<Match> arrayPending = ActiveOpponentFragment.arrayPending;
    ArrayList<Match> arrayMatch = ActiveOpponentFragment.arrayMatch;
    final ListView listView = ActiveOpponentFragment.listView;
    User myuser = FragmentContainer.userLobby;
    static User selectedUser = null;
    static int pos = 0;
    View rowView = null;
    InputStream inputStream = null;
    String[] categories = {"Bundesliga", "Euro/World Cups", "European Cups", "Foreign Leagues", "Miscellaneous", "National Teams"};
    Typeface roboto_black = null;
    Typeface roboto_medium_italic = null;
    Typeface roboto_black_italic = null;
	public MyAdapterActiveOpponent(Context context, ArrayList<Item> itemsArrayList)  {
		
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
    		//game is active
        	rowView = inflater.inflate(R.layout.activematch_layout, parent, false);
        	RelativeLayout subcategories_title = (RelativeLayout) rowView.findViewById(R.id.subcategories1_title);
        	subcategories_title.setVisibility(View.GONE);
        	
        	
        	
        	
        	
        	
        	/* set challenger image and fb icon */
        	ImageView challenger_img = (ImageView) rowView.findViewById(R.id.challenger_active);
        	Bitmap bitmap = null;
            if(!(ListAllFragment.challengerList.get(position).FbConnect.equals("0"))){
    			String url = String.format(ListAllFragment.challengerList.get(position).playerImage);
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
    			ImageView fbconnected = (ImageView) rowView.findViewById(R.id.challenger_active_fb);
    			fbconnected.setImageResource(R.drawable.facebook_round);
    		}else{
    			if(ListAllFragment.challengerList.get(position).playerImage.contains("default")){
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
            TextView active_level = (TextView) rowView.findViewById(R.id.challenger_active_level);
            long actualLevel = (long) (Math.pow(ListAllFragment.challengerList.get(position).playerPoints, (1/1.7)));
            active_level.setText(Long.toString(actualLevel));
            active_level.setTypeface(roboto_medium_italic);
            /*set club */
            TextView active_club = (TextView) rowView.findViewById(R.id.challenger_active_club);
            active_club.setText(ListAllFragment.challengerList.get(position).club);
            active_club.setTypeface(roboto_medium_italic);
            /* set name */
            TextView active_name = (TextView) rowView.findViewById(R.id.challenger_active_name);
            active_name.setText(ListAllFragment.challengerList.get(position).playerName);
        	active_name.setTypeface(roboto_black_italic);
        	selectedUser= ListAllFragment.challengerList.get(position);
        	
        	/* set category */
        	TextView category = (TextView) rowView.findViewById(R.id.challenger_receiver_category);
        	category.setText(arrayMatch.get(position).category);
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

        	final TextView pos_pending = (TextView) rowView.findViewById(R.id.position_pending3);
        	pos_pending.setText(Integer.toString(position));
        	
        	/* set rounds tile */
        	if(arrayMatch.get(position).IDsender_match == myuser.playerID){
        		/* round 1 */
        		ImageView round1 = (ImageView) rowView.findViewById(R.id.firstRound);
        		int round1_send = 0;
        		int round1_rec = 0;
        		if((arrayMatch.get(position).answer1_send != 0) && (arrayMatch.get(position).answer1_rec != 0)){
        			if(arrayMatch.get(position).answer1_send == 1) round1_send ++;
        			if(arrayMatch.get(position).answer2_send == 1) round1_send ++;
        			if(arrayMatch.get(position).answer3_send == 1) round1_send ++;

        			if(arrayMatch.get(position).answer1_rec == 1) round1_rec ++;
        			if(arrayMatch.get(position).answer2_rec == 1) round1_rec ++;
        			if(arrayMatch.get(position).answer3_rec == 1) round1_rec ++;
        	
        			if(round1_send > round1_rec) round1.setBackgroundResource(R.drawable.ico_won);
        			if(round1_send < round1_rec) round1.setBackgroundResource(R.drawable.ico_loose);
        			if(round1_send == round1_rec) round1.setBackgroundResource(R.drawable.ico_draw);
        		}
        		/* round 2 */
        		ImageView round2 = (ImageView) rowView.findViewById(R.id.secondRound);
        		int round2_send = 0;
        		int round2_rec = 0;
        		if((arrayMatch.get(position).answer4_send != 0) && (arrayMatch.get(position).answer4_rec != 0)){
        			if(arrayMatch.get(position).answer4_send == 1) round2_send ++;
        			if(arrayMatch.get(position).answer5_send == 1) round2_send ++;
        			if(arrayMatch.get(position).answer6_send == 1) round2_send ++;

        			if(arrayMatch.get(position).answer4_rec == 1) round2_rec ++;
        			if(arrayMatch.get(position).answer5_rec == 1) round2_rec ++;
        			if(arrayMatch.get(position).answer6_rec == 1) round2_rec ++;
        	
        			if(round2_send > round2_rec) round2.setBackgroundResource(R.drawable.ico_won);
        			if(round2_send < round2_rec) round2.setBackgroundResource(R.drawable.ico_loose);
        			if(round2_send == round2_rec) round2.setBackgroundResource(R.drawable.ico_draw);
        		}
        		/* round 3 */
        		
        		ImageView round3 = (ImageView) rowView.findViewById(R.id.thirdRound);
        		int round3_send = 0;
        		int round3_rec = 0;
        		if((arrayMatch.get(position).answer7_send != 0) && (arrayMatch.get(position).answer7_rec != 0)){
        			if(arrayMatch.get(position).answer7_send == 1) round3_send ++;
        			if(arrayMatch.get(position).answer8_send == 1) round3_send ++;
        			if(arrayMatch.get(position).answer9_send == 1) round3_send ++;

        			if(arrayMatch.get(position).answer7_rec == 1) round3_rec ++;
        			if(arrayMatch.get(position).answer8_rec == 1) round3_rec ++;
        			if(arrayMatch.get(position).answer9_rec == 1) round3_rec ++;
        	
        			if(round3_send > round3_rec) round3.setBackgroundResource(R.drawable.ico_won);
        			if(round3_send < round3_rec) round3.setBackgroundResource(R.drawable.ico_loose);
        			if(round3_send == round3_rec) round3.setBackgroundResource(R.drawable.ico_draw);
        		}
        		/* round 4 */
        		ImageView round4 = (ImageView) rowView.findViewById(R.id.fourthRound);
        		int round4_send = 0;
        		int round4_rec = 0;
        		if((arrayMatch.get(position).answer10_send != 0) && (arrayMatch.get(position).answer10_rec != 0)){
        			if(arrayMatch.get(position).answer10_send == 1) round4_send ++;
        			if(arrayMatch.get(position).answer11_send == 1) round4_send ++;
        			if(arrayMatch.get(position).answer12_send == 1) round4_send ++;

        			if(arrayMatch.get(position).answer10_rec == 1) round4_send ++;
        			if(arrayMatch.get(position).answer11_rec == 1) round4_send ++;
        			if(arrayMatch.get(position).answer12_rec == 1) round4_send ++;
        	
        			if(round4_send > round4_rec) round4.setBackgroundResource(R.drawable.ico_won);
        			if(round4_send < round4_rec) round4.setBackgroundResource(R.drawable.ico_loose);
        			if(round4_send == round4_rec) round4.setBackgroundResource(R.drawable.ico_draw);
        		}
        		/* round 5 */
        		ImageView round5 = (ImageView) rowView.findViewById(R.id.fifthRound);
        		int round5_send = 0;
        		int round5_rec = 0;
        		if((arrayMatch.get(position).answer13_send != 0) && (arrayMatch.get(position).answer13_rec != 0)){
        			if(arrayMatch.get(position).answer13_send == 1) round5_send ++;
        			if(arrayMatch.get(position).answer14_send == 1) round5_send ++;
        			if(arrayMatch.get(position).answer15_send == 1) round5_send ++;

        			if(arrayMatch.get(position).answer13_rec == 1) round5_send ++;
        			if(arrayMatch.get(position).answer14_rec == 1) round5_send ++;
        			if(arrayMatch.get(position).answer15_rec == 1) round5_send ++;
        	
        			if(round5_send > round5_rec) round5.setBackgroundResource(R.drawable.ico_won);
        			if(round5_send < round5_rec) round5.setBackgroundResource(R.drawable.ico_loose);
        			if(round5_send == round5_rec) round5.setBackgroundResource(R.drawable.ico_draw);
        		}
        	}else{
        		/* round 1 */
        		ImageView round1 = (ImageView) rowView.findViewById(R.id.firstRound);
        		int round1_send = 0;
        		int round1_rec = 0;
        		if((arrayMatch.get(position).answer1_send != 0) && (arrayMatch.get(position).answer1_rec != 0)){	
        			if(arrayMatch.get(position).answer1_send == 1) round1_send ++;
        			if(arrayMatch.get(position).answer2_send == 1) round1_send ++;
        			if(arrayMatch.get(position).answer3_send == 1) round1_send ++;

        			if(arrayMatch.get(position).answer1_rec == 1) round1_rec ++;
        			if(arrayMatch.get(position).answer2_rec == 1) round1_rec ++;
        			if(arrayMatch.get(position).answer3_rec == 1) round1_rec ++;
        	
        			if(round1_send < round1_rec) round1.setBackgroundResource(R.drawable.ico_won);
        			if(round1_send > round1_rec) round1.setBackgroundResource(R.drawable.ico_loose);
        			if(round1_send == round1_rec) round1.setBackgroundResource(R.drawable.ico_draw);
        		}
        		/* round 2 */
        		ImageView round2 = (ImageView) rowView.findViewById(R.id.secondRound);
        		int round2_send = 0;
        		int round2_rec = 0;
        		if((arrayMatch.get(position).answer4_send != 0) && (arrayMatch.get(position).answer4_rec != 0)){
        			if(arrayMatch.get(position).answer4_send == 1) round2_send ++;
        			if(arrayMatch.get(position).answer5_send == 1) round2_send ++;
        			if(arrayMatch.get(position).answer6_send == 1) round2_send ++;
        			
        			if(arrayMatch.get(position).answer4_rec == 1) round2_rec ++;
        			if(arrayMatch.get(position).answer5_rec == 1) round2_rec ++;
        			if(arrayMatch.get(position).answer6_rec == 1) round2_rec ++;
        	
        			if(round2_send < round2_rec) round2.setBackgroundResource(R.drawable.ico_won);
        			if(round2_send > round2_rec) round2.setBackgroundResource(R.drawable.ico_loose);
        			if(round2_send == round2_rec) round2.setBackgroundResource(R.drawable.ico_draw);
        		}
        		/* round 3 */
        		ImageView round3 = (ImageView) rowView.findViewById(R.id.thirdRound);
        		int round3_send = 0;
        		int round3_rec = 0;
        		if((arrayMatch.get(position).answer7_send != 0) && (arrayMatch.get(position).answer7_rec != 0)){
        			if(arrayMatch.get(position).answer7_send == 1) round3_send ++;
        			if(arrayMatch.get(position).answer8_send == 1) round3_send ++;
        			if(arrayMatch.get(position).answer9_send == 1) round3_send ++;

        			if(arrayMatch.get(position).answer7_rec == 1) round3_rec ++;
        			if(arrayMatch.get(position).answer8_rec == 1) round3_rec ++;
        			if(arrayMatch.get(position).answer9_rec == 1) round3_rec ++;
        	
        			if(round3_send < round3_rec) round3.setBackgroundResource(R.drawable.ico_won);
        			if(round3_send > round3_rec) round3.setBackgroundResource(R.drawable.ico_loose);
        			if(round3_send == round3_rec) round3.setBackgroundResource(R.drawable.ico_draw);
        		}
        		/* round 4 */
        		ImageView round4 = (ImageView) rowView.findViewById(R.id.fourthRound);
        		int round4_send = 0;
        		int round4_rec = 0;
        		if((arrayMatch.get(position).answer10_send != 0) && (arrayMatch.get(position).answer10_rec != 0)){
        			if(arrayMatch.get(position).answer10_send == 1) round4_send ++;
        			if(arrayMatch.get(position).answer11_send == 1) round4_send ++;
        			if(arrayMatch.get(position).answer12_send == 1) round4_send ++;

        			if(arrayMatch.get(position).answer10_rec == 1) round4_send ++;
        			if(arrayMatch.get(position).answer11_rec == 1) round4_send ++;
        			if(arrayMatch.get(position).answer12_rec == 1) round4_send ++;
        	
        			if(round4_send > round4_rec) round4.setBackgroundResource(R.drawable.ico_won);
        			if(round4_send < round4_rec) round4.setBackgroundResource(R.drawable.ico_loose);
        			if(round4_send == round4_rec) round4.setBackgroundResource(R.drawable.ico_draw);
        		}
        		/* round 5 */
        		ImageView round5 = (ImageView) rowView.findViewById(R.id.fifthRound);
        		int round5_send = 0;
        		int round5_rec = 0;
        		if((arrayMatch.get(position).answer13_send != 0) && (arrayMatch.get(position).answer13_rec != 0)){
        			if(arrayMatch.get(position).answer13_send == 1) round5_send ++;
        			if(arrayMatch.get(position).answer14_send == 1) round5_send ++;
        			if(arrayMatch.get(position).answer15_send == 1) round5_send ++;

        			if(arrayMatch.get(position).answer13_rec == 1) round5_send ++;
        			if(arrayMatch.get(position).answer14_rec == 1) round5_send ++;
        			if(arrayMatch.get(position).answer15_rec == 1) round5_send ++;
        	
        			if(round5_send < round5_rec) round5.setBackgroundResource(R.drawable.ico_won);
        			if(round5_send > round5_rec) round5.setBackgroundResource(R.drawable.ico_loose);
        			if(round5_send == round5_rec) round5.setBackgroundResource(R.drawable.ico_draw);
        		}
        	}
        	
        	/* set result */
        	TextView myresult = (TextView) rowView.findViewById(R.id.myresult);
        	
        	if(arrayMatch.get(position).IDsender_match == myuser.playerID){
        		myresult.setText(Integer.toString(arrayMatch.get(position).pointsSender) + " : " + Integer.toString(arrayMatch.get(position).pointsReceiver));
        		
        		
        	}else{
        		myresult.setText(Integer.toString(arrayMatch.get(position).pointsReceiver) + " : " + Integer.toString(arrayMatch.get(position).pointsSender));
        		
        	}
        	myresult.setTypeface(roboto_black);
        	
        	
        	rowView.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					pos = Integer.parseInt((String) pos_pending.getText());
					Intent i = new Intent(v.getContext(), Game.class);
        			Log.w("idmatch - 1" , Integer.toString(arrayMatch.get(pos).IDmatch));
        			i.putExtra("IDEXTRA", Integer.toString(arrayMatch.get(pos).IDmatch));
        			v.getContext().startActivity(i);
					
				}
			});
        
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
