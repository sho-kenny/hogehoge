package DP.template;

/**
 * Template Methodパターン。
 * 親クラスで、不変的な処理の流れをfinalメソッドで定義。
 * 個別の処理は子クラスに継承させ個別実行。
 * @author kenny
 */
public abstract class AbstractATMCashWithdraw {
	
	// ATMの開始処理（具体的な内容は分かんないけど、きっと何かあるだろう・・）
	public abstract void initCashWithdraw();
	// ATM引き落としの際の認証処理
	public abstract void certify();
	// 金額入力
	public abstract void inputAmount();
	// 引き落とし
	public abstract void withdraw();
	// ATMの終了処理（具体的な内容は分かんないけど、きっと何かあるだろう・・）
	public abstract void exitCashWithdraw();
	
	/**
	 * ATMから引き落とす際の処理プロセスを記載
	 */
	public final void doCashWithdraw() {
		// 中身はいずれも子クラスに継承（本当は引数あると思いますが面倒なので割愛）
		initCashWithdraw();
		certify();
		inputAmount();
		withdraw();
		exitCashWithdraw();
	}
}
