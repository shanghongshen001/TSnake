package model;

import event.TouchClick;
import event.TouchDown;
import event.TouchUp;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.graphics.RectF;

public class GameRect {
	public Bitmap bitmap;
	public RectF rectF;
	public GameRectType gameRectType;
	public String Id;
	public boolean ShowState = true;
	
	public TouchClick onClick;
	public TouchDown onDown;
	public TouchUp onUp;
	
	/**
	 * 
	 * @param Id 
	 * @param gameRectType
	 * @param bitmap
	 * @param rectF
	 */
	public GameRect(String Id,GameRectType gameRectType,Bitmap bitmap,RectF rectF){
		this.Id = Id;
		this.bitmap = bitmap;
		this.rectF = rectF;
		this.gameRectType = gameRectType;
	}
}
