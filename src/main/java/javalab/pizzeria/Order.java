package javalab.pizzeria;

import javalab.map.Point;

/**
 * Класс заказа
 */
public class Order {
    private Point location;
    private int id;
    //активен ли заказ
    private boolean isClose = false;
    private boolean isDone = false;
    //Время, когда будет сделан заказ
    private double time;
    private double doneTime;

    public double getDoneTime() {
        return doneTime;
    }

    public void setDoneTime(double doneTime) {
        this.doneTime = doneTime;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
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
