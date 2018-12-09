package javalab;

/**
 * Основной класс программы
 *
 */
public class Main {
	/**
	 * Точка входа программы. Если параметр равен "-d" а экран выводится полная информация о маршруте
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		Controller controller = new Controller(args);
		Thread thread1 = new Thread(controller);
		Thread thread2 = new Thread(controller.getView());
		thread1.start();

		thread2.start();
		thread2.join();
	}
}
