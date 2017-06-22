package bill;

import android.content.Context;
import android.view.SurfaceHolder;

public class GameManager implements SurfaceHolder.Callback {

	private GameViewDrawThread gbThread;
	private SurfaceHolder holder;
	Context context;
	public GameManager(SurfaceHolder holder, Context context) {
		this.holder = holder;
		this.context = context;
		holder.addCallback(this);
	}

	/**
	 * 开始游戏
	 */
	public void StartGame() {
		
	}

	/**
	 * 暂停游戏
	 */
	public void PauseGame() {

	}

	/**
	 * 结束游戏
	 */
	public void EndGame() {

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		gbThread = new GameViewDrawThread(holder, context);// 创建一个绘图线程
		gbThread.start();
	}
	
	

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		try {
			gbThread.stop();
		} catch (Exception e) {
		}

	}
}
