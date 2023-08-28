package simuRendering;

//座標ベクトルのクラス
class Point {
    double x, y, z;// エクストルーダーの座標と押出量
    double rx, ry, rz; // 回転させた後の座標
    int screenX, screenY; // スクリーン上の座標

    Point(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
