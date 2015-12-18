package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class Main extends Application implements Initializable {

    int numPresses = 0;
    @FXML
    public TextField input;

    @FXML
    public Button enterButton;

    @FXML
    public TextArea outScreen;

    private GameWorld game;
    private Limbo limbo;
    private Forest forest;
    private Agora agora;
    private PolHouse house;
    private Ampitheatre amp;
    private String command ="";


    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {

        limbo = new Limbo(outScreen, command, forest, agora, house);
        forest = new Forest(outScreen,command, limbo);
        agora = new Agora(outScreen,command,limbo);
        amp = new Ampitheatre(outScreen, command);
        house = new PolHouse(outScreen,command,limbo);

        limbo.setAgora(agora);
        limbo.setForest(forest);
        limbo.setHouse(house);

        game = new GameWorld(limbo, outScreen,command);

    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("The Quest For Doryphoros");
        primaryStage.setScene(new Scene(root, 1200, 900));
        primaryStage.show();
    }

    @FXML
    private void handleButtonAction(ActionEvent event)
    {
        if (!game.isSetup())
        {
            game.changeCommand(input.getText());
            game.main();
        }

        else if (game.getPlayer().getAgoraQuest() && game.getPlayer().getForestQuest() && game.getPlayer().getPolQuest())
        {
            amp.changeCommand(input.getText());
            amp.runAmpitheatre(game.getPlayer());
        }

        else if (limbo.isInLimbo())
        {
            limbo.changeCommand(input.getText());
            limbo.runLimbo(game.getPlayer());
        }

        else if (forest.isInForest())
        {
            forest.changeCommand(input.getText());
            forest.runForest(game.getPlayer());
        }

        else if (agora.isInAgora())
        {
            agora.changeCommand(input.getText());
            agora.runAgora(game.getPlayer());
        }

        else if (house.isAtPolHouse())
        {
            house.changeCommand(input.getText());
            house.runPolHouse(game.getPlayer());
        }

        else {
            outScreen.appendText("\n Something went wrong.");
        }

        input.clear();

    }



    public static void main(String[] args) {
        launch(args);
    }

}
