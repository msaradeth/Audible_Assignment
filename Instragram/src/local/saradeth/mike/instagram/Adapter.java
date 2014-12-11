package local.saradeth.mike.instagram;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import local.saradeth.mike.instragram.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;



public class Adapter extends ArrayAdapter<String> {

	  private int resource;
	  private ImageCache imageCache;
	  private Context context;
	  private List<String> alImageUrl;
	  
	  public static class ViewHolder {
			public ImageView imageView;
			public String urlString;
			public Bitmap bitMap;
			public int position;
			
			public ViewHolder() {
				// TODO Auto-generated constructor stub
			}

		}	  

	  public Adapter(Context context, int resource, List<String> alImageUrl, ImageCache imageCache) {
		  super(context, resource, alImageUrl);
		  
		  this.context = context;
		  this.resource = resource;	
		  this.alImageUrl = alImageUrl;
		  this.imageCache = imageCache;
	  }

	  @Override
	  public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = new ViewHolder();  
		
	    //LinearLayout view = (LinearLayout) convertView;	    
	    if (convertView == null) {	 
	    	LayoutInflater li = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    	convertView = li.inflate(resource, parent, false);
	    
			//Bind View to data
			holder.imageView = (ImageView) convertView.findViewById(R.id.image_view1);
			
			//save holder 
			convertView.setTag(holder);
			
		}else {
			holder = (ViewHolder) convertView.getTag();
		}	    

		
		//Get product base on current position
	    String imageUrl = alImageUrl.get(position);
   		
   		//Save data to holder
   		holder.position = position;	
   		holder.urlString = imageUrl;	   
   		
    	//Draw from memory cache if exist else load image and cache it
   		imageCache.drawImage(holder); 	    	
   		
	    return convertView;
	  }	  
	  
	  
}
