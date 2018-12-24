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
	private List<Data> data = new ArrayList<>();

	public List<Data> getData() {
		return data;
	}

	public void addData(int number, String start, String end, String time, String courier, boolean isDone){
		data.add(new Data(number, start, end, time, courier,isDone));
	}

	public class Data{
		public int number;
		public String start;
		public String end;
		public String time;
		public String courier;
		public boolean isDone;

		public Data(int number, String start, String end, String time, String courier, boolean isDone) {
			this.number = number;
			this.start = start;
			this.end = end;
			this.time = time;
			this.courier = courier;
			this.isDone = isDone;
		}

		public boolean isDone() {
			return isDone;
		}

		public void setDone(boolean done) {
			isDone = done;
		}

		public void setNumber(int number) {
			this.number = number;
		}

		public void setStart(String start) {
			this.start = start;
		}

		public void setEnd(String end) {
			this.end = end;
		}

		public void setTime(String time) {
			this.time = time;
		}

		public void setCourier(String courier) {
			this.courier = courier;
		}

		public int getNumber() {
			return number;
		}

		public String getStart() {
			return start;
		}

		public String getEnd() {
			return end;
		}

		public String getTime() {
			return time;
		}

		public String getCourier() {
			return courier;
		}
	}

	/**
	 * Возвращает список точек на карте
	 * @return список точек на карте
	 */
	public List<Point> getPoints() {
		return points;
	}

	public void addOrder(int point){

		pizzeria.addOrder(points.get(point-1));
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
