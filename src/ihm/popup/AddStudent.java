package ihm.popup;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import ihm.Interface;
import ihm.utils.TutoringUtils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import oop.Motivation;
import oop.Student;

public class AddStudent extends PopUp {
    List<Student> students;
    List<Student> alreadyThere;
    ListView<Student> searchList = new ListView<>();
    Student selected;
    Button ajouterFresh;
    Button ajouterList;

    private class AddingChecker implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent e) {
            if ((Button)e.getTarget() == ajouterList){
                confirm();
            }
            
        }

        void confirm(){
            Alert alert = new Alert(AlertType.CONFIRMATION,
                    "Vous allez ajouter " + selected.getName() + ". Êtes-vous certain(e)?",
                    ButtonType.YES, ButtonType.CANCEL);
            alert.headerTextProperty().set("");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.YES) {
                selected.addGrade(parent.dpt.currentTutoring.getResource(), Student.getDefaultGrade());
                parent.dpt.currentTutoring.addStudent(selected);                
                TutoringUtils.updateLists(parent);
                stage.close();
            }
        }

    }

    private class SearchStudentListener implements ChangeListener<String> {
        public void changed(ObservableValue<? extends String> arg0, String arg1, String newV) {
            List<Student> found = new ArrayList<>();
            ajouterList.setDisable(true);
            for (Student student : students) {
                if (student.getName().toUpperCase().contains(newV.toUpperCase())){
                    found.add(student);
                }
            }
            populateList(found);          
        }
        
    }

    private class SearchListListener implements ListChangeListener<Student> {

        public void onChanged(Change<? extends Student> c) {
            if (c.getList().size()>0){                
                selected = c.getList().get(0);
                ajouterList.setDisable(false);
            }
            else {
                ajouterList.setDisable(true);
            }
        }
        
    }

    public AddStudent(Interface parent) {
        super(parent);

        alreadyThere = new ArrayList<>(parent.dpt.currentTutoring.getTutored());
        alreadyThere.addAll(parent.dpt.currentTutoring.getTutors());
        students = new ArrayList<>(parent.dpt.getStudents());
        students.removeAll(alreadyThere);

        start(stage);
    }

    @Override
    public void start(Stage stage) {
        TabPane root = new TabPane();

        root.getTabs().addAll(fresh(), fromList());

        Scene scene = new Scene(root, 200, 200);
        stage.setScene(scene);
        stage.show();
    }

    Tab fresh (){
        TextField nom = new TextField();
        nom.setPromptText("Nom");

        TextField prenom = new TextField();
        prenom.setPromptText("Prénom");

        TextField absences = new TextField();
        absences.setPromptText("absences");

        ObservableList<Motivation> values = FXCollections.observableList(List.of(Motivation.values()));
        Spinner<Motivation> motivation = new Spinner<>(values);

        ajouterFresh = new Button("Ajouter");

        VBox root = new VBox(nom, prenom, absences, motivation, ajouterFresh);

        return new Tab("Nouveau", root);
    }

    Tab fromList(){
        searchList.getSelectionModel().getSelectedItems().addListener(new SearchListListener());
        populateList(students);
        TextField tf = new TextField();
        tf.setPromptText("Votre recherche");
        tf.textProperty().addListener(new SearchStudentListener());

        ajouterList = new Button("Ajouter");
        ajouterList.setDisable(true);
        ajouterList.setOnAction(new AddingChecker());
        VBox root = new VBox(tf, searchList, ajouterList);
        

        return new Tab("Depuis la liste", root);
    }

    private void populateList(List<? extends Student> students){
        searchList.getItems().clear();
        searchList.getItems().addAll(students);
    }
    
}
