package javalab.pizzeria;

import javalab.map.Transport;

/**
 * Человек
 */
public class Man extends Delivering{
	//Средняя скорость человека
	private static double speed = 4;

	/**
	 * Создает новый экземпляр человека
	 * @param name имя человека
	 */
	public Man(String name) {
		super(name);
		transport = Transport.MAN;
	}

	/**
	 * Возвращает скороть человека
	 * @return скорость челоека
	 */
	public static double getSpeed() {
		return speed;
	}
}
