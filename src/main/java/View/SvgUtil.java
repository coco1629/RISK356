package View;

import Model.Map;
import javafx.scene.Group;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.io.StringReader;
import java.util.HashMap;

public class SvgUtil extends Region {

    private Pane pane;

    private String pathName = "map/Risk_game_board.svg";

    private HashMap<String, CountryPath> countryPathHashMap = new HashMap<>();

    public SvgUtil(){
        pane = new Pane();
        loadPath();
    }

    public void loadPath(){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try{
            DocumentBuilder builder = factory.newDocumentBuilder();
            builder.setEntityResolver(
                    (publicId, systemId) -> new InputSource(new StringReader(""))
            );
            Document d = builder.parse(SvgUtil.class.getClassLoader().getResourceAsStream(pathName));
            for(int i = 1; i < 43;i++){
                Node node = d.getElementsByTagName("path").item(i);
                String content = node.getAttributes().getNamedItem("d").getNodeValue();
                String name =  node.getAttributes().getNamedItem("id").getNodeValue();
                CountryPath path = new CountryPath(name,content,0);
                countryPathHashMap.put(name, path);
                path.setStroke(Color.BLACK);
                pane.getChildren().add(path);
                path.setFill(Color.web("#f4f4f4"));
                pane.setVisible(true);
            }
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



}
