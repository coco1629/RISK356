module group.riskgame.Application {
    requires javafx.controls;
    requires javafx.fxml;

    requires javafx.media;
    requires org.kordamp.bootstrapfx.core;
    requires java.datatransfer;
    requires java.desktop;
    requires java.sql;
    requires org.slf4j;

    opens group.riskgame.Application to javafx.fxml;
    exports group.riskgame.Application;

    opens group.riskgame.Application.Connection to javafx.fxml;
    exports group.riskgame.Application.Connection;

    opens group.riskgame.Application.View to javafx.fxml;
    exports group.riskgame.Application.View;

//    exports group.riskgame.Application.Main;
//    opens group.riskgame.Application.Main to javafx.fxml;


}