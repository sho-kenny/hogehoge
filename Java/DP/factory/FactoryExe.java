package DP.factory;

public class FactoryExe {

	/**
	 * job�̓��e��؂�ւ���Ə����I������player�̃X�e�[�^�X���\�������
	 * @param args
	 */
	public static void main(String[] args) {
		// �{����args�̈����ƍl���Ē������
		String job = "��m";
		//String job = "���@�g��";
		
		// Factory���g���ƐV���ȐE�Ƃ̃N���X���ǉ�����Ă��A����Main�N���X�͏C�����Ȃ��Ă悢
		Factory factory = new Factory();
		// Player�C���^�[�t�F�[�X���e�E�ƃN���X�Ɏ������邱�ƂŁAMain�N���X�͌ʂ̐E�Ƃ��C�ɂ��Ȃ��ł���
		Player player = factory.createPlayer(job);
		player.display();
	}
}
