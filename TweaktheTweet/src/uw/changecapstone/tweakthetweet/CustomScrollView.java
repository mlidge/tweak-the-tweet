package uw.changecapstone.tweakthetweet;

import android.content.Context;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class CustomScrollView extends ScrollView{

	public CustomScrollView(Context context) {
		super(context);
	}

	// true if we can scroll (not locked)
    // false if we cannot scroll (locked)
	 private boolean mScrollable = true;

	 public void setIsScrollable(boolean scrollable) {
	     mScrollable = scrollable;
	 }
	 public boolean getIsScrollable(){
	     return mScrollable;
	 }

	 @Override
	 public boolean onTouchEvent(MotionEvent ev) {
	     switch (ev.getAction()) {
	         case MotionEvent.ACTION_DOWN:
	             // if we can scroll pass the event to the superclass
	             if (mScrollable) return super.onTouchEvent(ev);
	             // only continue to handle the touch event if scrolling enabled
	             return mScrollable; // mScrollable is always false at this point
	         default:
	            return super.onTouchEvent(ev);
	     }
	 }
}
