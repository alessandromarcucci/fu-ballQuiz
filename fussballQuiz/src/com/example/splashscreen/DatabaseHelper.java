package com.example.splashscreen;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class DatabaseHelper {
	
	/**getIDbyNameAndPassword**/
	/**take the value of the ID from the table USER using username and password**/
	public int getIDbyNameAndPassword(String username, String password){
		// declare parameters that are passed to PHP script i.e. the name "birthyear" and its value submitted by user   
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
			
		// define the parameter
		postParameters.add(new BasicNameValuePair("function", "getIDbyNameAndPassword"));
		postParameters.add(new BasicNameValuePair("playerName", username));
		postParameters.add(new BasicNameValuePair("playerPassword", password));
		String response = null;
		int id = 0;
		try {
      	  
		     response = CustomHttpClient.executeHttpPost("http://dbfootball.altervista.org/jsonscript.php", postParameters);
		     // store the result returned by PHP script that runs MySQL query
		     String result = response.toString();
		     try{
		    	 JSONArray jArray = new JSONArray(result);
                 for(int i=0;i<jArray.length();i++){
                         JSONObject json_data = jArray.getJSONObject(i);
                         Log.i("log_tag","id: "+json_data.getInt("id"));
                         
                         //Get an output to the screen
                         id = json_data.getInt("id");
                 }
             }
             catch(JSONException e){
                      Log.e("getIDbyNameAndPassword", "Error parsing data "+e.toString());
             }
		}catch (Exception e) {
		     Log.e("getIDbyNameAndPassword","Error in http connection!!" + e.toString());     
	    }
		return id;
	}
	
	
	
	/**getIDbyName**/
	/**take the value of the ID from the table USER using username**/
	public int getIDbyName(String username){
		// declare parameters that are passed to PHP script 
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		
		// define the parameter
		postParameters.add(new BasicNameValuePair("function", "getIDbyName"));
		postParameters.add(new BasicNameValuePair("playerName", username));
		
		String response = null;
		int id = 0;
		try {
      	  
		     response = CustomHttpClient.executeHttpPost("http://dbfootball.altervista.org/jsonscript.php", postParameters);
		     // store the result returned by PHP script that runs MySQL query
		     String result = response.toString();
		     try{
		    	 JSONArray jArray = new JSONArray(result);
                 for(int i=0;i<jArray.length();i++){
                         JSONObject json_data = jArray.getJSONObject(i);
                         Log.i("log_tag","id: "+json_data.getInt("id"));
                         
                         //Get an output to the screen
                         id = json_data.getInt("id");
                 }
             }
             catch(JSONException e){
                      Log.e("getIDbyName", "Error parsing data "+e.toString());
             }
		}catch (Exception e) {
		     Log.e("getIDbyName","Error in http connection!!" + e.toString());     
	    }
		return id;
	}
	
	
	/**getIDbyEmail**/
	/**take the value of the ID from the table USER using the email address**/
	public int getIDbyEmail(String email){
		//declare parameters that are passed to PHP script
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		
		//define the parameter
		postParameters.add(new BasicNameValuePair("function", "getIDbyEmail"));
		postParameters.add(new BasicNameValuePair("email", email));
		
		String response = null;
		int id = 0;
		try {
	      	  
		     response = CustomHttpClient.executeHttpPost("http://dbfootball.altervista.org/jsonscript.php", postParameters);
		     // store the result returned by PHP script that runs MySQL query
		     String result = response.toString();
		     try{
		    	 JSONArray jArray = new JSONArray(result);
                for(int i=0;i<jArray.length();i++){
                        JSONObject json_data = jArray.getJSONObject(i);
                        Log.i("log_tag","id: "+json_data.getInt("id"));
                        
                        //Get an output to the screen
                        id = json_data.getInt("id");
                }
            }
            catch(JSONException e){
                     Log.e("getIDbyEmail", "Error parsing data "+e.toString());
            }
		}catch (Exception e) {
		     Log.e("getIDbyEmail","Error in http connection!!" + e.toString());     
	    }
		return id;
	}
	
	
	/**setIsLoggedIn**/
	/**set the value of th isLoggedIn value to 1**/
	public void setIsLoggedIn(int id){
		// declare parameters that are passed to PHP script 
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		// define the parameter
		postParameters.add(new BasicNameValuePair("function", "setIsLoggedIn"));
		postParameters.add(new BasicNameValuePair("id", Integer.toString(id)));
		String response = null;
		try{
			response = CustomHttpClient.executeHttpPost("http://dbfootball.altervista.org/jsonscript.php", postParameters);
		}catch(Exception e){
			Log.e("setIsLoggedIn", "Error in http connection!!" +  e.toString());
		}
	}
	
	/**setIsLoggedOut**/
	/**set the value of th isLoggedIn value to 0**/
	public void setIsLoggedOut(int id){
		// declare parameters that are passed to PHP script 
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		// define the parameter
		postParameters.add(new BasicNameValuePair("function", "setIsLoggedOut"));
		postParameters.add(new BasicNameValuePair("id", Integer.toString(id)));
		String response = null;
		try{
			response = CustomHttpClient.executeHttpPost("http://dbfootball.altervista.org/jsonscript.php", postParameters);
		}catch(Exception e){
			Log.e("setIsLoggedOut", "Error in http connection!!" +  e.toString());
		}
	}
	
	/**createNewUser**/
	/**create a new entry in the user table using username, password and email**/
	public void createNewUser(String username, String password, String email, String club){
		//declare parameters that are passed to PHP script
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		//define the parameter
		postParameters.add(new BasicNameValuePair("function", "createNewUser"));
		postParameters.add(new BasicNameValuePair("username", username));
		postParameters.add(new BasicNameValuePair("password", password));
		postParameters.add(new BasicNameValuePair("email", email));
		postParameters.add(new BasicNameValuePair("club", club));
		String response = null;
		try{
			response = CustomHttpClient.executeHttpPost("http://dbfootball.altervista.org/jsonscript.php", postParameters);
		}catch(Exception e){
			Log.e("createNewUser", "Error in http connection!!" +  e.toString());
		}
	}
	/**createNewFbUser**/
	/**create a new entry for facebook login**/
	public void createNewFbUser(String username, String facebookID){
		//declare parameters that are passed to PHP script
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		//define the parameter
		postParameters.add(new BasicNameValuePair("function", "createNewFbUser"));
		postParameters.add(new BasicNameValuePair("username", username));
		postParameters.add(new BasicNameValuePair("facebookID", facebookID));
		String response = null;
		try{
			response = CustomHttpClient.executeHttpPost("http://dbfootball.altervista.org/jsonscript.php", postParameters);
		}catch(Exception e){
			Log.e("createNewFbUser", "Error in http connection!!" +  e.toString());
		}
	}
	
	/**getUser*/
	/**get all the elements from the table user using ID*/
	
	public User getUser(int id){
		User array = new User();
		//declare parameters that are passed to PHP script
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		//define the parameter
		postParameters.add(new BasicNameValuePair("function", "getUser"));
		postParameters.add(new BasicNameValuePair("id", Integer.toString(id)));
		String response = null;
		try{
			response = CustomHttpClient.executeHttpPost("http://dbfootball.altervista.org/jsonscript.php", postParameters);
			String result = response.toString();
			try{
		    	 JSONArray jArray = new JSONArray(result);
		    	 for(int i=0;i<jArray.length();i++){
                       JSONObject json_data = jArray.getJSONObject(i);
                       Log.i("log_tag","id: "+json_data.getInt("id"));
                       
                       //Get an output to the screen
                      
                       array.playerID = id;
                       array.playerName = json_data.getString("playerName");
                       array.playerImage = json_data.getString("playerImage");
                       array.playerPoints = json_data.getInt("playerPoints");
                       array.playerEmail = json_data.getString("playerEmail");
                       array.playerPassword = json_data.getString("playerPassword");
                       array.isPremium = json_data.getInt("isPremium");
                       array.FbConnect = json_data.getString("FBConnect");
                       array.isLoggedIn = json_data.getInt("isLoggedIn");
                       array.draw = json_data.getInt("draw");
                       array.won = json_data.getInt("won");
                       array.lost = json_data.getInt("lost");
                       array.club = json_data.getString("club");
                       array.rankBest = json_data.getInt("bestRank");
                       
               }
           }
           catch(JSONException e){
                    Log.e("getUser", "Error parsing data "+e.toString());
           }
		}catch(Exception e){
			Log.e("getUser", "Error in http connection (getUser)!!" + e.toString());
		}
		
		
		return array;
	}
	
	/**searchPlayer**/
	/** get the IDs of the players whom username contains the String passed*/
	public int[] searchPlayer(String username){
		int[] array = new int[100];
		//declare parameters that are passed to PHP script
				ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
				//define the parameter
				postParameters.add(new BasicNameValuePair("function", "searchPlayer"));
				postParameters.add(new BasicNameValuePair("playerName", username));
				String response = null;
				try{
					response = CustomHttpClient.executeHttpPost("http://dbfootball.altervista.org/jsonscript.php", postParameters);
					String result = response.toString();
					try{
				    	 JSONArray jArray = new JSONArray(result);
				    	 for(int i=0;i<jArray.length();i++){
		                       JSONObject json_data = jArray.getJSONObject(i);
		                       Log.i("search_player","id: "+json_data.getInt("id"));
		                       
		                       //Get an output to the screen
		                       array[i] = json_data.getInt("id");
		                      
		               }
		           }
		           catch(JSONException e){
		                    Log.e("searchPlayer", "Error parsing data "+e.toString());
		           }
				}catch(Exception e){
					Log.e("search_player", "Error in http connection (searchPlayer)!!" + e.toString());
				}
				return array;
	}
	
	/**setUsernameAndPassword**/
	/** set the username and password of the id player**/
	public void setUsernameAndPassword(int id, String username, String password){
		//declare parameters that are passed to PHP script
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		//define the parameter
		postParameters.add(new BasicNameValuePair("function", "setUsernameAndPassword"));
		postParameters.add(new BasicNameValuePair("id", Integer.toString(id)));
		postParameters.add(new BasicNameValuePair("playerName", username));
		postParameters.add(new BasicNameValuePair("playerPassword", password));
		String response = null;
		try{
			response = CustomHttpClient.executeHttpPost("http://dbfootball.altervista.org/jsonscript.php", postParameters);
			String result = response.toString();
			try{
		    	 JSONArray jArray = new JSONArray(result);
		    	 for(int i=0;i<jArray.length();i++){
                       JSONObject json_data = jArray.getJSONObject(i);
                       Log.i("search_player","id: "+json_data.getInt("id"));
                 }
           }
           catch(JSONException e){
                    Log.e("setUsernameAndPassword", "Error parsing data "+e.toString());
           }
		}catch(Exception e){
			Log.e("setUsernameAndPassword", "Error in http connection (searchPlayer)!!" + e.toString());
		}
	}
	
	/**setUsername**/
	/** set the username of the id player**/
	public void setUsername(int id, String username){
		//declare parameters that are passed to PHP script
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		//define the parameter
		postParameters.add(new BasicNameValuePair("function", "setUsernameAndPassword"));
		postParameters.add(new BasicNameValuePair("id", Integer.toString(id)));
		postParameters.add(new BasicNameValuePair("playerName", username));
		String response = null;
		try{
			response = CustomHttpClient.executeHttpPost("http://dbfootball.altervista.org/jsonscript.php", postParameters);
			String result = response.toString();
			try{
		    	 JSONArray jArray = new JSONArray(result);
		    	 for(int i=0;i<jArray.length();i++){
                       JSONObject json_data = jArray.getJSONObject(i);
                       Log.i("search_player","id: "+json_data.getInt("id"));
                 }
           }
           catch(JSONException e){
                    Log.e("setUsername", "Error parsing data "+e.toString());
           }
		}catch(Exception e){
			Log.e("setUsername", "Error in http connection (searchPlayer)!!" + e.toString());
		}
	}
	
	/**setPassword**/
	/** set the username of the id player**/
	public void setPassword(int id, String password){
		//declare parameters that are passed to PHP script
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		//define the parameter
		postParameters.add(new BasicNameValuePair("function", "setPassword"));
		postParameters.add(new BasicNameValuePair("id", Integer.toString(id)));
		postParameters.add(new BasicNameValuePair("playerPassword", password));
		String response = null;
		try{
			response = CustomHttpClient.executeHttpPost("http://dbfootball.altervista.org/jsonscript.php", postParameters);
			String result = response.toString();
			try{
		    	 JSONArray jArray = new JSONArray(result);
		    	 for(int i=0;i<jArray.length();i++){
                       JSONObject json_data = jArray.getJSONObject(i);
                       Log.i("setPassword","id: "+json_data.getInt("id"));
                 }
           }
           catch(JSONException e){
                    Log.e("setPassword", "Error parsing data "+e.toString());
           }
		}catch(Exception e){
			Log.e("setPassword", "Error in http connection (searchPlayer)!!" + e.toString());
		}
	}
	
	/**getPasswordbyID**/
	/**take the value of the password from the table USER using the playerID**/
	public String getPasswordbyID(int id){
		//declare parameters that are passed to PHP script
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		
		//define the parameter
		postParameters.add(new BasicNameValuePair("function", "getPasswordbyID"));
		postParameters.add(new BasicNameValuePair("id", Integer.toString(id)));
		
		String response = null;
		String password = null;
		try {
	      	  
		     response = CustomHttpClient.executeHttpPost("http://dbfootball.altervista.org/jsonscript.php", postParameters);
		     // store the result returned by PHP script that runs MySQL query
		     String result = response.toString();
		     try{
		    	 JSONArray jArray = new JSONArray(result);
                for(int i=0;i<jArray.length();i++){
                        JSONObject json_data = jArray.getJSONObject(i);
                        Log.i("getPasswordbyID","playerPass: "+json_data.getString("playerPassword"));
                        
                        //Get an output to the screen
                        password = json_data.getString("password");
                }
            }
            catch(JSONException e){
                     Log.e("getPasswordbyID", "Error parsing data "+e.toString());
            }
		}catch (Exception e) {
		     Log.e("getPasswordbyID","Error in http connection!!" + e.toString());     
	    }
		return password;
	}
	
	/** addFriend **/
	/** add the sender id and the receiver id in the friendRequest table **/
	public void addFriend(int sender, int receiver){
		//declare parameters that are passed to PHP script
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
				
		//define the parameter
		postParameters.add(new BasicNameValuePair("function", "addFriend"));
		postParameters.add(new BasicNameValuePair("id_send", Integer.toString(sender)));
		postParameters.add(new BasicNameValuePair("id_rec", Integer.toString(receiver)));
		Log.w("first", Integer.toString(sender));
		Log.w("first", Integer.toString(receiver));
		String response = null;
		
		try {
	      	  
		     response = CustomHttpClient.executeHttpPost("http://dbfootball.altervista.org/jsonscript.php", postParameters);
		     // store the result returned by PHP script that runs MySQL query
		     String result = response.toString();
		     Log.w("addFriend", result);
		     
					
		}catch (Exception e) {
		     Log.e("addFriend","Error in http connection!!" + e.toString());     
	    }
	}
	
	/** ifFriends **/
	/** check if a user is a friend in the friendRequest table **/
	public boolean ifFriends(int id, int friend_id){
		//declare parameters that are passed to PHP script
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
				
		//define the parameter
		postParameters.add(new BasicNameValuePair("function", "ifFriends"));
		postParameters.add(new BasicNameValuePair("id", Integer.toString(id)));
		postParameters.add(new BasicNameValuePair("friend_id", Integer.toString(friend_id)));
		String response = null;
		boolean id_request = false;
		try {
	      	  
		     response = CustomHttpClient.executeHttpPost("http://dbfootball.altervista.org/jsonscript.php", postParameters);
		     // store the result returned by PHP script that runs MySQL query
		     String result = response.toString();
		     try{
		    	 JSONArray jArray = new JSONArray(result);
               for(int i=0;i<jArray.length();i++){
                       JSONObject json_data = jArray.getJSONObject(i);
                       Log.i("id friendship","playerPass: "+json_data.getInt("IDrequest"));
                       
                       //Get an output to the screen
                        if (json_data.isNull("IDrequest") ) id_request = false;
                        else id_request = true; 
               }		
           }
           catch(JSONException e){
                    Log.e("ifFriends", "Error parsing data "+e.toString());
           }
		}catch (Exception e) {
		     Log.e("ifFriends","Error in http connection!!" + e.toString());     
	    }
		return id_request;
	}
	
	public void deleteFriend(int sender, int receiver){
		//declare parameters that are passed to PHP script
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
						
		//define the parameter
		postParameters.add(new BasicNameValuePair("function", "deleteFriend"));
		postParameters.add(new BasicNameValuePair("id", Integer.toString(sender)));
		postParameters.add(new BasicNameValuePair("friend_id", Integer.toString(receiver)));
		Log.w("first", Integer.toString(sender));
		Log.w("first", Integer.toString(receiver));
		String response = null;
		
		try {
	      	  
		     response = CustomHttpClient.executeHttpPost("http://dbfootball.altervista.org/jsonscript.php", postParameters);
		     // store the result returned by PHP script that runs MySQL query
		     String result = response.toString();
		     Log.w("addFriend", result);
		     
					
		}catch (Exception e) {
		     Log.e("deleteFriend","Error in http connection!!" + e.toString());     
	    }
	}
	
	public boolean alreadyAMatch(int sender, int receiver){
		//declare parameters that are passed to PHP script
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
								
		//define the parameter
		postParameters.add(new BasicNameValuePair("function", "alreadyAMatch"));
		postParameters.add(new BasicNameValuePair("idsender", Integer.toString(sender)));
		postParameters.add(new BasicNameValuePair("idreceiver", Integer.toString(receiver)));
		String response = null;
		boolean id_request = false;
		try {
	      	  
		     response = CustomHttpClient.executeHttpPost("http://dbfootball.altervista.org/jsonscript.php", postParameters);
		     // store the result returned by PHP script that runs MySQL query
		     String result = response.toString();
		     try{
		    	 JSONArray jArray = new JSONArray(result);
               for(int i=0;i<jArray.length();i++){
                       JSONObject json_data = jArray.getJSONObject(i);
                       
                       
                       //Get an output to the screen
                        if (json_data.isNull("IDmatch") ) {
                        	id_request = false;
                        	Log.w("already a match", "false");
                        	
                        }
                        else {
                        	id_request = true;
                        	Log.w("already a match", "true");
                        	Log.w("value", Integer.toString(json_data.getInt("IDmatch")));
                        }
                        
               }		
           }
           catch(JSONException e){
                    Log.e("alreadyAMatch", "Error parsing data "+e.toString());
           }
		}catch (Exception e) {
		     Log.e("alreadyAMatch","Error in http connection!!" + e.toString());     
	    }
		return id_request;
	}
	
	/**setUsersToMatch **/
	/**initialize a new match setting the IDsender, IDreceiver, state (to 1 -> pending) and expiration time */
	public void setUsersToMatch(int sender, int receiver, String category){
		//declare parameters that are passed to PHP script
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
				
		//define the parameter
		postParameters.add(new BasicNameValuePair("function", "setUsersToMatch"));
		postParameters.add(new BasicNameValuePair("idsender", Integer.toString(sender)));
		postParameters.add(new BasicNameValuePair("idreceiver", Integer.toString(receiver)));
		postParameters.add(new BasicNameValuePair("category", category));
		String response = null;
		
		try {
	   	     response = CustomHttpClient.executeHttpPost("http://dbfootball.altervista.org/jsonscript.php", postParameters);
		     // store the result returned by PHP script that runs MySQL query
		     String result = response.toString();
		     Log.w("setUsersToMatch", result);
		}catch (Exception e) {
		     Log.e("setUsersToMatch","Error in http connection!!" + e.toString());     
	    }
	}
	
	
	
	public void deleteExpiredGames(int id){
		//declare parameters that are passed to PHP script
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		//define the parameter
		postParameters.add(new BasicNameValuePair("function", "deleteExpiredGames"));
		postParameters.add(new BasicNameValuePair("id", Integer.toString(id)));
		String response = null;
		
		try {
	      	  
		     response = CustomHttpClient.executeHttpPost("http://dbfootball.altervista.org/jsonscript.php", postParameters);
		     // store the result returned by PHP script that runs MySQL query
		     String result = response.toString();
		     
           
		}catch (Exception e) {
		     Log.e("deleteExpiredGames","Error in http connection!!" + e.toString());     
	    }
		
		
	}
	
	public ArrayList<Match> searchPendingAndActiveGames(int id){
		//declare parameters that are passed to PHP script
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
				//define the parameter
				postParameters.add(new BasicNameValuePair("function", "searchPendingAndActiveGames"));
				postParameters.add(new BasicNameValuePair("id", Integer.toString(id)));
				String response = null;
				ArrayList<Match> array = new ArrayList<Match>();
				try{
					response = CustomHttpClient.executeHttpPost("http://dbfootball.altervista.org/jsonscript.php", postParameters);
					String result = response.toString();
					try{
				    	 JSONArray jArray = new JSONArray(result);
				    	 for(int i=0;i<jArray.length();i++){
		                       JSONObject json_data = jArray.getJSONObject(i);
		                       Log.i("activegames", "id: "+json_data.getInt("IDmatch"));
		                       Log.i("activegames", "id: "+json_data.getInt("IDsender_match"));
		                       //Get an output to the screen
		                       Match match = new Match();
		                       match.IDmatch = json_data.getInt("IDmatch");
		                       match.IDsender_match = json_data.getInt("IDsender_match");
		                       match.IDreceiver_match = json_data.getInt("IDreceiver_match");
		                       match.pointsSender = json_data.getInt("pointsSender");
		                       match.pointsReceiver = json_data.getInt("pointsReceiver");
		                       match.state = json_data.getInt("state");
		                       match.creation = json_data.getString("creation");
		                       match.expired = json_data.getString("expired");
		                       match.currentRoundNumber = json_data.getInt("currentRoundNumber");
		                       match.currentAnswerNumber = json_data.getInt("currentAnswerNumber");
		                       match.question1 = json_data.getInt("question1");
		                       match.answer1_send = json_data.getInt("answer1_send");
		                       match.answer1_rec = json_data.getInt("answer1_rec");
		                       match.question2 = json_data.getInt("question2");
		                       match.answer2_send = json_data.getInt("answer2_send");
		                       match.answer2_rec = json_data.getInt("answer2_rec");
		                       match.question3 = json_data.getInt("question3");
		                       match.answer3_send = json_data.getInt("answer3_send");
		                       match.answer3_rec = json_data.getInt("answer3_rec");
		                       match.question4 = json_data.getInt("question4");
		                       match.answer4_send = json_data.getInt("answer4_send");
		                       match.answer4_rec = json_data.getInt("answer4_rec");
		                       match.question5 = json_data.getInt("question5");
		                       match.answer5_send = json_data.getInt("answer5_send");
		                       match.answer5_rec = json_data.getInt("answer5_rec");
		                       match.question6 = json_data.getInt("question6");
		                       match.answer6_send = json_data.getInt("answer6_send");
		                       match.answer6_rec = json_data.getInt("answer6_rec");
		                       match.question7 = json_data.getInt("question7");
		                       match.answer7_send = json_data.getInt("answer7_send");
		                       match.answer7_rec = json_data.getInt("answer7_rec");
		                       match.question8 = json_data.getInt("question8");
		                       match.answer8_send = json_data.getInt("answer8_send");
		                       match.answer8_rec = json_data.getInt("answer8_rec");
		                       match.question9 = json_data.getInt("question9");
		                       match.answer9_send = json_data.getInt("answer9_send");
		                       match.answer9_rec = json_data.getInt("answer9_rec");
		                       match.question10 = json_data.getInt("question10");
		                       match.answer10_send = json_data.getInt("answer10_send");
		                       match.answer10_rec = json_data.getInt("answer10_rec");
		                       match.question11 = json_data.getInt("question11");
		                       match.answer11_send = json_data.getInt("answer11_send");
		                       match.answer11_rec = json_data.getInt("answer11_rec");
		                       match.question12 = json_data.getInt("question12");
		                       match.answer12_send = json_data.getInt("answer12_send");
		                       match.answer12_rec = json_data.getInt("answer12_rec");
		                       match.question13 = json_data.getInt("question13");
		                       match.answer13_send = json_data.getInt("answer13_send");
		                       match.answer13_rec = json_data.getInt("answer13_rec");
		                       match.question14 = json_data.getInt("question14");
		                       match.answer14_send = json_data.getInt("answer14_send");
		                       match.answer14_rec = json_data.getInt("answer14_rec");
		                       match.question15 = json_data.getInt("question15");
		                       match.answer15_send = json_data.getInt("answer15_send");
		                       match.answer15_rec = json_data.getInt("answer15_rec");
		                       match.category = json_data.getString("category");
		                       array.add(match);
		                 }
		           }
		           catch(JSONException e){
		                    Log.e("searchPendingAndActiveGames", "Error parsing data "+e.toString());
		           }
				}catch(Exception e){
					Log.e("searchPendingAndActiveGames", "Error in http connection (searchPendingAndActiveGames)!!" + e.toString());
				}
				return array;
	}
	
	public void deleteMatch(int id){
		//declare parameters that are passed to PHP script
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		//define the parameter
		postParameters.add(new BasicNameValuePair("function", "deleteMatch"));
		postParameters.add(new BasicNameValuePair("id", Integer.toString(id)));
		String response = null;
		
		try {
	      	  
		     response = CustomHttpClient.executeHttpPost("http://dbfootball.altervista.org/jsonscript.php", postParameters);
		     // store the result returned by PHP script that runs MySQL query
		     String result = response.toString();
		     
           
		}catch (Exception e) {
		     Log.e("deleteMatch","Error in http connection!!" + e.toString());     
	    }
		
		
	}
	
	public Match getMatch(int idMatch){
		//declare parameters that are passed to PHP script
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		//define the parameter
		postParameters.add(new BasicNameValuePair("function", "getMatch"));
		postParameters.add(new BasicNameValuePair("idMatch", Integer.toString(idMatch)));
		String response = null;
		Match match = new Match();
		try{
			response = CustomHttpClient.executeHttpPost("http://dbfootball.altervista.org/jsonscript.php", postParameters);
			String result = response.toString();
			try{
		    	 JSONArray jArray = new JSONArray(result);
		    	 for(int i=0;i<jArray.length();i++){
                       JSONObject json_data = jArray.getJSONObject(i);
                       
                       
                       //Get an output to the screen
                      
                       match.IDmatch = json_data.getInt("IDmatch");
                       match.IDsender_match = json_data.getInt("IDsender_match");
                       match.IDreceiver_match = json_data.getInt("IDreceiver_match");
                       match.pointsSender = json_data.getInt("pointsSender");
                       match.pointsReceiver = json_data.getInt("pointsReceiver");
                       match.state = json_data.getInt("state");
                       match.creation = json_data.getString("creation");
                       match.expired = json_data.getString("expired");
                       
                      
                       match.currentRoundNumber = json_data.getInt("currentRoundNumber");
                       match.currentAnswerNumber = json_data.getInt("currentAnswerNumber");
                       match.question1 = json_data.getInt("question1");
                       match.answer1_send = json_data.getInt("answer1_send");
                       match.answer1_rec = json_data.getInt("answer1_rec");
                       match.question2 = json_data.getInt("question2");
                       match.answer2_send = json_data.getInt("answer2_send");
                       match.answer2_rec = json_data.getInt("answer2_rec");
                       match.question3 = json_data.getInt("question3");
                       match.answer3_send = json_data.getInt("answer3_send");
                       match.answer3_rec = json_data.getInt("answer3_rec");
                       match.question4 = json_data.getInt("question4");
                       match.answer4_send = json_data.getInt("answer4_send");
                       match.answer4_rec = json_data.getInt("answer4_rec");
                       match.question5 = json_data.getInt("question5");
                       match.answer5_send = json_data.getInt("answer5_send");
                       match.answer5_rec = json_data.getInt("answer5_rec");
                       match.question6 = json_data.getInt("question6");
                       match.answer6_send = json_data.getInt("answer6_send");
                       match.answer6_rec = json_data.getInt("answer6_rec");
                       match.question7 = json_data.getInt("question7");
                       match.answer7_send = json_data.getInt("answer7_send");
                       match.answer7_rec = json_data.getInt("answer7_rec");
                       match.question8 = json_data.getInt("question8");
                       match.answer8_send = json_data.getInt("answer8_send");
                       match.answer8_rec = json_data.getInt("answer8_rec");
                       match.question9 = json_data.getInt("question9");
                       match.answer9_send = json_data.getInt("answer9_send");
                       match.answer9_rec = json_data.getInt("answer9_rec");
                       match.question10 = json_data.getInt("question10");
                       match.answer10_send = json_data.getInt("answer10_send");
                       match.answer10_rec = json_data.getInt("answer10_rec");
                       match.question11 = json_data.getInt("question11");
                       match.answer11_send = json_data.getInt("answer11_send");
                       match.answer11_rec = json_data.getInt("answer11_rec");
                       match.question12 = json_data.getInt("question12");
                       match.answer12_send = json_data.getInt("answer12_send");
                       match.answer12_rec = json_data.getInt("answer12_rec");
                       match.question13 = json_data.getInt("question13");
                       match.answer13_send = json_data.getInt("answer13_send");
                       match.answer13_rec = json_data.getInt("answer13_rec");
                       match.question14 = json_data.getInt("question14");
                       match.answer14_send = json_data.getInt("answer14_send");
                       match.answer14_rec = json_data.getInt("answer14_rec");
                       match.question15 = json_data.getInt("question15");
                       match.answer15_send = json_data.getInt("answer15_send");
                       match.answer15_rec = json_data.getInt("answer15_rec");
                       match.victoryPointsSender = json_data.getInt("victoryPointsSender");
                       match.victoryPointsReceiver = json_data.getInt("victoryPointsReceiver");
                       match.premiumPointsSender = json_data.getInt("premiumPointsSender");
                       match.premiumPointsReceiver = json_data.getInt("premiumPointsReceiver");
                       match.perfectPointsSender = json_data.getInt("perfectPointsSender");
                       match.perfectPointsReceiver = json_data.getInt("perfectPointsReceiver");
                       match.levelFactorSender = json_data.getString("levelFactorSender");
                       match.levelFactorReceiver = json_data.getString("levelFactorReceiver");
                       match.totalPointsSender = json_data.getInt("totalPointsSender");
                       match.totalPointsReceiver = json_data.getInt("totalPointsReceiver");
                       match.category = json_data.getString("category");
		    	 }
			}
	           catch(JSONException e){
	                    Log.e("getMatch", "Error parsing data "+e.toString());
	           }
			}catch(Exception e){
				Log.e("getMatch", "Error in http connection (getUser)!!" + e.toString());
			}
		return match;
	}
	
	/** startMatch **/
	/** set state = 2 and isRunning = 1**/
	public void startMatch(int idMatch){
		//declare parameters that are passed to PHP script
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		//define the parameter
		postParameters.add(new BasicNameValuePair("function", "startMatch"));
		postParameters.add(new BasicNameValuePair("idMatch", Integer.toString(idMatch)));
		String response = null;
		
		try {
	   	     response = CustomHttpClient.executeHttpPost("http://dbfootball.altervista.org/jsonscript.php", postParameters);
		     // store the result returned by PHP script that runs MySQL query
		     String result = response.toString();
		     Log.w("startMatch", result);
		}catch (Exception e) {
		     Log.e("startMatch","Error in http connection!!" + e.toString());     
	    }
		
	}
	
	
	
	
	public void fillQuestions(int idMatch){
		//declare parameters that are passed to PHP script
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		//define the parameter
		postParameters.add(new BasicNameValuePair("function", "fillQuestions"));
		postParameters.add(new BasicNameValuePair("idMatch", Integer.toString(idMatch)));
		String response = null;
		
		try {
	   	     response = CustomHttpClient.executeHttpPost("http://dbfootball.altervista.org/jsonscript.php", postParameters);
		     // store the result returned by PHP script that runs MySQL query
		     String result = response.toString();
		     Log.w("fillQuestions", result);
		}catch (Exception e) {
		     Log.e("fillQuestions","Error in http connection!!" + e.toString());     
	    }
		
	}
	
	public void fillQuestionsWithCategory(int idMatch, String category){
		//declare parameters that are passed to PHP script
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		//define the parameter
		postParameters.add(new BasicNameValuePair("function", "fillQuestionsWithCategory"));
		postParameters.add(new BasicNameValuePair("idMatch", Integer.toString(idMatch)));
		postParameters.add(new BasicNameValuePair("category", category));
		String response = null;
		
		try {
	   	     response = CustomHttpClient.executeHttpPost("http://dbfootball.altervista.org/jsonscript.php", postParameters);
		     // store the result returned by PHP script that runs MySQL query
		     String result = response.toString();
		     Log.w("fillQuestionsWithCategory", result);
		}catch (Exception e) {
		     Log.e("fillQuestionsWithCategory","Error in http connection!!" + e.toString());     
	    }
		
	}
	
	public Question getActualQuestion(int idMatch, int actualQuestion){
				
		//declare parameters that are passed to PHP script
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		//define the parameter
		postParameters.add(new BasicNameValuePair("function", "getActualQuestion"));
		postParameters.add(new BasicNameValuePair("idMatch", Integer.toString(idMatch)));
		postParameters.add(new BasicNameValuePair("actualQuestion", Integer.toString(actualQuestion)));
//		String response = null;
		Question question = new Question();
		
		try{
			HttpClient httpclient = new DefaultHttpClient();
	        HttpPost httppost = new HttpPost("http://dbfootball.altervista.org/jsonscript.php");
	        httppost.setEntity(new UrlEncodedFormEntity(postParameters, HTTP.UTF_8));
	        HttpResponse response = httpclient.execute(httppost);
	        
	        String result = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
	        
	        
			
			
			
//			response = CustomHttpClient.executeHttpPost("http://dbfootball.altervista.org/jsonscript.php", postParameters);
//			String result = response.toString();
			try{
		    	 JSONArray jArray = new JSONArray(result);
		    	 for(int i=0;i<jArray.length();i++){
                       JSONObject json_data = jArray.getJSONObject(i);
                       
                       Log.i("question", json_data.getString("question"));
                       Log.i("correct", json_data.getString("correct"));
                       Log.i("wrong1", json_data.getString("wrong1"));
                       Log.i("wrong2", json_data.getString("wrong2"));
                       Log.i("wrong3", json_data.getString("wrong3"));
                       //Get an output to the screen
                      
                       question.question = json_data.getString("question");
                       question.correct = json_data.getString("correct");
                       question.wrong1 = json_data.getString("wrong1");
                       question.wrong2 = json_data.getString("wrong2");
                       question.wrong3 = json_data.getString("wrong3");
		    	 }
			}
	           catch(JSONException e){
	                    Log.e("getActualQuestion", "Error parsing data "+e.toString());
	           }
			}catch(Exception e){
				Log.e("getActualQuestion", "Error in http connection!!" + e.toString());
			}
		return question;
	}
	
	public void setCorrect(int idMatch, int sender, int actualQuestion){
		String sendrec;
		String sendrecPoints;
		if(sender == 1){
			sendrec = "send";
			sendrecPoints = "Sender";
		}else{
			sendrec = "rec";
			sendrecPoints = "Receiver";
		}
		//declare parameters that are passed to PHP script
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		//define the parameter
		postParameters.add(new BasicNameValuePair("function", "setCorrect"));
		postParameters.add(new BasicNameValuePair("idMatch", Integer.toString(idMatch)));
		postParameters.add(new BasicNameValuePair("sendrec", sendrec));
		postParameters.add(new BasicNameValuePair("sendrecPoints", sendrecPoints));
		postParameters.add(new BasicNameValuePair("actualQuestion", Integer.toString(actualQuestion)));
		String response = null;
		try{
			response = CustomHttpClient.executeHttpPost("http://dbfootball.altervista.org/jsonscript.php", postParameters);
			String result = response.toString();
			
			}catch(Exception e){
				Log.e("setCorrect", "Error in http connection!!" + e.toString());
			}
		
	}
	
	public void setWrong(int idMatch, int sender, int actualQuestion, int questionNumber){
		String sendrec;
		String sendrecPoints;
		if(sender == 1){
			sendrec = "send";
			sendrecPoints = "Sender";
		}else{
			sendrec = "rec";
			sendrecPoints = "Receiver";
		}
		//declare parameters that are passed to PHP script
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		//define the parameter
		postParameters.add(new BasicNameValuePair("function", "setWrong"));
		postParameters.add(new BasicNameValuePair("idMatch", Integer.toString(idMatch)));
		postParameters.add(new BasicNameValuePair("sendrec", sendrec));
		postParameters.add(new BasicNameValuePair("sendrecPoints", sendrecPoints));
		postParameters.add(new BasicNameValuePair("actualQuestion", Integer.toString(actualQuestion)));
		postParameters.add(new BasicNameValuePair("questionNumber", Integer.toString(questionNumber)));
		String response = null;
			try{
			
				response = CustomHttpClient.executeHttpPost("http://dbfootball.altervista.org/jsonscript.php", postParameters);
				String result = response.toString();
			}catch(Exception e){
				Log.e("setWrong", "Error in http connection!!" + e.toString());
			}
	}
	
	public void switchRound(int idMatch){
	//declare parameters that are passed to PHP script
			ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
			//define the parameter
			postParameters.add(new BasicNameValuePair("function", "switchRound"));
			postParameters.add(new BasicNameValuePair("idMatch", Integer.toString(idMatch)));
		
			String response = null;
			try{
			
				response = CustomHttpClient.executeHttpPost("http://dbfootball.altervista.org/jsonscript.php", postParameters);
				String result = response.toString();
			}catch(Exception e){
				Log.e("switchRound", "Error in http connection!!" + e.toString());
			}
		}
	
	
	public void setMatchFinished(int idMatch){
		//declare parameters that are passed to PHP script
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		//define the parameter
		postParameters.add(new BasicNameValuePair("function", "setMatchFinished"));
		postParameters.add(new BasicNameValuePair("idMatch", Integer.toString(idMatch)));
		String response = null;
		try{
				response = CustomHttpClient.executeHttpPost("http://dbfootball.altervista.org/jsonscript.php", postParameters);
				String result = response.toString();
		}catch(Exception e){
				Log.e("setMatchFinished", "Error in http connection!!" + e.toString());
		}
	}
	
	
	
	public boolean isGamePlayable(int idMatch, int round, int sender){
		boolean isPlayable = false;
		String sendrec;
		if (sender == 0){
			 sendrec="send";
		}else sendrec="rec";
		int actualquestion = ((round - 1)*3) + 1;
		//declare parameters that are passed to PHP script
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		//define the parameter
		postParameters.add(new BasicNameValuePair("function", "isGamePlayable"));
		postParameters.add(new BasicNameValuePair("idMatch", Integer.toString(idMatch)));
		postParameters.add(new BasicNameValuePair("sendrec", sendrec));
		postParameters.add(new BasicNameValuePair("actualquestion", Integer.toString(actualquestion)));
		String response = null;
		int[] answers = new int[15];
		try{
			response = CustomHttpClient.executeHttpPost("http://dbfootball.altervista.org/jsonscript.php", postParameters);
			String result = response.toString();
			try{
		    	 JSONArray jArray = new JSONArray(result);
		    	 for(int i=0;i<jArray.length();i++){
                       JSONObject json_data = jArray.getJSONObject(i);
                       
                       
                       //Get an output to the screen
                      
                       
		    	 }
			}
	           catch(JSONException e){
	                    Log.e("isGamePlayable", "Error parsing data "+e.toString());
	           }
			}catch(Exception e){
				Log.e("isGamePlayable", "Error in http connection!!" + e.toString());
			}
		return isPlayable;
		
	}
	
	public List<Integer> getFriendsId(int id){
		//declare parameters that are passed to PHP script
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		//define the parameter
		postParameters.add(new BasicNameValuePair("function", "getFriendsId"));
		postParameters.add(new BasicNameValuePair("id", Integer.toString(id)));
		List<Integer> friends = new ArrayList<Integer>();
		String response;
		try{
			response = CustomHttpClient.executeHttpPost("http://dbfootball.altervista.org/jsonscript.php", postParameters);
			String result = response.toString();
			try{
		    	 JSONArray jArray = new JSONArray(result);
		    	 for(int i=0;i<jArray.length();i++){
                       JSONObject json_data = jArray.getJSONObject(i);
                       
                       //Get an output to the screen
                      
                       friends.add(Integer.parseInt(json_data.getString("friend")));
                       
		    	 }
			}
	           catch(JSONException e){
	                    Log.e("getFriendsId", "Error parsing data "+e.toString());
	           }
			}catch(Exception e){
				Log.e("getFriendsId", "Error in http connection!!" + e.toString());
			}
		
		
		
		return friends;
	}
	
	
	public List<Integer> getAllPlayersID(){
		//declare parameters that are passed to PHP script
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		//define the parameter
		postParameters.add(new BasicNameValuePair("function", "getAllPlayersID"));
		
		List<Integer> us = new ArrayList<Integer>();
		String response;
		try{
			response = CustomHttpClient.executeHttpPost("http://dbfootball.altervista.org/jsonscript.php", postParameters);
			String result = response.toString();
			try{
		    	 JSONArray jArray = new JSONArray(result);
		    	 for(int i=0;i<jArray.length();i++){
                       JSONObject json_data = jArray.getJSONObject(i);
                       
                      us.add(Integer.parseInt(json_data.getString("id")));
                       
		    	 }
			}
	           catch(JSONException e){
	                    Log.e("getAllPlayersID", "Error parsing data "+e.toString());
	           }
			}catch(Exception e){
				Log.e("getAllPlayersID", "Error in http connection!!" + e.toString());
			}
		
		
		
		return us;
	}
	
	public void submitQuestion(String question, String correct, String wrong1, String wrong2, String wrong3){
		//declare parameters that are passed to PHP script
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		//define the parameter
		postParameters.add(new BasicNameValuePair("function", "submitQuestion"));
		postParameters.add(new BasicNameValuePair("question", question));
		postParameters.add(new BasicNameValuePair("correct", correct));
		postParameters.add(new BasicNameValuePair("wrong1", wrong1));
		postParameters.add(new BasicNameValuePair("wrong2", wrong2));
		postParameters.add(new BasicNameValuePair("wrong3", wrong3));
		
		String response;
		try{
			response = CustomHttpClient.executeHttpPost("http://dbfootball.altervista.org/jsonscript.php", postParameters);
			String result = response.toString();
		}catch(Exception e){
			Log.e("submitQuestion", "Error in http connection!!" + e.toString());
		}
	}
	
	public int getIDFromFacebook(String FBid){
		//declare parameters that are passed to PHP script
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		//define the parameter
		postParameters.add(new BasicNameValuePair("function", "getIDFromFacebook"));
		postParameters.add(new BasicNameValuePair("facebookID", FBid));
		String response;
		
		int playerID = 0;
		try{
			response = CustomHttpClient.executeHttpPost("http://dbfootball.altervista.org/jsonscript.php", postParameters);
			String result = response.toString();
			try{
		    	 JSONArray jArray = new JSONArray(result);
		    	 if (jArray.length() > 0){
		    		 for(int i=0;i<jArray.length();i++){
	                       JSONObject json_data = jArray.getJSONObject(i);
	                       
	                      playerID = json_data.getInt("id");
	                      Log.w("playerID facebook1" , Integer.toString(playerID));
	                       
			    	 }
		    	 }
			}
	           catch(JSONException e){
	                    Log.e("getAllPlayersID", "Error parsing data "+e.toString());
	           }
			}catch(Exception e){
				Log.e("getAllPlayersID", "Error in http connection!!" + e.toString());
			}
		
		
		
		return playerID;
	}
	
	
	public ArrayList<User> getFriends(int id){
		//declare parameters that are passed to PHP script
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		//define the parameter
		postParameters.add(new BasicNameValuePair("function", "getFriends"));
		postParameters.add(new BasicNameValuePair("id", Integer.toString(id)));
		String response = null;
		ArrayList<User> array = new ArrayList<User>();
		try{
			response = CustomHttpClient.executeHttpPost("http://dbfootball.altervista.org/jsonscript.php", postParameters);
			String result = response.toString();
			try{
		    	 JSONArray jArray = new JSONArray(result);
		    	 Log.w("jArray", Integer.toString(jArray.length()));
		    	 for(int i=0;i<jArray.length();i++){
		                 	JSONObject json_data = jArray.getJSONObject(i);
		                 	Log.w("friendsss", "id: "+json_data.getInt("id"));
		                    //Log.i("activegames", "id: "+json_data.getInt("IDsender_match"));
		                       //Get an output to the screen
		                       User user = new User();
		                       
		                       user.playerID = json_data.getInt("id");
		                       user.playerName = json_data.getString("playerName");
		                       user.playerImage = json_data.getString("playerImage");
		                       user.playerPoints = json_data.getInt("playerPoints");
		                       user.playerEmail = json_data.getString("playerEmail");
		                       user.playerPassword = json_data.getString("playerPassword");
		                       user.isPremium = json_data.getInt("isPremium");
		                       user.FbConnect = json_data.getString("FBConnect");
		                       user.isLoggedIn = json_data.getInt("isLoggedIn");
		                       user.draw = json_data.getInt("draw");
		                       user.won = json_data.getInt("won");
		                       user.lost = json_data.getInt("lost");
		                       array.add(user);
		                 }
		           }
		           catch(JSONException e){
		                    Log.e("getFriends", "Error parsing data "+e.toString());
		           }
				}catch(Exception e){
					Log.e("getFriends", "Error in http connection (getFriends)!!" + e.toString());
				}
				return array;
	}
	
	public ArrayList<User> getFBFriends(String fbID){
		//declare parameters that are passed to PHP script
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		//define the parameter
		postParameters.add(new BasicNameValuePair("function", "getFBFriends"));
		
		Log.w("fbID", fbID);
		postParameters.add(new BasicNameValuePair("id", fbID));
		
		String response = null;
		ArrayList<User> array = new ArrayList<User>();
		try{
			response = CustomHttpClient.executeHttpPost("http://dbfootball.altervista.org/jsonscript.php", postParameters);
			String result = response.toString();
			try{
		    	 JSONArray jArray = new JSONArray(result);
		    	 Log.w("jArray", Integer.toString(jArray.length()));
		    	 for(int i=0;i<jArray.length();i++){
		                 	JSONObject json_data = jArray.getJSONObject(i);
		                 	Log.w("friendsssFB", "id: "+json_data.getInt("id"));
		                    //Log.i("activegames", "id: "+json_data.getInt("IDsender_match"));
		                       //Get an output to the screen
		                       User user = new User();
		                       
		                       user.playerID = json_data.getInt("id");
		                       user.playerName = json_data.getString("playerName");
		                       user.playerImage = json_data.getString("playerImage");
		                       user.playerPoints = json_data.getInt("playerPoints");
		                       user.playerEmail = json_data.getString("playerEmail");
		                       user.playerPassword = json_data.getString("playerPassword");
		                       user.isPremium = json_data.getInt("isPremium");
		                       user.FbConnect = json_data.getString("FBConnect");
		                       user.isLoggedIn = json_data.getInt("isLoggedIn");
		                       user.draw = json_data.getInt("draw");
		                       user.won = json_data.getInt("won");
		                       user.lost = json_data.getInt("lost");
		                       array.add(user);
		                 }
		           }
		           catch(JSONException e){
		                    Log.e("getFBFriends", "Error parsing data "+e.toString());
		           }
				}catch(Exception e){
					Log.e("getFBFriends", "Error in http connection (getFBFriends)!!" + e.toString());
				}
				return array;
		
	}
	
	
	public ArrayList<User> getRank(){
		//declare parameters that are passed to PHP script
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		//define the parameter
		postParameters.add(new BasicNameValuePair("function", "getRanking"));
		
		
		
		
		String response = null;
		ArrayList<User> array = new ArrayList<User>();
		try{
			response = CustomHttpClient.executeHttpPost("http://dbfootball.altervista.org/jsonscript.php", postParameters);
			String result = response.toString();
			try{
		    	 JSONArray jArray = new JSONArray(result);
		    	 Log.w("jArray", Integer.toString(jArray.length()));
		    	 for(int i=0;i<jArray.length();i++){
		                 	JSONObject json_data = jArray.getJSONObject(i);
		                 	Log.w("freindsRanking", "id: "+json_data.getInt("id"));
		                    //Log.i("activegames", "id: "+json_data.getInt("IDsender_match"));
		                       //Get an output to the screen
		                       User user = new User();
		                       
		                       user.playerID = json_data.getInt("id");
		                       user.playerName = json_data.getString("playerName");
		                       user.playerImage = json_data.getString("playerImage");
		                       user.playerPoints = json_data.getInt("playerPoints");
		                       user.playerEmail = json_data.getString("playerEmail");
		                       user.playerPassword = json_data.getString("playerPassword");
		                       user.isPremium = json_data.getInt("isPremium");
		                       user.FbConnect = json_data.getString("FBConnect");
		                       user.isLoggedIn = json_data.getInt("isLoggedIn");
		                       user.draw = json_data.getInt("draw");
		                       user.won = json_data.getInt("won");
		                       user.lost = json_data.getInt("lost");
		                       user.rank = json_data.getInt("rank");
		                       array.add(user);
		                 }
		           }
		           catch(JSONException e){
		                    Log.e("getRank", "Error parsing data "+e.toString());
		           }
				}catch(Exception e){
					Log.e("getRank", "Error in http connection (getFBFriends)!!" + e.toString());
				}
				return array;
		
	}
	
	public int getMyRank(int id){
		int rank = 0;
		//declare parameters that are passed to PHP script
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		//define the parameter
		postParameters.add(new BasicNameValuePair("function", "getMyRank"));
		postParameters.add(new BasicNameValuePair("id", Integer.toString(id)));
		String response = null;
		
		try{
			response = CustomHttpClient.executeHttpPost("http://dbfootball.altervista.org/jsonscript.php", postParameters);
			String result = response.toString();
			try{
		    	 JSONArray jArray = new JSONArray(result);
		    	 for(int i=0;i<jArray.length();i++){
                       JSONObject json_data = jArray.getJSONObject(i);
                       
                       
                       //Get an output to the screen
                      
                       rank = json_data.getInt("rank");
                       
               }
           }
           catch(JSONException e){
                    Log.e("getMyRank", "Error parsing data "+e.toString());
           }
		}catch(Exception e){
			Log.e("getMyRank", "Error in http connection (getMyRank)!!" + e.toString());
		}
		
		
		return rank;
	}
	
	public String[] getPointStats(int id){
		String[] values = new String[3];
		
		//declare parameters that are passed to PHP script
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		//define the parameter
		postParameters.add(new BasicNameValuePair("function", "getPointStats"));
		postParameters.add(new BasicNameValuePair("userID", Integer.toString(id)));
		String response = null;
		try{
			response = CustomHttpClient.executeHttpPost("http://dbfootball.altervista.org/jsonscript.php", postParameters);
			String result = response.toString();
			try{
		    	 JSONArray jArray = new JSONArray(result);
		    	 //for(int i=0;i<jArray.length();i++){
                       
		    	 	   JSONObject json_data = jArray.getJSONObject(0);
                       values[0] = json_data.getString("pointsFromLost");
                       if(values[0].equals("null")){
                    	   values[0] = "0";
                       }
                       Log.w("pointsFromLost", values[0]);
                       
                       
                       JSONObject json_data1 = jArray.getJSONObject(1);
                       values[1] = json_data1.getString("pointsFromWon");
                       if(values[1].equals("null")){
                    	   values[1] = "0";
                       }
                       Log.w("pointsFromWon", values[1]);
                       
                       
                       JSONObject json_data2 = jArray.getJSONObject(2);
                       values[2] = json_data2.getString("pointsFromDraw");
                       if (values[2].equals("null")){
                    	   values[2] = "0";
                       }
                       Log.w("pointsFromDraw", values[2]);
                       
                      
                       
                       
               //}
           }
           catch(JSONException e){
                    Log.e("getPointsStats", "Error parsing data "+e.toString());
           }
		}catch(Exception e){
			Log.e("getPointsStats", "Error in http connection (getMyRank)!!" + e.toString());
		}
		return values;
	}
	
	
	public String[] getQuestionStats(int id){
		String[] values = new String[2];
		
		//declare parameters that are passed to PHP script
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		//define the parameter
		postParameters.add(new BasicNameValuePair("function", "getQuestionStats"));
		postParameters.add(new BasicNameValuePair("userID", Integer.toString(id)));
		String response = null;
		try{
			response = CustomHttpClient.executeHttpPost("http://dbfootball.altervista.org/jsonscript.php", postParameters);
			String result = response.toString();
			try{
		    	 JSONArray jArray = new JSONArray(result);
		    	 //for(int i=0;i<jArray.length();i++){
                       
		    	 	   JSONObject json_data = jArray.getJSONObject(0);
                       values[0] = json_data.getString("numCorrectAnswers");
                       if(values[0].equals("null")){
                    	   values[0] = "0";
                       }
                       Log.w("numCorrectAnswers", values[0]);
                       
                       
                       JSONObject json_data1 = jArray.getJSONObject(1);
                       values[1] = json_data1.getString("numWrongAnswers");
                       if(values[1].equals("null")){
                    	   values[1] = "0";
                       }
                       Log.w("numWrongAnswers", values[1]);
                       
                                              
                       
                      
                       
                       
               //}
           }
           catch(JSONException e){
                    Log.e("getQuestionStats", "Error parsing data "+e.toString());
           }
		}catch(Exception e){
			Log.e("getQuestionStats", "Error in http connection (getMyRank)!!" + e.toString());
		}
		return values;
	}
	
	public String[] getConcededStats(int id){
		String[] values = new String[2];
		
		//declare parameters that are passed to PHP script
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		//define the parameter
		postParameters.add(new BasicNameValuePair("function", "getConcededStats"));
		postParameters.add(new BasicNameValuePair("userID", Integer.toString(id)));
		String response = null;
		try{
			response = CustomHttpClient.executeHttpPost("http://dbfootball.altervista.org/jsonscript.php", postParameters);
			String result = response.toString();
			try{
		    	 JSONArray jArray = new JSONArray(result);
		    	 //for(int i=0;i<jArray.length();i++){
                       
		    	 	   JSONObject json_data = jArray.getJSONObject(0);
                       values[0] = json_data.getString("numConcededSelf");
                       if(values[0].equals("null")){
                    	   values[0] = "0";
                       }
                       Log.w("numConcededSelf", values[0]);
                       
                       
                       JSONObject json_data1 = jArray.getJSONObject(1);
                       values[1] = json_data1.getString("numConcededOpponent");
                       if(values[1].equals("null")){
                    	   values[1] = "0";
                       }
                       Log.w("numConcededOpponent", values[1]);
                       
                                              
                       
                      
                       
                       
               //}
           }
           catch(JSONException e){
                    Log.e("getConcededStats", "Error parsing data "+e.toString());
           }
		}catch(Exception e){
			Log.e("getConcededStats", "Error in http connection (getConcededStats)!!" + e.toString());
		}
		return values;
	}
	
	public String[] getPerfectStats(int id){
		String[] values = new String[2];
		
		//declare parameters that are passed to PHP script
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		//define the parameter
		postParameters.add(new BasicNameValuePair("function", "getPerfectStats"));
		postParameters.add(new BasicNameValuePair("userID", Integer.toString(id)));
		String response = null;
		try{
			response = CustomHttpClient.executeHttpPost("http://dbfootball.altervista.org/jsonscript.php", postParameters);
			String result = response.toString();
			try{
		    	 JSONArray jArray = new JSONArray(result);
		    	 //for(int i=0;i<jArray.length();i++){
                       
		    	 	   JSONObject json_data = jArray.getJSONObject(0);
                       values[0] = json_data.getString("numPerfectSelf");
                       if(values[0].equals("null")){
                    	   values[0] = "0";
                       }
                       Log.w("numPerfectSelf", values[0]);
                       
                       
                       JSONObject json_data1 = jArray.getJSONObject(1);
                       values[1] = json_data1.getString("numPerfectOpponent");
                       if(values[1].equals("null")){
                    	   values[1] = "0";
                       }
                       Log.w("numPerfectOpponent", values[1]);
                       
                                              
                       
                      
                       
                       
               //}
           }
           catch(JSONException e){
                    Log.e("getPerfectStats", "Error parsing data "+e.toString());
           }
		}catch(Exception e){
			Log.e("getPerfectStats", "Error in http connection (getPerfectStats)!!" + e.toString());
		}
		return values;
	}
	
	public String[] getTimeoutStats(int id){
		String[] values = new String[2];
		
		//declare parameters that are passed to PHP script
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		//define the parameter
		postParameters.add(new BasicNameValuePair("function", "getTimeoutStats"));
		postParameters.add(new BasicNameValuePair("userID", Integer.toString(id)));
		String response = null;
		try{
			response = CustomHttpClient.executeHttpPost("http://dbfootball.altervista.org/jsonscript.php", postParameters);
			String result = response.toString();
			try{
		    	 JSONArray jArray = new JSONArray(result);
		    	 //for(int i=0;i<jArray.length();i++){
                       
		    	 	   JSONObject json_data = jArray.getJSONObject(0);
                       values[0] = json_data.getString("numTimeoutSelf");
                       if(values[0].equals("null")){
                    	   values[0] = "0";
                       }
                       Log.w("numTimeoutSelf", values[0]);
                       
                       
                       JSONObject json_data1 = jArray.getJSONObject(1);
                       values[1] = json_data1.getString("numTimeoutOpponent");
                       if(values[1].equals("null")){
                    	   values[1] = "0";
                       }
                       Log.w("numTimeoutOpponent", values[1]);
                       
                                              
                       
                      
                       
                       
               //}
           }
           catch(JSONException e){
                    Log.e("getPerfectStats", "Error parsing data "+e.toString());
           }
		}catch(Exception e){
			Log.e("getPerfectStats", "Error in http connection (getPerfectStats)!!" + e.toString());
		}
		return values;
	}
	
	/**
	public void setRemainingRoundWrong(int idMatch, int currentRound, int currentAnswer, int sender){
		//declare parameters that are passed to PHP script
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		//define the parameter
		String send = null;
		if (sender == 1){
			send = "send";
		}else{
			send = "rec";
		}
		int actualAnswer = currentAnswer;
		currentAnswer = ((currentRound- 1)*3)+currentAnswer;
		if ((currentAnswer - ((currentRound - 1)*3))>= 3) currentAnswer = currentAnswer - 3;
		int endOfRound = (currentRound)*3;
		
		postParameters.add(new BasicNameValuePair("function", "setRemainingRoundWrong"));
		postParameters.add(new BasicNameValuePair("currentRound", Integer.toString(currentRound)));
		Log.w("currentRound", Integer.toString(currentRound));
		postParameters.add(new BasicNameValuePair("idMatch", Integer.toString(idMatch)));
		Log.w("idMatch", Integer.toString(idMatch));
		postParameters.add(new BasicNameValuePair("endOfRound", Integer.toString(endOfRound)));
		Log.w("endOfRound", Integer.toString(endOfRound));
		postParameters.add(new BasicNameValuePair("currentAnswer", Integer.toString(currentAnswer)));
		Log.w("currentAnswer", Integer.toString(currentAnswer));
		postParameters.add(new BasicNameValuePair("send", send));
		Log.w("send", send);
		postParameters.add(new BasicNameValuePair("actualAnswer", Integer.toString(actualAnswer)));

		String response = null;
		try{
			response = CustomHttpClient.executeHttpPost("http://dbfootball.altervista.org/jsonscript.php", postParameters);
			String result = response.toString();
			try{
		    	 JSONArray jArray = new JSONArray(result);
		    	 for(int i=0;i<jArray.length();i++){
                       JSONObject json_data = jArray.getJSONObject(i);
                       
                       
                       
                       
               }
           }
           catch(JSONException e){
                    Log.e("setRemainingRoundWrong", "Error parsing data "+e.toString());
           }
		}catch(Exception e){
			Log.e("setRemainingRoundWrong", "Error in http connection (setRemainingRoundWrong)!!" + e.toString());
		}
	}**/
	
	
	public void setRemainingRoundWrong(int idMatch, int currentRound, int currentAnswer, int sender){
		//declare parameters that are passed to PHP script
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		//define the parameter
		
		
		postParameters.add(new BasicNameValuePair("function", "setRemainingRoundWrong"));
		postParameters.add(new BasicNameValuePair("currentRound", Integer.toString(currentRound)));
		Log.w("currentRound", Integer.toString(currentRound));
		postParameters.add(new BasicNameValuePair("idMatch", Integer.toString(idMatch)));
		Log.w("idMatch", Integer.toString(idMatch));
		
		postParameters.add(new BasicNameValuePair("currentAnswer", Integer.toString(currentAnswer)));
		Log.w("currentAnswer", Integer.toString(currentAnswer));
		postParameters.add(new BasicNameValuePair("sender", Integer.toString(sender)));
		
		

		String response = null;
		try{
			response = CustomHttpClient.executeHttpPost("http://dbfootball.altervista.org/jsonscript.php", postParameters);
			String result = response.toString();
			try{
		    	 JSONArray jArray = new JSONArray(result);
		    	 for(int i=0;i<jArray.length();i++){
                       JSONObject json_data = jArray.getJSONObject(i);
                       
                       
                       
                       
               }
           }
           catch(JSONException e){
                    Log.e("setRemainingRoundWrong", "Error parsing data "+e.toString());
           }
		}catch(Exception e){
			Log.e("setRemainingRoundWrong", "Error in http connection (setRemainingRoundWrong)!!" + e.toString());
		}
	}
	
	public void concedeGame(int idMatch, int idUser, int send){
		//declare parameters that are passed to PHP script
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		//define the parameter
		postParameters.add(new BasicNameValuePair("function", "concedeGame"));
		postParameters.add(new BasicNameValuePair("idMatch", Integer.toString(idMatch)));
		postParameters.add(new BasicNameValuePair("idUser", Integer.toString(idUser)));
		postParameters.add(new BasicNameValuePair("send", Integer.toString(send)));
		String response = null;
		try{
			response = CustomHttpClient.executeHttpPost("http://dbfootball.altervista.org/jsonscript.php", postParameters);
			String result = response.toString();
			/*try{
		    	 JSONArray jArray = new JSONArray(result);
		    	 for(int i=0;i<jArray.length();i++){
                       JSONObject json_data = jArray.getJSONObject(i);
                       
                       
                       
                       
               }
           }
           catch(JSONException e){
                    Log.e("concedeGame", "Error parsing data "+e.toString());
           }*/
		}catch(Exception e){
			Log.e("concedeGame", "Error in http connection (concedeGame)!!" + e.toString());
		}
	}
	
	public ArrayList<User> getFriendsByDifficulty(int playerPoints, int difficulty){
		//declare parameters that are passed to PHP script
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		//define the parameter
		postParameters.add(new BasicNameValuePair("function", "getFriendsByDifficulty"));
		postParameters.add(new BasicNameValuePair("playerPoints", Integer.toString(playerPoints)));
		postParameters.add(new BasicNameValuePair("difficulty", Integer.toString(difficulty)));
		String response = null;
		ArrayList<User> array = new ArrayList<User>();
		try{
			response = CustomHttpClient.executeHttpPost("http://dbfootball.altervista.org/jsonscript.php", postParameters);
			String result = response.toString();
			try{
		    	 JSONArray jArray = new JSONArray(result);
		    	 Log.w("jArray", Integer.toString(jArray.length()));
		    	 for(int i=0;i<jArray.length();i++){
		                 	JSONObject json_data = jArray.getJSONObject(i);
		                 	Log.w("freindsRanking", "id: "+json_data.getInt("id"));
		                    //Log.i("activegames", "id: "+json_data.getInt("IDsender_match"));
		                       //Get an output to the screen
		                       User user = new User();
		                       
		                       user.playerID = json_data.getInt("id");
		                       user.playerName = json_data.getString("playerName");
		                       user.playerImage = json_data.getString("playerImage");
		                       user.playerPoints = json_data.getInt("playerPoints");
		                       user.playerEmail = json_data.getString("playerEmail");
		                       user.playerPassword = json_data.getString("playerPassword");
		                       user.isPremium = json_data.getInt("isPremium");
		                       user.FbConnect = json_data.getString("FBConnect");
		                       user.isLoggedIn = json_data.getInt("isLoggedIn");
		                       user.draw = json_data.getInt("draw");
		                       user.won = json_data.getInt("won");
		                       user.lost = json_data.getInt("lost");
		                       
		                       array.add(user);
		                 }
		           }
		           catch(JSONException e){
		                    Log.e("getFriendsByDifficulty", "Error parsing data "+e.toString());
		           }
				}catch(Exception e){
					Log.e("getFriendsByDifficulty", "Error in http connection (getFriendsByDifficulty)!!" + e.toString());
				}
				return array;
		
	}
	
	public int selectBotByDifficulty(int playerPoints, int difficulty){
		//declare parameters that are passed to PHP script
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		//define the parameter
		postParameters.add(new BasicNameValuePair("function", "selectBotByDifficulty"));
		postParameters.add(new BasicNameValuePair("playerPoints", Integer.toString(playerPoints)));
		postParameters.add(new BasicNameValuePair("difficulty", Integer.toString(difficulty)));
		int IDbot = 0;
		String response = null;
		try{
			response = CustomHttpClient.executeHttpPost("http://dbfootball.altervista.org/jsonscript.php", postParameters);
			String result = response.toString();
			try{
		    	 JSONArray jArray = new JSONArray(result);
		    	 Log.w("jArray", Integer.toString(jArray.length()));
		    	  	JSONObject json_data = jArray.getJSONObject(0);
		    	 	IDbot = json_data.getInt("id");
		           }
		           catch(JSONException e){
		                    Log.e("selectBot", "Error parsing data "+e.toString());
		           }
				}catch(Exception e){
					Log.e("selectBot", "Error in http connection (selectBot)!!" + e.toString());
				}
		return IDbot;
	}
	
	public ArrayList<User> getRankingTop3(){
		//declare parameters that are passed to PHP script
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		//define the parameter
		postParameters.add(new BasicNameValuePair("function", "getRankingTop3"));
		
		ArrayList<User> rankingTop3 = new ArrayList<User>();
		String response = null;
		try{
			response = CustomHttpClient.executeHttpPost("http://dbfootball.altervista.org/jsonscript.php", postParameters);
			String result = response.toString();
			try{
		    	 JSONArray jArray = new JSONArray(result);
		    	 Log.w("jArray", Integer.toString(jArray.length()));
		    	 for(int i=0;i<jArray.length();i++){
		                 	JSONObject json_data = jArray.getJSONObject(i);
		                 	Log.w("freindsRanking", "id: "+json_data.getInt("id"));
		                    //Log.i("activegames", "id: "+json_data.getInt("IDsender_match"));
		                       //Get an output to the screen
		                       User user = new User();
		                       
		                       user.playerID = json_data.getInt("id");
		                       user.playerName = json_data.getString("playerName");
		                       user.playerImage = json_data.getString("playerImage");
		                       user.playerPoints = json_data.getInt("playerPoints");
		                       user.playerEmail = json_data.getString("playerEmail");
		                       user.playerPassword = json_data.getString("playerPassword");
		                       user.isPremium = json_data.getInt("isPremium");
		                       user.FbConnect = json_data.getString("FBConnect");
		                       user.isLoggedIn = json_data.getInt("isLoggedIn");
		                       user.draw = json_data.getInt("draw");
		                       user.won = json_data.getInt("won");
		                       user.lost = json_data.getInt("lost");
		                       user.rank = json_data.getInt("rank");
		                       rankingTop3.add(user);
		                 }
		           }
		           catch(JSONException e){
		                    Log.e("getRank", "Error parsing data "+e.toString());
		           }
				}catch(Exception e){
					Log.e("getRank", "Error in http connection (getFBFriends)!!" + e.toString());
				}
				return rankingTop3;
	}
		
	
	public ArrayList<Match> getAllMatches(int id){
		//declare parameters that are passed to PHP script
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		//define the parameter
		postParameters.add(new BasicNameValuePair("function", "getAllMatches"));
		postParameters.add(new BasicNameValuePair("id", Integer.toString(id)));
		String response = null;
		ArrayList<Match> array = new ArrayList<Match>();
		try{
			response = CustomHttpClient.executeHttpPost("http://dbfootball.altervista.org/jsonscript.php", postParameters);
			String result = response.toString();
			try{
		    	 JSONArray jArray = new JSONArray(result);
		    	 Log.w("jArray", Integer.toString(jArray.length()));
		    	 for(int i=0;i<jArray.length();i++){
		                 	JSONObject json_data = jArray.getJSONObject(i);
		                 	Log.w("match", "id: "+json_data.getInt("id"));
		                    //Log.i("activegames", "id: "+json_data.getInt("IDsender_match"));
		                       //Get an output to the screen
		                       Match match = new Match();
		                       match.IDmatch = json_data.getInt("IDmatch");
		                       match.IDsender_match = json_data.getInt("IDsender_match");
		                       match.IDreceiver_match = json_data.getInt("IDreceiver_match");
		                       match.pointsSender = json_data.getInt("pointsSender");
		                       match.pointsReceiver = json_data.getInt("pointsReceiver");
		                       match.state = json_data.getInt("state");
		                       match.creation = json_data.getString("creation");
		                       match.expired = json_data.getString("expired");
		                       
		                      
		                       match.currentRoundNumber = json_data.getInt("currentRoundNumber");
		                       match.currentAnswerNumber = json_data.getInt("currentAnswerNumber");
		                       match.question1 = json_data.getInt("question1");
		                       match.answer1_send = json_data.getInt("answer1_send");
		                       match.answer1_rec = json_data.getInt("answer1_rec");
		                       match.question2 = json_data.getInt("question2");
		                       match.answer2_send = json_data.getInt("answer2_send");
		                       match.answer2_rec = json_data.getInt("answer2_rec");
		                       match.question3 = json_data.getInt("question3");
		                       match.answer3_send = json_data.getInt("answer3_send");
		                       match.answer3_rec = json_data.getInt("answer3_rec");
		                       match.question4 = json_data.getInt("question4");
		                       match.answer4_send = json_data.getInt("answer4_send");
		                       match.answer4_rec = json_data.getInt("answer4_rec");
		                       match.question5 = json_data.getInt("question5");
		                       match.answer5_send = json_data.getInt("answer5_send");
		                       match.answer5_rec = json_data.getInt("answer5_rec");
		                       match.question6 = json_data.getInt("question6");
		                       match.answer6_send = json_data.getInt("answer6_send");
		                       match.answer6_rec = json_data.getInt("answer6_rec");
		                       match.question7 = json_data.getInt("question7");
		                       match.answer7_send = json_data.getInt("answer7_send");
		                       match.answer7_rec = json_data.getInt("answer7_rec");
		                       match.question8 = json_data.getInt("question8");
		                       match.answer8_send = json_data.getInt("answer8_send");
		                       match.answer8_rec = json_data.getInt("answer8_rec");
		                       match.question9 = json_data.getInt("question9");
		                       match.answer9_send = json_data.getInt("answer9_send");
		                       match.answer9_rec = json_data.getInt("answer9_rec");
		                       match.question10 = json_data.getInt("question10");
		                       match.answer10_send = json_data.getInt("answer10_send");
		                       match.answer10_rec = json_data.getInt("answer10_rec");
		                       match.question11 = json_data.getInt("question11");
		                       match.answer11_send = json_data.getInt("answer11_send");
		                       match.answer11_rec = json_data.getInt("answer11_rec");
		                       match.question12 = json_data.getInt("question12");
		                       match.answer12_send = json_data.getInt("answer12_send");
		                       match.answer12_rec = json_data.getInt("answer12_rec");
		                       match.question13 = json_data.getInt("question13");
		                       match.answer13_send = json_data.getInt("answer13_send");
		                       match.answer13_rec = json_data.getInt("answer13_rec");
		                       match.question14 = json_data.getInt("question14");
		                       match.answer14_send = json_data.getInt("answer14_send");
		                       match.answer14_rec = json_data.getInt("answer14_rec");
		                       match.question15 = json_data.getInt("question15");
		                       match.answer15_send = json_data.getInt("answer15_send");
		                       match.answer15_rec = json_data.getInt("answer15_rec");
		                       match.victoryPointsSender = json_data.getInt("victoryPointsSender");
		                       match.victoryPointsReceiver = json_data.getInt("victoryPointsReceiver");
		                       match.premiumPointsSender = json_data.getInt("premiumPointsSender");
		                       match.premiumPointsReceiver = json_data.getInt("premiumPointsReceiver");
		                       match.perfectPointsSender = json_data.getInt("perfectPointsSender");
		                       match.perfectPointsReceiver = json_data.getInt("perfectPointsReceiver");
		                       match.levelFactorSender = json_data.getString("levelFactorSender");
		                       match.levelFactorReceiver = json_data.getString("levelFactorReceiver");
		                       match.totalPointsSender = json_data.getInt("totalPointsSender");
		                       match.totalPointsReceiver = json_data.getInt("totalPointsReceiver");
		                       match.category = json_data.getString("category");
		                       array.add(match);
		                 }
		           }
		           catch(JSONException e){
		                    Log.e("getAllMatches", "Error parsing data "+e.toString());
		           }
				}catch(Exception e){
					Log.e("getAllMatches", "Error in http connection (getAllMatches)!!" + e.toString());
				}
				return array;
	}
	
	public int[] getAnswered(int idMatch, int currentAnswer){
		//declare parameters that are passed to PHP script
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		//define the parameter
		postParameters.add(new BasicNameValuePair("function", "getAnswered"));
		postParameters.add(new BasicNameValuePair("idMatch", Integer.toString(idMatch)));
		postParameters.add(new BasicNameValuePair("currentAnswer", Integer.toString(currentAnswer)));
		String response = null;
		int array[] = new int[2];
		try{
			response = CustomHttpClient.executeHttpPost("http://dbfootball.altervista.org/jsonscript.php", postParameters);
			String result = response.toString();
			try{
		    	 JSONArray jArray = new JSONArray(result);
		    	 Log.w("jArray", Integer.toString(jArray.length()));
		    	 for(int i=0;i<jArray.length();i++){
		                 	JSONObject json_data = jArray.getJSONObject(i);
		                 			                    //Log.i("activegames", "id: "+json_data.getInt("IDsender_match"));
		                       //Get an output to the screen
		                       array[0] = json_data.getInt("totAnswered");
		                       array[1] = json_data.getInt("correctAnswered");
		    	 	}
		    	 }
		           catch(JSONException e){
		                    Log.e("getAnswered", "Error parsing data "+e.toString());
		           }
				}catch(Exception e){
					Log.e("getAnswered", "Error in http connection (getAnswered)!!" + e.toString());
				}
				return array;
		
	}
	
	
	public ArrayList<chatMessage> getConversation(int id_sender, int id_receiver){
		//declare parameters that are passed to PHP script
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		//define the parameter
		postParameters.add(new BasicNameValuePair("function", "getConversation"));
		postParameters.add(new BasicNameValuePair("userId1", Integer.toString(id_sender)));
		postParameters.add(new BasicNameValuePair("userId2", Integer.toString(id_receiver)));
		String response = null;
		ArrayList<chatMessage> array = new ArrayList<chatMessage>();
		try{
			response = CustomHttpClient.executeHttpPost("http://dbfootball.altervista.org/jsonscript.php", postParameters);
			String result = response.toString();
			try{
		    	 JSONArray jArray = new JSONArray(result);
		    	 Log.w("jArray", Integer.toString(jArray.length()));
		    	 for(int i=0;i<jArray.length();i++){
		                 	JSONObject json_data = jArray.getJSONObject(i);
		                 	
		                       //Get an output to the screen
		                       chatMessage chat = new chatMessage();
		                       chat.messageId = json_data.getInt("messageId");
		                       chat.senderId = json_data.getInt("senderId");
		                       chat.receiverId = json_data.getInt("receiverId");
		                       chat.message = json_data.getString("message");
		                       chat.timestamp = json_data.getString("timestamp");
		                       
		                       
		                       array.add(chat);
		                 }
		           }
		           catch(JSONException e){
		                    Log.e("getAllMatches", "Error parsing data "+e.toString());
		           }
				}catch(Exception e){
					Log.e("getAllMatches", "Error in http connection (getAllMatches)!!" + e.toString());
				}
				return array;
	}
	
	public void createChatMessage(int sender, int receiver, String message){
		//declare parameters that are passed to PHP script
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		//define the parameter
		postParameters.add(new BasicNameValuePair("function", "createChatMessage"));
		postParameters.add(new BasicNameValuePair("senderId", Integer.toString(sender)));
		postParameters.add(new BasicNameValuePair("receiverId", Integer.toString(receiver)));
		postParameters.add(new BasicNameValuePair("message", message));
		String response = null;
		try{
			response = CustomHttpClient.executeHttpPost("http://dbfootball.altervista.org/jsonscript.php", postParameters);
			String result = response.toString();
		}catch(Exception e){
			Log.e("getAllMatches", "Error in http connection (getAllMatches)!!" + e.toString());
		}
		
	}
	
	public void forgotPassword(int id, String email){
		//declare parameters that are passed to PHP script
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		//define the parameter
		postParameters.add(new BasicNameValuePair("function", "forgotPassword"));
		postParameters.add(new BasicNameValuePair("id", Integer.toString(id)));
		postParameters.add(new BasicNameValuePair("email", email));
		
		String response = null;
		try{
			response = CustomHttpClient.executeHttpPost("http://dbfootball.altervista.org/jsonscript.php", postParameters);
			String result = response.toString();
		}catch(Exception e){
			Log.e("forgotPassword", "Error in http connection (forgotPassword)!!" + e.toString());
		}
		
	}
	
	public String[] getCategories(){
		//declare parameters that are passed to PHP script
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		//define the parameter
		postParameters.add(new BasicNameValuePair("function", "getCategories"));
		String[] array = new String[6];
		String response = null;
		try{
			response = CustomHttpClient.executeHttpPost("http://dbfootball.altervista.org/jsonscript.php", postParameters);
			String result = response.toString();
			try{
		    	 JSONArray jArray = new JSONArray(result);
		    	 Log.w("jArray", Integer.toString(jArray.length()));
		    	 for(int i=0;i<jArray.length();i++){
		                 	JSONObject json_data = jArray.getJSONObject(i);
		                 			                    //Log.i("activegames", "id: "+json_data.getInt("IDsender_match"));
		                       //Get an output to the screen
		                       array[i] = json_data.getString("category");
		                       
		    	 	}
		    	 }
		           catch(JSONException e){
		                    Log.e("getCategories", "Error parsing data "+e.toString());
		           }
				}catch(Exception e){
					Log.e("getCategories", "Error in http connection (getCategories)!!" + e.toString());
				}
				return array;
		
	}
	
	public int getNumMatches(int id){
		//declare parameters that are passed to PHP script
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		//define the parameter
		postParameters.add(new BasicNameValuePair("function", "getNumMatches"));
		postParameters.add(new BasicNameValuePair("id", Integer.toString(id)));
		int num = 0;
		String response = null;
		try{
			response = CustomHttpClient.executeHttpPost("http://dbfootball.altervista.org/jsonscript.php", postParameters);
			String result = response.toString();
			try{
		    	 JSONArray jArray = new JSONArray(result);
		    	 Log.w("jArray", Integer.toString(jArray.length()));
		    	 for(int i=0;i<jArray.length();i++){
		                 	JSONObject json_data = jArray.getJSONObject(i);
		                 			                    //Log.i("activegames", "id: "+json_data.getInt("IDsender_match"));
		                       //Get an output to the screen
		                       num = json_data.getInt("numMatches");
		                       
		    	 	}
		    	 }
		           catch(JSONException e){
		                    Log.e("getCategories", "Error parsing data "+e.toString());
		           }
				}catch(Exception e){
					Log.e("getCategories", "Error in http connection (getCategories)!!" + e.toString());
				}
				return num;
	}
	
	public RankHistory getRankHistoryForUser(int id){
		
		//declare parameters that are passed to PHP script
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		//define the parameter
		postParameters.add(new BasicNameValuePair("function", "getRankHistoryForUser"));
		postParameters.add(new BasicNameValuePair("id", Integer.toString(id)));
		
//		String response = null;
		RankHistory rankHistory = new RankHistory();
		
		try{
			HttpClient httpclient = new DefaultHttpClient();
	        HttpPost httppost = new HttpPost("http://dbfootball.altervista.org/jsonscript.php");
	        httppost.setEntity(new UrlEncodedFormEntity(postParameters, HTTP.UTF_8));
	        HttpResponse response = httpclient.execute(httppost);
	        
	        String result = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
	        
	        
			
			
			
//			response = CustomHttpClient.executeHttpPost("http://dbfootball.altervista.org/jsonscript.php", postParameters);
//			String result = response.toString();
			try{
		    	 JSONArray jArray = new JSONArray(result);
		    	 for(int i=0;i<jArray.length();i++){
                       JSONObject json_data = jArray.getJSONObject(i);
                       
                      
                      
                       rankHistory.userId = json_data.getInt("userId");
                       rankHistory.rankAtMinus1 = json_data.getInt("rankAtTMinus1");
                       rankHistory.rankAtMinus2 = json_data.getInt("rankAtTMinus2");
                       rankHistory.rankAtMinus3 = json_data.getInt("rankAtTMinus3");
                       rankHistory.rankAtMinus4 = json_data.getInt("rankAtTMinus4");
                       rankHistory.rankAtMinus5 = json_data.getInt("rankAtTMinus5");
                       rankHistory.rankAtMinus6 = json_data.getInt("rankAtTMinus6");
                       rankHistory.rankAtMinus7 = json_data.getInt("rankAtTMinus7");
                       
                       
		    	 }
			}
	           catch(JSONException e){
	                    Log.e("getRankHistoryForUsers", "Error parsing data "+e.toString());
	           }
			}catch(Exception e){
				Log.e("getRankHistoryForUsers", "Error in http connection!!" + e.toString());
			}
		return rankHistory;
	}
	
	
	public int getUserNumber(){
		//declare parameters that are passed to PHP script
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		//define the parameter
		postParameters.add(new BasicNameValuePair("function", "getUserNumber"));
		
		int user_number = 0;
		String response = null;
		try{
			response = CustomHttpClient.executeHttpPost("http://dbfootball.altervista.org/jsonscript.php", postParameters);
			String result = response.toString();
			try{
		    	 JSONArray jArray = new JSONArray(result);
		    	 Log.w("jArray", Integer.toString(jArray.length()));
		    	 for(int i=0;i<jArray.length();i++){
		                 	JSONObject json_data = jArray.getJSONObject(i);
		                 			                    //Log.i("activegames", "id: "+json_data.getInt("IDsender_match"));
		                       //Get an output to the screen
		                       user_number = json_data.getInt("numUser");
		                       
		    	 	}
		    	 }
		           catch(JSONException e){
		                    Log.e("getCategories", "Error parsing data "+e.toString());
		           }
				}catch(Exception e){
					Log.e("getCategories", "Error in http connection (getCategories)!!" + e.toString());
				}
				return user_number;
	}
	
	public int[] getTimeCategoryQuestionStats(int id, String time, String category){
		int[] values = new int[2];
		
		//declare parameters that are passed to PHP script
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		//define the parameter
		postParameters.add(new BasicNameValuePair("function", "getTimeCategoryQuestionStats"));
		postParameters.add(new BasicNameValuePair("userID", Integer.toString(id)));
		postParameters.add(new BasicNameValuePair("time", time));
		postParameters.add(new BasicNameValuePair("category", category));
		String response = null;
		try{
			response = CustomHttpClient.executeHttpPost("http://dbfootball.altervista.org/jsonscript.php", postParameters);
			String result = response.toString();
			try{
		    	 JSONArray jArray = new JSONArray(result);
		    	 //for(int i=0;i<jArray.length();i++){
                       
		    	 	   JSONObject json_data = jArray.getJSONObject(0);
                       values[0] = json_data.getInt("numCorrectAnswers");
                       
                       
                       
                       
                       JSONObject json_data1 = jArray.getJSONObject(1);
                       values[1] = json_data1.getInt("numWrongAnswers");
                       
                       
                       
                                              
                       
                      
                       
                       
               //}
           }
           catch(JSONException e){
                    Log.e("getTimeCategoryQuestionStats", "Error parsing data "+e.toString());
           }
		}catch(Exception e){
			Log.e("getTimeCategoryQuestionStats", "Error in http connection (getTimeCategoryQuestionStats)!!" + e.toString());
		}
		return values;
	}
	
	
	public int[] getTimeCategoryPointStats(int id, String time, String category){
		int[] values = new int[3];
		
		//declare parameters that are passed to PHP script
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		//define the parameter
		postParameters.add(new BasicNameValuePair("function", "getTimeCategoryPointStats"));
		postParameters.add(new BasicNameValuePair("userID", Integer.toString(id)));
		postParameters.add(new BasicNameValuePair("time", time));
		postParameters.add(new BasicNameValuePair("category", category));
		String response = null;
		try{
			response = CustomHttpClient.executeHttpPost("http://dbfootball.altervista.org/jsonscript.php", postParameters);
			String result = response.toString();
			try{
		    	 JSONArray jArray = new JSONArray(result);
		    	 //for(int i=0;i<jArray.length();i++){
                       
		    	 	   JSONObject json_data = jArray.getJSONObject(0);
                       values[0] = json_data.getInt("pointsFromLost");
                       
                       
                       
                       JSONObject json_data1 = jArray.getJSONObject(1);
                       values[1] = json_data1.getInt("pointsFromWon");
                       
                       
                       
                       
                       JSONObject json_data2 = jArray.getJSONObject(2);
                       values[2] = json_data2.getInt("pointsFromDraw");
                       
                       
                      
                       
                       
               //}
           }
           catch(JSONException e){
                    Log.e("getTimeCategoryPointStats", "Error parsing data "+e.toString());
           }
		}catch(Exception e){
			Log.e("getTimeCategoryPointStats", "Error in http connection (getTimeCategoryPointStats)!!" + e.toString());
		}
		return values;
	}
	
	public int[] getTimeCategoryConcededStats(int id, String time, String category){
		int[] values = new int[2];
		
		//declare parameters that are passed to PHP script
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		//define the parameter
		postParameters.add(new BasicNameValuePair("function", "getTimeCategoryConcededStats"));
		postParameters.add(new BasicNameValuePair("userID", Integer.toString(id)));
		postParameters.add(new BasicNameValuePair("time", time));
		postParameters.add(new BasicNameValuePair("category", category));
		String response = null;
		try{
			response = CustomHttpClient.executeHttpPost("http://dbfootball.altervista.org/jsonscript.php", postParameters);
			String result = response.toString();
			try{
		    	 JSONArray jArray = new JSONArray(result);
		    	 //for(int i=0;i<jArray.length();i++){
                       
		    	 	   JSONObject json_data = jArray.getJSONObject(0);
                       values[0] = json_data.getInt("numConcededSelf");
                       
                       
                       
                       JSONObject json_data1 = jArray.getJSONObject(1);
                       values[1] = json_data1.getInt("numConcededOpponent");
                       
                       
                                              
                       
                      
                       
                       
               //}
           }
           catch(JSONException e){
                    Log.e("getConcededStats", "Error parsing data "+e.toString());
           }
		}catch(Exception e){
			Log.e("getConcededStats", "Error in http connection (getConcededStats)!!" + e.toString());
		}
		return values;
	}
	
	public int[] getTimeCategoryPerfectStats(int id, String time, String category){
		int[] values = new int[2];
		
		//declare parameters that are passed to PHP script
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		//define the parameter
		postParameters.add(new BasicNameValuePair("function", "getTimeCategoryPerfectStats"));
		postParameters.add(new BasicNameValuePair("userID", Integer.toString(id)));
		postParameters.add(new BasicNameValuePair("time", time));
		postParameters.add(new BasicNameValuePair("category", category));
		String response = null;
		try{
			response = CustomHttpClient.executeHttpPost("http://dbfootball.altervista.org/jsonscript.php", postParameters);
			String result = response.toString();
			try{
		    	 JSONArray jArray = new JSONArray(result);
		    	 //for(int i=0;i<jArray.length();i++){
                       
		    	 	   JSONObject json_data = jArray.getJSONObject(0);
                       values[0] = json_data.getInt("numPerfectSelf");
                       
                       
                       
                       JSONObject json_data1 = jArray.getJSONObject(1);
                       values[1] = json_data1.getInt("numPerfectOpponent");
                       
                       
                                              
                       
                      
                       
                       
               //}
           }
           catch(JSONException e){
                    Log.e("getTimeCategoryPerfectStats", "Error parsing data "+e.toString());
           }
		}catch(Exception e){
			Log.e("getTimeCategoryPerfectStats", "Error in http connection (getTimeCategoryPerfectStats)!!" + e.toString());
		}
		return values;
	}
	
	public int[] getTimeCategoryTimeoutStats(int id, String time, String category){
		int[] values = new int[2];
		
		//declare parameters that are passed to PHP script
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		//define the parameter
		postParameters.add(new BasicNameValuePair("function", "getTimeCategoryTimeoutStats"));
		postParameters.add(new BasicNameValuePair("userID", Integer.toString(id)));
		postParameters.add(new BasicNameValuePair("time", time));
		postParameters.add(new BasicNameValuePair("category", category));
		String response = null;
		try{
			response = CustomHttpClient.executeHttpPost("http://dbfootball.altervista.org/jsonscript.php", postParameters);
			String result = response.toString();
			try{
		    	 JSONArray jArray = new JSONArray(result);
		    	 //for(int i=0;i<jArray.length();i++){
                       
		    	 	   JSONObject json_data = jArray.getJSONObject(0);
                       values[0] = json_data.getInt("numTimeoutSelf");
                       
                       
                       JSONObject json_data1 = jArray.getJSONObject(1);
                       values[1] = json_data1.getInt("numTimeoutOpponent");
                       
                                              
                       
                      
                       
                       
               //}
           }
           catch(JSONException e){
                    Log.e("getTimeCategoryTimeoutStats", "Error parsing data "+e.toString());
           }
		}catch(Exception e){
			Log.e("getTimeCategoryTimeoutStats", "Error in http connection (getTimeCategoryTimeoutStats)!!" + e.toString());
		}
		return values;
	}
	
	public int[] getTimeCategoryMatchStats(int id, String time, String category){
		int[] values = new int[3];
		
		//declare parameters that are passed to PHP script
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		//define the parameter
		postParameters.add(new BasicNameValuePair("function", "getTimeCategoryMatchStats"));
		postParameters.add(new BasicNameValuePair("userID", Integer.toString(id)));
		postParameters.add(new BasicNameValuePair("time", time));
		postParameters.add(new BasicNameValuePair("category", category));
		String response = null;
		try{
			response = CustomHttpClient.executeHttpPost("http://dbfootball.altervista.org/jsonscript.php", postParameters);
			String result = response.toString();
			try{
		    	 JSONArray jArray = new JSONArray(result);
		    	 //for(int i=0;i<jArray.length();i++){
                       
		    	 	   JSONObject json_data = jArray.getJSONObject(0);
                       values[0] = json_data.getInt("MatchLost");
                       
                       
                       
                       JSONObject json_data1 = jArray.getJSONObject(1);
                       values[1] = json_data1.getInt("MatchWon");
                       
                       
                       
                       
                       JSONObject json_data2 = jArray.getJSONObject(2);
                       values[2] = json_data2.getInt("MatchDraw");
                       
                       
                      
                       
                       
               //}
           }
           catch(JSONException e){
                    Log.e("getTimeCategoryMatchStats", "Error parsing data "+e.toString());
           }
		}catch(Exception e){
			Log.e("getTimeCategoryMatchStats", "Error in http connection (getTimeCategoryMatchStats)!!" + e.toString());
		}
		return values;
	}
	
	
	
		
	public class chatMessage{
		int messageId = 0;
		int senderId = 0;
		int receiverId = 0;
		String message = null;
		String timestamp = null;
	}
	
	
		
	public class User{
		int playerID = 0;
		String playerName = null;
		String playerImage = null;
		int playerPoints = 0;
		String playerEmail = null;
		String playerPassword = null;
		int isPremium = 0;
		String FbConnect = null;
		int isLoggedIn = 0;
		int draw = 0;
		int won = 0;
		int lost = 0;
		int rank = 0;
		String club = null;
		int rankBest = 0;
	}
	
	public class Match{
		int IDmatch = 0;
		int IDsender_match = 0;
		int IDreceiver_match = 0;
		int pointsSender = 0;
		int pointsReceiver = 0;
		int state = 0;
		String creation = null;
		String expired = null;
		
		
		int currentRoundNumber = 0;
		int currentAnswerNumber = 0;
		int question1 = 0;
		int answer1_send = 0;
		int answer1_rec = 0;
		int question2 = 0;
		int answer2_send = 0;
		int answer2_rec = 0;
		int question3 = 0;
		int answer3_send = 0;
		int answer3_rec = 0;
		int question4 = 0;
		int answer4_send = 0;
		int answer4_rec = 0;
		int question5 = 0;
		int answer5_send = 0;
		int answer5_rec = 0;
		int question6 = 0;
		int answer6_send = 0;
		int answer6_rec = 0;
		int question7 = 0;
		int answer7_send = 0;
		int answer7_rec = 0;
		int question8 = 0;
		int answer8_send = 0;
		int answer8_rec = 0;
		int question9 = 0;
		int answer9_send = 0;
		int answer9_rec = 0;
		int question10 = 0;
		int answer10_send = 0;
		int answer10_rec = 0;
		int question11 = 0;
		int answer11_send = 0;
		int answer11_rec = 0;
		int question12 = 0;
		int answer12_send = 0;
		int answer12_rec = 0;
		int question13 = 0;
		int answer13_send = 0;
		int answer13_rec = 0;
		int question14 = 0;
		int answer14_send = 0;
		int answer14_rec = 0;
		int question15 = 0;
		int answer15_send = 0;
		int answer15_rec = 0;
		int victoryPointsSender = 0;
        int victoryPointsReceiver = 0;
        int premiumPointsSender = 0;
        int premiumPointsReceiver = 0;
        int perfectPointsSender = 0;
        int perfectPointsReceiver = 0;
        String levelFactorSender = null;
        String levelFactorReceiver = null;
        int totalPointsSender = 0;
        int totalPointsReceiver = 0;
        String category = null;
	
	}
	
	public class Question{
		String question = null;
		String correct = null;
		String wrong1 = null;
		String wrong2 = null;
		String wrong3 = null;
		int accepted = 0;
	}
	
	public class RankHistory{
		int userId = 0;
		int rankAtMinus7 = 0;
		int rankAtMinus6 = 0;
		int rankAtMinus5 = 0;
		int rankAtMinus4 = 0;
		int rankAtMinus3 = 0;
		int rankAtMinus2 = 0;
		int rankAtMinus1 = 0;
	}
	
}
	
	

