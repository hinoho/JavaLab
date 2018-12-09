package javalab;

import java.io.InputStream;
import java.util.List;
import java.util.Scanner;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.*;

import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;


import javafx.stage.WindowEvent;
import javalab.map.Point;
import javalab.map.Road;
import javalab.pizzeria.Delivering;
import javalab.pizzeria.Order;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * Представление отображает запрашиваемую информацию на экран
 *
 */
public class View extends Application implements Runnable{
	private Model model;
	private static Scanner scanner = new Scanner(System.in);
	private final static Logger logger = Logger.getLogger(View.class);

	/**
	 * Создает новый экземпляр представления
	 * @param model - модель
	 */
	public View(Model model, String... param) {
		this.model = model;
		if(param.length > 0 && param[0].equals("-d")){
			logger.setLevel(Level.INFO);
		}
		else {
			logger.setLevel(Level.ERROR);
		}
	}

	public View(){

	}

	/**
	 * Выводит найденный путь
	 * @param way - путь
	 * @param order - заказ
	 * @param transport - доставщик
	 * @param time - время доставки
	 */
	public void show(List<Road> way, Order order, Delivering transport, double time) {
		StringBuilder stringBuilder = new StringBuilder("Заказ №" + order.getId() + '\n' +
				"Доставка в точку " + order.getLocation().getId() + '\n' +
				"Будет доставлен в " + Math.round(time) + '\n' +
				"Должен быть доставлен не позднее " + Math.round(order.getTime()+30) + '\n' +
				"Доставляет " + transport.getName() +'\n');

		stringBuilder.append("Путь:\n");
		for (Road road : way) {
			stringBuilder.append(road.toString() + "\n");
		}
		logger.info(stringBuilder);
	}

	public void show(List<Road> way1, List<Road> way2, Order order1, Order order2, Delivering transport, double time1, double time2) {
		StringBuilder stringBuilder = new StringBuilder(
				"Заказ №" + order1.getId() + " и Заказ №" + order2.getId() + '\n' +
				"Заказ №" + order1.getId() + ":" + '\n' +
				"Доставка в точку " + order1.getLocation().getId() + '\n' +
				"Будет доставлен в " + Math.round(time1) + '\n' +
				"Должен быть доставлен не позднее " + Math.round(order1.getTime()+30) + '\n' +
				"Заказ №" + order2.getId() + ":" +'\n' +
				"Доставка в точку " + order2.getLocation().getId() + '\n' +
				"Будет доставлен в " + Math.round(time2) + '\n' +
				"Должен быть доставлен не позднее " + Math.round(order2.getTime()+30) + '\n' +
				"Доставляет " + transport.getName() +'\n');

		stringBuilder.append("Путь до первого заказа:\n");
		for (Road road : way1) {
			stringBuilder.append(road.toString() + "\n");
		}
		stringBuilder.append("Путь до второго заказа:\n");
		for (Road road : way2) {
			stringBuilder.append(road.toString() + "\n");
		}
		logger.info(stringBuilder);
	}

	/**
	 * Запрашивет у пользоватеся начальную или конечную точку
	 */
	public Point askPoint(){
		int numberOfPoint = -1;
		String inputString;
		Point location=null;
		while (location==null) {
			try {
				System.out.println("Выберите номер точки для пиццерии:");
				//Вывод всех существующих точек
				for (Point point : model.getPoints()) {
					System.out.println(point.toString());
				}
				//Проверка, является ли строка числом
				inputString = scanner.nextLine();
				numberOfPoint = Integer.parseInt(inputString);
				//Проверка существет ли данная точка
				if (numberOfPoint <= 0 || numberOfPoint > model.getPoints().size()) {
					logger.error("Данной точки не существует");
				} else {
					location = model.getPoints().get(numberOfPoint - 1);
				}
			} catch (NumberFormatException e) {
				logger.error("Введите число");
			}
		}

		logger.info("Местонахождение пиццерии - " + location.toString());
		return location;
	}

	/**
	 * Выводит сообщение об ошибке
	 */
	public void error(){
		logger.error("Не возможно доставлять пиццу так далеко, измените настройки");
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		FlowPane fp = new FlowPane(50,50);
		fp.setOrientation(Orientation.VERTICAL);
		fp.setAlignment(Pos.BASELINE_CENTER);
		Scene scene = new Scene(fp, 1000, 500);
		primaryStage.setTitle("Доставка пиццы");
		primaryStage.setScene(scene);

		//menubar
		MenuBar menuBar = new MenuBar();
		Menu menuAbout = new Menu("О программе");
		menuBar.getMenus().addAll(menuAbout);
		menuBar.useSystemMenuBarProperty();
		menuBar.setPrefWidth(fp.getWidth());
		fp.getChildren().add(menuBar);

		menuAbout.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Stage stage = new Stage();
				Scene scene = new Scene(new VBox());
				stage.setTitle("popup");
				stage.setScene(scene);
				stage.show();
			}
		});


		/*
		* order part
	 	*/

		//textfield
		HBox orderDetailBox = new HBox(50);
		orderDetailBox.setPadding(new Insets(0,20,20,20));
		Label placeOrderLabel = new Label("В какую точку заказ:");
		final TextField textWhere = new TextField();
		textWhere.setPrefWidth(100);
		orderDetailBox.getChildren().add(placeOrderLabel);
		orderDetailBox.getChildren().add(textWhere);
		fp.getChildren().add(orderDetailBox);

		textWhere.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

			}
		});

		//button
		HBox makeOrderBox = new HBox(50);
		makeOrderBox.setPadding(new Insets(0,20,20,20));
		final Label orderStatusLabel = new Label("Сделайте заказ");
		makeOrderBox.getChildren().add(orderStatusLabel);

		Button orderButton = new Button("Сделать заказ");
		makeOrderBox.getChildren().add(orderButton);

		orderButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				orderStatusLabel.setText("Заказ поступил в систему");
				textWhere.setText("");
			}
		});

		fp.getChildren().add(makeOrderBox);

		/*
		* map
		* */
		Image mapImage = new Image("file: /mappicture.jpg");
		//Image mapImage = new Image(getClass().getResourceAsStream("file: /Users/asinecynalake/IdeaProjects/JavaLab/src/main/resources/mappicture.jpg"));
		ImageView mapView = new ImageView(mapImage);
		mapView.setCache(true);
		mapView.setX(1000);
		mapView.setImage(mapImage);
		fp.getChildren().add(mapView);
		System.out.println("Image loaded? " + !mapImage.isError());

		//separator
		Separator sep = new Separator();
		sep.prefWidth(fp.getHeight());
		fp.getChildren().add(sep);

		//order info
		Label newOrderLabel = new Label("Заказ поступил в точку: ");
		newOrderLabel.setText(textWhere.getText());
		fp.getChildren().add(newOrderLabel);

		primaryStage.show();
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				Platform.exit();
				System.exit(0);
			}
		});
	}

	@Override
	public void run()  {
		Application.launch();

	}
}
