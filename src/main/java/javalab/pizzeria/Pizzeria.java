package javalab.pizzeria;

import javalab.map.Point;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Класс пиццерии
 */
public class Pizzeria {
    //Список доставщиков
    private List<Delivering> delivers = new ArrayList<Delivering>();
    //Список заказов
    private LinkedList<Order> orders = new LinkedList<Order>();
    //Местонахождение пиццерии
    private Point location;

    /**
     * Создает экземпляр пиццерии
     * @param location - место
     */
    public Pizzeria(Point location) {
        this.location = location;
    }

    /**
     * Возвращает список доставщиков
     * @return список достащиков
     */
    public List<Delivering> getDelivers() {
        return delivers;
    }

    /**
     * Возвращает список заказов
     * @return - списоз заказов
     */
    public LinkedList<Order> getOrders() {
        return orders;
    }

    /**
     * Возвращает место
     * @return место
     */
    public Point getLocation() {
        return location;
    }

    /**
     * Добавляет заказ
     * @param order - заказ
     */
    public void addOrder(Order order){
        orders.add(order);
    }

    /**
     * Добавляет достащика
     * @param delivering - доставщик
     */
    public void addDelivering(Delivering delivering){
        delivers.add(delivering);
    }
}
