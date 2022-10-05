.
├── ./pom.xml
├── ./readme.txt
├── ./risk.iml
├── ./risk.ipr
├── ./risk.iws
├── ./src
│   ├── ./src/main
│   │   ├── ./src/main/java
│   │   │   ├── ./src/main/java/Application
│   │   │   │   └── ./src/main/java/Application/RiskGame.java
│   │   │   ├── ./src/main/java/Connection
│   │   │   │   ├── ./src/main/java/Connection/ClientHandler.java
│   │   │   │   ├── ./src/main/java/Connection/Server.java
│   │   │   │   └── ./src/main/java/Connection/ServerHandler.java
│   │   │   ├── ./src/main/java/Controller
│   │   │   │   └── ./src/main/java/Controller/GameController.java
│   │   │   ├── ./src/main/java/JDBC
│   │   │   │   ├── ./src/main/java/JDBC/DBUtil.java
│   │   │   │   ├── ./src/main/java/JDBC/Test.java
│   │   │   │   ├── ./src/main/java/JDBC/User.java
│   │   │   │   └── ./src/main/java/JDBC/UserDao.java
│   │   │   ├── ./src/main/java/Model
│   │   │   │   ├── ./src/main/java/Model/GameModel.java
│   │   │   │   ├── ./src/main/java/Model/Map.java
│   │   │   │   ├── ./src/main/java/Model/Player.java
│   │   │   │   ├── ./src/main/java/Model/Territory.java
│   │   │   │   ├── ./src/main/java/Model/currentProcess.java
│   │   │   │   └── ./src/main/java/Model/troopCategroy.java
│   │   │   └── ./src/main/java/View
│   │   │       ├── ./src/main/java/View/Country.java
│   │   │       ├── ./src/main/java/View/CountryPath.java
│   │   │       ├── ./src/main/java/View/InviteController.java
│   │   │       ├── ./src/main/java/View/LoginView.java
│   │   │       ├── ./src/main/java/View/MainView.java
│   │   │       ├── ./src/main/java/View/SvgUtil.java
│   │   │       └── ./src/main/java/View/ViewBase.java
│   │   └── ./src/main/resources
│   │       ├── ./src/main/resources/Sounds
│   │       │   └── ./src/main/resources/Sounds/GOT.mp3
│   │       ├── ./src/main/resources/map
│   │       │   ├── ./src/main/resources/map/Risk_game_board.svg
│   │       │   ├── ./src/main/resources/map/neighbourhood.properties
│   │       │   └── ./src/main/resources/map/territorycards.properties
│   │       └── ./src/main/resources/view
│   │           ├── ./src/main/resources/view/InviteView.fxml
│   │           ├── ./src/main/resources/view/LoginView.fxml
│   │           ├── ./src/main/resources/view/MainView.fxml
│   │           ├── ./src/main/resources/view/css
│   │           │   └── ./src/main/resources/view/css/MainCss.css
│   │           └── ./src/main/resources/view/img
│   │               ├── ./src/main/resources/view/img/check-mark.png
│   │               ├── ./src/main/resources/view/img/cover.jpg
│   │               ├── ./src/main/resources/view/img/map2.jpg
│   │               ├── ./src/main/resources/view/img/risk.png
│   │               ├── ./src/main/resources/view/img/soldier1.jpg
│   │               ├── ./src/main/resources/view/img/soldier2.jpg
│   │               └── ./src/main/resources/view/img/title.png
│   └── ./src/test
│       └── ./src/test/DatabaseTest.java
└── ./target
    ├── ./target/classes
    │   ├── ./target/classes/Application
    │   │   └── ./target/classes/Application/RiskGame.class
    │   ├── ./target/classes/Connection
    │   │   ├── ./target/classes/Connection/ClientHandler.class
    │   │   ├── ./target/classes/Connection/Server.class
    │   │   └── ./target/classes/Connection/ServerHandler.class
    │   ├── ./target/classes/Controller
    │   │   ├── ./target/classes/Controller/GameController$1.class
    │   │   └── ./target/classes/Controller/GameController.class
    │   ├── ./target/classes/JDBC
    │   │   ├── ./target/classes/JDBC/DBUtil.class
    │   │   ├── ./target/classes/JDBC/Test.class
    │   │   ├── ./target/classes/JDBC/User.class
    │   │   └── ./target/classes/JDBC/UserDao.class
    │   ├── ./target/classes/Model
    │   │   ├── ./target/classes/Model/GameModel$1.class
    │   │   ├── ./target/classes/Model/GameModel.class
    │   │   ├── ./target/classes/Model/Map.class
    │   │   ├── ./target/classes/Model/Player.class
    │   │   ├── ./target/classes/Model/Territory.class
    │   │   ├── ./target/classes/Model/currentProcess.class
    │   │   └── ./target/classes/Model/troopCategroy.class
    │   ├── ./target/classes/map
    │   │   ├── ./target/classes/map/Risk_game_board.svg
    │   │   ├── ./target/classes/map/neighbourhood.properties
    │   │   └── ./target/classes/map/territorycards.properties
    │   └── ./target/classes/view
    │       ├── ./target/classes/view/Country.class
    │       ├── ./target/classes/view/CountryPath.class
    │       ├── ./target/classes/view/InviteController.class
    │       ├── ./target/classes/view/InviteView.fxml
    │       ├── ./target/classes/view/LoginView.class
    │       ├── ./target/classes/view/LoginView.fxml
    │       ├── ./target/classes/view/MainView.class
    │       ├── ./target/classes/view/MainView.fxml
    │       ├── ./target/classes/view/SvgUtil$1.class
    │       ├── ./target/classes/view/SvgUtil$2.class
    │       ├── ./target/classes/view/SvgUtil$3.class
    │       ├── ./target/classes/view/SvgUtil$4.class
    │       ├── ./target/classes/view/SvgUtil$5.class
    │       ├── ./target/classes/view/SvgUtil$6.class
    │       ├── ./target/classes/view/SvgUtil$7.class
    │       ├── ./target/classes/view/SvgUtil$8.class
    │       ├── ./target/classes/view/SvgUtil.class
    │       ├── ./target/classes/view/ViewBase.class
    │       ├── ./target/classes/view/css
    │       │   └── ./target/classes/view/css/MainCss.css
    │       └── ./target/classes/view/img
    │           ├── ./target/classes/view/img/check-mark.png
    │           ├── ./target/classes/view/img/cover.jpg
    │           ├── ./target/classes/view/img/map2.jpg
    │           ├── ./target/classes/view/img/risk.png
    │           ├── ./target/classes/view/img/soldier1.jpg
    │           ├── ./target/classes/view/img/soldier2.jpg
    │           └── ./target/classes/view/img/title.png
    └── ./target/generated-sources
        └── ./target/generated-sources/annotations

29 directories, 89 files
