package DP.factory;

/**
 * �E�Ɩ��őΏۂ̃N���X��؂蕪����Factory�N���X
 * @author sho
 */
public class Factory {

	public Player createPlayer(final String job) {
		
		if("��m".equals(job)) {
			return new Warrior(job);
		} else if("���@�g��".equals(job)) {
			return new Magician(job);
		}
		return null;
	}
}
