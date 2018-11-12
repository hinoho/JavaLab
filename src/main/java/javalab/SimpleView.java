package javalab;

import javalab.map.Model;
import javalab.map.Point;
import javalab.map.Road;
import javalab.pizzeria.Delivering;

import java.util.List;

public class SimpleView extends View {
    public SimpleView(Model model) {

        super(model);
        System.out.println("Вы вошли как пользователь");
    }

    /**
     * Выводит на экран кратчайший маршрут, полученный от модели
     */
    public void show(List<Road> way, Point point, Delivering transport, double time, double maxTime) {
        System.out.println("Доставка в точку " + point.getId());
        if(way.isEmpty()){
            System.out.println("Не удалось найти путь");
        }
        else {
            System.out.println("Путь:");
            for (Road road : way) {
                System.out.println(road.getName());
            }
        }
        System.out.println();
    }
}
