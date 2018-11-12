package javalab.pizzeria;

import javalab.map.Point;

/**
 * Класс заказа
 */
public class Order {
    private Point location;
    private int id;
    //Время, когда будет сделан заказ
    private double time;
    //Статус заказа, был ли он доставлен
    private boolean isCompleted = false;

    /**
     * Создает новый экземпляр заказа
     * @param location - место, куда нужно доставить заказ
     * @param id - номер заказ
     * @param timeBeforeOrder - время заказа
     */
    public Order(int id, Point location, int timeBeforeOrder) {
        this.id = id;
        this.location = location;
        this.time = timeBeforeOrder;
    }

    /**
     * Возвращает статус заказа
     * @return статус заказа
     */
    public boolean isCompleted() {
        return isCompleted;
    }

    /**
     * Устанавливает статус заказа
     * @param completed - статус заказа
     */
    public void setCompleted(boolean completed) {
        isCompleted = completed;
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

}
