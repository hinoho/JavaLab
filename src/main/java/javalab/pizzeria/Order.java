package javalab.pizzeria;

import javalab.map.Point;
import javalab.map.Road;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Класс заказа
 */
public class Order {
    private Point location;
    private int id;
    //активен ли заказ
    private boolean isClose = false;
    //Время, когда будет сделан заказ
    private double time;
    private double doneTime;
    private boolean checked;
    private int delay;
    private List<String> way = new ArrayList<>();

    public List<String> getWay() {
        return way;
    }

    public void setWay(List<Road> roads) {
        for(Road road : roads){
            way.add((road.getStart() + 1) + "" + (road.getEnd() + 1));
        }
    }

    public int getRandomDelay(){
        int arr[] = {0,0,0,1,1,2,3};
        return arr[new Random().nextInt(7)];
    }

    public int getDelay() {
        return delay;
    }

    public boolean getChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public double getDoneTime() {
        return doneTime;
    }

    public void setDoneTime(double doneTime) {
        this.doneTime = doneTime;
    }

    @Override
    public String toString() {
        return "Заказ №" + id;
    }

    public boolean isClose() {
        return isClose;
    }

    public void setClose(boolean close) {
        isClose = close;
    }

    /**
     * Создает новый экземпляр заказа
     * @param location - место, куда нужно доставить заказ
     * @param id - номер заказ
     * @param timeBeforeOrder - время заказа
     */
    public Order(int id, Point location, long timeBeforeOrder) {
        this.id = id;
        this.location = location;
        this.time = timeBeforeOrder;
        delay = getRandomDelay();
    }

    /**
     * Возвращает номер заказа
     * @return номер заказа
     */
    public int getId() {
        return id;
    }

    /**
     * Возвращает время заказа
     * @return время заказа
     */
    public double getTime() {
        return time;
    }

    /**
     * Возвращет место доставкти
     * @return место доставки
     */
    public Point getLocation() {

        return location;
    }

    /**
     * Откладывает доставку на время
     * @param extraTime - время
     */
    public void delay(double extraTime){
        time+=extraTime;
    }
}
