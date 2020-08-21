package DP.template;

/**
 * Template Methodパターン。
 * 個別の処理を子クラスに継承させ個別に処理内容記載。
 * ここは時代に合わせて特徴を変更する。
 * @author kenny
 */
public class ATMCashWithdraw extends AbstractATMCashWithdraw {

	@Override
	public void initCashWithdraw() {
		System.out.println("===開始処理===");
	}

	@Override
	public void certify() {
		System.out.println("===認証処理===");
	}

	@Override
	public void inputAmount() {
		System.out.println("===金額入力処理===");
	}

	@Override
	public void withdraw() {
		System.out.println("===引落処理===");
	}

	@Override
	public void exitCashWithdraw() {
		System.out.println("===終了処理===");
	}
}
