//SimuRenderingクラスのインスタンス化と情報の取得
//正常にレンダリングが行われているか，アニメーションの操作が行えるか，を確認する．

// ドラッグ：回転
// ホイール：拡大縮小
// 矢印キー：平行移動
// 一度クリックすると出る
// Enterキーでリセット
// スペースで回転リセット

import simuRendering.SimuRendering;

public class Test2 {

	public static void main(String[] args) {
		// {x, y, z}の座標系，入力順に線を結ぶ
		Double[][] pointData = { { -10.0, 0.0, 0.0 }, { 0.0, 10.0, 0.0 }, { 0.0, 0.0, -10.0 }, { 10.0, 0.0, 0.0 },
				{ 0.0, 0.0, 10.0 }, { 0.0, -10.0, 0.0 } };

		String name = "Test2.java"; // ウィンドウ名
		int[] size = { 600, 600 }; // 描画エリアsize
		boolean fixedToCenter = true; // 回転中心をモデルの中心へ移動
		SimuRendering sr = new SimuRendering(name, size, pointData, fixedToCenter);

		sr.show();
	}

}

/*
 * 正四面体ワイヤ（一部）
 *
 * { -10.0, 0.0, 0.0 }, { 0.0, 10.0, 0.0 }, { 0.0, 0.0, -10.0 },
 * { 10.0, 0.0, 0.0 }, { 0.0, 0.0, 10.0 }, { 0.0, -10.0, 0.0 }
 *
 * 正三角形(底辺中心)
 * { 0.0, 10 * Math.sqrt(3.0), 0.0 }, { -10.0, 0.0, 0.0 }, { 10.0, 0.0, 0.0 },{
 * 0.0, 10 * Math.sqrt(3.0), 0.0 }
 *
 * * 正三角形(重心中心)
 * { 0.0, 10 * Math.sqrt(3.0)/3*2, 0.0 }, { -10.0, -10 * Math.sqrt(3.0)/3, 0.0
 * }, { 10.0, -10 * Math.sqrt(3.0)/3, 0.0 },{
 * 0.0, 10 * Math.sqrt(3.0)/3*2, 0.0 }
 *
 */