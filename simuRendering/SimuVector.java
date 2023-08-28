package simuRendering;

//エクストルーダーの動きのベクトルのクラス
class SimuVector {
    Point[] p = new Point[2];// 始点と終点

    SimuVector(Point p0, Point p1) {
        p[0] = p0;
        p[1] = p1;
    }
}
