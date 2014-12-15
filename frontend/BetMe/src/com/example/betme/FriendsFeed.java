package com.example.betme;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.betme.R;
import com.example.betme.R.id;
import com.example.betme.R.layout;
import com.example.betme.R.menu;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;



import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("NewApi")
public class FriendsFeed extends ActionBarActivity {
	private String userID = new String();
	Context context;
	
	private ArrayList<String> activeBets = new ArrayList<String>();
	private ArrayList<String> finishedBets = new ArrayList<String>();
    
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressLint("NewApi")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy); 
        
        Intent myIntent = getIntent(); // gets the previously created intent
        userID = myIntent.getStringExtra("id");
        setContentView(R.layout.friends_feed);
        
        //Uncomment to test with backend
        getFriendsBets();
            
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
    public void myFeed(View view){
    	Intent myIntent = new Intent(this,MyFeed.class);
    
    	myIntent.putExtra("id", userID);
    	startActivity(myIntent);
    }
    public void addFriend(View view){
    	Intent myIntent = new Intent(this,Search.class);
    
    	myIntent.putExtra("id", userID);
    	startActivity(myIntent);
    }
    public void singleBet(View view){
    	Intent myIntent = new Intent(this, ViewBet.class);
    	myIntent.putExtra("id", userID);
    	String betID = "";
    	
    	ViewGroup parent = (ViewGroup) view.getParent();
    	if(parent.findViewById(R.id.activeTab) != null){
    		int betPosition = parent.indexOfChild(view)-1;
    		betID = activeBets.get(betPosition);
    	}
    	else{
    		int betPosition = parent.indexOfChild(view)-1;
    		betID = finishedBets.get(betPosition);
    	}
    	
    	myIntent.putExtra("betID", betID);
    	
    	startActivity(myIntent);
    }
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressLint("NewApi")
	public void getFriendsBets(){
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

		StrictMode.setThreadPolicy(policy); 
       

        RequestParams params = new RequestParams();
        params.put("id", userID);
       
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://betme-os.appspot.com/friendsfeed", params, new AsyncHttpResponseHandler() {

            @Override
			public void onSuccess(int statusCode, Header[] headers, byte[] response) {
            	String str;
				try {
					str = new String(response, "UTF-8");
					JSONObject dict = new JSONObject(str);
					
					JSONArray active = dict.getJSONArray("active");
					JSONArray finished = dict.getJSONArray("finished");
					
					for(int i = 0; i < active.length();i++){
						activeBets.add(active.get(i).toString());
					}
					for(int i = 0; i < finished.length();i++){
						finishedBets.add(finished.get(i).toString());
					}
				
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for(int i = 0; i < activeBets.size(); i++){
	                LinearLayout layout = (LinearLayout) findViewById(R.id.activeLayout);
	                LayoutInflater inf = (LayoutInflater) context
	                        .getSystemService(context.LAYOUT_INFLATER_SERVICE);
	                View convertView = inf.inflate(R.layout.friend_bet_preview, null);
	                
	                layout.addView(convertView);
	                getBet(convertView, activeBets.get(i));
	            }
	            for(int i = 0; i < finishedBets.size(); i++){
	                LinearLayout layout = (LinearLayout) findViewById(R.id.finishedLayout);
	                LayoutInflater inf = (LayoutInflater) context
	                        .getSystemService(context.LAYOUT_INFLATER_SERVICE);
	                View convertView = inf.inflate(R.layout.friend_bet_preview, null);
	                
	                layout.addView(convertView);
	                getBet(convertView, finishedBets.get(i));
	            }
	            
				    				
            }

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub
				
			}
        });
	}
    
    public void getBet(final View view, String betID){
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

		StrictMode.setThreadPolicy(policy); 
       
//        Intent myIntent = getIntent(); // gets the previously created intent
//        String key = myIntent.getStringExtra("key");
        
        RequestParams params = new RequestParams();
        
        params.put("bet_id", betID);
       // final ArrayList<String> images = new ArrayList<String>();
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://betme-os.appspot.com/getfriendsbet", params, new AsyncHttpResponseHandler() {

            @Override
			public void onSuccess(int statusCode, Header[] headers, byte[] response) {
            	String str;
				try {
					str = new String(response, "UTF-8");
					JSONObject dict = new JSONObject(str);
					String friend1 = dict.getString("friend1_name");
					String friend2 = dict.getString("friend2_name");
					String friendPicture1 = dict.getString("friend1_pic");
					String friendPicture2 = dict.getString("friend2_pic");
					String team1 = dict.getString("team1");
					String team2 = dict.getString("team2");
					
					
			            TextView friendName1 = (TextView) view.findViewById(R.id.friend_name1);
			            friendName1.setText(friend1);
			            TextView friendName2 = (TextView) view.findViewById(R.id.friend_name2);
			            friendName2.setText(friend2);
			            
			            TextView teamName1 = (TextView) view.findViewById(R.id.team1);
			            teamName1.setText(team1);
			            TextView teamName2 = (TextView) view.findViewById(R.id.team2);
			            teamName2.setText(team2);
			            
			            ImageView friendPhoto1 = (ImageView) view.findViewById(R.id.profile_pic);
			            ImageView friendPhoto2 = (ImageView) view.findViewById(R.id.profile_pic2);
			            
			            try {
			            	  
			            	  Bitmap bitmap1 = BitmapFactory.decodeStream((InputStream)new URL(friendPicture1).getContent());
			            	  friendPhoto1.setImageBitmap(bitmap1);
			            	  Bitmap bitmap2 = BitmapFactory.decodeStream((InputStream)new URL(friendPicture2).getContent());
			            	  friendPhoto2.setImageBitmap(bitmap2); 
			            	} catch (MalformedURLException e) {
			            	  e.printStackTrace();
			            	} catch (IOException e) {
			            	  e.printStackTrace();
			            	}
					
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				    				
            }

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub
				
			}
        });
	}
}
