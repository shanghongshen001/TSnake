package model;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.RectF;

public class SnakeNode extends GameRect
{
	public SnakeNode(String Id, GameRectType gameRectType, Bitmap bitmap,RectF rectF) {
		super(Id, gameRectType, bitmap, rectF);
	}
	/**
	 * 画笔
	 */
	public Paint praint;
	/**
	 * 蛇节排序 默认是0请修改	
	 */
	public int SnakeOrderIndex = 0;
	
	/**
	 * 方向
	 */
	public Direction direction;
}
