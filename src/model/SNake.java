package model;

import helper.Timer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.TimerTask;

import android.graphics.Paint;
import android.graphics.RectF;

public class SNake {
	public ArrayList<SnakeNode> SnakeNodes = new ArrayList<SnakeNode>();
	public HashMap<String, GameRect> Rects;
	public SnakeNode HeadNode;

	/*
	 * 速度
	 */
	int Speed = 20;
	public boolean SnakeState = true;
	GameRect mapRect;
	int eachMap;
	int mapOther;
	boolean AutoDirection;
	public boolean IsPause;//暂停
	public boolean IsLive;//是否活着
	Timer timer = new Timer();

	public SNake(GameRect mapRect, int eachMap, int mapOther,
			SnakeNode HeadNode, HashMap<String, GameRect> Rects) {
		this.mapRect = mapRect;
		this.eachMap = eachMap;
		this.mapOther = mapOther;
		this.HeadNode = HeadNode;
		this.Rects = Rects;
		timer = new Timer();
		IsLive = true;
		timer.SetTimerTaskAndTime(task, Speed);
		timer.start();
	}

	TimerTask task = new TimerTask() {
		public void run() {
			if (HeadNode != null&&!IsPause) {
				if (AutoDirection) {
					Direction[] dirs = Direction.values();
					Direction fan = null;
					switch (HeadNode.direction) {
					case Top:
						fan = Direction.Down;
						break;
					case Down:
						fan = Direction.Top;
						break;
					case Left:
						fan = Direction.Right;
						break;
					case Right:
						fan = Direction.Left;
						break;
					}
					while (true) {
						int a = (int) (Math.random() * 3);
						if (a == fan.ordinal()) {
							continue;
						}
						Run(dirs[a]);
						break;
					}
				} else {
					Run(HeadNode.direction);
				}

			}
		}
	};

	public void Stop() {
		try {
			SnakeState = false;
			timer.setStop();
		} catch (Exception e) {
		}
	}

	public void Start() {
		
	}
	/**
	 * 是否暂停
	 */
	public void IsPause(boolean isp){
		IsPause = isp;
	}

	/**
	 * 设置速度
	 * @param speed
	 */
	public void SetSpeed(int speed) {
		this.Speed = speed;
		timer.SetTimerTaskAndTime(task, Speed);
	}

	/**
	 * 设置智能转向
	 */
	public void setAutoDirection(boolean AutoDirection) {
		this.AutoDirection = AutoDirection;
	}

	/**
	 * 前进
	 */
	public synchronized void Run(Direction direction) {
		if (!SnakeState) {
			try {
				timer.stop();
			} catch (Exception e) {
			}
			return;
		}
		this.SnakeState = false;
		for (int i = SnakeNodes.size() - 1; i > 0; i--) {
			SnakeNodes.get(i).rectF.bottom = SnakeNodes.get(i - 1).rectF.bottom;
			SnakeNodes.get(i).rectF.top = SnakeNodes.get(i - 1).rectF.top;
			SnakeNodes.get(i).rectF.left = SnakeNodes.get(i - 1).rectF.left;
			SnakeNodes.get(i).rectF.right = SnakeNodes.get(i - 1).rectF.right;
			SnakeNodes.get(i).direction = SnakeNodes.get(i - 1).direction;
		}
		SnakeNode head = SnakeNodes.get(0);
		head.direction = direction;

		switch (direction) {
		case Top:
			head.Id = GameRectId.snakeHeadTop;
			head.bitmap = Rects.get(GameRectId.snakeHeadTop).bitmap;
			head.rectF.top -= eachMap;
			head.rectF.bottom -= eachMap;
			if (head.rectF.top < mapRect.rectF.top) {
				head.rectF.top += eachMap * 18;
				head.rectF.bottom += eachMap * 18;
			}
			break;
		case Right:
			head.Id = GameRectId.snakeHeadRight;
			head.bitmap = Rects.get(GameRectId.snakeHeadRight).bitmap;
			head.rectF.left += eachMap;
			head.rectF.right += eachMap;
			if (head.rectF.right > mapRect.rectF.right) {
				head.rectF.left -= eachMap * 18;
				head.rectF.right -= eachMap * 18;
			}
			break;
		case Down:
			head.Id = GameRectId.snakeHeadDown;
			head.bitmap = Rects.get(GameRectId.snakeHeadDown).bitmap;
			head.rectF.top += eachMap;
			head.rectF.bottom += eachMap;
			if (head.rectF.bottom > mapRect.rectF.bottom) {
				head.rectF.top -= eachMap * 18;
				head.rectF.bottom -= eachMap * 18;
			}
			break;
		case Left:
			head.Id = GameRectId.snakeHeadLeft;
			head.bitmap = Rects.get(GameRectId.snakeHeadLeft).bitmap;
			head.rectF.left -= eachMap;
			head.rectF.right -= eachMap;
			if (head.rectF.left < mapRect.rectF.left) {
				head.rectF.left += eachMap * 18;
				head.rectF.right += eachMap * 18;
			}
			break;
		}
		this.SnakeState = true;
	}
}
