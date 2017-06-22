package bill;

import helper.ScreenHelper;

import com.shanghongshen.tsnake.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;
import android.view.SurfaceHolder;

public class GameViewDrawThread extends Thread {

	SurfaceHolder holder;
	Context context;
	CanvasState canvasState;
	

	public GameViewDrawThread(SurfaceHolder holder, Context context) {
		this.holder = holder;
		this.context = context;
		canvasState = CanvasState.getInstance();
		canvasState.context = context;
		canvasState.holder = holder;
	}

	@Override
	public void run() {
		while(true){
			Canvas cv = null;
			try {
				synchronized (holder) {
					cv = holder.lockCanvas();// 锁定画布，一般在锁定后就可以通过其返回的画布对象Canvas，在其上面画图等操作了。
					canvasState.Draw(cv);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (cv != null) {
					holder.unlockCanvasAndPost(cv);// 结束锁定画图，并提交改变。
				}
			}
		}
	}	

}
