from google.appengine.api import users
from google.appengine.ext import ndb
from google.appengine.api import images
from google.appengine.api import mail
from google.appengine.api import files, images

import time
import threading
import datetime
import webapp2
import cgi
import os
import urllib
import re
from random import uniform
from urlparse import urlparse, parse_qs

import json


DEFAULT_USER_NAME = 'default_user'
DEFAULT_BET_NAME = 'default_bet'


# We set a parent key  to ensure that they are all in the same
# entity group. Queries across the single entity group will be consistent.
# However, the write rate should be limited to ~1/second.

def bet_key(bet_name=DEFAULT_BET_NAME):
    """Constructs a Datastore key for a Bet entity with bet_name."""
    return ndb.Key('Bet', bet_name)
def user_key(user_name=DEFAULT_USER_NAME):
    """Constructs a Datastore key for a User entity with user_name."""
    return ndb.Key('User', user_name)	
	
	
class Bet(ndb.Model):
	"""Models an individual Bet entry"""
	user1 = ndb.StringProperty()
	user2 = ndb.StringProperty()
	team1 = ndb.StringProperty(default=True)
	team2 = ndb.StringProperty(default=True)
	terms = ndb.StringProperty(default=True)
<<<<<<< HEAD
	#league = ndb.StringProperty(default=True)
=======
	league = ndb.StringProperty(default=True)
>>>>>>> 5c12361e884215b7622b8d106ee0ce75779542c4
	date_made = ndb.DateTimeProperty(auto_now_add=True)
	finish_date = ndb.DateProperty()
	accepted = ndb.BooleanProperty()
	
class User(ndb.Model):
	"""Models a User entry"""
	name = ndb.StringProperty()
	id = ndb.StringProperty()
	prof_url = ndb.StringProperty(indexed=False)
	prof_pic_url = ndb.StringProperty(indexed=False)
	
	#friends are listed by unique ID's
	friends = ndb.StringProperty(repeated=True)
	
	#bets are listed by their keys. Whenever i'm passing them to mobile I do urlsafe so that they can be read like strings
	active_bets = ndb.KeyProperty(kind=Bet, repeated=True)
	invited_bets = ndb.KeyProperty(kind=Bet, repeated=True)
	completed_bets = ndb.KeyProperty(kind=Bet, repeated=True)

#####debug	

class ClearUsers(webapp2.RequestHandler):
	def get(self):
		ndb.delete_multi(User.query(ancestor = user_key(DEFAULT_USER_NAME)).fetch(keys_only=True))
		
class ClearBets(webapp2.RequestHandler):
	def get(self):
<<<<<<< HEAD
		ndb.delete_multi(Bet.query(ancestor = bet_key(DEFAULT_BET_NAME)).fetch(keys_only=True))		
=======
		ndb.delete_multi(Bet.query(ancestor = bet_key(DEFAULT_USER_NAME)).fetch(keys_only=True))		
>>>>>>> 5c12361e884215b7622b8d106ee0ce75779542c4
		
class Home(webapp2.RequestHandler):
	def get(self):
		user_query = User.query(ancestor = user_key(DEFAULT_USER_NAME))	
		all_users = user_query.fetch()
		
<<<<<<< HEAD
		bet_query = Bet.query(ancestor = bet_key(DEFAULT_BET_NAME))	
=======
		bet_query = Bet.query(ancestor = bet_key(DEFAULT_USER_NAME))	
>>>>>>> 5c12361e884215b7622b8d106ee0ce75779542c4
		all_bets = bet_query.fetch()
		
		#Display user fields
		user_ids = []
		user_names = []
		user_urls = []
		user_pics = []
		
		bet_ids = []
		bet_user1 = []
		bet_user2 = []
		bet_team1 = []
		bet_team2 = []
		bet_terms = []
		bet_finish = []
		bet_accept = []
		
		for user in all_users:
			user_ids.append(user.id)
			user_names.append(user.name)
			user_urls.append(user.prof_url)
			user_pics.append(user.prof_pic_url)
		
		for bet in all_bets:
			bet_ids.append(bet.key.urlsafe())
			bet_user1.append(bet.user1)
			bet_user2.append(bet.user2)
			bet_team1.append(bet.team1)
			bet_team2.append(bet.team2), 
			bet_terms.append(bet.terms)
			bet_finish.append( str(bet.finish_date.month) + "/" + str(bet.finish_date.day) + "/" + str(bet.finish_date.year) )
			bet_accept.append(bet.accepted)
			
		self.response.write(json.dumps({'user_ids': user_ids, 'user_names':user_names, 'user_urls': user_urls, 'user_pics': user_pics,'bet_ids': bet_ids, 'bet_user1': bet_user1, 'bet_user2': bet_user2,'bet_team1': bet_team1,'bet_team2': bet_team2,'bet_terms': bet_terms,'bet_finish': bet_finish,'bet_accept': bet_accept}))

		
		
	
class SignIn(webapp2.RequestHandler):
	def post(self):
		name = self.request.get("name")	
		id = self.request.get("id")		
		picture = self.request.get("picture")
		profile = self.request.get("profile")
		
		#Search for unique user id in DB
		user_query = User.query(ancestor = user_key(DEFAULT_USER_NAME))	
		user_query = user_query.filter(User.id == id)
		user_in_db = user_query.fetch(1)
		
		#if user id is not found in DB add them
		if not(user_in_db):		
			new_user = User(parent=user_key(DEFAULT_USER_NAME))
			new_user.name = name
			new_user.id = id
			new_user.prof_pic_url = picture
			new_user.prof_url = profile
			new_user.friends = []
			new_user.active_bets = []
			new_user.invited_bets = []
			new_user.completed_bets = []
			new_user.put()

class MyFeed(webapp2.RequestHandler):
	def get(self):
		id = self.request.get("id")

		#Search for unique user id in DB
		user_query = User.query(ancestor = user_key(DEFAULT_USER_NAME))	
		user_query = user_query.filter(User.id == id)
		user_in_db = user_query.fetch(1)
		user = user_in_db[0]
		
		#For output to mobile ap
		invited_strings = []
		active_strings = []
		completed_strings = []
		
		#get list of invited bet ID's (the keys in string form)
		for invited_key in user.invited_bets:
			invited_strings.append(invited_key.urlsafe())
			
		#get list of active bet ID's, check if finish date has passed. Add to completed bet list if it has and remove from active bets
		current_date= datetime.datetime.now().date()
		for active_key in user.active_bets[:]:	# <-- this syntax so that when elements are removed it does mess with iterations
			this_bet = active_key.get()
			if current_date > this_bet.finish_date:
				#move this bet to completed_bets and delete it from active_bets in this User's database object
				user.completed_bets.insert(0, active_key)
				user.active_bets.remove(active_key)
				user.put()
			else:
				active_strings.append(active_key.urlsafe())
		
		#get list completed bets and put in string form
		for completed_key in user.completed_bets:
			completed_strings.append(completed_key.urlsafe())
			
<<<<<<< HEAD
		self.response.write(json.dumps({'invited': invited_strings, 'active':active_strings, 'finished': completed_strings}))
=======
		self.response.write(json.dumps({'invited': invited_strings, 'active':active_strings, 'completed': completed_strings}))
>>>>>>> 5c12361e884215b7622b8d106ee0ce75779542c4

	
class GetBet(webapp2.RequestHandler):
	def get(self):
		bet_id = self.request.get("bet_id")	
<<<<<<< HEAD
		user_id = self.request.get("id")		
=======
		user_id = self.request.get("user_id")		
>>>>>>> 5c12361e884215b7622b8d106ee0ce75779542c4

		bet_key = ndb.Key(urlsafe=bet_id)
		this_bet = bet_key.get()
		
		#Determine which user is opponent
		if this_bet.user1 == user_id:
			opponent_id = this_bet.user2
		else:
			opponent_id = this_bet.user1
			
				
		#Search for opponent id in DB
		user_query = User.query(ancestor = user_key(DEFAULT_USER_NAME))	
		user_query = user_query.filter(User.id == opponent_id)
		results = user_query.fetch(1)
		opponent = results[0];
		
		#Get Traits
		opponent_name = opponent.name
		opponent_picture = opponent.prof_pic_url
		team1 = this_bet.team1
		team2 = this_bet.team2
<<<<<<< HEAD
		
		self.response.write(json.dumps({'opponent_name': opponent_name, 'opponent_picture':opponent_picture, 'team1': team1, 'team2':team2}))

class GetFriendsBet(webapp2.RequestHandler):
	def get(self):
		bet_id = self.request.get("bet_id")	
		bet_key = ndb.Key(urlsafe=bet_id)
		this_bet = bet_key.get()
		
=======
		
		self.response.write(json.dumps({'opponent_name': opponent_name, 'opponent_picture':opponent_picture, 'team1': team1, 'team2':team2}))

class GetFriendsBet(webapp2.RequestHandler):
	def get(self):
		bet_id = self.request.get("bet_id")	
		bet_key = ndb.Key(urlsafe=bet_id)
		this_bet = bet_key.get()
		
>>>>>>> 5c12361e884215b7622b8d106ee0ce75779542c4
		friend1_id = this_bet.user1
		friend2_id = this_bet.user2

		#Search for friends id in DB
		f1_query = User.query(ancestor = user_key(DEFAULT_USER_NAME))	
		f1_query = f1_query.filter(User.id == friend1_id)
		results = f1_query.fetch(1)
		friend1 = results[0];
<<<<<<< HEAD
		
		f2_query = User.query(ancestor = user_key(DEFAULT_USER_NAME))	
		f2_query = f2_query.filter(User.id == friend2_id)
		results = f2_query.fetch(1)
		friend2 = results[0];
		
		#Get Traits
		friend1_name = friend1.name
		friend2_name = friend2.name
		friend1_pic = friend1.prof_pic_url
		friend2_pic = friend2.prof_pic_url
		team1 = this_bet.team1
		team2 = this_bet.team2
		
=======
		
		f2_query = User.query(ancestor = user_key(DEFAULT_USER_NAME))	
		f2_query = f2_query.filter(User.id == friend2_id)
		results = f2_query.fetch(1)
		friend2 = results[0];
		
		#Get Traits
		friend1_name = friend1.name
		friend2_name = friend2.name
		friend1_pic = friend1.prof_pic_url
		friend2_pic = friend2.prof_pic_url
		team1 = this_bet.team1
		team2 = this_bet.team2
		
>>>>>>> 5c12361e884215b7622b8d106ee0ce75779542c4
		self.response.write(json.dumps({'friend1_name': friend1_name,'friend2_name': friend2_name, 'friend1_pic':friend1_pic, 'friend2_pic':friend2_pic, 'team1': team1, 'team2':team2}))
		

class FriendsFeed(webapp2.RequestHandler):
	def get(self):
		id = self.request.get("id")

		#Search for unique user id in DB
		user_query = User.query(ancestor = user_key(DEFAULT_USER_NAME))	
		user_query = user_query.filter(User.id == id)
		user_in_db = user_query.fetch(1)
		user = user_in_db[0]
		
		#For output to mobile app
		active_strings = []
		completed_strings = []
		
		for friend_id in user.friends:
			#Get friend's object
			f_query = User.query(ancestor = user_key(DEFAULT_USER_NAME))	
			f_query = f_query.filter(User.id == friend_id)
			results = f_query.fetch(1)
			friend = results[0];
			
			#get list of active bet ID's, not checking any date stuff because it will update when the friend views their MyFeed page
			for active_key in friend.active_bets:	
				active_strings.append(active_key.urlsafe())
			#get list completed bets and put in string form
			for completed_key in friend.completed_bets:
				completed_strings.append(completed_key.urlsafe())
				
		#removes duplicates
		active_strings = list(set(active_strings)) 
		completed_strings = list(set(completed_strings))
		self.response.write(json.dumps({'active': active_strings, 'finished': completed_strings}))
		

class SearchFriend(webapp2.RequestHandler):
	def get(self):
		id = self.request.get("id")
		text = self.request.get("text", "")
		
		user_query = User.query(ancestor = user_key(DEFAULT_USER_NAME))	
		all_users = user_query.fetch()
		
		#For output to mobile app
		matched_names = []
		matched_ids = []
		matched_pics = []

		for user_i in all_users:
			if text.lower() in user_i.name.lower():
				matched_names.append(user_i.name)
				matched_ids.append(user_i.id)
				matched_pics.append(user_i.prof_pic_url)
		
		self.response.write(json.dumps({'names': matched_names, 'id_array':matched_ids, 'profile_pics': matched_pics}))


	def post(self):
<<<<<<< HEAD
		user_id = self.request.get("user_id")		
=======
		id = self.request.get("user_id")		
>>>>>>> 5c12361e884215b7622b8d106ee0ce75779542c4
		friend_id = self.request.get("friend_id")
		
		#Search for unique user id in DB
		user_query = User.query(ancestor = user_key(DEFAULT_USER_NAME))	
<<<<<<< HEAD
		user_query = user_query.filter(User.id == user_id)
=======
		user_query = user_query.filter(User.id == id)
>>>>>>> 5c12361e884215b7622b8d106ee0ce75779542c4
		results = user_query.fetch(1)		
		user = results[0]
		
		if not(user_id == friend_id):				#make sure you don't add yourself as friend
			user.friends.append(friend_id)
			user.friends = list(set(user.friends))  #make sure no duplicate friends
			user.put()	#update database

		
class MakeBet(webapp2.RequestHandler):
	def get(self):
		id = self.request.get("id")
		
		#Search for unique user id in DB
		user_query = User.query(ancestor = user_key(DEFAULT_USER_NAME))	
		user_query = user_query.filter(User.id == id)
		results = user_query.fetch(1)		
		user = results[0]	
		
		#For output to mobile app
		friend_names = []
		friend_pics = []
		friend_ids = []
		
		for friend_id in user.friends:
			friend_ids.append(friend_id)
			
			#Search for friend user id in DB
			f_query = User.query(ancestor = user_key(DEFAULT_USER_NAME))	
			f_query = f_query.filter(User.id == friend_id)
			results = f_query.fetch(1)		
			friend = results[0]				
			
			friend_names.append(friend.name)
			friend_pics.append(friend.prof_pic_url)
			
		self.response.write(json.dumps({'friends': friend_names, 'friend_pics':friend_pics, 'friend_ids': friend_ids}))
			
	def post(self):
<<<<<<< HEAD
		id = self.request.get("user_id")
		friend_id = self.request.get("friend_id")
		team1 = self.request.get("team1")
		team2 = self.request.get("team2")
		#league = self.request.get("league")
=======
		id = self.request.get("id")
		friend_id = self.request.get("friend_id")
		team1 = self.request.get("team1")
		team2 = self.request.get("team2")
		league = self.request.get("league")
>>>>>>> 5c12361e884215b7622b8d106ee0ce75779542c4
		terms = self.request.get("terms")
		date = self.request.get("date")
		
		#create bet
		new_bet = Bet(parent=bet_key(DEFAULT_BET_NAME))
		new_bet.user1 = id
		new_bet.user2 = friend_id
		new_bet.team1 = team1
		new_bet.team2 = team2
<<<<<<< HEAD
		#new_bet.league = "league"
		new_bet.terms = terms 
		new_bet.accepted = False
		
		#date_temp = datetime.datetime.now().date()
		date_year = int(date[4:])
		date_month = int(date[:2])
		date_day = int(date[2:4])

		date_temp = datetime.date(date_year, date_month, date_day) 
		new_bet.finish_date = date_temp 
		
		bet_id = new_bet.put()		#simultaneously creates unique bet id by using the key
=======
		new_bet.league = league 
		new_bet.terms = terms 
		new_bet.accepted = False
		
		date_temp = datetime.datetime.now().date()
		date_temp.replace(year=int(date[4:]), month=int(date[:2]), day = int(date[2:4])) 
		new_bet.finish_date = date_temp 
		
		bet_key = new_bet.put()		#simultaneously creates unique bet id by using the key
>>>>>>> 5c12361e884215b7622b8d106ee0ce75779542c4

		#add the key for this bet to active_bets for user1 and invited_bets for user 2 
		#Search for id in DB
		user_query = User.query(ancestor = user_key(DEFAULT_USER_NAME))	
		user_query = user_query.filter(User.id == id)
		results = user_query.fetch(1)		
		owner = results[0]	
		
<<<<<<< HEAD
		owner.active_bets.insert(0, bet_id)
=======
		owner.active_bets.insert(0, bet_key)
>>>>>>> 5c12361e884215b7622b8d106ee0ce75779542c4
		owner.put()

		#Search for friend_id in DB
		user_query = User.query(ancestor = user_key(DEFAULT_USER_NAME))	
		user_query = user_query.filter(User.id == friend_id)
		results = user_query.fetch(1)		
		opponent = results[0]	
		
<<<<<<< HEAD
		opponent.invited_bets.insert(0, bet_id)
=======
		opponent.invited_bets.insert(0, bet_key)
>>>>>>> 5c12361e884215b7622b8d106ee0ce75779542c4
		opponent.put()
		
class ViewBet(webapp2.RequestHandler):
	def get(self):	
		bet_id = self.request.get("bet_id")
		
		bet_key = ndb.Key(urlsafe=bet_id)
		this_bet = bet_key.get()
		
		user1_id = this_bet.user1 
		user2_id = this_bet.user2 
		
		#Search for users in the database
		user_query = User.query(ancestor = user_key(DEFAULT_USER_NAME))	
		user_query = user_query.filter(User.id == user1_id)
		results = user_query.fetch(1)		
		user1 = results[0]	

		user_query = User.query(ancestor = user_key(DEFAULT_USER_NAME))	
		user_query = user_query.filter(User.id == user2_id)
		results = user_query.fetch(1)		
		user2 = results[0]			
		
		date_string = str(this_bet.finish_date.month) + "/" + str(this_bet.finish_date.day) + "/" + str(this_bet.finish_date.year)  
		self.response.write(json.dumps({'person1': user1.name, 'person_pic1':user1.prof_pic_url, 'person_id1': user1.id, 'person2': user2.name, 'person_pic2': user2.prof_pic_url, 'person_id2': user2.id, 'team1': this_bet.team1, 'team2': this_bet.team2, 'date': date_string, 'terms': this_bet.terms, 'accepted': this_bet.accepted}))

	def post(self):	
		bet_id = self.request.get("bet_id")
		choice = self.request.get("choice")
	
		bet_key = ndb.Key(urlsafe=bet_id)
		this_bet = bet_key.get()	
		
		user2_id = this_bet.user2 
		#Search for user in the database
		user_query = User.query(ancestor = user_key(DEFAULT_USER_NAME))	
		user_query = user_query.filter(User.id == user2_id)
		results = user_query.fetch(1)		
		user2 = results[0]
		
		# If accepted, remove from user2's invited_bets list and add to active_bets
		if choice == "accept":
			user2.active_bets.insert(0, bet_key)
			user2.invited_bets.remove(bet_key)
			user2.put()
			
			this_bet.accepted = True
			this_bet.put()
			
		#If declined, remove from user1's active_bets and user2's invited_bets
		if choice == "decline":
			user1_id = this_bet.user1
			#Search for user in the database
			user_query = User.query(ancestor = user_key(DEFAULT_USER_NAME))	
			user_query = user_query.filter(User.id == user1_id)
			results = user_query.fetch(1)		
			user1 = results[0]
			
			user1.active_bets.remove(bet_key)
			user2.invited_bets.remove(bet_key)
			user1.put()
			user2.put()		
		
	
# class MobileView(webapp2.RequestHandler):
	# def get(self):
		# stream_query = Stream.query(ancestor=stream_key(DEFAULT_STREAM_NAME)).order(Stream.date_added)
		# streams = stream_query.fetch()
		
		
		# streamUrls = []
		# streamKeys = []
		# #If no cover_url, sets first (oldest) picture in stream to be cover
		# for stream in streams:
			# streamUrls.append(stream.cover)
			# streamKeys.append(stream.key.urlsafe())
		
		# self.response.write(json.dumps({'urls':streamUrls, 'keys':streamKeys}))

# class MobileSingle(webapp2.RequestHandler):
	# def get(self):
		# #urlString = self.request.get("stream_key")
		# #streamKey = ndb.Key(urlsafe=urlString)
		# #stream_obj = streamKey.get()
		
		# #if not post_upload:
		# #	stream_obj.viewcount = stream_obj.viewcount + 1
		# #	stream_obj.datetime_viewed.append(datetime.datetime.now())
		# #	stream_obj.put()
		
		# urlString = self.request.get("key")
		# streamKey = ndb.Key(urlsafe=urlString)

		# #keyDict = parse_qs(urlparse(url).query)
		# #streamKey = keyDict['key']
		# if(streamKey != ""):
			# images_query = Image.query(ancestor=streamKey).order(-Image.date)
			# images = images_query.fetch(10)
		
			# imageUrls = []
			# for image in images:
				# imageUrls.append(image.blob_url)
		
			# self.response.write(json.dumps(imageUrls))
			
# class MobileSearch(webapp2.RequestHandler):
	# def get(self):
		# search_text = self.request.get("text")
		# search_text.lower()
		# result_streams = []
		# result_urls = []
		# streamKeys = []
		# num = 0
	
		# if search_text != "":
			# #search_done = True
			# stream_query = Stream.query(ancestor=stream_key(DEFAULT_STREAM_NAME)).order(Stream.date_added)
			# streams = stream_query.fetch()
			
			# for stream in streams:
				# if search_text.lower() in stream.name.lower():
					# result_streams.append(stream)
					# continue
				# for tag in stream.tags:
					# if search_text.lower() in tag.lower():
						# result_streams.append(stream)
						# break
			# for stream in result_streams:
				# result_urls.append(stream.cover)
				# streamKeys.append(stream.key.urlsafe())
			
			# num = len(result_urls)
		# numArray = [str(num)]
		
		# self.response.write(json.dumps({'result': result_urls, 'streamKeys': streamKeys, 'size': numArray}))

# class MobileNear(webapp2.RequestHandler):
	# def get(self):
		# stream_query = Stream.query(ancestor=stream_key(DEFAULT_STREAM_NAME))
		# streams = stream_query.fetch()
		
		
		# streamUrls = []
		# streamKeys = []
		# #If no cover_url, sets first (oldest) picture in stream to be cover
		# for stream in streams:
			# streamUrls.append(stream.cover)
			# streamKeys.append(stream.key.urlsafe())
		
		# self.response.write(json.dumps({'urls':streamUrls, 'keys':streamKeys}))

# class MobileUpload(webapp2.RequestHandler):
	# def get(self):
		# key = self.request.get("key")
		

		# stream_query = Stream.query(ancestor=stream_key(DEFAULT_STREAM_NAME))
		# streams = stream_query.fetch()

		# name = ""
		# for stream in streams:
			# if stream.key.urlsafe() == key:
				# name = stream.name
		# self.response.write(json.dumps(name))
		

application = webapp2.WSGIApplication([
	('/', 		Home),
	('/clearbets', ClearBets),
	('/clearusers', ClearUsers),
    ('/signin', SignIn),
    ('/myfeed', MyFeed),
    ('/getbet', GetBet),
	('/getfriendsbet', GetFriendsBet),
	('/friendsfeed', FriendsFeed),
	('/searchfriend', SearchFriend),
    ('/makebet', MakeBet),
    ('/viewbet', ViewBet)				# <--- add comma here if you add pages
	# ('/mobileView', MobileView),
	# ('/mobileSingle', MobileSingle),
	# ('/mobileSearch', MobileSearch),
	# ('/mobileNear', MobileNear),
	# ('/mobileUpload', MobileUpload)
], debug=True)
