package javalab.pizzeria;

import javalab.map.Transport;

/**
 * Класс доставщика на машине
 */

public class Car extends Delivering{
	//средняя скорость машины
	private static double speed = 60;

	/**
	 * Создает новый экземпляр машины
	 * @param name - имя доставщика
	 */
	public Car(String name) {
		super(name);
		//Устанавливается тип транспорта - машина
		transport = Transport.CAR;
	}

	/**
	 * Возвращает скорость машины
	 * @return скорость машины
	 */
	public static double getSpeed() {
		return speed;
	}
}
