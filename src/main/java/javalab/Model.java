package javalab;

import javalab.map.Point;
import javalab.pizzeria.Pizzeria;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Model - карта. Хранит в себе данны ео локациях: точки и дороги между ними
 * 
 */
public class Model {

	//Графы дорог для всех видов транспорта
	private SimpleWeightedGraph man, bicycle, car;
	//Список точек на карте
	private List<Point> points = new ArrayList<Point>();
	private Pizzeria pizzeria;

	/**
	 * Возвращает список точек на карте
	 * @return список точек на карте
	 */
	public List<Point> getPoints() {
		return points;
	}

	/**
	 * Возвращает спико точек
	 * @param points - список точек
	 */
	public void setPoints(List<Point> points) {
		this.points = points;
	}

	/**
	 * Возвращает граф, по которому перемещается человек
	 * @return - граф , по которому перемещается человек
	 */
	public SimpleWeightedGraph getMan() {
		return man;
	}

	/**
	 * Возвращает граф, по которому перемещается велосипедист
	 * @return - граф , по которому перемещается велосипедист
	 */
	public SimpleWeightedGraph getBicycle() {
		return bicycle;
	}

	/**
	 * Возвращает граф, по которому перемещается машина
	 * @return - граф , по которому перемещается машина
	 */
	public SimpleWeightedGraph getCar() {
		return car;
	}

	/**
	 * Устанавливает граф дорог, по которому может перемещается человек
	 * @param man - граф
	 */
	public void setMan(SimpleWeightedGraph man) {
		this.man = man;
	}

	/**
	 * Устанавливает граф дорог, по которому может перемещается велосипедист
	 * @param bicycle - граф
	 */
	public void setBicycle(SimpleWeightedGraph bicycle) {
		this.bicycle = bicycle;
	}

	/**
	 * Устанавливает граф дорог, по которому может перемещается машина
	 * @param car - граф
	 */
	public void setCar(SimpleWeightedGraph car) {
		this.car = car;
	}

	/**
	 * Возвращает случайную точку, которая не является местонахождением пиццерии
	 * @return - случаная точка
	 */
	public Point getRandomPoint(){
		int i;
		Random r = new Random();
		int p = pizzeria.getLocation().getId();
		do {
			i = r.nextInt(points.size());
		}while (i==p);
		return points.get(i);
	}

	/**
	 * Возвращает пиццерию
	 * @return пиццерия
	 */
	public Pizzeria getPizzeria() {
		return pizzeria;
	}

	/**
	 * Устанавливает пиццерию
	 * @param pizzeria - пиццерия
	 */
	public void setPizzeria(Pizzeria pizzeria) {
		this.pizzeria = pizzeria;
	}
}
