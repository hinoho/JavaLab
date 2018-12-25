package javalab;

import java.io.File;
import java.util.List;
import java.util.Scanner;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
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
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javafx.stage.WindowEvent;
import javafx.util.Duration;
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
		Scene scene = new Scene(fp, 1000, 1000);
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
		HBox orderDetailBox = new HBox(30);
		orderDetailBox.setPadding(new Insets(0, 20, 20, 20));


		Label placeOrderLabel = new Label("В какую точку заказ:");
		final TextField textWhere = new TextField();
		textWhere.setPrefWidth(100);
		orderDetailBox.getChildren().add(placeOrderLabel);
		orderDetailBox.getChildren().add(textWhere);
		fp.getChildren().add(orderDetailBox);

		HBox orderbox = new HBox();
		orderbox.setMaxHeight(300);
		fp.getChildren().add(orderbox);

		//button
		HBox makeOrderBox = new HBox(30);
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
				}
				else
					errLabel.setVisible(true);
				textWhere.setText("");
			}
		});

		orderbox.getChildren().add(makeOrderBox);


		final Label listTitle = new Label("Выполненные заказы");
		makeOrderBox.getChildren().add(listTitle);

		final ListView<String> listView = new ListView(FXCollections.observableArrayList(controller.getFinishedOrders()));
		listView.setMaxWidth(100);
		listView.setMaxHeight(100);
		orderDetailBox.getChildren().add(listView);

		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), ae -> listView.setItems(FXCollections.observableArrayList(controller.getFinishedOrders()))));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();

		Label checkOrder = new Label("Введите номер проверенного заказа:");
		final TextField checkedOrder = new TextField();
		checkedOrder.setPrefWidth(100);
		makeOrderBox.getChildren().add(checkOrder);
		makeOrderBox.getChildren().add(checkedOrder);

		final Button checkButton = new Button("Проверить заказ");
		makeOrderBox.getChildren().add(checkButton);

		final Label errLabel2 = new Label("Неправильно введен номер заказа");
		errLabel2.setVisible(false);
		makeOrderBox.getChildren().add(errLabel2);
		checkButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (!checkedOrder.getText().equals("") && (checkedOrder.getText().length() != 0) && listView.getItems().contains("Заказ №" + checkedOrder.getText())) {
					checkOrder.setText("Заказ отмечен");

					logger.info("order checked");
					textWhere.setText("");

					controller.checkOrder(checkedOrder.getText());
					controller.updateModelData();
					table.setItems(FXCollections.observableArrayList(model.getData()));
					errLabel.setVisible(false);
				}
				else
					errLabel.setVisible(true);
				textWhere.setText("");
			}
		});
		/*
		 * map
		 * */

		VBox mapox = new VBox();
		//mapox.setPadding(new Insets(0,0,0,20));
		Image mapImage = new Image(new File("src/main/resources/map.png").toURI().toString());
		ImageView mapView = new ImageView(mapImage);
		mapView.setCache(true);
		mapView.setViewport(new Rectangle2D(-20,0,400,200));
		mapView.setImage(mapImage);
		mapox.getChildren().add(mapView);
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
		orderStatusBox.setPadding(new Insets(0,0,0,20));
		fp.getChildren().add(orderStatusBox);


		//Button updateButton = new Button("Обновить данные");
		//fp.getChildren().addAll(updateButton);
		//orderStatusBox.getChildren().add(updateButton);
		//table.setMaxHeight(200);
		table.setMaxWidth(672);


		//table.setPadding(new Insets(20,20,20,20));
		TableColumn<Model.Data, Integer> number = new TableColumn<Model.Data, Integer>("Номер");
		number.setMinWidth(50);

		TableColumn<Model.Data, String> start = new TableColumn<Model.Data, String>("Начальная точка");
		start.setMinWidth(120);

		TableColumn<Model.Data, String> end = new TableColumn<Model.Data, String>("Конечная точка");
		end.setMinWidth(120);

		TableColumn<Model.Data, String> courier = new TableColumn<Model.Data, String>("Транспорт");
		courier.setMinWidth(150);

		TableColumn<Model.Data, String> time = new TableColumn<>("Время");
		time.setMinWidth(120);

        TableColumn<Model.Data, String> status = new TableColumn<>("Закончен");

		number.setCellValueFactory(new PropertyValueFactory<Model.Data, Integer>("number"));
		start.setCellValueFactory(new PropertyValueFactory<Model.Data, String>("start"));
		end.setCellValueFactory(new PropertyValueFactory<Model.Data, String>("end"));
		courier.setCellValueFactory(new PropertyValueFactory<Model.Data, String>("courier"));
		time.setCellValueFactory(new PropertyValueFactory<Model.Data, String>("time"));
		status.setCellValueFactory(new PropertyValueFactory<Model.Data, String>("status"));
		status.setCellFactory(column -> {
			return new 	TableCell<Model.Data, String>() {
				@Override
				protected void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);

					setText(empty ? "" : getItem().toString());
					setGraphic(null);

					TableRow<Model.Data> currentRow = getTableRow();

					if (!isEmpty()) {

						if(item.equals("нет"))
							currentRow.setStyle("-fx-background-color:lightcoral");
						else
							currentRow.setStyle("-fx-background-color:lightgreen");
					}
				}
			};

		});

		table.setItems(FXCollections.observableArrayList(model.getData()));
		table.getColumns().addAll(number, start, end, courier, time, status);
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

