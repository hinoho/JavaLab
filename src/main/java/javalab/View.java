package javalab;

import java.util.List;
import java.util.Scanner;

import javalab.map.Point;
import javalab.map.Road;
import javalab.pizzeria.Delivering;
import javalab.pizzeria.Order;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * Представление отображает запрашиваемую информацию на экран
 *
 */
public class View{
	private Model model;
	private static Scanner scanner = new Scanner(System.in);
	private final static Logger logger = Logger.getLogger(View.class);

	/**
	 * Создает новый экземпляр представления
	 * @param model - модель
	 */
	public View(Model model, String... param) {
		this.model = model;
		if(param[0].equals("-d")){
			logger.setLevel(Level.INFO);
		}
		else {
			logger.setLevel(Level.ERROR);
		}
	}

	/**
	 * Выводит найденный путь
	 * @param way - путь
	 * @param order - заказ
	 * @param transport - доставщик
	 * @param time - время доставки
	 * @param maxTime - время, до которого должен быть доставлен
	 */
	public void show(List<Road> way, Order order, Delivering transport, double time, double maxTime) {
		StringBuilder stringBuilder = new StringBuilder("Заказ №" + order.getId() + '\n' +
				"Доставка в точку " + order.getLocation().getId() + '\n' +
				"Будет доставлен в " + Math.round(time) + '\n' +
				"Должен быть доставлен не позднее " + Math.round(maxTime+30) + '\n' +
				"Доставляет " + transport.getName() +'\n');
		if(way.isEmpty()){
			logger.warn("Не удалось найти путь");
		}
		else {
			stringBuilder.append("Путь:\n");
			for (Road road : way) {
				stringBuilder.append(road.toString() + "\n");
			}
			logger.info(stringBuilder);
		}
	}

	/**
	 * Запрашивет у пользоватеся начальную или конечную точку
	 */
	public Point askPoint(){

		System.out.println("Выберите номер точки для пиццерии:");
		//Вывод всех существующих точек
		for(Point point : model.getPoints()){
			System.out.println(point.toString());
		}
		Point point = model.getPoints().get(scanner.nextInt()-1);
		logger.info("Местонахождение пиццерии - " + point.toString());
		return point;
	}

	/**
	 * Выводит сообщение об ошибке
	 */
	public void error(){
		logger.error("Не возможно доставлять пиццу так далеко, измените настройки");
	}

}