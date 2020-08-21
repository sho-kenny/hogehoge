package DP.singleton;

/**
 * Singletonの呼び出しクラス
 * @author kenny
 */
public class SingletonExe {

	public static void main(String[] args) {
		
		// privateコンストラクタによりコンパイルエラー
		// Singleton instance0 = new Singleton();
		
		Singleton instance1 = Singleton.getInstance();
		Singleton instance2 = Singleton.getInstance();
		
		// 2回呼び出したインスタンスが同じものか実験
		if (instance1 == instance2) {
            System.out.println("instance1 and instance2 are same Instance");
        } else {
            System.out.println("instance1 and instance2 aren't same Instance");
        }
	}
}
