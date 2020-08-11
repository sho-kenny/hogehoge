package DP;

/**
 * @author kenny
 */
public class Singleton {
		
	// 変数はコンフィグやコンテキストから取得するものが多い
	private String userName;
	
	/**
	 * privateコンストラクタ
	 */
	private Singleton( ) {
	}
	
	/**
	 * Singletonのインスタンス返却メソッド
	 * @return
	 */
	public static synchronized Singleton getInstance( ) {
		return SingletonHolder.INSTANCE;
	}
	
	/**
	 * 内部クラスにシングルトンのインスタンス変数を内包することで、
	 * Singletonクラスがロードされた時点では、Singletonインスタンスは生成されない
	 * @author kenny
	 */
	private static class SingletonHolder {
		private static final Singleton INSTANCE = new Singleton();
	}
	
	/**
	 * ここはコンテキストの内容次第
	 * @return
	 */
	public String getUserName() {
        return this.userName;
    }
}
