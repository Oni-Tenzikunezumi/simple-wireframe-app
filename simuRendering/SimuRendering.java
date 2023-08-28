package simuRendering;

//レンダリングした画像を表示させるクラス
public class SimuRendering {
	private String name;
	private int[] size;
	private Double[][] Point_DATA;
	private SimuWin gw;

	public SimuRendering(String name, int[] size, Double[][] Point_DATA, boolean fixedToCenter) {
		this.name = name;
		this.size = size;
		this.Point_DATA = Point_DATA;

		if (fixedToCenter) {
			moveToModelCenter();
		}
	}

	public void show() {
		gw = new SimuWin(name, size);
		gw.add(new SimuDraw(Point_DATA, size));
		gw.setVisible(true);
	}

	private void moveToModelCenter() {
		Double[] sumDoubles = { 0.0, 0.0, 0.0 };
		int len = this.Point_DATA.length;

		for (Double[] p : this.Point_DATA) {
			sumDoubles[0] += p[0];
			sumDoubles[1] += p[1];
			sumDoubles[2] += p[2];
		}
		Double[] center = { sumDoubles[0] / len, sumDoubles[1] / len, sumDoubles[2] / len };

		for (int i = 0; i < len; i++) {
			this.Point_DATA[i][0] -= center[0];
			this.Point_DATA[i][1] -= center[1];
			this.Point_DATA[i][2] -= center[2];
		}

	}
}