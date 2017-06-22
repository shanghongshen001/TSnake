package ui;

import event.CheckEvent;
import bill.GameManager;
import bill.GameViewDrawThread;
import android.app.Notification.Action;
import android.content.Context;
import android.graphics.Canvas;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView {

	GameManager gameManager;
	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		SurfaceHolder holder = this.getHolder();
		gameManager = new GameManager(holder,this.getContext());
		
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getActionMasked();

		//down
		if (action==event.ACTION_DOWN) {
			CheckEvent.getInstance().CheckDown(event.getX(),event.getY());
		}
		//up
		else if (action==event.ACTION_UP) {
			CheckEvent.getInstance().CheckUp(event.getX(),event.getY());
		}
		
		return true;
	}
	
}
