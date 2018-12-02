package javalab.pizzeria;

import javalab.map.Transport;

/**
 * Класс доставщиков
 */
public class Delivering {
	protected String name;
	//Тип транспорта
	protected Transport transport;
	//Время, когда освободится доставщик
	protected double time = 0;
	protected boolean isFree = true;
	//Время, на которое опаздывает доставщик
	protected double delay = 0;
	/**
	 * Создавет новый экземпляр доставщика
	 * @param name - имя доставщика
	 */
	public Delivering(String name) {
		this.name = name;
	}

	/**
	 * Возвращает свободен ли доставщик
	 * @return свободен ли доставщик
	 */
	public boolean isFree() {
		return isFree;
	}

	/**
	 * Устанавливает параметр - занятость доставщика
	 * @param free - занятость доставщика
	 */
	public void setFree(boolean free) {
		isFree = free;
	}

	/**
	 * Возвращает время, когда доставщик освободится
	 * @return время, когда доставщик освободится
	 */
	public double getTime() {
		return time;
	}

	/**
	 * Установливает время, когда доставщик освободится
	 * @param time время, когда доставщик освободится
	 */
	public void setTime(double time) {
		this.time = time;
	}

	/**
	 * Возвращает тип транспорта
	 * @return тип транспорта
	 */
	public Transport getTransport() {
		return transport;
	}

	public double getDelay() {
		return delay;
	}

	public void setDelay(double delay) {
		this.delay = delay;
	}

	/**
	 * Возвращает имя доставщика
	 * @return имя доставщика
	 */
	public String getName() {
		return name;
	}
}
