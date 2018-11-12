package javalab.pizzeria;

import javalab.map.Transport;

/**
 * Класс доставщика на велосипеде
 */
public class Bicycle extends Delivering {
	//Средняя скорость велосипеда
	private static double speed = 20;

	/**
	 * Коструктор создет нового велосипедиста
	 * @param name - имя выелосипелиста
	 */

	public Bicycle(String name) {
		super(name);
		//Устанавливается тип транспорта - велосипедист
		transport = Transport.BICYCLE;
	}

	/**
	 * Возвращает скорость велосипедиста
	 * @return скорость велосипедиста
	 */
	public static double getSpeed() {
		return speed;
	}
}
