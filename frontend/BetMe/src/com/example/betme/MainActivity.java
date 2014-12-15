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

import com.example.betme.R;
import com.example.betme.R.id;
import com.example.betme.R.layout;
import com.example.betme.R.menu;

import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

	@SuppressLint("NewApi")
	public class MainActivity extends Activity implements OnClickListener,
	      GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

	  /* Request code used to invoke sign in user interactions. */
	  private static final int RC_SIGN_IN = 0;

	  /* Client used to interact with Google APIs. */
	  private GoogleApiClient mGoogleApiClient;

	  /* A flag indicating that a PendingIntent is in progress and prevents
	   * us from starting further intents.
	   */
	  private boolean mIntentInProgress;

	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_main);
	    mGoogleApiClient = new GoogleApiClient.Builder(this)
	        .addConnectionCallbacks(this)
	        .addOnConnectionFailedListener(this)
	        .addApi(Plus.API)
	        .addScope(Plus.SCOPE_PLUS_LOGIN)
	        .build();
	    
	    findViewById(R.id.sign_in_button).setOnClickListener(this);
	    ((SignInButton) findViewById(R.id.sign_in_button)).setSize(SignInButton.SIZE_WIDE);
	  }
	  
	  /* Track whether the sign-in button has been clicked so that we know to resolve
	   * all issues preventing sign-in without waiting.
	   */
	  private boolean mSignInClicked;

	  /* Store the connection result from onConnectionFailed callbacks so that we can
	   * resolve them when the user clicks sign-in.
	   */
	  private ConnectionResult mConnectionResult;

	  /* A helper method to resolve the current ConnectionResult error. */
	  private void resolveSignInError() {
	    if (mConnectionResult.hasResolution()) {
	      try {
	        mIntentInProgress = true;
	        startIntentSenderForResult(mConnectionResult.getResolution().getIntentSender(),
	            RC_SIGN_IN, null, 0, 0, 0);
	      } catch (SendIntentException e) {
	        // The intent was canceled before it was sent.  Return to the default
	        // state and attempt to connect to get an updated ConnectionResult.
	        mIntentInProgress = false;
	        mGoogleApiClient.connect();
	      }
	    }
	  }

	  public void onConnectionFailed(ConnectionResult result) {
	    if (!mIntentInProgress) {
	      // Store the ConnectionResult so that we can use it later when the user clicks
	      // 'sign-in'.
	      mConnectionResult = result;

	      if (mSignInClicked) {
	        // The user has already clicked 'sign-in' so we attempt to resolve all
	        // errors until the user is signed in, or they cancel.
	        resolveSignInError();
	      }
	    }
	  }
	  public void onClick(View view) {
		  if (view.getId() == R.id.sign_in_button
		    && !mGoogleApiClient.isConnecting()) {
		    mSignInClicked = true;
		    resolveSignInError();
		  }
		}
	  protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
		  if (requestCode == RC_SIGN_IN) {
		    if (responseCode != RESULT_OK) {
		      mSignInClicked = false;
		    }

		    mIntentInProgress = false;

		    if (!mGoogleApiClient.isConnecting()) {
		      mGoogleApiClient.connect();
		    }
		  }
		}
	  @Override
	  public void onConnected(Bundle connectionHint) {
		String personID = new String();
	    mSignInClicked = false;
	    Toast.makeText(this, "User is connected!", Toast.LENGTH_LONG).show();
	    if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
	        Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
	        String personName = currentPerson.getDisplayName();
	        String personPhoto = currentPerson.getImage().getUrl();
	        String photoLink = personPhoto.substring(0, personPhoto.length()-2);
	        photoLink += "200";
	        personID = currentPerson.getId();
	        String personGooglePlusProfile = currentPerson.getUrl();
	       postUser(personName, personID, photoLink, personGooglePlusProfile);
	        
	     }
	    
	    	Intent intent = new Intent(this, MyFeed.class);
	    	intent.putExtra("id", personID);
	    	startActivity(intent);
	    
	  }
	  protected void onStart() {
	    super.onStart();
	    mGoogleApiClient.connect();
	  }

	  protected void onStop() {
	    super.onStop();

	    if (mGoogleApiClient.isConnected()) {
	      mGoogleApiClient.disconnect();
	    }
	  }
	 

		
		
		public void onConnectionSuspended(int cause) {
			  mGoogleApiClient.connect();
			}
    
    	
    	public void createAccount(View view){
    		Uri uri = Uri.parse("https://accounts.google.com/SignUp");
    		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
    		startActivity(intent);
    	}
    	
    	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
		@SuppressLint("NewApi")
		public void postUser(String name, String id, String picture, String profile){
    		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

    		StrictMode.setThreadPolicy(policy); 
           
//            Intent myIntent = getIntent(); // gets the previously created intent
//            String key = myIntent.getStringExtra("key");
            
            RequestParams params = new RequestParams();
            params.put("name", name);
            params.put("id", id);
            params.put("picture", picture);
            params.put("profile", profile);
            
           // final ArrayList<String> images = new ArrayList<String>();
            AsyncHttpClient client = new AsyncHttpClient();
            client.post("http://betme-os.appspot.com/signin", params, new AsyncHttpResponseHandler() {

                @Override
    			public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                	System.out.println("success");
    				    				
                }

    			@Override
    			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
    					Throwable arg3) {
    				System.out.println("fail");
    				// TODO Auto-generated method stub
    				
    			}
            });
    	}
    	


}
