package local.saradeth.mike.instagram;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import local.saradeth.mike.instragram.R;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/*
Programmer:  Mike Saradeth
Date: 12/10/2014
Data Source: https://api.instagram.com/v1/tags/selfie/media/recent?client_id=b9e0d20895a74ecc87cf33911032865c

Description:  Using the Instagram API, create an Android app that displays photos tagged with 
hashtag #selfie and arranges them using the pattern: big, small, small, big, small small, and repeat. 
Then implement one of the following features: Tap to enlarge, Drag and drop reordering, or Infinite scrolling. 
Scrolling should be smooth so performance is essential. Be creative and have fun!
*/
public class MainActivity extends Activity {	
	private List<String> alImageUrl = new ArrayList<String>();
	private ImageCache imageCache = new ImageCache();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_list_view);

		getActionBar().hide();	//hide actionbar
		
		//Load images from fragment cache if exist on Orientation change
		RetainFragment retainFragment = RetainFragment.findOrCreateRetainFragment(getFragmentManager());		
		if (retainFragment.mRetainedCache == null) {
	        retainFragment.mRetainedCache = imageCache.getImageCache();	
		}else {
			imageCache.setImageCache(retainFragment.mRetainedCache);
		}		
		

		//Load imageUrl from fragment cache if exist on Orientation change
		if (retainFragment.mRetainedImageUrl == null) {	
	        retainFragment.mRetainedImageUrl = this.alImageUrl;
			
			//Load ImageUrl from Instagram
			LoadImageUrl loadImageUrl = new LoadImageUrl(this, this.alImageUrl);		
	        try {
	        	loadImageUrl.execute();
	        }
	        catch (Exception e){
	        	loadImageUrl.cancel(true);
	            alert("Problem loading from Instagram");
	        }		        
		}else {	//get ImageUrl from memory
			this.alImageUrl = retainFragment.mRetainedImageUrl;
			System.out.println("retainFragment.mRetainedImageUrl != null alImageUrl.size() =" + alImageUrl.size());
			this.setListView(alImageUrl);
		}	
				

	}

	

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();		
	
	}
	
	

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
	}
	

	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		//imageCache.clearCache();
		System.gc();
	}


	//Toast a given message
    public void alert (String msg){
        Toast.makeText(this.getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }
	
    

    //setup and create list view after data is loaded
	public void setListView(List<String> myImageUrl) {
		this.alImageUrl = myImageUrl;
		
        //Bind data to view
		Adapter adapter = new Adapter(this, R.layout.image_list_row, alImageUrl, imageCache);
		ListView lvImage = (ListView) findViewById(R.id.image_list_view);	
		lvImage.setAdapter(adapter);
		
		
		lvImage.setOnItemClickListener(new OnItemClickListener() {
			@Override
			// Click event for single list row
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	
				String imageUrl = alImageUrl.get( (int) id );	//id is 0 base array
				Intent intent = new Intent(getApplicationContext(), LargeImageActivity.class);
				intent.putExtra("imageUrl", imageUrl);

				startActivity(intent);
			
			}
			
		});		
    }
	
     
  


static class RetainFragment extends Fragment {
    private static final String TAG = "RetainFragment";
    //public LruCache<String, Bitmap> mRetainedCache;
    public Map<String, Bitmap> mRetainedCache; 
    public List<String> mRetainedImageUrl; 
   

    public RetainFragment() {}

    public static RetainFragment findOrCreateRetainFragment(FragmentManager fm) {
        RetainFragment fragment = (RetainFragment) fm.findFragmentByTag(TAG);
        if (fragment == null) {
            fragment = new RetainFragment();
            fm.beginTransaction().add(fragment, TAG).commit();
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

}
		
}
