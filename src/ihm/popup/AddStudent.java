package ihm.popup;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import graphs.Couple;
import ihm.Interface;
import ihm.events.Events;
import ihm.utils.StudentCellFactory;
import ihm.utils.TutoringUtils;
import ihm.utils.WidgetUtils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import oop.Motivation;
import oop.Student;
import oop.Tutor;
import oop.Tutored;
import utility.Couples;

public class AddStudent extends PopUp {
    List<Student> students;
    ListView<Student> searchList = new ListView<>();
    Student selectedStudent;
    Button ajouterFresh;
    Button ajouterList;
    Boolean interdite;
    TextField nom, prenom, moyenne, absences;
    Spinner<Motivation> motivation;
    Spinner<Integer> niveau;
    TabPane root;

    Tutored toReaffect;
    Tutor toRemove;
    boolean replacing = false;

    TextField searchListTf = new TextField();

    CheckBox tutoredCb = new CheckBox("Tutorés");
    CheckBox tutorCb = new CheckBox("Tuteurs");
    HBox listCb = new HBox(tutoredCb, WidgetUtils.filler(25), tutorCb);

    private class AddingChecker implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            Button target = (Button) e.getTarget();
            if (replacing) {
                confirmReplace();
            } else if (interdite != null) {
                StudentCellFactory.draggedStudent = parent.selectedStudent;
                Events.DragNDropHandler(parent, selectedStudent, interdite);
            } else if (target == ajouterFresh) {
                if (checkInputs()) {
                    createAndAddStudent();
                }
            } else {
                confirm();
            }
            TutoringUtils.updateLists(parent);
            stage.close();

        }

        void confirmReplace() {
            Alert alert = new Alert(AlertType.CONFIRMATION,
                    "Vous allez remplacer " + toRemove.getName() + " par " + selectedStudent.getName()
                            + ". Êtes-vous certain(e)?",
                    ButtonType.YES, ButtonType.CANCEL);
            alert.headerTextProperty().set("");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.YES) {
                selectedStudent.addGrade(parent.dpt.currentTutoring.getResource(), Student.getDefaultGrade());
                parent.dpt.currentTutoring.addStudent(selectedStudent);
                parent.dpt.currentTutoring.removeStudent(toRemove);
                if (toReaffect != null) {
                    parent.dpt.currentTutoring.addForcedAssignments(toReaffect, (Tutor) selectedStudent);
                }
                parent.dpt.currentTutoring.getWaitingList().remove(toReaffect);
                parent.dpt.currentTutoring.getWaitingList().remove(selectedStudent);
                parent.dpt.currentTutoring.affectations.add(new Couple(toReaffect, (Tutor)selectedStudent));
            }
        }

        void confirm() {
            Alert alert = new Alert(AlertType.CONFIRMATION,
                    "Vous allez ajouter " + selectedStudent.getName() + ". Êtes-vous certain(e)?",
                    ButtonType.YES, ButtonType.CANCEL);
            alert.headerTextProperty().set("");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.YES) {
                selectedStudent.addGrade(parent.dpt.currentTutoring.getResource(), Student.getDefaultGrade());
                parent.dpt.currentTutoring.addStudent(selectedStudent);
            }
        }

        boolean checkInputs() {
            boolean isOk = true;
            StringBuilder errorMessage = new StringBuilder();
            Alert alert = new Alert(AlertType.ERROR);
            alert.headerTextProperty().set("Saisie incorrecte");

            if (nom.getText().isEmpty()) {
                errorMessage.append("Le nom est vide.\n");
                isOk = false;
            }

            if (prenom.getText().isEmpty()) {
                errorMessage.append("Le prénom est vide.\n");
                isOk = false;
            }

            try {
                Double.parseDouble(moyenne.getText().replace(",", "."));
            } catch (NumberFormatException e) {
                errorMessage.append("La moyenne doit être une valeur numérique.\n");
                isOk = false;
            }

            try {
                Integer.parseInt(absences.getText());
            } catch (NumberFormatException e) {
                errorMessage.append("Les absences doivent être un nombre entier.\n");
                isOk = false;
            }

            if (!isOk) {
                alert.setContentText(errorMessage.toString());
                alert.showAndWait();
            }
            return isOk;
        }

        void createAndAddStudent() {
            String nomString = nom.getText();
            String prenomString = prenom.getText();
            double moyenneDouble = Double.parseDouble(moyenne.getText());
            int absencesInt = Integer.parseInt(absences.getText());
            Motivation motivationValue = motivation.getValue();
            int niveauInt = niveau.getValue();

            Alert alert = new Alert(AlertType.CONFIRMATION,
                    "Vous allez ajouter " + nomString + " " + prenomString + ". Êtes-vous certain(e)?",
                    ButtonType.YES, ButtonType.CANCEL);
            alert.headerTextProperty().set("");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.CANCEL) {
                return;
            }

            if (niveauInt == 1) {
                Tutored student = new Tutored(prenomString + " " + nomString, absencesInt, motivationValue.getAbbr());
                student.addGrade(parent.dpt.currentTutoring.getResource(), moyenneDouble);
                parent.dpt.currentTutoring.addStudent(student);
            } else {
                Tutor student = new Tutor(prenomString + " " + nomString, niveauInt, absencesInt,
                        motivationValue.getAbbr());
                student.addGrade(parent.dpt.currentTutoring.getResource(), moyenneDouble);
                parent.dpt.currentTutoring.addStudent(student);
            }
        }
    }

    private class CheckBoxListener implements ChangeListener<Boolean> {

        public void changed(ObservableValue<? extends Boolean> obs, Boolean o, Boolean n) {
            updateSearchList(searchListTf.getText());
        }
    }

    private void updateSearchList(String str) {
        List<Student> found = new ArrayList<>();
        ajouterList.setDisable(true);
        for (Student student : students) {
            if (student.getName().toUpperCase().contains(str.toUpperCase())) {
                if ((tutorCb.isSelected() && !student.isTutored())
                        || (tutoredCb.isSelected() && student.isTutored())) {
                    found.add(student);
                }
            }
        }
        populateList(found);
    }

    private class SearchStudentListener implements ChangeListener<String> {
        public void changed(ObservableValue<? extends String> arg0, String arg1, String newV) {
            updateSearchList(newV);
        }

    }

    private class SearchListListener implements ListChangeListener<Student> {

        public void onChanged(Change<? extends Student> c) {
            if (c.getList().size() > 0) {
                selectedStudent = c.getList().get(0);
                ajouterList.setDisable(false);
            } else {
                ajouterList.setDisable(true);
            }
        }

    }

    public AddStudent(Interface parent, boolean interdite) {
        super(parent);
        this.interdite = interdite;
        List<Couple> containedIn;

        if (parent.affectationInterdite) {
            containedIn = Couples.containedIn(parent.dpt.currentTutoring.getForbiddenCouples(), parent.selectedStudent);
        } else {
            containedIn = Couples.containedIn(parent.dpt.currentTutoring.getForcedCouples(), parent.selectedStudent);
        }

        if (parent.selectedStudent.isTutored()) {
            students = new ArrayList<>(parent.dpt.currentTutoring.getTutors());
            students.removeAll(Couples.getTutors(containedIn));

        } else {
            students = new ArrayList<>(parent.dpt.currentTutoring.getTutored());
            students.removeAll(Couples.getTutored(containedIn));
        }

        start(stage);
        stage.setTitle((interdite ? "Interdire" : "Forcer") + " une affectation");
        root.getTabs().remove(0);
    }

    public AddStudent(Interface parent, Tutor toRemove) {
        super(parent);
        replacing = true;
        this.toRemove = toRemove;
        students = new ArrayList<>();
        for (Student student : parent.dpt.getStudents()) {
            if (!student.isTutored()) {
                students.add(student);
            }
        }
        students.removeAll(parent.dpt.currentTutoring.getTutors());
        listCb.setDisable(true);
        List<Couple> containedIn = Couples.containedIn(parent.dpt.currentTutoring.affectations, toRemove);
        if (containedIn.size()>0){
            toReaffect = containedIn.get(0).getTutored();
            students.removeAll(Couples
                    .getTutors(Couples.containedIn(parent.dpt.currentTutoring.getForbiddenCouples(), toReaffect)));
        }
        start(stage);
        stage.setTitle("Remplacer " + toRemove.getName());
        root.getTabs().remove(0);

    }

    public AddStudent(Interface parent) {
        super(parent);

        students = new ArrayList<>(parent.dpt.getStudents());
        students.removeAll(parent.dpt.currentTutoring.getTutored());
        students.removeAll(parent.dpt.currentTutoring.getTutors());
        stage.setTitle("Ajouter un étudiant");

        start(stage);
    }

    @Override
    public void start(Stage stage) {
        root = new TabPane();

        root.getTabs().addAll(fresh(), fromList());
        root.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

        Scene scene = new Scene(root, 400, 400);
        stage.setScene(scene);
        stage.show();
    }

    Tab fresh() {
        HBox nameBox = new HBox();
        nameBox.setAlignment(Pos.CENTER_LEFT);
        nameBox.setPadding(new Insets(5));
        nameBox.setPrefWidth(300);
        Label nameLabel = new Label("Nom :");
        nom = new TextField();
        nom.setPromptText("Nom");
        HBox.setMargin(nom, new Insets(0, 75, 0, 0));
        Region nameSpacer = new Region();
        HBox.setHgrow(nameSpacer, Priority.ALWAYS);
        nameBox.getChildren().addAll(nameLabel, nameSpacer, nom);

        HBox prenomBox = new HBox();
        prenomBox.setAlignment(Pos.CENTER_LEFT);
        prenomBox.setPadding(new Insets(5));
        prenomBox.setPrefWidth(300);
        Label prenomLabel = new Label("Prénom :");
        prenom = new TextField();
        prenom.setPromptText("Prénom");
        HBox.setMargin(prenom, new Insets(0, 75, 0, 0));
        Region prenomSpacer = new Region();
        HBox.setHgrow(prenomSpacer, Priority.ALWAYS);
        prenomBox.getChildren().addAll(prenomLabel, prenomSpacer, prenom);

        HBox niveauBox = new HBox();
        niveauBox.setAlignment(Pos.CENTER_LEFT);
        niveauBox.setPadding(new Insets(5));
        niveauBox.setPrefWidth(300);
        Label niveauLabel = new Label("Niveau :");
        ObservableList<Integer> intvalues = FXCollections.observableList(List.of(1, 2, 3));
        niveau = new Spinner<>(intvalues);
        HBox.setMargin(niveau, new Insets(0, 75, 0, 0));
        Region niveauSpacer = new Region();
        HBox.setHgrow(niveauSpacer, Priority.ALWAYS);
        niveauBox.getChildren().addAll(niveauLabel, niveauSpacer, niveau);

        HBox moyenneBox = new HBox();
        moyenneBox.setAlignment(Pos.CENTER_LEFT);
        moyenneBox.setPadding(new Insets(5));
        moyenneBox.setPrefWidth(300);
        Label moyenneLabel = new Label("Moyenne :");
        moyenne = new TextField();
        moyenne.setPromptText("Moyenne");
        moyenne.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                if (newValue.contains(",")) {
                    moyenne.setText(newValue.replaceAll(",", "."));
                }
                if (!newValue.matches("\\d*")) {
                    moyenne.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        HBox.setMargin(moyenne, new Insets(0, 75, 0, 0));
        Region moyenneSpacer = new Region();
        HBox.setHgrow(moyenneSpacer, Priority.ALWAYS);
        moyenneBox.getChildren().addAll(moyenneLabel, moyenneSpacer, moyenne);

        HBox absencesBox = new HBox();
        absencesBox.setAlignment(Pos.CENTER_LEFT);
        absencesBox.setPadding(new Insets(5));
        absencesBox.setPrefWidth(300);
        Label absencesLabel = new Label("Absences :");
        absences = new TextField();
        absences.setPromptText("absences");
        HBox.setMargin(absences, new Insets(0, 75, 0, 0));
        Region absencesSpacer = new Region();
        HBox.setHgrow(absencesSpacer, Priority.ALWAYS);
        absencesBox.getChildren().addAll(absencesLabel, absencesSpacer, absences);

        HBox motivationBox = new HBox();
        motivationBox.setAlignment(Pos.CENTER_LEFT);
        motivationBox.setPadding(new Insets(5));
        motivationBox.setPrefWidth(300);
        Label motivationLabel = new Label("Motivation :");
        Region motivationSpacer = new Region();
        HBox.setHgrow(motivationSpacer, Priority.ALWAYS);
        ObservableList<Motivation> values = FXCollections.observableList(List.of(Motivation.values()));
        motivation = new Spinner<>(values);
        HBox.setMargin(motivation, new Insets(0, 75, 0, 0));
        motivationBox.getChildren().addAll(motivationLabel, motivationSpacer, motivation);

        ajouterFresh = new Button("Ajouter");
        ajouterFresh.setAlignment(Pos.CENTER);
        ajouterFresh.setPrefWidth(125);
        ajouterFresh.setPrefHeight(50);
        ajouterFresh.setOnAction(new AddingChecker());

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        VBox root = new VBox(nameBox, prenomBox, niveauBox, moyenneBox, absencesBox, motivationBox, spacer,
                ajouterFresh);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        return new Tab("Nouveau", root);
    }

    Tab fromList() {
        tutoredCb.setSelected(true);
        tutorCb.setSelected(true);

        tutoredCb.selectedProperty().addListener(new CheckBoxListener());
        tutorCb.selectedProperty().addListener(new CheckBoxListener());

        searchList.getSelectionModel().getSelectedItems().addListener(new SearchListListener());
        populateList(students);
        searchListTf.setPromptText("Votre recherche");
        searchListTf.textProperty().addListener(new SearchStudentListener());

        ajouterList = new Button("Ajouter");
        ajouterList.setDisable(true);
        ajouterList.setOnAction(new AddingChecker());
        VBox.setMargin(ajouterList, new Insets(10, 0, 0, 0));
        VBox.setVgrow(searchListTf, Priority.ALWAYS);
        VBox root = new VBox(listCb, searchListTf, searchList, ajouterList);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        return new Tab("Depuis la liste", root);
    }

    private void populateList(List<? extends Student> students) {
        searchList.getItems().clear();
        searchList.getItems().addAll(students);
    }

}
