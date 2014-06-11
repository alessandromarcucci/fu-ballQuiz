package com.example.splashscreen;



import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.SeriesSelection;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import com.example.splashscreen.DatabaseHelper.RankHistory;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class RankingStatsFragment extends Fragment {
	DatabaseHelper dbHelper = new DatabaseHelper();
	InputStream inputStream = null;
	RankHistory rankHistory = null;
	private GraphicalView mChart;
	private String[] mMonth = null;
	Typeface roboto_black = null;
	Typeface roboto_black_italic = null;
	Typeface roboto_medium_italic = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.e("Test", "hello");
		
		
		
		
		
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// Define the number of elements you want in the chart.
		/* get the rank history for the user **/
		rankHistory = dbHelper.getRankHistoryForUser(FragmentContainer.userLobby.playerID);
		int user_number = dbHelper.getUserNumber();
		Log.w("user number", Integer.toString(user_number));
		
		/** retrieve font from the asset **/
		roboto_black = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Black.ttf");
		roboto_black_italic = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-BlackItalic.ttf");
		roboto_medium_italic = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-MediumItalic.ttf");
		
		/** set font to text **/
		TextView ranking_title = (TextView) getActivity().findViewById(R.id.ranking_title);
		ranking_title.setTypeface(roboto_medium_italic);
		
		TextView currentRank = (TextView) getActivity().findViewById(R.id.statsRank);
		currentRank.setText("#" + rankHistory.rankAtMinus1);
		currentRank.setTypeface(roboto_medium_italic);
		
		TextView bestRanking = (TextView) getActivity().findViewById(R.id.bestRanking);
		bestRanking.setTypeface(roboto_black_italic);
		
		TextView rankBest = (TextView) getActivity().findViewById(R.id.rankBest);
		rankBest.setTypeface(roboto_black_italic);
		rankBest.setText("#" + Integer.toString(FragmentContainer.userLobby.rankBest));
		
		TextView ranking_in = (TextView) getActivity().findViewById(R.id.rankingin);
		ranking_in.setTypeface(roboto_medium_italic);
		
		TextView rankingDate = (TextView) getActivity().findViewById(R.id.rankingDate);
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		Date today = Calendar.getInstance().getTime();
		String reportDate = df.format(today);
		rankingDate.setText(reportDate);
		rankingDate.setTypeface(roboto_medium_italic);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/** calculate days for the x axis **/
		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		Calendar c = Calendar.getInstance();
		
		String currentDate = sdf.format(c.getTime());
		
		c.add(Calendar.DATE, -1);  // number of days to add
		String minus1 = sdf.format(c.getTime());  // dt is now the new date
		Log.w("minus1", minus1);
	    
		c.add(Calendar.DATE,  -1);
		String minus2 = sdf.format(c.getTime());
		Log.w("minus2", minus2);

		c.add(Calendar.DATE, -1);
		String minus3 = sdf.format(c.getTime());
		Log.w("minus3", minus3);

		
		c.add(Calendar.DATE, -1);
		String minus4 = sdf.format(c.getTime());
		Log.w("minus4", minus4);

		
		c.add(Calendar.DATE, -1);
		String minus5 = sdf.format(c.getTime());
		Log.w("minus5", minus5);

		
		c.add(Calendar.DATE, -1);
		String minus6 = sdf.format(c.getTime());
		Log.w("minus6", minus6);

		
		mMonth = new String[] {(minus6 + "."), (minus5 + "."), (minus4 + "."), (minus3 + "."), (minus2 + "."), (minus1 + "."), (currentDate + ".") };
		
		int z[]={0,1,2,3,4,5,6};
	    int x[]={(user_number - rankHistory.rankAtMinus7),(user_number - rankHistory.rankAtMinus6), (user_number - rankHistory.rankAtMinus5), (user_number - rankHistory.rankAtMinus4),(user_number - rankHistory.rankAtMinus3),(user_number - rankHistory.rankAtMinus2),(user_number - rankHistory.rankAtMinus1)};
	     

	      // Create XY Series for X Series.
	     XYSeries xSeries=new XYSeries("X Series");
	     

	     //  Adding data to the X Series.
	     for(int i=0;i<z.length;i++)
	     {
	      xSeries.add(z[i],x[i]);
	   
	     }

	        
	     // Create a Dataset to hold the XSeries.
	     XYMultipleSeriesDataset dataset=new XYMultipleSeriesDataset();
	     
	      // Add X series to the Dataset.   
	     dataset.addSeries(xSeries);
	     
	     
	      // Create XYSeriesRenderer to customize XSeries

	     XYSeriesRenderer Xrenderer=new XYSeriesRenderer();
	     
	     Xrenderer.setDisplayChartValues(true);
	     Xrenderer.setLineWidth(2);
	     Xrenderer.setFillPoints(true);
	     Xrenderer.setColor(Color.WHITE);
	        Xrenderer.setPointStyle(PointStyle.CIRCLE);
	        Xrenderer.setFillPoints(true);
	        Xrenderer.setLineWidth(5);
	        Xrenderer.setDisplayChartValues(false);
	        
	        Xrenderer.setFillBelowLine(true);
	        Xrenderer.setFillBelowLineColor(getActivity().getResources().getColor(R.color.GreenGame));
	        Xrenderer.setPointStrokeWidth(5);
	       
	     // Create XYMultipleSeriesRenderer to customize the whole chart

	    
	     
	     
	     
	     XYMultipleSeriesRenderer mRenderer=new XYMultipleSeriesRenderer();
	     
	     
	     mRenderer.setZoomButtonsVisible(false);
	     mRenderer.setXLabels(0);
	     mRenderer.setPanEnabled(false);
	   mRenderer.setAxesColor(Color.WHITE);
	     mRenderer.setShowGrid(false);
	     mRenderer.setExternalZoomEnabled(false);
	     mRenderer.setShowLegend(false);
	     mRenderer.setShowGridY(false);
	     mRenderer.setYLabels(0);
	     mRenderer.setClickEnabled(false);
	     mRenderer.setMarginsColor(Color.argb(0x00, 0xff, 0x00, 0x00));
	     for(int i=0;i<z.length;i++)
	     {
	      mRenderer.addXTextLabel(i, mMonth[i]);
	     }
	     
	       // Adding the XSeriesRenderer to the MultipleRenderer. 
	     mRenderer.addSeriesRenderer(Xrenderer);
	  
	     
	     LinearLayout chart_container=(LinearLayout) getActivity().findViewById(R.id.Chart_layout);

	   // Creating an intent to plot line chart using dataset and multipleRenderer
	     
	     mChart=(GraphicalView)ChartFactory.getLineChartView(getActivity().getBaseContext(), dataset, mRenderer);
	     
	     //  Adding click event to the Line Chart.

	     mChart.setOnClickListener(new View.OnClickListener() {
	   
	   @Override
	   public void onClick(View arg0) {
	    // TODO Auto-generated method stub
	    
	    SeriesSelection series_selection=mChart.getCurrentSeriesAndPoint();
	    
	    if(series_selection!=null)
	    {
	     int series_index=series_selection.getSeriesIndex();
	     
	     String select_series="X Series";
	     if(series_index==0)
	     {
	      select_series="X Series";
	     }else
	     {
	      select_series="Y Series";
	     }
	     
	     String month=mMonth[(int)series_selection.getXValue()];
	     
	     int amount=(int)series_selection.getValue();
	     
	     Toast.makeText(getActivity().getBaseContext(), select_series+"in" + month+":"+amount, Toast.LENGTH_LONG).show();
	    }
	   }
	  });
	     
	// Add the graphical view mChart object into the Linear layout .
	     chart_container.addView(mChart);
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.rankingstats_layout, container, false);
		
		return view;
	}
	
	

}
	
		
		



