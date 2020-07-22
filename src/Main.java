import controller.NewSaleController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
        stage.setScene(new Scene(root, 800, 630));
        stage.show();
    }

}
