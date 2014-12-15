package com.example.betme;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;


public class Category {

	public ArrayList<Category> children;
	public ArrayList<String> selection;
	
	
	public String name;
	
	public Category() {
		children = new ArrayList<Category>();
		selection = new ArrayList<String>();
	}
	
	public Category(String name) {
		this();
		this.name = name;
	}
	
	public String toString() {
		return this.name;
	}
	
	// generate some random amount of child objects (1..10)
	private void generateChildren(String name) {
		 String nfl_names[] = {"Bears","Bengals","Bills","Broncos","Browns","Bucaneers","Cardinals"
	   			 ,"Chargers","Chiefs","Colts","Cowboys","Dolphins","Eagles","Falcons","49ers","Giants","Jaguars"
	   			 ,"Jets","Lions","Packers","Panthers","Patriots","Raiders","Rams","Ravens","Redskins"
	   			 ,"Saints","Seahawks","Steelers","Texans","Titans","Vikings"};
		 String mlb_names[]={"Angels","Astros","Athletics", "Blue Jays","Braves","Brewers","Cardinals"
	   			 ,"Cubs","Diamondbacks", "Dodgers","Giants","Indians","Mariners", "Marlins","Mets",
	   			 "Nationals","Orioles","Padres","Phillies","Pirates","Rangers","Rays", "Red Sox",
	   			 "Reds","Rockies","Royals","Tigers","Twins","White Sox","Yankees"};
		 String nba_names[] = { "Blazers", "Bucks","Bulls","Cavaliers","Celtics","Clippers"
	   			 ,"Grizzlies","Hawks","Heat","Hornets","Jazz","Kings","Knicks","Lakers"
	   			 ,"Magic", "Mavericks", "Nets","Nuggets","Pacers","Pelicans","Pistons","Raptors","Rockets"
	   			 ,"76ers", "Spurs","Sun","Thunders","Timberwolves","Warriors","Wizards"};
//		for(int i=0; i < rand.nextInt(9)+1; i++) {
//			Category cat = new Category("Child "+i);
//			this.children.add(cat);
//		}
		if(name.equals("NFL")){
			for(String team : nfl_names){
				Category cat = new Category(team);
				this.children.add(cat);
			}
		}
		else if(name.equals("NBA")){
			for(String team : nba_names){
				Category cat = new Category(team);
				this.children.add(cat);
			}
		}
		if(name.equals("MLB")){
			for(String team : mlb_names){
				Category cat = new Category(team);
				this.children.add(cat);
			}
		}
		
	}
	
	public static ArrayList<Category> getCategories() {
		ArrayList<Category> categories = new ArrayList<Category>();
//		for(int i = 0; i < 10 ; i++) {
//			Category cat = new Category("Category "+i);
//			cat.generateChildren();
//			categories.add(cat);
//		}
		Category nfl = new Category("NFL");
		nfl.generateChildren(nfl.toString());
		categories.add(nfl);
		
		Category nba = new Category("NBA");
		nba.generateChildren(nba.toString());
		categories.add(nba);
		
		Category mlb = new Category("MLB");
		mlb.generateChildren(mlb.toString());
		categories.add(mlb);
		
		return categories;
	}
	
	public static Category get(String name)
	{
		ArrayList<Category> collection = Category.getCategories();
		for (Iterator<Category> iterator = collection.iterator(); iterator.hasNext();) {
			Category cat = (Category) iterator.next();
			if(cat.name.equals(name)) {
				return cat;
			}
			
		}
		return null;
	}
}