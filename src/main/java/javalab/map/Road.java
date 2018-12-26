package javalab.map;

import javalab.pizzeria.Bicycle;
import javalab.pizzeria.Car;
import javalab.pizzeria.Man;
import org.jgrapht.graph.DefaultWeightedEdge;

/**
 * Класс дороги
 */
public class Road extends DefaultWeightedEdge {
    private String name;
    private Transport transport;
    private double length;
    private int start;
    private int end;

    /**
     * Создает новый экземпляр дороги
     * @param name - имя
     * @param transport - тип транспорта, который может по ней перемещаться
     * @param length - длина
     */
    public Road(String name, Transport transport, double length, int start, int end) {
        this.name = name;
        this.transport = transport;
        this.length = length;
        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    /**
     * Возвращает время, за которое транспорт проедет по дороге
     * @return время, за которое транспорт проедет по дороге
     */
    public double getTime(){
        switch (transport){
            case CAR: return length/Car.getSpeed();
            case MAN: return length/ Man.getSpeed();
            case BICYCLE: return length/ Bicycle.getSpeed();
        }
        return -1;
    }

    /**
     * Приводит класс к строке
     * @return полное название дороги
     */
    @Override
    public String toString() {
        return name + " между "+  getSource().toString() + " и " + getTarget();
    }
}
