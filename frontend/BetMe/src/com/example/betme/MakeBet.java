package com.example.betme;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

@SuppressLint("NewApi")
@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class MakeBet extends Activity {
	private String userID = new String();
	
    private ExpandListAdapter ExpAdapter1;
    private ArrayList<Group> ExpListItems1;
    private ExpandableListView ExpandList1;
    
    private ExpandListAdapter ExpAdapter2;
    private ArrayList<Group> ExpListItems2;
    private ExpandableListView ExpandList2;
    
    private ExpandListAdapter nba;
    private ArrayList<Group> nbaItems;
    private ExpandableListView nbaList;
    private ExpandListAdapter nfl;
    private ArrayList<Group> nflItems;
    private ExpandableListView nflList;
    private ExpandListAdapter mlb;
    private ArrayList<Group> mlbItems;
    private ExpandableListView mlbList;
    
    private ExpandListAdapter ExpAdapter3;
    private ArrayList<Group> ExpListItems3;
    private ExpandableListView ExpandList3;
    
    
    
    private ExpandListAdapter ExpAdapter4;
    private ArrayList<Group> ExpListItems4;
    private ExpandableListView ExpandList4;
    
    public static final String arrGroupelements[] = { "NBA","NFL","MLB" };
    
    private SettingsListAdapter adapter;
	private ExpandableListView categoriesList;
	private ArrayList<Category> categories;
	
	protected Context mContext;
	
	private ArrayList<String> friends = new ArrayList<String>();
	private ArrayList<String> friendPics = new ArrayList<String>();
	private ArrayList<String> friendIDs = new ArrayList<String>();
	private ArrayList<Boolean> friendSelected = new ArrayList<Boolean>();
	private ArrayList<String> teams = new ArrayList<String>();
	private String terms = new String();
	private String date = new String();
	
	public final ArrayList<ArrayList<Boolean>> mCheckedState = new ArrayList<ArrayList<Boolean>>();
    
	int nfl_images[] = { R.drawable.bears, R.drawable.bengals,  R.drawable.bills,  R.drawable.broncos,
   		 R.drawable.browns,  R.drawable.bucs,  R.drawable.cardinals,  R.drawable.chargers,  R.drawable.chiefs,
   		 R.drawable.colts,  R.drawable.cowboys, R.drawable.dolphins, R.drawable.eagles,  R.drawable.falcons,
   		 R.drawable.forty9ers,  R.drawable.nygiants,  R.drawable.jaguars,  R.drawable.jets,  R.drawable.lions
   		 ,  R.drawable.packers, R.drawable.panthers, R.drawable.patriots, R.drawable.raiders, R.drawable.rams,
   		 R.drawable.ravens, R.drawable.redskins, R.drawable.saints, R.drawable.seahawks, R.drawable.steelers,
   		 R.drawable.texans, R.drawable.titans, R.drawable.vikings};
//    /**
//     * strings for child elements
//     */
//    public static final String arrChildelements[][] = {{ "Blazers", "Bucks","Bulls","Cavaliers","Celtics","Clippers"
//			 ,"Grizzlies","Hawks","Heat","Hornets","Jazz","Kings","Knicks","Lakers"
//			 ,"Magic", "Mavericks", "Nets","Nuggets","Pacers","Pelicans","Pistons","Raptors","Rockets"
//			 ,"76ers", "Spurs","Sun","Thunders","Timberwolves","Warriors","Wizards"},{"Bears","Bengals","Bills","Broncos","Browns","Bucaneers","Cardinals"
//			 ,"Chargers","Chiefs","Colts","Cowboys","Dolphins","Eagles","Falcons","49ers","Giants","Jaguars"
//			 ,"Jets","Lions","Packers","Panthers","Patriots","Raiders","Rams","Ravens","Redskins"
//			 ,"Saints","Seahawks","Steelers","Texans","Titans","Vikings"},{"Angels","Astros","Athletics", "Blue Jays","Braves","Brewers","Cardinals"
//			 ,"Cubs","Diamondbacks", "Dodgers","Giants","Indians","Mariners", "Marlins","Mets",
//			 "Nationals","Orioles","Padres","Phillies","Pirates","Rangers","Rays", "Red Sox",
//			 "Reds","Rockies","Royals","Tigers","Twins","White Sox","Yankees"} };
   
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	Intent myIntent = getIntent(); // gets the previously created intent
        userID = myIntent.getStringExtra("id");
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.make_bet);
        
        getFriends();
        
        
        
      ExpandList2 = (ExpandableListView) findViewById(R.id.pick_league);
      ExpListItems2 = SetStandardGroups2();
      ExpAdapter2 = new ExpandListAdapter(MakeBet.this, ExpListItems2);
      ExpandList2.setAdapter(ExpAdapter2); 
        
//        nflList = (ExpandableListView) findViewById(R.id.nfl);
//        nflItems = SetNfl();
//        nfl = new ExpandListAdapter(MakeBet.this, nflItems);
//        nflList.setAdapter(nfl);
//        
//        nbaList = (ExpandableListView) findViewById(R.id.nba);
//        nbaItems = SetNba();
//        nba = new ExpandListAdapter(MakeBet.this, nbaItems);
//        nbaList.setAdapter(nba);
//        
//        mlbList = (ExpandableListView) findViewById(R.id.mlb);
//        mlbItems = SetMlb();
//        mlb = new ExpandListAdapter(MakeBet.this, mlbItems);
//        mlbList.setAdapter(mlb);
      mContext = this;
		categoriesList = (ExpandableListView)findViewById(R.id.league_teams);
		categories = Category.getCategories();
		adapter = new SettingsListAdapter(this, 
				categories, categoriesList);
      categoriesList.setAdapter(adapter);
      
      ArrayList<Boolean> nfl = new ArrayList<Boolean>();
      for(int k=0; k<32;k++){
    	  nfl.add(false);
      }
      mCheckedState.add(nfl);
      ArrayList<Boolean> nba = new ArrayList<Boolean>();
      for(int k=0; k<30;k++){
    	  nba.add(false);
      }
      mCheckedState.add(nba);
      ArrayList<Boolean> mlb = new ArrayList<Boolean>();
      for(int k=0; k<30;k++){
    	  mlb.add(false);
      }
      mCheckedState.add(mlb);
      
      categoriesList.setOnChildClickListener(new OnChildClickListener() {
			
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				
				Category category = categories.get(groupPosition);
				
				CheckedTextView checkbox = (CheckedTextView)v.findViewById(R.id.list_item_text_child);
				if(mCheckedState.get(groupPosition).get(childPosition)){
					mCheckedState.get(groupPosition).set(childPosition, false);
					
					// remove child category from parent's selection list
					category.selection.remove(checkbox.getText().toString());
					
					//remove from team list
					teams.remove(checkbox.getText().toString());
				}
				else{
					mCheckedState.get(groupPosition).set(childPosition, true);
					// add child category to parent's selection list
					category.selection.add(checkbox.getText().toString());
					
					//add to team list
					teams.add(checkbox.getText().toString());
					
					// sort list in alphabetical order
					Collections.sort(category.selection, new CustomComparator());
				}
				checkbox.toggle();
				
				
				// find parent view by tag
				View parentView = categoriesList.findViewWithTag(categories.get(groupPosition).name);
				if(parentView != null) {
					TextView sub = (TextView)parentView.findViewById(R.id.list_item_text_subscriptions);
					
					if(sub != null) {
//						Category category = categories.get(groupPosition);
//						if(mCheckedState.get(groupPosition).get(childPosition)) {
//							// add child category to parent's selection list
//							category.selection.add(checkbox.getText().toString());
//							
//							// sort list in alphabetical order
//							Collections.sort(category.selection, new CustomComparator());
//						}
//						else {
//							// remove child category from parent's selection list
//							category.selection.remove(checkbox.getText().toString());
//						}		
						
						// display selection list
						sub.setText(category.selection.toString());
					}
				}				
				return true;
			}
		});
        	
       
//        
//        expList = (ExpandableListView) this.findViewById(R.id.pick_league);
//        expList.setGroupIndicator(null);
//        expList.setAdapter(new SecondLevelAdapter(this,expList));
//      
//        
        ExpandList3 = (ExpandableListView) findViewById(R.id.pick_terms);
        ExpListItems3 = SetStandardGroups3();
        ExpAdapter3 = new ExpandListAdapter(MakeBet.this, ExpListItems3);
        ExpandList3.setAdapter(ExpAdapter3);
        
        ExpandList4 = (ExpandableListView) findViewById(R.id.pick_date);
        ExpListItems4 = SetStandardGroups4();
        ExpAdapter4 = new ExpandListAdapter(MakeBet.this, ExpListItems4);
        ExpandList4.setAdapter(ExpAdapter4);
        

    }
    
    private ArrayList<Group> SetNba() {
    	String option = "NBA";
    	
    	
    	String nba_names[] = { "Blazers", "Bucks","Bulls","Cavaliers","Celtics","Clippers"
   			 ,"Grizzlies","Hawks","Heat","Hornets","Jazz","Kings","Knicks","Lakers"
   			 ,"Magic", "Mavericks", "Nets","Nuggets","Pacers","Pelicans","Pistons","Raptors","Rockets"
   			 ,"76ers", "Spurs","Sun","Thunders","Timberwolves","Warriors","Wizards"};

        ArrayList<Group> list = new ArrayList<Group>();

        ArrayList<Object> ch_list;
    	
        Group gru = new Group();
        gru.setName(option);

        ch_list = new ArrayList<Object>();
        for (int j=0; j < 30; j++) {
            Child ch = new Child();
            ch.setName(nba_names[j]);
         //   ch.setImage(Images[j]);
            ch_list.add(ch);
        }
        gru.setItems(ch_list);
        list.add(gru);
        
        return list;
	}
    
    private ArrayList<Group> SetMlb() {
    	String option = "MLB";
    

        ArrayList<Group> list = new ArrayList<Group>();

        ArrayList<Object> ch_list;

        String mlb_names[]={"Angels","Astros","Athletics", "Blue Jays","Braves","Brewers","Cardinals"
   			 ,"Cubs","Diamondbacks", "Dodgers","Giants","Indians","Mariners", "Marlins","Mets",
   			 "Nationals","Orioles","Padres","Phillies","Pirates","Rangers","Rays", "Red Sox",
   			 "Reds","Rockies","Royals","Tigers","Twins","White Sox","Yankees"};

        
            Group gru = new Group();
            gru.setName(option);

            ch_list = new ArrayList<Object>();
         
            	for (int j=0; j < 30; j++) {
                    Child ch = new Child();
                    ch.setName(mlb_names[j]);
                 //   ch.setImage(Images[j]);
                    ch_list.add(ch);
                }
                gru.setItems(ch_list);
                list.add(gru);
           
            
        

        return list;
	}
    private ArrayList<Group> SetNfl() {
    	String option = "NFL";
    

        ArrayList<Group> list = new ArrayList<Group>();

        ArrayList<Object> ch_list;

        String nfl_names[] = {"Bears","Bengals","Bills","Broncos","Browns","Bucaneers","Cardinals"
   			 ,"Chargers","Chiefs","Colts","Cowboys","Dolphins","Eagles","Falcons","49ers","Giants","Jaguars"
   			 ,"Jets","Lions","Packers","Panthers","Patriots","Raiders","Rams","Ravens","Redskins"
   			 ,"Saints","Seahawks","Steelers","Texans","Titans","Vikings"};
        
        int nfl_images[] = { R.drawable.bears, R.drawable.bengals,  R.drawable.bills,  R.drawable.broncos,
        		 R.drawable.browns,  R.drawable.bucs,  R.drawable.cardinals,  R.drawable.chargers,  R.drawable.chiefs,
        		 R.drawable.colts,  R.drawable.cowboys, R.drawable.dolphins, R.drawable.eagles,  R.drawable.falcons,
        		 R.drawable.forty9ers,  R.drawable.nygiants,  R.drawable.jaguars,  R.drawable.jets,  R.drawable.lions
        		 ,  R.drawable.packers, R.drawable.panthers, R.drawable.patriots, R.drawable.raiders, R.drawable.rams,
        		 R.drawable.ravens, R.drawable.redskins, R.drawable.saints, R.drawable.seahawks, R.drawable.steelers,
        		 R.drawable.texans, R.drawable.titans, R.drawable.vikings};
        
            Group gru = new Group();
            gru.setName(option);

            ch_list = new ArrayList<Object>();
         
            	for (int j=0; j < 32; j++) {
                    Child ch = new Child();
                    ch.setName(nfl_names[j]);
                    ch.setImage(nfl_images[j]);
                    ch_list.add(ch);
                }
                gru.setItems(ch_list);
                list.add(gru);
           
            
        

        return list;
	}
    
    private ArrayList<Group> SetStandardGroups4() {
    	String option = "Enter a date";
    	
    	
        ArrayList<Group> list = new ArrayList<Group>();

        ArrayList<Object> ch_list;
    	
        Group gru = new Group();
        gru.setName(option);

        ch_list = new ArrayList<Object>();
//        for (int j=0; j < 30; j++) {
            Child ch = new Child();
            ch.setName("MM/DD/YYYY");
         //   ch.setImage(Images[j]);
            ch_list.add(ch);
//        }
        gru.setItems(ch_list);
        list.add(gru);
        
        return list;
	}

	private ArrayList<Group> SetStandardGroups3() {
    	String option = "Set your terms";
    

        ArrayList<Group> list = new ArrayList<Group>();

        ArrayList<Object> ch_list;

        
            Group gru = new Group();
            gru.setName(option);

            ch_list = new ArrayList<Object>();
         
            //	for (int j=0; j < 30; j++) {
                    Child ch = new Child();
                    ch.setName("Set your terms");
                 //   ch.setImage(Images[j]);
                    ch_list.add(ch);
              //  }
                gru.setItems(ch_list);
                list.add(gru);
           
            
        

        return list;
	}

	private ArrayList<Group> SetStandardGroups2() {
    	String option = "Pick a league";
    	

        ArrayList<Group> list = new ArrayList<Group>();

        ArrayList<Object> ch_list;

    	 Group gru = new Group();
         gru.setName(option);

         ch_list = new ArrayList<Object>();
         	
	
         gru.setItems(ch_list);
         list.add(gru);
//    	 }
     	

         return list;
	}

	public ArrayList<Group> SetStandardGroups1() {

    	String option="Choose a friend";
    	
    	
        ArrayList<Group> list = new ArrayList<Group>();

        ArrayList<Object> ch_list;

        Group gru = new Group();
        gru.setName(option);

            ch_list = new ArrayList<Object>();
            
           
            	for (int j=0; j < friends.size(); j++) {
                    Child ch = new Child();
                    ch.setName(friends.get(j));
                   // ch.setImage(friendPics.get(j));
                    ch_list.add(ch);
                }
                gru.setItems(ch_list);
                list.add(gru);

                
          
            
        

        return list;
    }
	
    public void cancel(View view){
    	Intent myIntent = new Intent(this, MyFeed.class);
    	startActivity(myIntent);
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressLint("NewApi")
	public void getFriends(){
    	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

		StrictMode.setThreadPolicy(policy); 
       
//        Intent myIntent = getIntent(); // gets the previously created intent
//        String key = myIntent.getStringExtra("key");
        
        RequestParams params = new RequestParams();
        params.put("id", userID);
        
       // final ArrayList<String> images = new ArrayList<String>();
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://betme-os.appspot.com/makebet", params, new AsyncHttpResponseHandler() {

            @Override
			public void onSuccess(int statusCode, Header[] headers, byte[] response) {
            	String str;
				try {
					str = new String(response, "UTF-8");
					JSONObject dict = new JSONObject(str);
					JSONArray jsonFriends = dict.getJSONArray("friends");
					JSONArray jsonFriendIds = dict.getJSONArray("friend_ids");
					JSONArray jsonFriendPics = dict.getJSONArray("friend_pics");
					for(int i = 0; i < jsonFriends.length();i++){
						friends.add(jsonFriends.get(i).toString());
						friendIDs.add(jsonFriendIds.get(i).toString());
						friendPics.add(jsonFriendPics.get(i).toString());
						friendSelected.add(false);
						String friendName = friends.get(i);
					}
					ExpandList1 = (ExpandableListView) findViewById(R.id.pick_friend);
			        ExpListItems1 = SetStandardGroups1();
			        ExpAdapter1 = new ExpandListAdapter(MakeBet.this, ExpListItems1);
			        ExpandList1.setAdapter(ExpAdapter1);
			        
					
			           
					
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
				System.out.print("fail");
			}
        });
	
    }
    public void addBet(View view){
    	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

		StrictMode.setThreadPolicy(policy); 
       
//        Intent myIntent = getIntent(); // gets the previously created intent
//        String key = myIntent.getStringExtra("key");
       int index = -1;
       for(int k = 0; k <friendSelected.size(); k++){
    	   if(friendSelected.get(k)){
    		   index = k;
    	   }
       }
        View makeBetLayout = (View) view.getParent().getParent();
        ViewGroup chooseTermsLayout = (ViewGroup) makeBetLayout.findViewById(R.id.pick_terms);
        TextView termView = (TextView) chooseTermsLayout.getChildAt(1).findViewById(R.id.editText1);
        terms = termView.getText().toString();
        
        ViewGroup chooseDateLayout = (ViewGroup) makeBetLayout.findViewById(R.id.pick_date);
        TextView dateView = (TextView) chooseDateLayout.getChildAt(1).findViewById(R.id.editText1);
        date = dateView.getText().toString();
        
        RequestParams params = new RequestParams();
        params.put("user_id", userID);
        params.put("friend_id", friendIDs.get(index));
        params.put("team1", teams.get(0));
        params.put("team2", teams.get(1));
        params.put("terms", terms);
        params.put("date", date);
        
        
        
       // final ArrayList<String> images = new ArrayList<String>();
        AsyncHttpClient client = new AsyncHttpClient();
        client.post("https://betme-os.appspot.com/makebet", params, new AsyncHttpResponseHandler() {

            @Override
			public void onSuccess(int statusCode, Header[] headers, byte[] response) {
              		System.out.println("test");	
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
    public void setCheck(View view){
    	CheckBox check = (CheckBox) view.findViewById(R.id.checkbox);
    	View parent = (View) view.getParent();
		ViewGroup grandParent = (ViewGroup) parent.getParent();
		int addPosition = grandParent.indexOfChild(parent)-1;
		
    	if (check.isChecked()){
    		friendSelected.set(addPosition, true);
    	}
    	else{
    		friendSelected.set(addPosition, false);
    	}
    }
    
    
    public class ExpandListAdapter extends BaseExpandableListAdapter {

        private Context context;
        private ArrayList<Group> groups;

        public ExpandListAdapter(Context context, ArrayList<Group> groups) {
            this.context = context;
            this.groups = groups;
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            ArrayList<Object> chList = groups.get(groupPosition).getItems();
            return chList.get(childPosition);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition,
                boolean isLastChild, View convertView, ViewGroup parent) {
//        	ViewParent test = parent.getParent();
//        	if(groupPosition == 1){
//        		convertView = getGroupView(groupPosition, false,convertView, parent);
//        	}
//        	else{
//        	Group group = (Group) getGroup(groupPosition);
//        	if(group.getName().equals("Pick a league")){
//        		convertView = getGroupView(groupPosition, false,convertView, parent);
//        		return convertView;
//        	}
        	//
        	Group group = (Group) getGroup(groupPosition);
        	String name=group.getName();
        	if(name.equals("Set your terms") || name.equals("Enter a date"))
        	{
        		Child child = (Child) getChild(groupPosition, childPosition);
                if (convertView == null) {
                    LayoutInflater infalInflater = (LayoutInflater) context
                            .getSystemService(context.LAYOUT_INFLATER_SERVICE);
                    convertView = infalInflater.inflate(R.layout.text_box, null);
                    
                }
                if(name.equals("Enter a date")){
                	TextView tv = (TextView) convertView.findViewById(R.id.editText1);
                	tv.setHint("MMDDYYYY");
                }
                return convertView;
        	}
//        	else if(name.equals("Pick a league") && this.getChildId(groupPosition, childPosition) ==0 ){
//        		ExpandableListView SecondLevelexplv = new ExpandableListView(MakeBet.this);
//        		ArrayList<Group> groups = SetStandardGroups();
//                
//                SecondLevelexplv.setAdapter(new SecondLevelAdapter(MakeBet.this, groups));
//               // SecondLevelexplv.setGroupIndicator(null);
//               // convertView= SecondLevelexplv;
//        	}
        	else {
        		Child child = (Child) getChild(groupPosition, childPosition);
                if (convertView == null) {
                    LayoutInflater infalInflater = (LayoutInflater) context
                            .getSystemService(context.LAYOUT_INFLATER_SERVICE);
                    convertView = infalInflater.inflate(R.layout.child_item, null);
                }
                TextView tv = (TextView) convertView.findViewById(R.id.country_name);
                ImageView iv = (ImageView) convertView.findViewById(R.id.flag);

                tv.setText(child.getName().toString());
                //
	   	            
	            try {
	            	  
	            	  Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(friendPics.get(childPosition)).getContent());
	            	  iv.setImageBitmap(bitmap); 
	            	} catch (MalformedURLException e) {
	            	  e.printStackTrace();
	            	} catch (IOException e) {
	            	  e.printStackTrace();
	            	}
                
                
//        	}
        	}

            return convertView;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            ArrayList<Object> chList = groups.get(groupPosition).getItems();
            return chList.size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return groups.get(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return groups.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                View convertView, ViewGroup parent) {
            Group group = (Group) getGroup(groupPosition);
            if (convertView == null) {
                LayoutInflater inf = (LayoutInflater) context
                        .getSystemService(context.LAYOUT_INFLATER_SERVICE);
                convertView = inf.inflate(R.layout.group_item, null);
            }
            
            TextView tv = (TextView) convertView.findViewById(R.id.group_name);
            tv.setText(group.getName());
            return convertView;
        }
//        public View getLeagueGroupView(int groupPosition, boolean isExpanded,
//                View convertView, ViewGroup parent){
//        	 Group group = (Group) getGroup(groupPosition);
//             if (convertView == null) {
//                 LayoutInflater inf = (LayoutInflater) context
//                         .getSystemService(context.LAYOUT_INFLATER_SERVICE);
//                 convertView = inf.inflate(R.layout.team_group, null);
//             }
//             TextView tv = (TextView) convertView.findViewById(R.id.group_name);
//             tv.setText(group.getName());
//             return convertView;
//        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
        
      
    }
    public class CustomComparator implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            return o1.compareTo(o2);
        }
    }
    public class SettingsListAdapter extends BaseExpandableListAdapter {
    	 
    	 
        private LayoutInflater inflater;
        private ArrayList<Category> mParent;
        private ExpandableListView accordion;
        public int lastExpandedGroupPosition;    
        
     
        public SettingsListAdapter(Context context, ArrayList<Category> parent, ExpandableListView accordion) {
            mParent = parent;        
            inflater = LayoutInflater.from(context);
            this.accordion = accordion;       
            
    	}
     
     
        @Override
        //counts the number of group/parent items so the list knows how many times calls getGroupView() method
        public int getGroupCount() {
            return mParent.size();
        }
     
        @Override
        //counts the number of children items so the list knows how many times calls getChildView() method
        public int getChildrenCount(int i) {
            return mParent.get(i).children.size();
        }
     
        @Override
        //gets the title of each parent/group
        public Object getGroup(int i) {
            return mParent.get(i).name;
        }
     
        @Override
        //gets the name of each item
        public Object getChild(int i, int i1) {
            return mParent.get(i).children.get(i1);
        }
     
        @Override
        public long getGroupId(int i) {
            return i;
        }
     
        @Override
        public long getChildId(int i, int i1) {
            return i1;
        }
     
        @Override
        public boolean hasStableIds() {
            return true;
        }
     
        @Override
        //in this method you must set the text to see the parent/group on the list
        public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        	
            if (view == null) {
                view = inflater.inflate(R.layout.settings_list_item_parent, viewGroup,false);
            }
            // set category name as tag so view can be found view later
            view.setTag(getGroup(i).toString());
            
            TextView textView = (TextView) view.findViewById(R.id.list_item_text_view);
            
            //"i" is the position of the parent/group in the list
            textView.setText(getGroup(i).toString());
            
            TextView sub = (TextView) view.findViewById(R.id.list_item_text_subscriptions);        
            
            if(mParent.get(i).selection.size()>0) {
            	sub.setText(mParent.get(i).selection.toString());
            }
            else {
            	sub.setText("");
            }
            
            //return the entire view
            return view;
        }
        
     
        @Override
        //in this method you must set the text to see the children on the list
        public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = inflater.inflate(R.layout.settings_list_item_child, viewGroup,false);
            }
     
            
            CheckedTextView textView = (CheckedTextView) view.findViewById(R.id.list_item_text_child);
         
            if(i == 0 && mParent.get(i).children.size() == 32){
            	ImageView logo = (ImageView) view.findViewById(R.id.imageView1);
            	logo.setImageResource(nfl_images[i1]);
            }else{
            	ImageView logo = (ImageView) view.findViewById(R.id.imageView1);
            	logo.setImageResource(R.drawable.ic_launcher);
            }
            //"i" is the position of the parent/group in the list and 
            //"i1" is the position of the child
            String teamName = mParent.get(i).children.get(i1).name;
            textView.setText(teamName);        
     
            // set checked if parent category selection contains child category
            if(mCheckedState.get(i).get(i1)) {
        		textView.setChecked(true);
        		//mCheckedState.get(i).set(i1,true);
            }
            else {
            	textView.setChecked(false);
            	//mCheckedState.get(i).set(i1,false);
            }
            
            //return the entire view
            return view;
        }
     
        @Override
        public boolean isChildSelectable(int i, int i1) {
            return true;
        }
        
        @Override
        /**
         * automatically collapse last expanded group
         * @see http://stackoverflow.com/questions/4314777/programmatically-collapse-a-group-in-expandablelistview
         */    
        public void onGroupExpanded(int groupPosition) {
        	
        	if(groupPosition != lastExpandedGroupPosition){
                accordion.collapseGroup(lastExpandedGroupPosition);
            }
        	
            super.onGroupExpanded(groupPosition);
         
            lastExpandedGroupPosition = groupPosition;
            
        }
        
        
        
    }

    
    
}