package bill;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EventListener;
import java.util.EventListenerProxy;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import model.BtnGameRect;
import model.Direction;
import model.GameRect;
import model.GameRectId;
import model.GameRectType;
import model.SNake;
import model.SnakeNode;

import helper.CollisionHelper;
import helper.ScreenHelper;

import com.shanghongshen.tsnake.R;

import event.CheckEvent;
import event.TouchClick;

import android.R.id;
import android.R.integer;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;
import android.view.SurfaceHolder;
import android.widget.Toast;

public class CanvasState {

	public HashMap<String, GameRect> GameRects = new HashMap<String, GameRect>();
	public ArrayList<BtnGameRect> btns = new ArrayList<BtnGameRect>();
	public ArrayList<SNake> snakes = new ArrayList<SNake>();
	public ArrayList<GameRect> foods = new ArrayList<GameRect>();
	public SNake mainSnake;

	private CanvasState() {
	}

	public static CanvasState getInstance() {
		if (thisObj == null) {
			thisObj = new CanvasState();
		}
		return thisObj;
	}

	static CanvasState thisObj;
	int FrameRate = 15;
	SurfaceHolder holder;
	Context context;
	int ScreenWidth;
	int ScreenHeight;
	int eachWidth;
	int eachHeight;
	int eachMap;
	int mapOther;
	boolean loadGameRect;
	int maxFood = 5;
	int maxSnakeSize = 5;
	int mapWidth = 18;
	int mapHeight = 18;
	CollisionHelper collisionHelper = new CollisionHelper();

	public void Restart() {
		thisObj = new CanvasState();
	}

	/**
	 * 注册所有的矩阵块
	 */
	void loadGameRect() {

		ScreenWidth = ScreenHelper.getScreenWidth(context);
		ScreenHeight = ScreenHelper.getScreenHeight(context);
		eachHeight = ScreenHeight / 12;
		eachWidth = ScreenWidth / 6;

		// 背景
		GameRect gameRect = new GameRect(GameRectId.background,
				GameRectType.background, BitmapFactory.decodeResource(
						context.getResources(), R.drawable.background),
				new RectF(0, 0, ScreenWidth, ScreenHeight));
		GameRects.put(GameRectId.background, gameRect);

		// 地图
		gameRect = new GameRect(GameRectId.map, GameRectType.map,
				BitmapFactory.decodeResource(context.getResources(),
						R.drawable.map), new RectF(50, eachHeight * 2,
						ScreenWidth - 50,
						(float) (ScreenWidth - 100 + eachHeight * 2)));
		GameRects.put(GameRectId.map, gameRect);
		eachMap = (int) ((int) (ScreenWidth - 100) / 18.5);
		mapOther = eachMap / 4;

		// 蓝色的食物
		gameRect = new GameRect(GameRectId.foodBlue, GameRectType.food,
				BitmapFactory.decodeResource(context.getResources(),
						R.drawable.blue), new RectF(0, 0, 0, 0));
		GameRects.put(GameRectId.foodBlue, gameRect);
		// 红色的食物
		gameRect = new GameRect(GameRectId.foodRed, GameRectType.food,
				BitmapFactory.decodeResource(context.getResources(),
						R.drawable.red), new RectF(0, 0, 0, 0));
		GameRects.put(GameRectId.foodRed, gameRect);
		// 绿色的食物
		gameRect = new GameRect(GameRectId.foodGreen, GameRectType.food,
				BitmapFactory.decodeResource(context.getResources(),
						R.drawable.green), new RectF(0, 0, 0, 0));
		GameRects.put(GameRectId.foodGreen, gameRect);

		// 按钮背景
		gameRect = new GameRect(GameRectId.btnFameGreen,
				GameRectType.background, BitmapFactory.decodeResource(
						context.getResources(), R.drawable.frame_green),
				new RectF(0, eachHeight * 9, ScreenWidth, ScreenHeight));
		GameRects.put(GameRectId.btnFameGreen, gameRect);

		// 按钮右
		BtnGameRect gameRectBtn = new BtnGameRect(GameRectId.btnRight,
				GameRectType.button, BitmapFactory.decodeResource(
						context.getResources(), R.drawable.right),
				BitmapFactory.decodeResource(context.getResources(),
						R.drawable.rightblue),
				new RectF((float) (eachWidth * 3.5),
						(float) (eachHeight * 9.8), (float) (eachWidth * 5.5),
						(float) (eachHeight * 11.2)), true);
		gameRectBtn.onClick = new TouchClick() {
			@Override
			public void OnClick() {
				RightClick();
			}
		};
		GameRects.put(GameRectId.btnRight, gameRectBtn);
		btns.add(gameRectBtn);
		CheckEvent.getInstance().RegisterEventGameRects.add(gameRectBtn);
		// 按钮左
		gameRectBtn = new BtnGameRect(GameRectId.btnLeft, GameRectType.button,
				BitmapFactory.decodeResource(context.getResources(),
						R.drawable.left), BitmapFactory.decodeResource(
						context.getResources(), R.drawable.leftblue),
				new RectF((float) (eachWidth * 0.5),
						(float) (eachHeight * 9.8), (float) (eachWidth * 2.5),
						(float) (eachHeight * 11.2)), true);
		gameRectBtn.onClick = new TouchClick() {
			@Override
			public void OnClick() {
				LeftClick();
			}
		};
		GameRects.put(GameRectId.btnLeft, gameRectBtn);
		btns.add(gameRectBtn);
		CheckEvent.getInstance().RegisterEventGameRects.add(gameRectBtn);
		// 按钮上
		gameRectBtn = new BtnGameRect(GameRectId.btnUp, GameRectType.button,
				BitmapFactory.decodeResource(context.getResources(),
						R.drawable.up), BitmapFactory.decodeResource(
						context.getResources(), R.drawable.upblue), new RectF(
						(float) (eachWidth * 2), (float) (eachHeight * 9.2),
						(float) (eachWidth * 4), (float) (eachHeight * 10.5)),
				true);
		gameRectBtn.onClick = new TouchClick() {
			@Override
			public void OnClick() {
				UpClick();
			}
		};
		GameRects.put(GameRectId.btnUp, gameRectBtn);
		btns.add(gameRectBtn);
		CheckEvent.getInstance().RegisterEventGameRects.add(gameRectBtn);
		// 按钮下
		gameRectBtn = new BtnGameRect(GameRectId.btnDown, GameRectType.button,
				BitmapFactory.decodeResource(context.getResources(),
						R.drawable.down), BitmapFactory.decodeResource(
						context.getResources(), R.drawable.downblue),
				new RectF((float) (eachWidth * 2), (float) (eachHeight * 10.5),
						(float) (eachWidth * 4), (float) (eachHeight * 11.8)),
				true);
		gameRectBtn.onClick = new TouchClick() {
			@Override
			public void OnClick() {
				DownClick();
			}
		};
		GameRects.put(GameRectId.btnDown, gameRectBtn);
		btns.add(gameRectBtn);
		CheckEvent.getInstance().RegisterEventGameRects.add(gameRectBtn);
		// 按钮on
		gameRectBtn = new BtnGameRect(GameRectId.btnOn, GameRectType.button,
				BitmapFactory.decodeResource(context.getResources(),
						R.drawable.on_blue), BitmapFactory.decodeResource(
						context.getResources(), R.drawable.on_red), new RectF(
						(float) (eachWidth * 4), (float) (eachHeight * 0.5),
						(float) (eachWidth * 5.7), (float) (eachHeight * 1.5)),
				true);
		gameRectBtn.onClick = new TouchClick() {
			@Override
			public void OnClick() {
				BtnGameRect btnOn = (BtnGameRect) GameRects
						.get(GameRectId.btnOn);
				BtnGameRect btnOff = (BtnGameRect) GameRects
						.get(GameRectId.btnOff);
				btnOn.ShowState = false;
				btnOff.ShowState = true;
				setIsSuspend(false);
			}
		};
		GameRects.put(GameRectId.btnOn, gameRectBtn);
		btns.add(gameRectBtn);
		CheckEvent.getInstance().RegisterEventGameRects.add(gameRectBtn);
		// 按钮off
		gameRectBtn = new BtnGameRect(GameRectId.btnOff, GameRectType.button,
				BitmapFactory.decodeResource(context.getResources(),
						R.drawable.off_blue), BitmapFactory.decodeResource(
						context.getResources(), R.drawable.off_red), new RectF(
						(float) (eachWidth * 4), (float) (eachHeight * 0.5),
						(float) (eachWidth * 5.7), (float) (eachHeight * 1.5)),
				true);
		gameRectBtn.onClick = new TouchClick() {
			@Override
			public void OnClick() {
				BtnGameRect btnOn = (BtnGameRect) GameRects
						.get(GameRectId.btnOn);
				BtnGameRect btnOff = (BtnGameRect) GameRects
						.get(GameRectId.btnOff);
				btnOn.ShowState = true;
				btnOff.ShowState = false;
				setIsSuspend(true);

			}
		};
		GameRects.put(GameRectId.btnOff, gameRectBtn);
		gameRectBtn.ShowState = false;
		btns.add(gameRectBtn);
		CheckEvent.getInstance().RegisterEventGameRects.add(gameRectBtn);

		// 加载snake 资源
		SnakeNode snakeNode = new SnakeNode(GameRectId.snakeHeadTop,
				GameRectType.snakeNode, BitmapFactory.decodeResource(
						context.getResources(), R.drawable.head_n), new RectF(
						0, 0, 0, 0));
		GameRects.put(GameRectId.snakeHeadTop, snakeNode);
		snakeNode = new SnakeNode(GameRectId.snakeHeadDown,
				GameRectType.snakeNode, BitmapFactory.decodeResource(
						context.getResources(), R.drawable.head_s), new RectF(
						0, 0, 0, 0));
		GameRects.put(GameRectId.snakeHeadDown, snakeNode);
		snakeNode = new SnakeNode(GameRectId.snakeHeadLeft,
				GameRectType.snakeNode, BitmapFactory.decodeResource(
						context.getResources(), R.drawable.head_w), new RectF(
						0, 0, 0, 0));
		GameRects.put(GameRectId.snakeHeadLeft, snakeNode);
		snakeNode = new SnakeNode(GameRectId.snakeHeadRight,
				GameRectType.snakeNode, BitmapFactory.decodeResource(
						context.getResources(), R.drawable.head_e), new RectF(
						0, 0, 0, 0));
		GameRects.put(GameRectId.snakeHeadRight, snakeNode);
		snakeNode = new SnakeNode(GameRectId.snakeNode, GameRectType.snakeNode,
				BitmapFactory.decodeResource(context.getResources(),
						R.drawable.snake), new RectF(0, 0, 0, 0));
		GameRects.put(GameRectId.snakeNode, snakeNode);

		mainSnake = getSnake(3, GameRectId.snakeHeadLeft);
		mainSnake.SetSpeed(1000);
		mainSnake.IsPause(true);
		snakes.add(mainSnake);

		// OpenAutoCreateSnake();
		OpenAutoCreateFood();
	}

	public void OpenAutoCreateSnake() {
		if (this.snakes.size() < this.maxSnakeSize) {
			int c = this.maxSnakeSize - this.snakes.size() + 1;
			for (int i = 0; i < c; i++) {
				SNake sk = getSnake(4, GameRectId.snakeHeadRight);
				sk.SetSpeed(2000);
				sk.setAutoDirection(true);
				snakes.add(sk);
			}
		}
	}

	public void OpenAutoCreateFood() {

		int needSize = maxFood - foods.size();
		LinkedList bankMap = GetAllBankIndex();
		GameRect mapRect = GameRects.get(GameRectId.map);

		for (int i = 0; i < needSize; i++) {
			int ran = (int) (Math.random() * 3);
			int ranLocation = (int) bankMap.get((int) (Math.random() * bankMap
					.size()));

			RectF tempRect = new RectF();
			tempRect.top = mapRect.rectF.top + this.mapOther + ranLocation
					/ this.mapWidth * this.eachMap;
			tempRect.bottom = tempRect.top + this.eachMap;
			tempRect.left = mapRect.rectF.left + this.mapOther + ranLocation
					% this.mapWidth * this.eachMap;
			tempRect.right = tempRect.left + this.eachMap;
			switch (ran) {
			case 0:
				GameRect old1 = GameRects.get(GameRectId.foodRed);
				GameRect gr1 = new GameRect(old1.Id, old1.gameRectType,
						old1.bitmap, tempRect);
				foods.add(gr1);
				break;
			case 1:
				GameRect old2 = GameRects.get(GameRectId.foodBlue);
				GameRect gr2 = new GameRect(old2.Id, old2.gameRectType,
						old2.bitmap, tempRect);
				foods.add(gr2);
				break;
			case 2:
				GameRect old3 = GameRects.get(GameRectId.foodGreen);
				GameRect gr3 = new GameRect(old3.Id, old3.gameRectType,
						old3.bitmap, tempRect);
				foods.add(gr3);
				break;
			default:
				OpenAutoCreateFood();
				break;
			}
		}

	}

	/**
	 * 获取所有的空白场地的索引
	 */
	public LinkedList GetAllBankIndex() {
		LinkedList rst = new LinkedList();
		int mapAll = mapWidth * mapHeight;
		for (int i = 0; i < mapAll; i++) {
			rst.add(i);
		}
		GameRect mapRect = GameRects.get(GameRectId.map);
		// 排除所有的snake
		for (SNake snake : snakes) {
			if (snake.SnakeState) {
				for (SnakeNode node : snake.SnakeNodes) {
					OutRect(mapRect.rectF, node.rectF, rst);
				}
			}
		}
		// 排除所有的食物
		for (GameRect food : foods) {
			OutRect(mapRect.rectF, food.rectF, rst);
		}
		return rst;
	}

	void OutRect(RectF mapRect, RectF neetOutRect, LinkedList rst) {
		int left = (int) ((neetOutRect.left - mapRect.left - this.mapOther) / this.eachMap);
		int top = (int) ((neetOutRect.top - mapRect.top - this.mapOther) / this.eachMap);
		int index = rst.indexOf(left + top * this.mapWidth - 1);
		if (index == -1) {
			return;
		}
		rst.remove(index);
	}

	void CheckDie(SNake snake) {
		SnakeNode head = snake.HeadNode;
		for (int i = 1; i < snake.SnakeNodes.size(); i++) {
			SnakeNode node = snake.SnakeNodes.get(i);
			if (head.rectF.left == node.rectF.left&&head.rectF.top == node.rectF.top) {
				snake.IsLive = false;
				break;
			}
		}
	}

	/**
	 * 碰撞检测
	 */
	public void CheckCollision() {
		for (int i = 0; i < snakes.size(); i++) {
			SNake item = snakes.get(i);
			CheckDie(item);
			// 蛇和蛇碰撞
			// if (item!=mainSnake&&mainSnake.SnakeState) {
			// SnakeNode mainSnakeHead = mainSnake.SnakeNodes.get(0);
			// for (int j = 0; j < item.SnakeNodes.size(); j++) {
			// boolean rst =
			// collisionHelper.IsRectCollision(mainSnakeHead.rectF,item.SnakeNodes.get(j).rectF);
			// if (rst) {
			// //mainSnake.Stop();
			// OpenAutoCreateSnake();
			// return;
			// }
			// }
			// }
			// 蛇和食物碰撞
			int foodSize = foods.size();
			for (int j = 0; j < foodSize; j++) {
				if (foods.size() == j) {
					break;
				}
				GameRect sHead = item.SnakeNodes.get(0);
				GameRect nowFood = foods.get(j);
				boolean rst = collisionHelper.IsRectCollision(sHead.rectF,
						nowFood.rectF);
				if (rst) {
					// 移除食物
					foods.remove(j);
					// 增加蛇节
					SnakeNode last = item.SnakeNodes
							.get(item.SnakeNodes.size() - 1);
					RectF newRf = new RectF(last.rectF.left, last.rectF.top,
							last.rectF.right, last.rectF.bottom);
					switch (last.direction) {
					case Top:
						newRf.top += eachMap;
						newRf.bottom += eachMap;
						break;
					case Down:
						newRf.top -= eachMap;
						newRf.bottom -= eachMap;
						break;
					case Left:
						newRf.left += eachMap;
						newRf.right += eachMap;
						break;
					case Right:
						newRf.left -= eachMap;
						newRf.right -= eachMap;
						break;
					}
					SnakeNode sn = new SnakeNode(last.Id, last.gameRectType,
							last.bitmap, newRf);
					item.SnakeNodes.add(sn);

					OpenAutoCreateFood();
				}
			}

		}
	}

	/**
	 * 暂停恢复
	 * 
	 * @param suspend
	 */
	public void setIsSuspend(boolean suspend) {
		if (suspend) {
			mainSnake.IsPause(true);
		} else {
			mainSnake.IsPause(false);
		}
	}

	void Draw(Canvas cv) {
		if (!loadGameRect) {
			loadGameRect();
			loadGameRect = true;
		}
		// 画背景
		DrawBackground(cv);
		// 碰撞检测
		CheckCollision();
		CheckGameState();
		// 画蛇
		DrawSnake(cv);
		// 画食物
		DrawFood(cv);
	}
	
	public void CheckGameState(){
		if (!this.mainSnake.IsLive) {
			//游戏结束
			foods.clear();
			snakes.clear();
			mainSnake = getSnake(3, GameRectId.snakeHeadLeft);
			mainSnake.SetSpeed(1000);
			mainSnake.IsPause(true);
			snakes.add(mainSnake);
			OpenAutoCreateFood();
			BtnGameRect btnOn = (BtnGameRect) GameRects
					.get(GameRectId.btnOn);
			BtnGameRect btnOff = (BtnGameRect) GameRects
					.get(GameRectId.btnOff);
			btnOn.ShowState = true;
			btnOff.ShowState = false;
			setIsSuspend(true);
		}
	}

	private void DrawFood(Canvas cv) {
		for (GameRect item : foods) {
			cv.drawBitmap(item.bitmap, null, item.rectF, null);
		}
	}

	private void DrawSnake(Canvas cv) {
		for (SNake temp : snakes) {
			if (temp.SnakeState) {
				for (SnakeNode item : temp.SnakeNodes) {
					if (item.ShowState) {
						cv.drawBitmap(item.bitmap, null, item.rectF, null);
					}
				}
			}
		}
	}

	private void DrawBackground(Canvas cv) {
		cv.drawColor(Color.WHITE);
		DrawGameRect(GameRectId.background, cv);
		DrawGameRect(GameRectId.map, cv);
		DrawGameRect(GameRectId.btnFameGreen, cv);
		DrawGameRect(GameRectId.btnLeft, cv);
		DrawGameRect(GameRectId.btnRight, cv);
		DrawGameRect(GameRectId.btnUp, cv);
		DrawGameRect(GameRectId.btnDown, cv);
		DrawGameRect(GameRectId.btnOn, cv);
		DrawGameRect(GameRectId.btnOff, cv);
	}

	void DrawGameRect(String id, Canvas cv) {
		GameRect rect = GameRects.get(id);
		if (rect != null && rect.ShowState) {
			cv.drawBitmap(rect.bitmap, null, rect.rectF, null);
		}
	}

	/*
	 * 获取一条蛇
	 */
	public SNake getSnake(int size, String headId) {
		SnakeNode head = (SnakeNode) this.GameRects.get(headId);
		SnakeNode node = (SnakeNode) this.GameRects.get(GameRectId.snakeNode);
		SnakeNode headNode = new SnakeNode(head.Id, head.gameRectType,
				head.bitmap, getRectFRandom(null, headId));
		SetDirection(headId, headNode);
		SNake sk = new SNake(this.GameRects.get(GameRectId.map), eachMap,
				mapOther, headNode, GameRects);
		if (head != null) {
			sk.SnakeNodes.add(headNode);
		}
		for (int i = 0; i < size - 1; i++) {
			if (node != null) {
				RectF tempR = getRectFRandom(sk.SnakeNodes.get(i).rectF, headId);
				SnakeNode snakeNode = new SnakeNode(GameRectId.snakeNode,
						GameRectType.snakeNode, node.bitmap, tempR);
				SetDirection(headId, snakeNode);
				sk.SnakeNodes.add(snakeNode);
			}
		}
		return sk;
	}

	void SetDirection(String headId, SnakeNode node) {
		if (node == null)
			return;
		switch (headId) {
		// 上
		case GameRectId.snakeHeadTop:
			node.direction = Direction.Top;
			break;
		// 下
		case GameRectId.snakeHeadDown:
			node.direction = Direction.Down;
			break;
		// 左
		case GameRectId.snakeHeadLeft:
			node.direction = Direction.Left;
			break;
		// 右
		case GameRectId.snakeHeadRight:
			node.direction = Direction.Right;
			break;
		}
	}

	RectF getRectFRandom(RectF lastRect, String headId) {
		RectF temp = new RectF();
		GameRect rect = GameRects.get(GameRectId.map);
		if (lastRect == null) {
			int randNumLeft = (int) (Math.random() * (13 - 7) + 7);
			int randNumTop = (int) (Math.random() * (13 - 7) + 7);
			temp.left = eachMap * randNumLeft + mapOther + rect.rectF.left;
			temp.right = eachMap * randNumLeft + mapOther + rect.rectF.left
					+ eachMap;
			temp.top = eachMap * randNumTop + mapOther + rect.rectF.top;
			temp.bottom = eachMap * randNumTop + mapOther + rect.rectF.top
					+ eachMap;

		} else if (headId == null) {
			int randNumLeft = (int) (Math.random() * 18);
			int randNumTop = (int) (Math.random() * 18);
			temp.left = eachMap * randNumLeft + mapOther + rect.rectF.left;
			temp.right = eachMap * randNumLeft + mapOther + rect.rectF.left
					+ eachMap;
			temp.top = eachMap * randNumTop + mapOther + rect.rectF.top;
			temp.bottom = eachMap * randNumTop + mapOther + rect.rectF.top
					+ eachMap;
		} else {
			switch (headId) {
			// 上
			case GameRectId.snakeHeadTop:
				temp.top = lastRect.top - eachMap;
				temp.bottom = lastRect.bottom - eachMap;
				temp.right = lastRect.right;
				temp.left = lastRect.left;
				break;
			// 下
			case GameRectId.snakeHeadDown:
				temp.top = lastRect.top + eachMap;
				temp.bottom = lastRect.bottom + eachMap;
				temp.right = lastRect.right;
				temp.left = lastRect.left;
				break;
			// 左
			case GameRectId.snakeHeadLeft:
				temp.top = lastRect.top;
				temp.bottom = lastRect.bottom;
				temp.right = lastRect.right + eachMap;
				temp.left = lastRect.left + eachMap;
				break;
			// 右
			case GameRectId.snakeHeadRight:
				temp.top = lastRect.top;
				temp.bottom = lastRect.bottom;
				temp.right = lastRect.right - eachMap;
				temp.left = lastRect.left - eachMap;
				break;
			}
		}
		return temp;
	}

	void UpClick() {
		if (!mainSnake.IsPause)
			mainSnake.Run(Direction.Top);

	}

	void DownClick() {
		if (!mainSnake.IsPause)
			mainSnake.Run(Direction.Down);
	}

	void RightClick() {
		if (!mainSnake.IsPause)
			mainSnake.Run(Direction.Right);
	}

	void LeftClick() {
		if (!mainSnake.IsPause)
			mainSnake.Run(Direction.Left);
	}
}
