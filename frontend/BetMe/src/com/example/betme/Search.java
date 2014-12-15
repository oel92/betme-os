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

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("NewApi")
public class Search extends ActionBarActivity{
	private String userID = new String();
	Context context;
	
	private ArrayList<String> names = new ArrayList<String>();
	private ArrayList<String> ids = new ArrayList<String>();
	private ArrayList<String> pics = new ArrayList<String>();
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        
        Intent myIntent = getIntent(); // gets the previously created intent
        userID = myIntent.getStringExtra("id");
        setContentView(R.layout.search_friend);
        
        
        
//        for(int i = 0; i <10; i++){
//        	LinearLayout layout = (LinearLayout) findViewById(R.id.scrollLayout);
//        	LayoutInflater inf = (LayoutInflater) context
//        			.getSystemService(context.LAYOUT_INFLATER_SERVICE);
//        	View convertView = inf.inflate(R.layout.search_list, null);
//        
//        	layout.addView(convertView);
//        }
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
    public void friendsFeed(View view){
    	Intent myIntent = new Intent(this,FriendsFeed.class);
    	
    	myIntent.putExtra("id", userID);
    	startActivity(myIntent);
    }
    public void search(View view){
    	View parent = (View) view.getParent();
    	TextView searchText = (TextView) parent.findViewById(R.id.editText1);
    	String text = searchText.getText().toString();
    	//Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    	getSearch(text, parent);			//uncomment to test with backendd
    }
    public void add(View view){
    	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

		StrictMode.setThreadPolicy(policy); 
       
//        Intent myIntent = getIntent(); // gets the previously created intent
//        String key = myIntent.getStringExtra("key");
        ViewGroup parent = (ViewGroup) view.getParent().getParent();
        int position = parent.indexOfChild((View) view.getParent());
        String friendID = ids.get(position);
        
        RequestParams params = new RequestParams();
        params.put("user_id", userID);
        params.put("friend_id", friendID);
        
        
       // final ArrayList<String> images = new ArrayList<String>();
        AsyncHttpClient client = new AsyncHttpClient();
        client.post("http://betme-os.appspot.com/searchfriend", params, new AsyncHttpResponseHandler() {

            @Override
			public void onSuccess(int statusCode, Header[] headers, byte[] response) {
            	System.out.print("test");
            }

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub
				
			}
        });
        Intent myFeed = new Intent(this, MyFeed.class);
        myFeed.putExtra("id", userID);
        startActivity(myFeed);
	}
    
    
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressLint("NewApi")
	public void getSearch(String text, View view){
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

		StrictMode.setThreadPolicy(policy); 
       
//        Intent myIntent = getIntent(); // gets the previously created intent
//        String key = myIntent.getStringExtra("key");
        
        RequestParams params = new RequestParams();
        params.put("id", userID);
        params.put("text", text);
        
        final View searchView = (View) view.getParent();
        
       // final ArrayList<String> images = new ArrayList<String>();
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://betme-os.appspot.com/searchfriend", params, new AsyncHttpResponseHandler() {

            @Override
			public void onSuccess(int statusCode, Header[] headers, byte[] response) {
            	String str;
				try {
					str = new String(response, "UTF-8");
					JSONObject dict = new JSONObject(str);
					JSONArray jsonNames = dict.getJSONArray("names");
					JSONArray jsonIDs = dict.getJSONArray("id_array");
					JSONArray jsonPics = dict.getJSONArray("profile_pics");
					
					for(int i = 0; i < jsonNames.length();i++){
						names.add(jsonNames.get(i).toString());
						ids.add(jsonIDs.get(i).toString());
						pics.add(jsonPics.get(i).toString());
						
						LinearLayout layout = (LinearLayout) searchView.findViewById(R.id.scrollLayout);
			            LayoutInflater inf = (LayoutInflater) context
			                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
			            View convertView = inf.inflate(R.layout.search_list, null);
			            
			            layout.addView(convertView);
			            
			            TextView searchName = (TextView) convertView.findViewById(R.id.search_name);
			            searchName.setText(jsonNames.get(i).toString());
			                
			            
			            ImageView profile_photo = (ImageView) convertView.findViewById(R.id.search_pic);
			            
			            
			            try {
			            	  
			            	  Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(jsonPics.getString(i)).getContent());
			            	  profile_photo.setImageBitmap(bitmap); 
			            	} catch (MalformedURLException e) {
			            	  e.printStackTrace();
			            	} catch (IOException e) {
			            	  e.printStackTrace();
			            	}
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
