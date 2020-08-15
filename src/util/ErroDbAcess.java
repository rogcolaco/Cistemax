package util;

import javafx.scene.control.Alert;

import static util.Utils.mostrarAlerta;

public class ErroDbAcess {

    public void erroBdAcess(){
        mostrarAlerta("Tipos de Sessão e Preços", "Valor da Sessão", "Falha ao Acessar o Banco de Dados. Por Favor, entre em contato com o Adminstrador", Alert.AlertType.ERROR);
    }
}
