package DP;

/**
 * @author kenny
 */
public class Singleton {
		
	// �ϐ��̓R���t�B�O��R���e�L�X�g����擾������̂�����
	private String userName;
	
	/**
	 * private�R���X�g���N�^
	 */
	private Singleton( ) {
	}
	
	/**
	 * Singleton�̃C���X�^���X�ԋp���\�b�h
	 * @return
	 */
	public static synchronized Singleton getInstance( ) {
		return SingletonHolder.INSTANCE;
	}
	
	/**
	 * �����N���X�ɃV���O���g���̃C���X�^���X�ϐ������邱�ƂŁA
	 * Singleton�N���X�����[�h���ꂽ���_�ł́ASingleton�C���X�^���X�͐�������Ȃ�
	 * @author kenny
	 */
	private static class SingletonHolder {
		private static final Singleton INSTANCE = new Singleton();
	}
	
	/**
	 * �����̓R���e�L�X�g�̓��e����
	 * @return
	 */
	public String getUserName() {
        return this.userName;
    }
}
