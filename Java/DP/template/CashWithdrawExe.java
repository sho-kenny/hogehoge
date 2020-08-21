package DP.template;

/**
 * Template Methodパターンを実行するMainクラス。
 * @author kenny
 */
public class CashWithdrawExe {

	public static void main(String[] args) {
		// 以下はAbstractクラスなのでインスタンスを生成できない。コンパイルエラー。
		// AbstractATMCashWithdraw exe = new AbstractATMCashWithdraw();
		AbstractATMCashWithdraw exe = new ATMCashWithdraw();
		// 処理の流れを実行
		exe.doCashWithdraw();
	}
}
