package event;

import java.util.ArrayList;

import model.BtnGameRect;
import model.GameRect;

public class CheckEvent {
	public ArrayList<BtnGameRect> RegisterEventGameRects = new ArrayList<BtnGameRect>();
	static CheckEvent thisObj;

	private CheckEvent() {

	}

	public static CheckEvent getInstance() {
		if (thisObj == null) {
			thisObj = new CheckEvent();
		}
		return thisObj;
	}

	public void Restart() {
		thisObj = new CheckEvent();
	}

	public void CheckDown(float x, float y) {
		for (int i = 0; i < RegisterEventGameRects.size(); i++) {
			BtnGameRect item = RegisterEventGameRects.get(i);
			if (item.rectF.left < x && item.rectF.right > x
					&& item.rectF.top < y && item.rectF.bottom > y
					&& item.ShowState) {
				if (item.onDown != null) {
					item.onDown.OnDown();
				}

				break;
			}
		}
	}

	public void CheckUp(float x, float y) {
		for (int i = 0; i < RegisterEventGameRects.size(); i++) {
			BtnGameRect item = RegisterEventGameRects.get(i);
			//if (item.rectF.left < x && item.rectF.right > x
			//		&& item.rectF.top < y && item.rectF.bottom > y
			//		&& item.ShowState) {
			if (item.DownState) {
				if (item.onUp != null) {
					item.onUp.OnUp();
				}
				if (item.onClick != null) {
					item.onClick.OnClick();
				}
			}
			//}
		}
	}

}
