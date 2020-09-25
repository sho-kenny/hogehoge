package DP.factory;

public class Magician implements Player {

	private String name;
	private String job;
	private int power;
	private int defence;
	private int magicPower;
	private int speed;
	
	/**
	 * �R���X�g���N�^�ŏ����l��ݒ�
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
			System.out.println("���O�F������");
		} else {
			System.out.println("���O�F" + this.name);
		}
		System.out.println("�E�ƁF" + this.job);
		System.out.println("�́F" + this.power);
		System.out.println("�h��F" + this.defence);
		System.out.println("���́F" + this.magicPower);
		System.out.println("�f�����F" + this.speed);
	}

	@Override
	public void changeName(final String name) {
		this.name = name;
	}
}
