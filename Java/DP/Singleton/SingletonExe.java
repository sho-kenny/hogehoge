package DP.singleton;

/**
 * Singleton�̌Ăяo���N���X
 * @author kenny
 */
public class SingletonExe {

	public static void main(String[] args) {
		
		// private�R���X�g���N�^�ɂ��R���p�C���G���[
		// Singleton instance0 = new Singleton();
		
		Singleton instance1 = Singleton.getInstance();
		Singleton instance2 = Singleton.getInstance();
		
		// 2��Ăяo�����C���X�^���X���������̂�����
		if (instance1 == instance2) {
            System.out.println("instance1 and instance2 are same Instance");
        } else {
            System.out.println("instance1 and instance2 aren't same Instance");
        }
	}
}
