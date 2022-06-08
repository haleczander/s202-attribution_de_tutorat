package ihm.utils;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class WidgetUtils {
    public static Region spacer(){
        return spacer(Double.MAX_VALUE);
    }
    
    public static Region spacer(double maxSize){
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        spacer.setMaxWidth(maxSize);
        return spacer;
    }
    
    static Region filler(double size){
        Region filler = new Region();
        filler.setMinWidth(size);
        return filler;
    }
    
    public static Region filler(){
        return filler(10);
    }

    public static HBox labelButton(String label, String button, EventHandler<ActionEvent> handler, String tooltip){
        HBox box = new HBox();        
        box.setAlignment(Pos.CENTER);

        Label lb = new Label(label);

        Button bt = new Button(button);
        bt.setOnAction(handler);
        bt.setStyle("-fx-font-size : 15;");
        bt.setTooltip(new Tooltip(tooltip));
        bt.setPadding(new Insets(0));
        bt.setPrefSize(20, 20);

        box.getChildren().addAll(lb, WidgetUtils.spacer(), bt);
        return box;
    }

}
