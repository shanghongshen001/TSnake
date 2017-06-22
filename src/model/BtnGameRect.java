package model;

import event.TouchDown;
import event.TouchUp;
import android.graphics.Bitmap;
import android.graphics.RectF;

public class BtnGameRect extends GameRect{
	
	public boolean AutoHandleOnDown = true;
	public boolean DownState;
	Bitmap upB;
	Bitmap downB;
	
	
	public BtnGameRect(String Id, GameRectType gameRectType, Bitmap src1,Bitmap srcOnDown,RectF rectF,boolean AutoHandleOnDown) {
		super(Id, gameRectType, src1, rectF);
		this.upB = src1;
		this.downB = srcOnDown;
				
		if (AutoHandleOnDown&&srcOnDown!=null) {
			this.onDown = new TouchDown(){
				@Override
				public void OnDown() {
					bitmap = downB;
					DownState = true;
				}
			};
			this.onUp = new TouchUp(){
				@Override
				public void OnUp() {
					bitmap = upB;
					DownState = false;
				}
			};
		}
	}
}
