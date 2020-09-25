package DP.factory;

public class FactoryExe {

	/**
	 * jobの内容を切り替えると初期選択したplayerのステータスが表示される
	 * @param args
	 */
	public static void main(String[] args) {
		// 本来はargsの引数と考えて頂ければ
		String job = "戦士";
		//String job = "魔法使い";
		
		// Factoryを使うと新たな職業のクラスが追加されても、このMainクラスは修正しなくてよい
		Factory factory = new Factory();
		// Playerインターフェースを各職業クラスに実装することで、Mainクラスは個別の職業を気にしないでいい
		Player player = factory.createPlayer(job);
		player.display();
	}
}
