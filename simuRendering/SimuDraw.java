package simuRendering;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;

import javax.swing.JPanel;

//座標から画像をレンダリングするクラス．
public class SimuDraw extends JPanel implements MouseMotionListener, MouseWheelListener, KeyListener {
    private Double[][] Point_DATA;// 座標のデータ
    private ArrayList<Point> points = new ArrayList<Point>(); // 頂点列を初期化
    private ArrayList<SimuVector> Vectors = new ArrayList<SimuVector>(); // 面列を初期化
    private int[] size; // ウィンドウサイズ
    private float[] center = new float[2]; // ウィンドウの中心座標
    private double scale; // モデル描画時のスケール
    private double phi = 0.0; // x軸周りの回転角
    private double theta = 0.0; // y軸周りの回転角
    private double[] mousePosition = { 0, 0 };
    private Image bufferImage; // ダブルバッファリング用のイメージ
    private double zoom = 1;// サイズ変更用の変数
    private int[] move = { 0, 0 };// 平行移動用の変数

    public SimuDraw(Double[][] pData, int[] size) {
        setFocusable(true);
        addKeyListener(this);
        addMouseWheelListener(this);
        addMouseMotionListener(this);// イベントリスナーの登録

        Point_DATA = pData;
        this.size = size;

        center[0] = size[0] / 2;
        center[1] = size[1] / 2;// ウィンドウの中心座標
        scale = size[1] * 0.03 / 2; // モデル描画時のスケール
    }

    public void paint(Graphics g) {
        // ダブルバッファリング用のイメージを作成
        if (bufferImage == null) {
            bufferImage = createImage(size[0], size[1]);
        }

        // バッファにモデルを描画
        drawModel(bufferImage.getGraphics());

        // バッファイメージをアプレットに描画
        g.drawImage(bufferImage, 0, 0, this);
    }

    // 描画更新時に背景の塗りつぶし処理を行わないためのオーバーライド
    public void update(Graphics g) {
        paint(g);
    }

    public void mouseMoved(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        int m = 20;
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:// 上キー
                move[1] -= m;
                break;
            case KeyEvent.VK_DOWN:// 下キー
                move[1] += m;
                break;
            case KeyEvent.VK_LEFT:// 左キー
                move[0] -= m;
                break;
            case KeyEvent.VK_RIGHT:// 右キー
                move[0] += m;
                break;

            case KeyEvent.VK_ENTER: // エンターキー
                resetTransform();
            case KeyEvent.VK_SPACE: // スペースキー
                resetRotate();
        }
        // 頂点のスクリーン座標の更新
        setScreenPosition();

        // 描画更新
        repaint();
    }

    public void resetTransform() {
        phi = 0.0; // x軸周りの回転角
        theta = 0.0; // y軸周りの回転角
        zoom = 1;// サイズ変更用の変数
        move[0] = 0;// 平行移動用の変数
        move[1] = 0;
    }

    public void resetRotate() {
        phi = 0.0; // x軸周りの回転角
        theta = 0.0; // y軸周りの回転角
    }

    public void mouseDragged(MouseEvent e) { // 回転角の更新
        theta += (e.getX() - mousePosition[0]) * 0.01 * zoom;
        phi += (e.getY() - mousePosition[1]) * 0.01 * zoom;
        // x軸周りの回転角に上限を設定
        phi = Math.min(phi, Math.PI / 2);
        phi = Math.max(phi, -Math.PI / 2);

        // マウス位置の更新
        mousePosition[0] = e.getX();
        mousePosition[1] = e.getY();

        // 頂点のスクリーン座標の更新
        setScreenPosition();

        // 描画更新
        repaint();
    }

    public void mousePressed(MouseEvent e) {
        // マウス位置の更新
        mousePosition[0] = e.getX();
        mousePosition[0] = e.getY();
    }

    public void mouseWheelMoved(MouseWheelEvent e) {
        if (e.getWheelRotation() > 0)
            zoom *= 0.9;
        else
            zoom /= 0.9;
        // 頂点のスクリーン座標の更新
        setScreenPosition();

        // 描画更新
        repaint();
    }

    // モデルデータの設定
    public void setModelData() {// 頂点とベクトルの設定
        // 頂点の作成
        for (int i = 0; i < Point_DATA.length; i++) {
            double x = Point_DATA[i][0];
            double y = Point_DATA[i][1];
            double z = Point_DATA[i][2];
            points.add(new Point(x, y, z));
        }

        // ベクトルの作成
        for (int i = 0; i < Point_DATA.length - 1; i++) {
            Point p1 = points.get(i);
            Point p2 = points.get(i + 1);
            Vectors.add(new SimuVector(p1, p2));
        }
    }

    // 頂点のスクリーン座標を更新する
    private void setScreenPosition() {
        for (int i = 0; i < points.size(); i++) {
            Point p = points.get(i);

            // 回転後の座標値の算出
            p.rx = p.x * Math.cos(theta) + p.z * Math.sin(theta);
            p.ry = p.x * Math.sin(phi) * Math.sin(theta) + p.y * Math.cos(phi) - p.z * Math.sin(phi) * Math.cos(theta);
            p.rz = -p.x * Math.cos(phi) * Math.sin(theta) + p.y * Math.sin(phi) + p.z * Math.cos(phi) * Math.cos(theta);

            // スクリーン座標の算出
            p.screenX = (int) ((int) ((center[0] + scale * p.rx) + move[0]) * zoom);
            p.screenY = (int) ((int) ((center[1] - scale * p.ry) + move[1]) * zoom);
        }
    }

    public void drawModel(Graphics g) {
        setModelData();
        super.paintComponent(g);
        // 白色で全体をクリア
        g.setColor(Color.white);
        g.fillRect(0, 0, size[0], size[1]);

        // 辺の描画
        for (int i = 0; i < Vectors.size(); i++) {
            SimuVector Vector = Vectors.get(i);

            // 輪郭線の描画
            g.setColor(Color.black);
            for (int j = 0; j < 2; j++) {
                g.drawLine(Vector.p[j].screenX,
                        Vector.p[j].screenY,
                        Vector.p[(j + 1) % 2].screenX,
                        Vector.p[(j + 1) % 2].screenY);
            }
        }
    }

}
