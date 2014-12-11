package local.saradeth.mike.instagram;


import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import local.saradeth.mike.instagram.Adapter.ViewHolder;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
/*
Programmer:  Mike Saradeth
Date: 12/10/2014
*/

public class ImageCache {	
	private Map<String, Bitmap> imageCache = new HashMap<String, Bitmap>();

    
    public void drawImage (ViewHolder holder) {
        if (imageCache.get(holder.urlString) != null) {   
        	//if image Bitmap exist in memory use it.
        	holder.imageView.setImageBitmap(imageCache.get(holder.urlString));
        }else {
        	//Get image Bitmap from imageUrl
            new ImageTask().execute(holder);
        }
    }
            
    
    private class ImageTask extends AsyncTask<Adapter.ViewHolder, Void, ViewHolder> {

		@Override
		protected ViewHolder doInBackground(ViewHolder... params) {
			//load image directly
			ViewHolder holder = params[0];
			
			 //load image directly
			try {
				URL imageURL = new URL(holder.urlString);
				holder.bitMap = BitmapFactory.decodeStream(imageURL.openStream());
			} catch (IOException e) {
				Log.e("error", "Downloading Image Failed");
			}		
						
			return holder;
		}
		
		@Override
		protected void onPostExecute(ViewHolder result) { 
			super.onPostExecute(result);
			
			if (result.bitMap != null){
				result.imageView.setImageBitmap(result.bitMap);	
				synchronized (imageCache) {																	
					imageCache.put(result.urlString, result.bitMap);
				}			
			}		
		}		        
    }
    
	
    public Map<String, Bitmap> getImageCache() {
		return imageCache;
	}


	public void setImageCache(Map<String, Bitmap> imageCache) {
		this.imageCache = imageCache;
	}


    
    

}
