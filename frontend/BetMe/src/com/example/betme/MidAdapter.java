package com.example.betme;



import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.TextView;

class MidAdapter extends BaseExpandableListAdapter {

    private Context myContext;
    ExpandableListView _list1;
    ExpandableListView _list2;
    ExpandableListView _list3;

    public MidAdapter(Context context,ExpandableListView list1, ExpandableListView list2,ExpandableListView list3) {
        myContext = context;
        _list1=list1;
        _list2=list2;
        _list3=list3;
        
    }


    private Context context;
    private ArrayList<Group> groups;

    
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
//    	ViewParent test = parent.getParent();
//    	if(groupPosition == 1){
//    		convertView = getGroupView(groupPosition, false,convertView, parent);
//    	}
//    	else{
//    	Group group = (Group) getGroup(groupPosition);
//    	if(group.getName().equals("Pick a league")){
//    		convertView = getGroupView(groupPosition, false,convertView, parent);
//    		return convertView;
//    	}
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
            	tv.setHint("MM/DD/YYYY");
            }
            return convertView;
    	}
//    	else if(name.equals("Pick a league") && this.getChildId(groupPosition, childPosition) ==0 ){
//    		ExpandableListView SecondLevelexplv = new ExpandableListView(MakeBet.this);
//    		ArrayList<Group> groups = SetStandardGroups();
//            
//            SecondLevelexplv.setAdapter(new SecondLevelAdapter(MakeBet.this, groups));
//           // SecondLevelexplv.setGroupIndicator(null);
//           // convertView= SecondLevelexplv;
//    	}
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
            iv.setImageResource(child.getImage());
//    	}
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
//    public View getLeagueGroupView(int groupPosition, boolean isExpanded,
//            View convertView, ViewGroup parent){
//    	 Group group = (Group) getGroup(groupPosition);
//         if (convertView == null) {
//             LayoutInflater inf = (LayoutInflater) context
//                     .getSystemService(context.LAYOUT_INFLATER_SERVICE);
//             convertView = inf.inflate(R.layout.team_group, null);
//         }
//         TextView tv = (TextView) convertView.findViewById(R.id.group_name);
//         tv.setText(group.getName());
//         return convertView;
//    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
    
  
}
