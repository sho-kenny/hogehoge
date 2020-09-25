package DP.factory;

/**
 * 職業名で対象のクラスを切り分けるFactoryクラス
 * @author sho
 */
public class Factory {

	public Player createPlayer(final String job) {
		
		if("戦士".equals(job)) {
			return new Warrior(job);
		} else if("魔法使い".equals(job)) {
			return new Magician(job);
		}
		return null;
	}
}
