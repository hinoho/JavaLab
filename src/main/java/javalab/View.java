package javalab;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import javalab.map.Road;
import javalab.pizzeria.Delivering;
import javalab.pizzeria.Order;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;

import java.io.File;
import java.util.List;
import java.util.Scanner;

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
	public void show(List<Road> way, Order order, Delivering transport, double time , double delay) {
		StringBuilder stringBuilder = new StringBuilder("Заказ №" + order.getId() + '\n' +
				"Доставка в точку " + order.getLocation().getId() + '\n' +
				"Будет доставлен в " + getTime(time) + '\n' +
				"Должен быть доставлен не позднее " + getTime(order.getTime() + 30000) + '\n' +
				"Доставляет " + transport.getName() + '\n'+
				"Задержка " + delay + '\n');

		stringBuilder.append("Путь:\n");
		for (Road road : way) {
			stringBuilder.append(road.toString() + "\n");
		}
		logger.info(stringBuilder);
	}

	public void show(List<Road> way1, List<Road> way2, Order order1, Order order2, Delivering transport, double time1, double time2, double delay) {


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
						"Доставляет " + transport.getName() + '\n'+
						"Задержка " + delay + '\n');


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
		primaryStage.setAlwaysOnTop(true);
		primaryStage.setX(0);
		final TableView<Model.Data> table = new TableView<Model.Data>();
		FlowPane fp = new FlowPane();
		fp.setCache(false);
		fp.setOrientation(Orientation.VERTICAL);
		fp.setAlignment(Pos.BASELINE_CENTER);
		Scene scene = new Scene(fp, 700, 1000);
		primaryStage.setTitle("Доставка пиццы");
		primaryStage.setScene(scene);

		//menubar
		MenuBar menuBar = new MenuBar();
		Menu menuAbout = new Menu("Помощь");
		Menu menuFile = new Menu("Файл");

		menuBar.getMenus().addAll(menuFile, menuAbout);

		MenuItem menuUploadFile = new MenuItem("Загрузить файл");
		MenuItem menuAboutItem = new MenuItem("О программе");
		menuAbout.getItems().add(menuAboutItem);
		menuFile.getItems().add(menuUploadFile);

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


		menuUploadFile.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				FileChooser fc = new FileChooser();
				fc.setTitle("Откройте файл с картой");
				File file = fc.showOpenDialog(primaryStage);
				if (file != null) {
					controller.downloadFromFile(file);

				}
			}
		});



		/*
		 * order part
		 */

		//textfield
		HBox orderbox = new HBox();
		fp.getChildren().add(orderbox);

		VBox makeorderbox = new VBox(10);
		makeorderbox.setPadding(new Insets(10,20,0,20));
		orderbox.getChildren().add(makeorderbox);
		Label placeOrderLabel = new Label("В какую точку заказ:");
		placeOrderLabel.setPadding(new Insets(10,0,0,0));
		final TextField textWhere = new TextField();
		textWhere.setPrefWidth(100);
		makeorderbox.getChildren().add(placeOrderLabel);
		makeorderbox.getChildren().add(textWhere);


		//button



		final Button orderButton = new Button("Сделать заказ");
		makeorderbox.getChildren().add(orderButton);

		final Label errLabel = new Label("Заказ поступил в систему");
		errLabel.setPadding(new Insets(10,0,10,0));
		errLabel.setStyle("-fx-text-fill: #000000");
		errLabel.setVisible(false);
		makeorderbox.getChildren().add(errLabel);
		orderButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (!textWhere.getText().equals("") && (Integer.valueOf(textWhere.getText()) >1) & (Integer.valueOf(textWhere.getText()) < 8) && (textWhere.getText().length() != 0)) {
					errLabel.setText("Заказ поступил в систему");
					errLabel.setStyle("-fx-text-fill: black");
					errLabel.setVisible(true);
					//errLabel.setStyle("-fx-text-fill: black");
					controller.addOrder(Integer.valueOf(textWhere.getText()));
					logger.info("added order");
					textWhere.setText("");
					controller.run();
					errLabel.setVisible(false);
					table.setItems(FXCollections.observableArrayList(model.getData()));
				}
				else
					errLabel.setText("Неправильно введена точка");
					errLabel.setStyle("-fx-text-fill: red");
					errLabel.setVisible(true);
				textWhere.setText("");
			}
		});




		VBox madeorders = new VBox(10);
		madeorders.setPadding(new Insets(0,20,0,20));
		orderbox.getChildren().add(madeorders);
		final Label listTitle = new Label("Выполненные заказы");
		listTitle.setPadding(new Insets(10,0,10,0));
		madeorders.getChildren().add(listTitle);

		VBox deliveredOrders = new VBox(10);
		orderbox.getChildren().add(deliveredOrders);
		Label delivered = new Label("Доставленные заказы: ");
		delivered.setPadding(new Insets(10,0,10,0));
		deliveredOrders.getChildren().add(delivered);

		final ListView<String> listView = new ListView(FXCollections.observableArrayList(controller.getFinishedOrders()));
		listView.setMaxWidth(200);
		listView.setMaxHeight(100);
		deliveredOrders.getChildren().add(listView);

		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent ae) {
				listView.setItems(FXCollections.observableArrayList(controller.getFinishedOrders()));
				controller.updateModelData();
				table.setItems(FXCollections.observableArrayList(model.getData()));
				table.refresh();
			}
		}));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();

		Label checkOrder = new Label("Введите номер проверенного заказа:");
		checkOrder.setPadding(new Insets(10,0,10,0));
		final TextField checkedOrder = new TextField();
		checkedOrder.setPrefWidth(50);
		madeorders.getChildren().add(checkedOrder);

		final Button checkButton = new Button("Завершить заказ");
		madeorders.getChildren().add(checkButton);

		final Label errLabel2 = new Label("Неправильно введен номер заказа");
		errLabel2.setPadding(new Insets(10,0,10,0));
		errLabel2.setStyle("-fx-text-fill: red");
		errLabel2.setVisible(false);
		madeorders.getChildren().add(errLabel2);

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
					table.refresh();
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

		/////////
		VBox mapox = new VBox();
		mapox.setMinHeight(200);
		//mapox.setPadding(new Insets(0,0,0,20));
		//Image mapImage = new Image(new File("src/main/resources/map.png").toURI().toString());
		//ImageView mapView = new ImageView(mapImage);
		//mapView.setCache(true);
		//mapView.setViewport(new Rectangle2D(-20,0,400,200));
		//mapView.setImage(mapImage);
		//mapox.getChildren().add(mapView);
		//System.out.println("Image loaded? " + !mapImage.isError());

		//////////////////
		Graph mapGrap = new SingleGraph("Карта города");
		mapGrap.setStrict(true);

		String styleSheet =
				"node {" + "fill-color: black, green;" + "}";
		mapGrap.addAttribute("ui.stylesheet", styleSheet);


		mapGrap.addNode("1");
		mapGrap.getNode("1").setAttribute("ui.label", 1);
		mapGrap.addNode("2");
		mapGrap.getNode("2").setAttribute("ui.label", 2);
		mapGrap.addNode("3");
		mapGrap.getNode("3").setAttribute("ui.label", 3);
		mapGrap.addNode("4");
		mapGrap.getNode("4").setAttribute("ui.label", 4);
		mapGrap.addNode("5");
		mapGrap.getNode("5").setAttribute("ui.label", 5);
		mapGrap.addNode("6");
		mapGrap.getNode("6").setAttribute("ui.label", 6);
		mapGrap.addNode("7");
		mapGrap.getNode("7").setAttribute("ui.label", 7);

		mapGrap.addEdge("12", "1", "2");
		mapGrap.getEdge("12").setAttribute("ui.label", 10);
		mapGrap.addEdge("13", "1", "3");
		mapGrap.getEdge("13").setAttribute("ui.label", 20);
		mapGrap.addEdge("24", "2", "4");
		mapGrap.getEdge("24").setAttribute("ui.label", 20);
		mapGrap.addEdge("25", "2", "5");
		mapGrap.getEdge("25").setAttribute("ui.label", 20);
		mapGrap.addEdge("34", "3", "4");
		mapGrap.getEdge("34").setAttribute("ui.label", 20);
		mapGrap.addEdge("36", "3", "6");
		mapGrap.getEdge("36").setAttribute("ui.label", 10);
		mapGrap.addEdge("45", "4", "5");
		mapGrap.getEdge("45").setAttribute("ui.label", 15);
		mapGrap.addEdge("47", "4", "7");
		mapGrap.getEdge("47").setAttribute("ui.label", 10);
		mapGrap.addEdge("57", "5", "7");
		mapGrap.getEdge("57").setAttribute("ui.label", 12);
		mapGrap.addEdge("67", "6", "7");
		mapGrap.getEdge("67").setAttribute("ui.label", 20);


		mapGrap.display();

		fp.getChildren().add(mapox);
		///////////


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
		orderStatusBox.setPadding(new Insets(0,20,0,20));
		fp.getChildren().add(orderStatusBox);


		table.setMaxHeight(250);
		table.setMaxWidth(fp.getWidth());


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
							currentRow.setStyle("-fx-background-color:#ff9c9c");
						else if(item.equals("да"))
							currentRow.setStyle("-fx-background-color:#adffa4");
						else
							currentRow.setStyle("-fx-background-color:#fff478");
					}
				}
			};

		});
		table.setRowFactory(tv -> {
			TableRow<Model.Data> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if(event.getClickCount() == 1 && !row.isEmpty()){
					Order order = model.getPizzeria().getOrders().get(row.getItem().getNumber()-1);
					for(Edge edge : mapGrap.getEdgeSet()){
						edge.addAttribute("ui.style", "fill-color: black;");
					}
					for(String road : order.getWay()){
						mapGrap.getEdge(road).addAttribute("ui.style", "fill-color: green;");

					}
				}

			});
			return row;

		});

		table.setItems(FXCollections.observableArrayList(model.getData()));
		table.refresh();
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

