package simuRendering;

//シミュレーションを表示するウィンドウのクラス
import javax.swing.JFrame;

class SimuWin extends JFrame {
    SimuWin(String title, int[] size) {
        super(title);
        // System extを利用して閉じる
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(size[0], size[1]);
        setLocationRelativeTo(null);// 指定しないと真ん中にセット
        setResizable(true);// windowサイズを変えさせない．
    }
}