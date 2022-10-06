package View;

import Model.Country;
import Model.CountryPath;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.css.*;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.event.WeakEventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.*;

import static javafx.scene.input.MouseEvent.*;

public class SvgUtil extends Region {

    private Pane pane;
    private Pane unitPane;
    private Group group;
    private String pathName = "map/Risk_game_board.svg";
    private HashMap<String, CountryPath> countryPathHashMap = new HashMap<>();

    private static String pressedColorCode = "#ff9900";

    /**
     * SVG and Country specific fields
     */
    private ObjectProperty<Country> selectedCountry;
    private Country formerSelectedCountry;
    private Map<String, List<CountryPath>> countryPaths;

    protected EventHandler<MouseEvent> _mouseEnterHandler;
    protected EventHandler<MouseEvent> _mousePressHandler;
    protected EventHandler<MouseEvent> _mouseReleaseHandler;
    protected EventHandler<MouseEvent> _mouseExitHandler;
    private EventHandler<MouseEvent> mouseEnterHandler;
    private EventHandler<MouseEvent> mousePressHandler;
    private EventHandler<MouseEvent> mouseReleaseHandler;
    private EventHandler<MouseEvent> mouseExitHandler;

    private CountryPath selectedPath;


    public SvgUtil(){
        pane = new Pane();
        unitPane = new Pane();
        group = new Group();
//        countryPaths = createCountryPaths();
        selectedCountry = new ObjectPropertyBase<Country>() {
            @Override
            public Object getBean() {
                return SvgUtil.this;
            }

            @Override
            public String getName() {
                return "selectedCountry";
            }
        };

        /**
         * Set event handlers
         */
        _mouseEnterHandler = evt -> handleMouseEvent(evt, mouseEnterHandler);
        _mousePressHandler = evt -> handleMouseEvent(evt, mousePressHandler);
        _mouseReleaseHandler = evt -> handleMouseEvent(evt, mouseReleaseHandler);
        _mouseExitHandler = evt -> handleMouseEvent(evt, mouseExitHandler);

        loadPath();
    }

    public Pane getUnitPane() {
        return unitPane;
    }

    public void setUnitPane(Pane unitPane) {
        this.unitPane = unitPane;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public void loadPath(){
        // Create a DOM factory object
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try{
            // DocumentBuilder object
            DocumentBuilder builder = factory.newDocumentBuilder();
            // Disable DTD validation to prevent network blocking
            builder.setEntityResolver(
                    (publicId, systemId) -> new InputSource(new StringReader(""))
            );
            Color fill = getFillColor();
            Color stroke = getStrokeColor();

            // Get the document object
            Document d = builder.parse(SvgUtil.class.getClassLoader().getResourceAsStream(pathName));
            for(int i = 1; i < 43;i++){
                // Get the path element node collection;Fetch each element
                Node node = d.getElementsByTagName("path").item(i);
                String content = node.getAttributes().getNamedItem("d").getNodeValue();
                String name =  node.getAttributes().getNamedItem("id").getNodeValue();
                Country country = Country.valueOf(name);
                CountryPath path = new CountryPath(name,content,0);
                countryPathHashMap.put(name, path);

//                path.setOnMouseEntered(new WeakEventHandler<>(_mouseEnterHandler));
                path.setOnMousePressed(new WeakEventHandler<>(_mousePressHandler));
//                path.setOnMouseReleased(new WeakEventHandler<>(_mouseReleaseHandler));
//                path.setOnMouseExited(new WeakEventHandler<>(_mouseExitHandler));

                Circle circle = new Circle(path.getText().getX() + 2,path.getText().getY() -5,12);
                circle.setFill(Color.WHITE);
                circle.setStrokeWidth(1);
                circle.setStroke(Color.BLACK);
                circle.setPickOnBounds(false);
                circle.setMouseTransparent(true);
                unitPane.getChildren().add(circle);

                Text text = path.getText();
                text.setPickOnBounds(false);
                text.setMouseTransparent(true);
                unitPane.getChildren().add(text);
                unitPane.setVisible(true);

                path.setStroke(stroke);
                pane.getChildren().add(path);
                path.setFill(null == country.getColor() ? fill : country.getColor());
                pane.setVisible(true);
            }
            group.getChildren().add(pane);
            group.getChildren().add(unitPane);
            getChildren().setAll(group);
            this.setPickOnBounds(false);
            pane.setPickOnBounds(false);
            unitPane.setPickOnBounds(false);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public Pane getPane() {
        return pane;
    }

    public void setPane(Pane pane) {
        this.pane = pane;
    }

//    public void setController(GameController controller){
//        this.controller = controller;
//    }

    private void handleMouseEvent(MouseEvent evt, EventHandler<MouseEvent> eventHandler) {
        final CountryPath countryPath = (CountryPath) evt.getSource();
        selectedPath = countryPath;
        final String countryName = countryPath.getName();
//        System.out.println(countryName);
        final Country country = Country.valueOf(countryName);
//        System.out.println(country);
        final CountryPath paths = countryPathHashMap.get(countryName);
//        System.out.println(paths);
        final EventType<? extends MouseEvent> eventType = evt.getEventType();

//        if (MOUSE_ENTERED == eventType){
//            if (isHoverEnabled()){
//                Color color = isSelectionEnabled() && country.equals(getSelectedCountry()) ? getSelectedColor()
//                        : getHoverColor();
//                paths.setFill(color);
////                for (SVGPath path:paths){
////                    path.setFill(color);
////                }
//            }
        if (MOUSE_PRESSED == eventType) {
//            paths.setStroke();

            if(paths.isOccupied()){
                if(paths.getStrokeWidth() == 3)
                    paths.setStrokeWidth(1);
                else
                    paths.setStrokeWidth(3);
                return;
            }

            if(paths.isSelect()){
//            if (isSelectionEnabled()) {
                Color color;
                if (null == getSelectedCountry()) {
                    setSelectedCountry(country);
                    color = Color.WHITE;
                } else {
                    color = null == getSelectedCountry().getColor() ? getFillColor() : getSelectedCountry().getColor();
                }
                paths.setFill(color);
                paths.setSelect(false);
                paths.setStrokeWidth(1);
//                System.out.println("unselected You occupied " + countryName);
//                setSelectionEnabled(false);
//                for (SVGPath path:countryPaths.get(getSelectedCountry().getName())){
//                    path.setFill(color);
//                }
            } else {
                System.out.println(country);
                setSelectedCountry(country);
                paths.setSelect(true);
//                setSelectionEnabled(true);
                paths.setFill(Color.web(pressedColorCode));

//                System.out.println("You occupied " + countryName);
//                if (isHoverEnabled()) {
//                    paths.setFill(getPressedColor());
//                }
            }
//            System.out.println("You occupied " + countryName);
        }

//        }else if (MOUSE_RELEASED == eventType){
//            Color color;
//            if (isSelectionEnabled()){
//                if (formerSelectedCountry == country){
//                    setSelectedCountry(null);
//                    color = null == country.getColor() ? getFillColor() : country.getColor();
//                }else {
//                    setSelectedCountry(country);
//                    color = getSelectedColor();
//                }
//                formerSelectedCountry = getSelectedCountry();
//            }else {
//                color = getHoverColor();
//            }
//            setFillAndStroke();
//            if (isHoverEnabled()){
//                paths.setFill(color);
//            }
//        else if (MOUSE_EXITED == eventType){
//            if (isHoverEnabled()){
//                Color color = isSelectionEnabled() && country.equals(getSelectedCountry()) ? getSelectedColor()
//                        : getFillColor();
//                paths.setFill(null == country.getColor() || country == getSelectedCountry() ? color : country.getColor());
//            }
//        }
//        if (null != eventHandler){
//            eventHandler.handle(evt);
//        }
    }


    /**
     *Getters and setters
     */
    public void setMouseEnterHandler(final EventHandler<MouseEvent> eventHandler) {
    mouseEnterHandler = eventHandler;
}

    public void setMousePressHandler(final EventHandler<MouseEvent> eventHandler) {
        mousePressHandler = eventHandler;
    }

    public void setMouseReleaseHandler(final EventHandler<MouseEvent> eventHandler) {
        mouseReleaseHandler = eventHandler;
    }
    public Country getSelectedCountry() {
        return selectedCountry.get();
    }

    public Color getFillColor() {
        return Color.WHITE;
    }
    public Color getStrokeColor() {
        return Color.BLACK;
    }

    public void setSelectedCountry(final Country country) {
        selectedCountry.set(country);
    }

    public void setPathColor(Color color,Country country){
        CountryPath paths = countryPathHashMap.get(country.getName());
//        System.out.println(color);
//        System.out.println(paths);
        paths.setOccupied(true);
        paths.setFill(color);
        paths.getText().setText(String.valueOf(country.getPopulation()));
    }

    public CountryPath getSelectedPath() {
        return selectedPath;
    }

    public void setSelectedPath(CountryPath selectedPath) {
        this.selectedPath = selectedPath;
    }

    public static String getPressedColorCode() {
        return pressedColorCode;
    }

    public static void setPressedColorCode(String pressedColorCode) {
        SvgUtil.pressedColorCode = pressedColorCode;
    }

    public void setCountryTroops(Country country, int num){
        CountryPath paths = countryPathHashMap.get(country.getName());
        paths.getText().setText(String.valueOf(num));
    }
}
