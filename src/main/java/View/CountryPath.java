package View;

import javafx.scene.shape.SVGPath;

public class CountryPath extends SVGPath {

    private final String name;

    public CountryPath(final String name) {
        this.name = name;
    }

    public CountryPath(final String name,final String content,final int unit){
        super();
        this.name = name;
        setContent(content);
    }
}
