# RISK Game #

Risk game built by Java and JavaFX, adopted agile development approach, implemented different kinds od design patterns.

## Game Rules ##

* [Official Risk game rules by Hasbro (1)](https://www.hasbro.com/common/instruct/risk.pdf)

* [Official Risk game rules by Hasbro (2)](http://media.wizards.com/2015/downloads/ah/Risk_rules.pdf)

## Requirements ##

* java 18
* javafx
* Junit

## Participants ##

| Name       | ID       |
| ---------- | -------- |
| Cancy Yang | 20008099 |
| Coco Wang  |          |
| Shawn Liu  |          |

## Software ##

### IntelliJ IDEA ###

We use the IntelliJ Idea for java. Download from: https://www.jetbrains.com/idea/download/#section=mac .

### Scene Builder ###

We use JavaFX Scene Builder to make View. Download from: http://gluonhq.com/products/scene-builder/ .

### MSSQL ###

We use MSSQL to test sql server. Download from: https://www.microsoft.com/zh-cn/sql-server/sql-server-downloads .

## Catalog Structure ##

### Master ###

The master of the project is master.

### Features ###

* Coco-dev
* Shawn-dev
* connection1

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
│   │       ├── ./src/main/resources/map   
│   │       └── ./src/main/resources/view   
│   └── ./src/test  
│       └── ./src/test/DatabaseTest.java  
└── ./target  
├── ./target/classes  
└── ./target/generated-sources  
└── ./target/generated-sources/annotations

## Framework ##

* Maven - Dependent property management
* Springboot
* MVC
* Microsoft Azure

The above is the basic content.

****

## Format for adding updates and questions ##
****

Cancy Yang 2022.8.25  

Content: Updated the README.md  

Need to be improved: Nothing.  

****

Cancy Yang 2022.8.26   

Content: Try to finish the sql server, but there is also something wrong.  

Need to be improved: Connect to remote sql server.  

****

Shawn Liu 2022.8.26   

Content: Success to draw map.  

Need to be improved: Nothing.  

****

Coco Wang 2022.8.26  

Content: Learn skills about scene builder.  

Need to be improved: Draw login view tomorrow.  

****

Coco Wang 2022.8.27   

Content: Draw login view.  

Need to be improved: Nothing.  

****

Cancy Yang 2022.8.28   

Content: Nothing.  

Need to be improved: I applied for Azure, and I will learn how to use it.  

****
