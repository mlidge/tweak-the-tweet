package uw.changecapstone.tweakthetweet;

import com.google.android.gms.maps.MapFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class CustomMapFragment extends MapFragment{
	 
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState)
    {
         View view = super.onCreateView(inflater, viewGroup, savedInstanceState);

         view.setOnTouchListener(new View.OnTouchListener()
         {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int action = event.getAction();

                if(action == MotionEvent.ACTION_UP){
                	System.out.println("CREATING VIEWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
                   v.getParent().requestDisallowInterceptTouchEvent(false);
                }else if(action == MotionEvent.ACTION_DOWN){
                   v.getParent().requestDisallowInterceptTouchEvent(true);
                }
                return true;
			}
			

         });

         return view;
    }

}
