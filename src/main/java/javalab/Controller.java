package javalab;

import javafx.util.Pair;
import javalab.map.*;
import javalab.pizzeria.*;
import org.apache.log4j.Level;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.generate.GnmRandomGraphGenerator;
import org.jgrapht.generate.GraphGenerator;
import org.jgrapht.graph.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.jgrapht.util.SupplierUtil;

/**
 * Контроллер перенаправлет запросы пользователя модели для поиска кратчайшего маршрута и
 * представлению - для вывода информации об этом маршруте
 */
public class Controller{
    final static Logger logger = Logger.getLogger(Controller.class);
    private String dataFile;
    //количества заказов
    private int NUMBER_OF_ORDERS;
    //количество машин
    private int NUMBER_OF_CARS;
    //количество людей
    private int NUMBER_OF_MEN;
    //количество велосипедистов
    private int NUMBER_OF_BICYCLES;
    //Максимальное время доставки
    private int AVG_DELIVERY_TIME;
    //Количество вершин
    private int NUMBER_OF_NODES;
    //Количество ребер
    private int NUMBER_OF_EDGES;
    private double TIME_FOR_STOP = 5000;
    private Model model;
    private View view;
    private Pizzeria pizzeria;

    /**
     * Создает новый экземпляр контроллера
     * @param param
     */
    public Controller(String... param) {
        //Если кстановлен параметр, устанавливается уровень лога DEBUG
        if(param.length > 0 && param[0].equals("-d")){
            logger.setLevel(Level.DEBUG);
        }
        else logger.setLevel(Level.WARN);
        logger.info("loading properties");
        loadProperties();
        logger.info("creating model");
        this.model = new Model();
        logger.info("downloading data from file");
        downloadFromFile(new File(dataFile));
        //generateGraph(NUMBER_OF_NODES,NUMBER_OF_EDGES);
        logger.info("creating view");
        view = new View(model, param);
        view.setController(this);
        logger.info("creating pizzeria");
        model.setPizzeria(new Pizzeria(model.getPoints().get(0)));
        pizzeria = model.getPizzeria();
        //Генерация доставщиков
        logger.info("creating delivers");
        for (int i = 0; i < NUMBER_OF_CARS; i++) {
            pizzeria.addDelivering(new Car("Машина №" + (i+1)));
        }
        for (int i = 0; i < NUMBER_OF_BICYCLES; i++) {
            pizzeria.addDelivering(new Bicycle("Велосепидист №" + (i+1)));
        }
        for (int i = 0; i < NUMBER_OF_MEN; i++) {
            pizzeria.addDelivering(new Man("Человек №" + (i+1)));
        }
        view.main(param);
    }

    /**
     * Загружает настройки из файла
     */
    public void loadProperties(){
        Properties prop = new Properties();
        try {
            InputStream inputStream = new FileInputStream("config.properties");
            prop.load(inputStream);
            dataFile = prop.getProperty("datafile");
            NUMBER_OF_ORDERS = Integer.valueOf(prop.getProperty("NUMBER_OF_ORDERS", "10"));
            NUMBER_OF_CARS = Integer.valueOf(prop.getProperty("NUMBER_OF_CARS", "10"));
            NUMBER_OF_MEN = Integer.valueOf(prop.getProperty("NUMBER_OF_MEN", "5"));
            NUMBER_OF_BICYCLES = Integer.valueOf(prop.getProperty("NUMBER_OF_BICYCLES", "5"));
            AVG_DELIVERY_TIME = Integer.valueOf(prop.getProperty("AVG_DELIVERY_TIME", "5"));
            NUMBER_OF_NODES = Integer.valueOf(prop.getProperty("NUMBER_OF_NODES", "10"));
            NUMBER_OF_EDGES = Integer.valueOf(prop.getProperty("NUMBER_OF_EDGES", "15"));
            inputStream.close();
            logger.info("loading complete");
        }catch (IOException e){
            logger.error("config loading failed", e);
        }
    }

    public void addOrder(int point){
        model.addOrder(point);
    }

//    /**
//     * Генерирует случайный граф
//     * @param numberOfNodes - количество вершин
//     * @param numberOfEdges - количество ребер
//     */
//    public void generateGraph(int numberOfNodes, int numberOfEdges){
//        SimpleWeightedGraph<Point, DefaultWeightedEdge> man = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
//        SimpleWeightedGraph<Point, DefaultWeightedEdge> bicycle = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
//        SimpleWeightedGraph<Point, DefaultWeightedEdge> car = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
//        List<Point> points = new ArrayList<Point>();
//
//        //Создается генератор графов
//        GraphGenerator<Integer, DefaultEdge, Integer> gen = new GnmRandomGraphGenerator<>(numberOfNodes, numberOfEdges, 0, false, false);
//        Graph<Integer, DefaultEdge> graph = new SimpleGraph<>(SupplierUtil.createIntegerSupplier(), SupplierUtil.createDefaultEdgeSupplier(), false);
//        //Генерируется граф
//        gen.generateGraph(graph);
//        //Создаются точки и добавляются на графы
//        for (int i = 0; i < numberOfNodes; ++i) {
//            Point point = new Point(i);
//            points.add(point);
//            man.addVertex(point);
//            bicycle.addVertex(point);
//            car.addVertex(point);
//        }
//
//        int i = 0;
//        Random random = new Random();
//        //На основе сгенерированного графа создаются графы дорог
//        for(DefaultEdge edge : graph.edgeSet()) {
//            List<Transport> types = new ArrayList<>();
//            Double length = random.nextDouble() * 1000;
//            String name = "Улица №" + ++i;
//            //Дороги для машин - 100%
//            types.add(Transport.CAR);
//            //Дороги для людей - 50%
//            if (random.nextInt() % 2 == 0) {
//                types.add(Transport.MAN);
//            }
//            //Дороги для велосипедистов - 33%
//            if (random.nextInt() % 2 == 0) {
//                types.add(Transport.BICYCLE);
//            }
//
//            Point start = new Point(graph.getEdgeSource(edge));
//            Point end = new Point(graph.getEdgeTarget(edge));
//            //Для каждого типа транспорта создается дорога и добавляется на соответсвующий граф
//            for (Transport transport : types) {
//                Road road = new Road(name, transport, length);
//                switch (transport) {
//                    case CAR:
//                        car.addEdge(start, end, road);
//                        car.setEdgeWeight(road, road.getTime());
//                        break;
//                    case BICYCLE:
//                        bicycle.addEdge(start, end, road);
//                        bicycle.setEdgeWeight(road, road.getTime());
//                        break;
//                    case MAN:
//                        man.addEdge(start, end, road);
//                        man.setEdgeWeight(road, road.getTime());
//                        break;
//                }
//            }
//        }
//        model.setMan(man);
//        model.setBicycle(bicycle);
//        model.setCar(car);
//        model.setPoints(points);
//    }

    /**
     * Ищет кратчайший путь между двумя точками
     * @param start - начало маршрута
     * @param end - конец маршрута
     */
    public GraphPath<Point, Road>  findWay(Point start, Point end, Transport transport) {
        //В зависимости от типа транспорта выбирается граф и по методу Дийкстры находится оптимальный путь
        DijkstraShortestPath<Point, Road> path;
        switch (transport){
            case MAN: path = new DijkstraShortestPath<Point, Road>(model.getMan());
                break;
            case BICYCLE: path = new DijkstraShortestPath<Point, Road>(model.getBicycle());
                break;
            case CAR: path = new DijkstraShortestPath<Point, Road>(model.getCar());
                break;
            default: return null;
        }
        return path.getPath(start,end);
    }
    /**
     * Загружет данные из файла
     * @param file - файл
     */
    public void downloadFromFile(File file) {
        //добавление точек
        logger.info("creating graphs");
        SimpleWeightedGraph<Point, DefaultWeightedEdge> man = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
        SimpleWeightedGraph<Point, DefaultWeightedEdge> bicycle = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
        SimpleWeightedGraph<Point, DefaultWeightedEdge> car = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
        List<Point> points = new ArrayList<Point>();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
            while (reader.ready()){
                String[] lines = reader.readLine().split(" ");
                Point[] p = { new Point(Integer.valueOf(lines[0])), new Point(Integer.valueOf(lines[1]))};
                for(Point point : p) {
                    if (!points.contains(point)) {
                        points.add(point);
                        man.addVertex(point);
                        bicycle.addVertex(point);
                        car.addVertex(point);
                    }
                }
                int place = 3;
                List<Transport> types = new ArrayList<>();
                while (lines[place].equals("CAR") || lines[place].equals("BICYCLE") || lines[place].equals("MAN")){
                    types.add(Transport.valueOf(lines[place]));
                    place++;
                }

                StringBuilder name = new StringBuilder();
                for (int i = place; i < lines.length; i++) {
                    name.append(lines[i]).append(" ");
                }
                for(Transport transport : types) {
                    Road road = new Road(name.toString().trim(), transport, Double.valueOf(lines[2]));
                    switch (transport) {
                        case CAR:
                            car.addEdge(p[0], p[1], road);
                            car.setEdgeWeight(road, road.getTime());
                            break;
                        case BICYCLE:
                            bicycle.addEdge(p[0], p[1], road);
                            bicycle.setEdgeWeight(road, road.getTime());
                            break;
                        case MAN:
                            man.addEdge(p[0], p[1], road);
                            man.setEdgeWeight(road, road.getTime());
                            break;
                    }
                }
            }
            reader.close();
            logger.info("downloading completed");
        }catch (IOException e){
            logger.error("loading from file failed", e);
        }
        //создание карты
        model.setMan(man);
        model.setBicycle(bicycle);
        model.setCar(car);
        model.setPoints(points);

    }

    /**
     * Симуляция работы пиццерии
     */
    public void run(){
        int alarm = 0;

        List<Order> orders = pizzeria.getOrders();
        logger.info("simulating delivering");
        Point start = pizzeria.getLocation();
        List<Order> activeOrders = new LinkedList<>();
        boolean isDone = false;
        while (!isDone){
            long currentTime = System.currentTimeMillis();
            for(Order order : orders){
                if(!order.isClose() && order.getTime()<=currentTime && !activeOrders.contains(order)){
                    activeOrders.add(order);
                }
            }
            ListIterator<Order>iterator = activeOrders.listIterator();
            while(iterator.hasNext()){
                Order firstOrder = iterator.next();
                double minTime = firstOrder.getTime() + AVG_DELIVERY_TIME - currentTime;
                if(currentTime+ AVG_DELIVERY_TIME < firstOrder.getTime()){
                    logger.warn("order cannot be delivered in time. Delaying order");
                    firstOrder.delay(AVG_DELIVERY_TIME);
                    alarm++;
                }
                Pair<GraphWalk<Point, Road>, Delivering> pair = getOptimalWay(minTime,start,firstOrder.getLocation());
                GraphWalk<Point, Road> firstWay = pair.getKey();
                Delivering optimalDeliver = pair.getValue();
                if(!firstWay.isEmpty() && iterator.hasNext()){
                    Order secondOrder = iterator.next();
                    //путь от первого заказа
                    GraphWalk<Point, Road> secondWay = (GraphWalk<Point, Road>) findWay(firstOrder.getLocation(), secondOrder.getLocation(), optimalDeliver.getTransport());
                    if(!firstWay.isEmpty() && !secondWay.isEmpty() && (secondWay.getWeight()*1000+firstWay.getWeight()*1000 + TIME_FOR_STOP + currentTime < secondOrder.getTime()*1000 + AVG_DELIVERY_TIME)){
                        iterator.remove();
                        iterator.previous();
                        iterator.remove();
                        view.show(firstWay.getEdgeList(),secondWay.getEdgeList(),firstOrder,secondOrder,optimalDeliver,currentTime+firstWay.getWeight()*1000,currentTime+secondWay.getWeight()*1000);
                        GraphWalk<Point, Road> wayBack = (GraphWalk<Point, Road>) findWay(secondOrder.getLocation(), pizzeria.getLocation(), optimalDeliver.getTransport());
                        optimalDeliver.setTime(currentTime + firstWay.getWeight()*1000 + TIME_FOR_STOP + secondWay.getWeight()*1000 + TIME_FOR_STOP + wayBack.getWeight()*1000);
                        optimalDeliver.setFree(false);
                        firstOrder.setClose(true);
                        secondOrder.setClose(true);
                        firstOrder.setDoneTime(firstOrder.getTime()+firstWay.getWeight()*1000);
                        secondOrder.setDoneTime(secondOrder.getTime()+firstWay.getWeight()*1000+TIME_FOR_STOP+secondWay.getWeight()*1000);
                        model.addData(firstOrder.getId(), start.toString(), firstOrder.getLocation().toString(), getTime(firstOrder.getTime()), optimalDeliver.getName());
                        model.addData(secondOrder.getId(), start.toString(), secondOrder.getLocation().toString(), getTime(secondOrder.getTime()), optimalDeliver.getName());
                    }
                }
                else if(!firstWay.isEmpty()) {
                    iterator.remove();
                    model.addData(firstOrder.getId(), start.toString(), firstOrder.getLocation().toString(), getTime(firstOrder.getTime()), optimalDeliver.getName());
                    view.show(firstWay.getEdgeList(), firstOrder, optimalDeliver, currentTime+firstWay.getWeight()*1000);

                    optimalDeliver.setTime(currentTime + firstWay.getWeight()*2000 + TIME_FOR_STOP);
                    optimalDeliver.setFree(false);
                    firstOrder.setDoneTime(firstOrder.getTime()+firstWay.getWeight()*1000);
                    firstOrder.setClose(true);
                }
            }
            //обновляется статус доставщиков
            for(Delivering delivering : pizzeria.getDelivers()){
                if(!delivering.isFree()){
                    if(currentTime>delivering.getTime()){
                        delivering.setFree(true);
                    }
                }
            }
            if(activeOrders.size()==0)isDone=true;
            if(alarm>pizzeria.getOrders().size()/2){
                logger.warn("Too many alarms. Increase amount of delivers");
            }
        }

    }

    public Pair<GraphWalk<Point, Road>, Delivering> getOptimalWay(double minTime, Point start, Point end ){
        //Для всех видов транспорта расчитывается оптимальный путь
        SimpleWeightedGraph<Point, Road> emptyGraph = new SimpleWeightedGraph<Point, Road>(Road.class);
        GraphWalk<Point, Road> optimalWay = GraphWalk.emptyWalk(emptyGraph);
        Delivering optimalDeliver = null;
        for(Transport transport : Transport.values()){
            GraphWalk<Point, Road> way = (GraphWalk<Point, Road>)findWay(start, end, transport);
            if(way!=null && way.getWeight()>0 && way.getWeight() < minTime) {
                minTime = way.getWeight();
                //Из свободных доставщиковВыбирается доставщик с минимальным путем
                List<Delivering> delivers = model.getPizzeria().getDelivers();
                for(Delivering deliver : delivers){
                    if(deliver.getTransport().equals(transport) && deliver.isFree()){
                        optimalDeliver = deliver;
                        optimalWay = way;
                        break;
                    }
                }
            }
        }
        return new Pair<>(optimalWay,optimalDeliver);
    }

    public String getTime(double time){
        long t=Math.round(time/1000);
        return t/60%60 + ":" + t%60;
    }

    public List<String> getFinishedOrders(){
        return model.getPizzeria().getOrders().stream().filter(order -> (!order.getChecked())&&System.currentTimeMillis() > order.getDoneTime()).map(order -> order.toString()).collect(Collectors.toList());
    }

    public void checkOrder(String number){
        model.getPizzeria().getOrders().get(Integer.valueOf(number)-1).setChecked(true);
    }

    public void updateModelData(){
        List<Order> orders = model.getPizzeria().getOrders();
        for (Model.Data data:  model.getData()) {
            if(orders.get(data.getNumber()-1).getChecked())
                data.setStatus("да");

        }
    }
}