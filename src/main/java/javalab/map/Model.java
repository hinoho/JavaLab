package javalab.map;

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
	private SimpleWeightedGraph man, bicycle, car;

	public Model() {
	}

	private List<Point> points = new ArrayList<Point>();
	private Pizzeria pizzeria;

	public void setPoints(List<Point> points) {
		this.points = points;
	}

	public SimpleWeightedGraph getMan() {
		return man;
	}

	public SimpleWeightedGraph getBicycle() {
		return bicycle;
	}

	public SimpleWeightedGraph getCar() {
		return car;
	}

	public void setMan(SimpleWeightedGraph man) {
		this.man = man;
	}

	public void setBicycle(SimpleWeightedGraph bicycle) {
		this.bicycle = bicycle;
	}

	public void setCar(SimpleWeightedGraph car) {
		this.car = car;
	}

	/**
	 * Возвращает список точек на карте
	 * @return список точек на карте
	 */
	public List<Point> getPoints() {
		return points;
	}

	public Point getRandomPoint(){
		int i;
		Random r = new Random();
		int p = pizzeria.getLocation().getId();
		do {
			i = r.nextInt(points.size());
		}while (i==p);
		return points.get(i);
	}

	public Pizzeria getPizzeria() {
		return pizzeria;
	}

	public void setPizzeria(Pizzeria pizzeria) {
		this.pizzeria = pizzeria;
	}
}
