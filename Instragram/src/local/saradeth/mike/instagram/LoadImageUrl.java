package local.saradeth.mike.instagram;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

/*
Programmer:  Mike Saradeth
Date: 12/10/2014
*/
public class LoadImageUrl extends AsyncTask<String, Integer, List<String>>{
	private Activity activity;
	private ProgressDialog progDialog;	
	List<String> alImageUrl = new ArrayList<String>();
	
	
	//Constructor 
	public LoadImageUrl(Activity activity, List<String> alImageUrl) {
		this.activity = activity;
		this.alImageUrl = alImageUrl;
	}

	
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		String title = "Load Image URL";
		String message = "Loading image URL from Instagram ...";
		progDialog = ProgressDialog.show(this.activity, title, message, true, false);

	}

	
	@Override
	protected List<String> doInBackground(String... params) {
		String urlString = "https://api.instagram.com/v1/tags/selfie/media/recent?client_id=b9e0d20895a74ecc87cf33911032865c";        
        String jsonString;
        
        try {
            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
            
            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(urlString));
            
            if(httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
            	Log.d("responseCode", "responseCode != HttpStatus.SC_OK " + httpResponse.getStatusLine().getStatusCode());
            	return alImageUrl;
            }else {
            	jsonString = EntityUtils.toString(httpResponse.getEntity());
            }                           
        }catch (IOException ioException) {
        	Log.d("ioException", "ioException EntityUtils.toString(httpResponse.getEntity() " +ioException.toString());             
            return alImageUrl;
        }

        
        //Parse JSON String and load array list
        try {
        	JSONObject jsonObject;
			int counter = 0;
			alImageUrl.clear();
        	
			JSONObject jsonObjects = new JSONObject(jsonString);
			JSONArray jsonArray = jsonObjects.getJSONArray("data");
			for(int ii=0; ii<jsonArray.length(); ii++) {
				jsonObject = jsonArray.getJSONObject(ii);
				
				if ( (counter%3==0)) {	//big, small, small, big, small, small, repeat		
					alImageUrl.add(jsonObject.getJSONObject("images").getJSONObject("standard_resolution").getString("url"));										
				}else {
					alImageUrl.add(jsonObject.getJSONObject("images").getJSONObject("low_resolution").getString("url"));										
				}
				counter = counter +1;								
			}        
        } catch (JSONException e) {
			e.printStackTrace();
		}
        
        return alImageUrl;
	    
	}
	
	@Override
	protected void onPostExecute(List<String> result) {
	    System.out.println("result.size() =" + result.size());	    
	    if (result.isEmpty()) {
	    	System.out.println("result is empty string");	    	
	    }
		
    	progDialog.dismiss();
    	((MainActivity) activity).setListView(result);           
	}	


}
