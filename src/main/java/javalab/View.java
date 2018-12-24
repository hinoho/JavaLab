package javalab;

import java.util.List;
import java.util.Scanner;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.*;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
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
public class View extends Application{
	private static Model model;
	private static Controller controller;
	private static Scanner scanner = new Scanner(System.in);
	private final static Logger logger = Logger.getLogger(View.class);

	/**
	 * Создает новый экземпляр представления
	 *
	 * @param model - модель
	 */

	public View(Model model, String... param) {
		this.model = model;
		if (param.length > 0 && param[0].equals("-d")) {
			logger.setLevel(Level.INFO);
		} else {
			logger.setLevel(Level.ERROR);
		}
	}

	public View() {

	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	/**
	 * Выводит найденный путь
	 *
	 * @param way       - путь
	 * @param order     - заказ
	 * @param transport - доставщик
	 * @param time      - время доставки
	 */
	public void show(List<Road> way, Order order, Delivering transport, double time) {
		StringBuilder stringBuilder = new StringBuilder("Заказ №" + order.getId() + '\n' +
				"Доставка в точку " + order.getLocation().getId() + '\n' +
				"Будет доставлен в " + getTime(time) + '\n' +
				"Должен быть доставлен не позднее " + getTime(order.getTime() + 30000) + '\n' +
				"Доставляет " + transport.getName() + '\n');

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
						"Будет доставлен в " + getTime(time1) + '\n' +
						"Должен быть доставлен не позднее " +getTime(order1.getTime() + 30000) + '\n' +
						"Заказ №" + order2.getId() + ":" + '\n' +
						"Доставка в точку " + order2.getLocation().getId() + '\n' +
						"Будет доставлен в " + getTime(time2) + '\n' +
						"Должен быть доставлен не позднее " + getTime(order2.getTime() + 30000) + '\n' +
						"Доставляет " + transport.getName() + '\n');

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
	public Point askPoint() {
		int numberOfPoint = -1;
		String inputString;
		Point location = null;
		while (location == null) {
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
	public void error() {
		logger.error("Не возможно доставлять пиццу так далеко, измените настройки");
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		final TableView<Model.Data> table = new TableView<Model.Data>();
		FlowPane fp = new FlowPane(20, 20);
		fp.setCache(false);
		fp.setOrientation(Orientation.VERTICAL);
		fp.setAlignment(Pos.BASELINE_CENTER);
		Scene scene = new Scene(fp, 800, 800);
		primaryStage.setTitle("Доставка пиццы");
		primaryStage.setScene(scene);

		//menubar
		MenuBar menuBar = new MenuBar();
		Menu menuAbout = new Menu("Помощь");
		menuBar.getMenus().addAll(menuAbout);
		MenuItem menuAboutItem = new MenuItem("О программе");
		menuAbout.getItems().add(menuAboutItem);
		menuBar.useSystemMenuBarProperty();
		menuBar.setPrefWidth(scene.getWidth());
		fp.getChildren().add(menuBar);

		menuAbout.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				//Group fp = new Group();
				FlowPane fp = new FlowPane();
				fp.setAlignment(Pos.CENTER);
				fp.setOrientation(Orientation.VERTICAL);
				Stage stage = new Stage();
				Scene scene = new Scene(fp, 300,100, Color.WHITESMOKE);
				stage.setTitle("О программе");
				stage.setScene(scene);
				stage.show();

				Label l1 = new Label("Программа Доставка Пиццы v1");
				Label l2 = new Label("Выполнили: ");
				Label l3 = new Label("Кагирова Альфия");
				Label l4 = new Label("Пинижанинова Анастасия");
				Label l5 = new Label("2018");
				fp.getChildren().addAll(l1,l2,l3,l4,l5);
			}
		});

		/*
		 * order part
		 */

		//textfield
		HBox orderDetailBox = new HBox(50);
		orderDetailBox.setPadding(new Insets(0, 20, 20, 20));
		Label placeOrderLabel = new Label("В какую точку заказ:");
		final TextField textWhere = new TextField();
		textWhere.setPrefWidth(100);
		orderDetailBox.getChildren().add(placeOrderLabel);
		orderDetailBox.getChildren().add(textWhere);
		fp.getChildren().add(orderDetailBox);


		//button
		HBox makeOrderBox = new HBox(50);
		makeOrderBox.setPadding(new Insets(0, 20, 20, 20));
		final Label orderStatusLabel = new Label("Сделайте заказ");
		makeOrderBox.getChildren().add(orderStatusLabel);

		final Button orderButton = new Button("Сделать заказ");
		makeOrderBox.getChildren().add(orderButton);

		final Label errLabel = new Label("Неправильно введена точка!");
		errLabel.setVisible(false);
		makeOrderBox.getChildren().add(errLabel);
		orderButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (!textWhere.getText().equals("") && (Integer.valueOf(textWhere.getText()) >0) & (Integer.valueOf(textWhere.getText()) < 8) && (textWhere.getText().length() != 0)) {
					orderStatusLabel.setText("Заказ поступил в систему");
					controller.addOrder(Integer.valueOf(textWhere.getText()));
					logger.info("added order");
					textWhere.setText("");
					controller.run();
					errLabel.setVisible(false);
					table.setItems(FXCollections.observableArrayList(model.getData()));
				}
				else
					errLabel.setVisible(true);
				textWhere.setText("");
			}
		});


		fp.getChildren().add(makeOrderBox);

		/*
		 * map
		 * */

		VBox mapox = new VBox();
		Image mapImage = new Image("file: ~/JavaLab/src/main/resources/mappp.jpg");
		ImageView mapView = new ImageView(mapImage);
		mapView.setCache(true);
		mapView.setX(1000);
		mapView.setImage(mapImage);
		fp.getChildren().add(mapView);
		System.out.println("Image loaded? " + !mapImage.isError());
		fp.getChildren().add(mapox);


		//separator
		Separator sep = new Separator();
		sep.prefWidth(fp.getHeight());
		fp.getChildren().add(sep);

		//order info
		Label newOrderLabel = new Label("Заказ поступил в точку: ");
		newOrderLabel.setText(textWhere.getText());
		fp.getChildren().add(newOrderLabel);



		//fp.getChildren().addAll(orderBpx);

		/*/
		TableView
		 */
		VBox orderStatusBox = new VBox();
		orderStatusBox.setPadding(new Insets(0,0,20,40));
		fp.getChildren().add(orderStatusBox);


		//Button updateButton = new Button("Обновить данные");
		//fp.getChildren().addAll(updateButton);
		//orderStatusBox.getChildren().add(updateButton);
		table.setMaxHeight(300);
		table.setMaxWidth(800);

		//table.setPadding(new Insets(20,20,20,20));
		TableColumn<Model.Data, Integer> number = new TableColumn<Model.Data, Integer>("Номер заказа");
		number.setMinWidth(100);

		TableColumn<Model.Data, String> start = new TableColumn<Model.Data, String>("Начальная точка");
		start.setMinWidth(120);

		TableColumn<Model.Data, String> end = new TableColumn<Model.Data, String>("Конечная точка");
		end.setMinWidth(120);

		TableColumn<Model.Data, String> courier = new TableColumn<Model.Data, String>("Транспорт");
		courier.setMinWidth(150);

		TableColumn<Model.Data, String> time = new TableColumn<>("Время");
		time.setMinWidth(100);

        TableColumn<Model.Data, String> isDone = new TableColumn<>("Статус");
        isDone.setMinWidth(50);

		//TableColumn<Model.Data, Boolean> finished = new TableColumn<Order, Boolean>("Закончен");

		number.setCellValueFactory(new PropertyValueFactory<Model.Data, Integer>("number"));
		start.setCellValueFactory(new PropertyValueFactory<Model.Data, String>("start"));
		end.setCellValueFactory(new PropertyValueFactory<Model.Data, String>("end"));
		courier.setCellValueFactory(new PropertyValueFactory<Model.Data, String>("courier"));
		time.setCellValueFactory(new PropertyValueFactory<Model.Data, String>("time"));
		isDone.setCellValueFactory(new PropertyValueFactory<Model.Data, String>("isDone"));
		table.setItems(FXCollections.observableArrayList(model.getData()));
		table.getColumns().addAll(number, start, end, courier, time);

		orderStatusBox.getChildren().add(table);


		primaryStage.show();
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				Platform.exit();
				System.exit(0);
			}
		});
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

	public void run() {
		Application.launch();
	}

    public String getTime(double time){
        long t=Math.round(time/1000);
        return t/60%60 + ":" + t%60;
    }

}

