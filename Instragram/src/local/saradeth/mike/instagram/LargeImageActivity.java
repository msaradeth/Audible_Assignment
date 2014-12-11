package local.saradeth.mike.instagram;

import local.saradeth.mike.instagram.Adapter.ViewHolder;
import local.saradeth.mike.instragram.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

/*
Programmer:  Mike Saradeth
Date: 12/10/2014
*/

public class LargeImageActivity extends Activity {
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Going full screen
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);   
        
        setContentView(R.layout.large_image);

		Bundle args = getIntent().getExtras();
		String imageUrl = args.getString("imageUrl");
		
    	ViewHolder holder = new ViewHolder();
    	holder.urlString = imageUrl;		
		holder.imageView = (ImageView)findViewById(R.id.large_image);
		
		ImageCache imageDraw = new ImageCache();
		imageDraw.drawImage(holder);
				
	}
	
		
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		
	}
}
