package javalab;

import javalab.map.Model;
import javalab.map.Point;
import javalab.map.Road;
import javalab.pizzeria.Delivering;
import javalab.pizzeria.Order;

import java.util.List;

public class AdminView extends View {
    public AdminView(Model model) {
        super(model);
        System.out.println("Вы вошли как админ");
    }

    public void show(List<Road> way, Order order, Delivering transport, double time, double maxTime) {
        System.out.println("Заказ №" + order.getID());
        System.out.println("Доставка в точку " + order.getLocation().getId());
        System.out.format("Будет доставлен в %.0f%n",time);
        System.out.format("Должен быть доставлен не позднее %.0f%n", (maxTime+30));
        System.out.println("Доставляет " + transport.getName());
        if(way.isEmpty()){
            System.out.println("Не удалось найти путь");
        }
        else {
            System.out.println("Путь:");
            for (Road road : way) {
                System.out.println(road.toString());
            }
        }
        System.out.println();
    }
}
