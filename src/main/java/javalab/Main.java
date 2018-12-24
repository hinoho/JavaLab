package javalab;

/**
 * Основной класс программы
 *
 */
public class Main{
	/**
	 * Точка входа программы. Если параметр равен "-d" а экран выводится полная информация о маршруте
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		Controller controller = new Controller(args);
	}

}
