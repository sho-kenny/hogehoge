package DP.template;

/**
 * Template Method�p�^�[���B
 * �e�N���X�ŁA�s�ϓI�ȏ����̗����final���\�b�h�Œ�`�B
 * �ʂ̏����͎q�N���X�Ɍp�������ʎ��s�B
 * @author kenny
 */
public abstract class AbstractATMCashWithdraw {
	
	// ATM�̊J�n�����i��̓I�ȓ��e�͕�����Ȃ����ǁA�����Ɖ������邾�낤�E�E�j
	public abstract void initCashWithdraw();
	// ATM�������Ƃ��̍ۂ̔F�؏���
	public abstract void certify();
	// ���z����
	public abstract void inputAmount();
	// �������Ƃ�
	public abstract void withdraw();
	// ATM�̏I�������i��̓I�ȓ��e�͕�����Ȃ����ǁA�����Ɖ������邾�낤�E�E�j
	public abstract void exitCashWithdraw();
	
	/**
	 * ATM����������Ƃ��ۂ̏����v���Z�X���L��
	 */
	public final void doCashWithdraw() {
		// ���g�͂�������q�N���X�Ɍp���i�{���͈�������Ǝv���܂����ʓ|�Ȃ̂Ŋ����j
		initCashWithdraw();
		certify();
		inputAmount();
		withdraw();
		exitCashWithdraw();
	}
}
