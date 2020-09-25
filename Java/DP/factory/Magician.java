package DP.factory;

public class Magician implements Player {

	private String name;
	private String job;
	private int power;
	private int defence;
	private int magicPower;
	private int speed;
	
	/**
	 * コンストラクタで初期値を設定
	 * @param job
	 */
	public Magician(final String job) {
		this.job = job;
		this.power = 5;
		this.defence = 10;
		this.magicPower = 20;
		this.speed = 15;
	}

	@Override
	public void display() {
		if(this.name == null) {
			System.out.println("名前：未入力");
		} else {
			System.out.println("名前：" + this.name);
		}
		System.out.println("職業：" + this.job);
		System.out.println("力：" + this.power);
		System.out.println("防御：" + this.defence);
		System.out.println("魔力：" + this.magicPower);
		System.out.println("素早さ：" + this.speed);
	}

	@Override
	public void changeName(final String name) {
		this.name = name;
	}
}
