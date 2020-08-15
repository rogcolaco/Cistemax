package util;

import javafx.scene.control.Alert;

import static util.Utils.mostrarAlerta;

public class ErroDbAccess {

    public void erroBdAcess(){
        mostrarAlerta("Acesso ao Banco de Dados", "Erro - Acesso Banco de Dados", "Falha ao Acessar o Banco de Dados. Por Favor, entre em contato com o Adminstrador", Alert.AlertType.ERROR);
    }
}
