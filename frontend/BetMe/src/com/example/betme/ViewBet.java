package com.example.betme;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("NewApi")
public class ViewBet extends ActionBarActivity{
	
	private String userID = new String();
	private String betID = new String();
	private boolean accepted = false;
	private String opponentID = new String();
   
	private String person1 = new String();
	private String person2 = new String();
	private String personPic1 = new String();
	private String personPic2 = new String();
	private String team1 = new String();
	private String team2 = new String();
	private String date = new String();
	private String terms = new String();
	
	int nfl_images[] = { R.drawable.bears, R.drawable.bengals,  R.drawable.bills,  R.drawable.broncos,
	   		 R.drawable.browns,  R.drawable.bucs,  R.drawable.cardinals,  R.drawable.chargers,  R.drawable.chiefs,
	   		 R.drawable.colts,  R.drawable.cowboys, R.drawable.dolphins, R.drawable.eagles,  R.drawable.falcons,
	   		 R.drawable.forty9ers,  R.drawable.nygiants,  R.drawable.jaguars,  R.drawable.jets,  R.drawable.lions
	   		 ,  R.drawable.packers, R.drawable.panthers, R.drawable.patriots, R.drawable.raiders, R.drawable.rams,
	   		 R.drawable.ravens, R.drawable.redskins, R.drawable.saints, R.drawable.seahawks, R.drawable.steelers,
	   		 R.drawable.texans, R.drawable.titans, R.drawable.vikings};
	 String nfl_names[] = {"Bears","Bengals","Bills","Broncos","Browns","Bucaneers","Cardinals"
   			 ,"Chargers","Chiefs","Colts","Cowboys","Dolphins","Eagles","Falcons","49ers","Giants","Jaguars"
   			 ,"Jets","Lions","Packers","Panthers","Patriots","Raiders","Rams","Ravens","Redskins"
   			 ,"Saints","Seahawks","Steelers","Texans","Titans","Vikings"};
	
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressLint("NewApi")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy); 
        
        Intent myIntent = getIntent(); // gets the previously created intent
        betID = myIntent.getStringExtra("betID");
        userID = myIntent.getStringExtra("id");
        setContentView(R.layout.view_bet);
        
        getBetInfo();
        
//        if(accepted || !opponentID.equals(userID) ){
//        	Button acceptButton = (Button) findViewById(R.id.submit);
//        	Button declineButton = (Button) findViewById(R.id.cancel);
//        	acceptButton.setVisibility(View.GONE);
//        	declineButton.setVisibility(View.GONE);
//        }
//        //Set name and image of people
//        TextView challenger_name = (TextView) this.findViewById(R.id.challenger);
//        challenger_name.setText(person1);
//        TextView opponent_name = (TextView) this.findViewById(R.id.Opponent);
//        opponent_name.setText(person2);
//        
//        ImageView challenger_photo = (ImageView) this.findViewById(R.id.challenger_pic);
//        ImageView acceptor_photo = (ImageView) this.findViewById(R.id.opponent_pic);
//        try {
//        	  
//        	  Bitmap bitmap1 = BitmapFactory.decodeStream((InputStream)new URL(personPic1).getContent());
//        	  challenger_photo.setImageBitmap(bitmap1);
//        	  Bitmap bitmap2 = BitmapFactory.decodeStream((InputStream)new URL(personPic2).getContent());
//        	  acceptor_photo.setImageBitmap(bitmap2); 
//
//        	} catch (MalformedURLException e) {
//        	  e.printStackTrace();
//        	} catch (IOException e) {
//        	  e.printStackTrace();
//        	}
//        
//        //Set team names and images
//        TextView team1_name = (TextView) this.findViewById(R.id.team1Name);
//        team1_name.setText(team1);
//        TextView team2_name = (TextView) this.findViewById(R.id.team2Name);
//        team2_name.setText(team2);
//        
//        ImageView team1_photo = (ImageView) this.findViewById(R.id.team1);
//        ImageView team2_photo = (ImageView) this.findViewById(R.id.team2);
//        for(int j = 0; j < nfl_names.length; j++){
//        	if(nfl_names[j].equalsIgnoreCase(team1)){
//        		team1_photo.setImageResource(nfl_images[j]);
//        	}
//        	if(nfl_names[j].equalsIgnoreCase(team2)){
//        		team2_photo.setImageResource(nfl_images[j]);
//        	}
//        }
//        
//        TextView termText = (TextView) this.findViewById(R.id.terms);
//        termText.setText(terms);
//        TextView dateText = (TextView) this.findViewById(R.id.date);
//        dateText.setText(date);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void accept(View view){
    	Intent myIntent = new Intent(this,MyFeed.class);
    	
    	myIntent.putExtra("id", userID);

 	   StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

 		StrictMode.setThreadPolicy(policy); 
       
//        Intent myIntent = getIntent(); // gets the previously created intent
//        String key = myIntent.getStringExtra("key");
        
        RequestParams params = new RequestParams();
        params.put("bet_id", betID);
        params.put("choice", "accept");
        
       // final ArrayList<String> images = new ArrayList<String>();
        AsyncHttpClient client = new AsyncHttpClient();
        client.post("http://betme-os.appspot.com/viewbet", params, new AsyncHttpResponseHandler() {

            @Override
 			public void onSuccess(int statusCode, Header[] headers, byte[] response) {
            	
 				    				
            }

 			@Override
 			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
 					Throwable arg3) {
 				// TODO Auto-generated method stub
 				
 			}
        });
    
    	
    	
    	
    	startActivity(myIntent);
    }
    public void decline(View view){
    	Intent myIntent = new Intent(this,MyFeed.class);
    	
    	myIntent.putExtra("id", userID);

 	   StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

 		StrictMode.setThreadPolicy(policy); 
       
//        Intent myIntent = getIntent(); // gets the previously created intent
//        String key = myIntent.getStringExtra("key");
        
        RequestParams params = new RequestParams();
        params.put("bet_id", betID);
        params.put("choice", "decline");
        
       // final ArrayList<String> images = new ArrayList<String>();
        AsyncHttpClient client = new AsyncHttpClient();
        client.post("http://betme-os.appspot.com/viewbet", params, new AsyncHttpResponseHandler() {

            @Override
 			public void onSuccess(int statusCode, Header[] headers, byte[] response) {
            	
 				    				
            }

 			@Override
 			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
 					Throwable arg3) {
 				// TODO Auto-generated method stub
 				
 			}
        });
    
    	
    	
    	
    	startActivity(myIntent);
    }
    public void myFeed(View view){
    	Intent myIntent = new Intent(this,MyFeed.class);
    
    	myIntent.putExtra("id", userID);
    	startActivity(myIntent);
    }
    public void friendsFeed(View view){
    	Intent myIntent = new Intent(this,FriendsFeed.class);
    	
    	myIntent.putExtra("id", userID);
    	startActivity(myIntent);
    }
    
    
   public void getBetInfo(){
	   StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

		StrictMode.setThreadPolicy(policy); 
      
//       Intent myIntent = getIntent(); // gets the previously created intent
//       String key = myIntent.getStringExtra("key");
       
       RequestParams params = new RequestParams();
       params.put("bet_id", betID);
       
      // final ArrayList<String> images = new ArrayList<String>();
       AsyncHttpClient client = new AsyncHttpClient();
       client.get("http://betme-os.appspot.com/viewbet", params, new AsyncHttpResponseHandler() {

           @Override
			public void onSuccess(int statusCode, Header[] headers, byte[] response) {
           	String str;
				try {
					str = new String(response, "UTF-8");
					JSONObject dict = new JSONObject(str);
//					JSONObject jsonPerson1 = dict.getJSONObject("person1");
//					JSONObject jsonPerson2 = dict.getJSONObject("person2");
//					JSONObject jsonPicture1 = dict.getJSONObject("person_pic1");
//					JSONObject jsonPicture2 = dict.getJSONObject("person_pic2");
//					JSONObject jsonTeam1 = dict.getJSONObject("team1");
//					JSONObject jsonTeam2 = dict.getJSONObject("team2");
//					JSONObject jsonDate = dict.getJSONObject("date");
//					JSONObject jsonTerms = dict.getJSONObject("terms");
					//JSONObject jsonAccepted = dict.getJSONObject("accepted");

					person1 = dict.getString("person1");
					person2 = dict.getString("person2");
					personPic1 = dict.getString("person_pic1");
					personPic2 = dict.getString("person_pic2");
					team1 = dict.getString("team1");
					team2 = dict.getString("team2");
					date = dict.getString("date");
					terms = dict.getString("terms");
					accepted = dict.getBoolean("accepted");
					
					opponentID = dict.getString("person_id2");
					
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(accepted || !opponentID.equals(userID) ){
		        	Button acceptButton = (Button) findViewById(R.id.submit);
		        	Button declineButton = (Button) findViewById(R.id.cancel);
		        	acceptButton.setVisibility(View.GONE);
		        	declineButton.setVisibility(View.GONE);
		        }
		        //Set name and image of people
		        TextView challenger_name = (TextView)findViewById(R.id.challenger);
		        challenger_name.setText(person1);
		        TextView opponent_name = (TextView) findViewById(R.id.Opponent);
		        opponent_name.setText(person2);
		        
		        ImageView challenger_photo = (ImageView) findViewById(R.id.challenger_pic);
		        ImageView acceptor_photo = (ImageView) findViewById(R.id.opponent_pic);
		        try {
		        	  
		        	  Bitmap bitmap1 = BitmapFactory.decodeStream((InputStream)new URL(personPic1).getContent());
		        	  challenger_photo.setImageBitmap(bitmap1);
		        	  Bitmap bitmap2 = BitmapFactory.decodeStream((InputStream)new URL(personPic2).getContent());
		        	  acceptor_photo.setImageBitmap(bitmap2); 

		        	} catch (MalformedURLException e) {
		        	  e.printStackTrace();
		        	} catch (IOException e) {
		        	  e.printStackTrace();
		        	}
		        
		        //Set team names and images
		        TextView team1_name = (TextView) findViewById(R.id.team1Name);
		        team1_name.setText(team1);
		        TextView team2_name = (TextView) findViewById(R.id.team2Name);
		        team2_name.setText(team2);
		        
		        ImageView team1_photo = (ImageView) findViewById(R.id.team1);
		        ImageView team2_photo = (ImageView) findViewById(R.id.team2);
		        for(int j = 0; j < nfl_names.length; j++){
		        	if(nfl_names[j].equalsIgnoreCase(team1)){
		        		team1_photo.setImageResource(nfl_images[j]);
		        	}
		        	if(nfl_names[j].equalsIgnoreCase(team2)){
		        		team2_photo.setImageResource(nfl_images[j]);
		        	}
		        }
		        
		        TextView termText = (TextView)findViewById(R.id.terms);
		        termText.setText(terms);
		        TextView dateText = (TextView) findViewById(R.id.date);
		        dateText.setText(date);
				    				
           }

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub
				
			}
       });
   }

   
}
