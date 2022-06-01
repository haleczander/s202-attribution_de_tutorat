package nonUsed;

           
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

class TitledPane1 extends StackPane {
    TitledPane1(String titleString, Node content) {
        Label title = new Label(" " + titleString + " ");
        title.getStyleClass().add("bordered-titled-title");
        title.setStyle("-fx-translate-y: -16;");
        StackPane.setAlignment(title, Pos.TOP_LEFT);

        StackPane contentPane = new StackPane();
        //   content.getStyleClass().add("bordered-titled-content");
        content.setStyle("-fx-padding: 15 5 5 5;");
        contentPane.getChildren().add(content);

        // getStyleClass().add("bordered-titled-border");
        setStyle("-fx-content-display: top; -fx-border-insets: 20 0 0 0 ; -fx-border-color: grey; -fx-border-width: 1;");
        getChildren().addAll(title, contentPane);
    }
  }
