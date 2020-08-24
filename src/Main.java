import controller.NewSaleController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.Ticket;

import java.io.IOException;

public class Main extends Application {
    private Stage stage;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/view/NewSale.fxml"));
        this.stage = primaryStage;
        stage.setTitle("Cistemax - Gerenciar Vendas");
        stage.setResizable(false);

        Image image = new Image("/Image/logoTransparente.png");
        stage.getIcons().add(image);

        stage.setScene(new Scene(root, 940, 650));
        stage.show();
    }

}
