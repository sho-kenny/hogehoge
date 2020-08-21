package DP.template;

/**
 * Template Method�p�^�[���B
 * �ʂ̏������q�N���X�Ɍp�������ʂɏ������e�L�ځB
 * �����͎���ɍ��킹�ē�����ύX����B
 * @author kenny
 */
public class ATMCashWithdraw extends AbstractATMCashWithdraw {

	@Override
	public void initCashWithdraw() {
		System.out.println("===�J�n����===");
	}

	@Override
	public void certify() {
		System.out.println("===�F�؏���===");
	}

	@Override
	public void inputAmount() {
		System.out.println("===���z���͏���===");
	}

	@Override
	public void withdraw() {
		System.out.println("===��������===");
	}

	@Override
	public void exitCashWithdraw() {
		System.out.println("===�I������===");
	}
}
