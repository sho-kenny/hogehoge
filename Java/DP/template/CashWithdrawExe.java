package DP.template;

/**
 * Template Method�p�^�[�������s����Main�N���X�B
 * @author kenny
 */
public class CashWithdrawExe {

	public static void main(String[] args) {
		// �ȉ���Abstract�N���X�Ȃ̂ŃC���X�^���X�𐶐��ł��Ȃ��B�R���p�C���G���[�B
		// AbstractATMCashWithdraw exe = new AbstractATMCashWithdraw();
		AbstractATMCashWithdraw exe = new ATMCashWithdraw();
		// �����̗�������s
		exe.doCashWithdraw();
	}
}
