package javalab.map;

import java.util.Objects;

/**
 * Точка - место пересечений дорог
 */
public class Point {
    private int id;

    /**
     * Оздает новый экземпляр класса с номером
     * @param id - номер
     */
    public Point(int id) {
        this.id = id;
    }

    /**
     * Сравнивает две точки
     * @param o - точка
     * @return равны ли точки
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return id == point.id;
    }

    /**
     * Возвращает хэш-код
     * @return хэш-код
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Возвращает номер точки
     * @return номер
     */
    public int getId() {
        return id;
    }

    /**
     * Приводт точку к строке
     * @return точку, приведенную к строке
     */
    @Override
    public String toString() {
        return "Точка №" + (id+1);
    }


}
