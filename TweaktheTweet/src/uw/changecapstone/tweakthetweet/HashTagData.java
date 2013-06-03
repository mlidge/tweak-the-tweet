package uw.changecapstone.tweakthetweet;
import android.os.Parcel;
import android.os.Parcelable;


public class HashTagData implements Parcelable{

	private String name;
	private String description;
	private String category;
	private String lattopright;
	private String longtopright;
	private String latbotleft;
	private String longbotleft;
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeStringArray(new String[] {this.name, this.description, 
				this.category, this.lattopright, this.longtopright, this.latbotleft, this.longbotleft});
	}
	
	public HashTagData(String name, String description, String category, String lattopright, String longtopright, String latbotleft, String longbotleft){
		this.name = name;
		this.description = description;
		this.category = category;
		this.lattopright = lattopright;
		this.longtopright = longtopright;
		this.latbotleft = latbotleft;
	}
	
	public HashTagData(Parcel in){
		String[] data = new String[7];
		
		in.readStringArray(data);
		this.name = data[0];
		this.description = data[1];
		this.category = data[2];
		this.lattopright = data[3];
		this.longtopright = data[4];
		this.latbotleft = data[5];
		this.longbotleft = data[6];
		
	}
	
	public static final Parcelable.Creator<HashTagData> CREATOR
    = new Parcelable.Creator<HashTagData>() {
		public HashTagData createFromParcel(Parcel in) {
		    return new HashTagData(in);
		}
		
		public HashTagData[] newArray(int size) {
		    return new HashTagData[size];
		}
	};

}