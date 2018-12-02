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
		if(param.length > 0 && param[0].equals("-d")){
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
	 */
	public void show(List<Road> way, Order order, Delivering transport, double time) {
		StringBuilder stringBuilder = new StringBuilder("Заказ №" + order.getId() + '\n' +
				"Доставка в точку " + order.getLocation().getId() + '\n' +
				"Будет доставлен в " + Math.round(time) + '\n' +
				"Должен быть доставлен не позднее " + Math.round(order.getTime()+30) + '\n' +
				"Доставляет " + transport.getName() +'\n');

		stringBuilder.append("Путь:\n");
		for (Road road : way) {
			stringBuilder.append(road.toString() + "\n");
		}
		logger.info(stringBuilder);
	}

	public void show(List<Road> way1, List<Road> way2, Order order1, Order order2, Delivering transport, double time1, double time2) {
		StringBuilder stringBuilder = new StringBuilder(
				"Заказ №" + order1.getId() + " и Заказ №" + order2.getId() + '\n' +
				"Заказ №" + order1.getId() + ":" + '\n' +
				"Доставка в точку " + order1.getLocation().getId() + '\n' +
				"Будет доставлен в " + Math.round(time1) + '\n' +
				"Должен быть доставлен не позднее " + Math.round(order1.getTime()+30) + '\n' +
				"Заказ №" + order2.getId() + ":" +'\n' +
				"Доставка в точку " + order2.getLocation().getId() + '\n' +
				"Будет доставлен в " + Math.round(time2) + '\n' +
				"Должен быть доставлен не позднее " + Math.round(order2.getTime()+30) + '\n' +
				"Доставляет " + transport.getName() +'\n');

		stringBuilder.append("Путь до первого заказа:\n");
		for (Road road : way1) {
			stringBuilder.append(road.toString() + "\n");
		}
		stringBuilder.append("Путь до второго заказа:\n");
		for (Road road : way2) {
			stringBuilder.append(road.toString() + "\n");
		}
		logger.info(stringBuilder);
	}

	/**gfvvvvvvvvvv
	 * Запрашивет у пользоватеся начальную или конечную точку
	 */
	public Point askPoint(){
		int numberOfPoint = -1;
		String inputString;
		Point location=null;
		while (location==null) {
			try {
				System.out.println("Выберите номер точки для пиццерии:");
				//Вывод всех существующих точек
				for (Point point : model.getPoints()) {
					System.out.println(point.toString());
				}
				//Проверка, является ли строка числом
				inputString = scanner.nextLine();
				numberOfPoint = Integer.parseInt(inputString);
				//Проверка существет ли данная точка
				if (numberOfPoint <= 0 || numberOfPoint > model.getPoints().size()) {
					logger.error("Данной точки не существует");
				} else {
					location = model.getPoints().get(numberOfPoint - 1);
				}
			} catch (NumberFormatException e) {
				logger.error("Введите число");
			}
		}

		logger.info("Местонахождение пиццерии - " + location.toString());
		return location;
	}

	/**
	 * Выводит сообщение об ошибке
	 */
	public void error(){
		logger.error("Не возможно доставлять пиццу так далеко, измените настройки");
	}

}
