package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Objects;


/**
 * Created by Владислав on 13.11.2015.
 */
public class ManageInfo {
    private Stage stage;
    private Scene scene;
    private Group root;
    private ToggleGroup radioButtonGroup;
    private boolean isEditInfo;
    private VBox vBox;

    ManageInfo(boolean _isEditInfo) {
        isEditInfo = _isEditInfo;
        stage = new Stage();
        root = new Group();
        scene = new Scene(root, 400, 300);
        vBox = new VBox();
        vBox.setLayoutX(50);
        vBox.setLayoutY(70);
        stage.setScene(scene);

        radioButtonGroup = new ToggleGroup();

        Button applyButton = new Button("Apply");
        applyButton.setLayoutX(100);
        applyButton.setLayoutY(250);
        applyButton.setDisable(true);
        Button cancelButton = new Button("Cancel");
        cancelButton.setLayoutX(250);
        cancelButton.setLayoutY(250);

        RadioButton noteButton = new RadioButton("Note");
        RadioButton deadlineButton = new RadioButton("Deadline");
        RadioButton markButton = new RadioButton("Mark");
        RadioButton absenceButton = new RadioButton("Absence");

        noteButton.setOnAction(event -> {
            vBox.getChildren().clear();
            if (isEditInfo) {
                ComboBox<String> comboBox = new ComboBox<>();
                vBox.getChildren().add(comboBox);
                ObservableList<String> observableList = FXCollections.observableArrayList(DataLists.getInstance().getNoteListDates());
                comboBox.setItems(observableList);
                comboBox.setPromptText("Choose note date...");
                comboBox.setOnHiding(event1 -> {
                    if (comboBox.getValue() != null) {
                        TextArea noteTextArea = new TextArea();
                        for (int i = 0; i < observableList.size(); i++) {
                            if (Objects.equals(observableList.get(i), comboBox.getValue())) {
                                vBox = cutVBox(vBox, 1, 1);
                                noteTextArea.setText(DataLists.getInstance().getNoteList().get(i).getInfo());
                                noteTextArea.setPrefWidth(300);
                                noteTextArea.setPrefHeight(100);
                                vBox.getChildren().add(noteTextArea);
                                final int finalI = i;
                                applyButton.setOnAction(event2 -> {
                                    DataLists.getInstance().getNoteList().get(finalI).setInfo(noteTextArea.getText());
                                    Timeline.getInstance().synchronize();
                                    MainWindow.getInstance().synchronize();
                                    DataLists.getInstance().synchronizeWithFile();
                                    stage.close();
                                });
                                applyButton.setDisable(false);
                                break;
                            }
                        }
                    }
                });
            } else {
                TextField dayTextField = new TextField();
                dayTextField.setPromptText("Enter day");
                dayTextField.setPrefWidth(50);
                TextField monthTextField = new TextField();
                monthTextField.setPromptText("Enter month");
                monthTextField.setPrefWidth(50);
                TextArea noteTextArea = new TextArea();
                noteTextArea.setPromptText("Enter note");
                noteTextArea.setPrefWidth(300);
                noteTextArea.setPrefHeight(100);
                vBox.getChildren().addAll(dayTextField, monthTextField, noteTextArea);
                applyButton.setOnAction(event2 -> {
                    try {
                        EventDate date = new EventDate(Integer.parseInt(monthTextField.getText()), Integer.parseInt((dayTextField.getText())));
                        if (date.isCorrect()) {
                            DataLists.getInstance().getNoteList().add(new Note(date, noteTextArea.getText()));
                            Timeline.getInstance().synchronize();
                            MainWindow.getInstance().synchronize();
                            DataLists.getInstance().synchronizeWithFile();
                            stage.close();
                        } else {
                            throw new Exception();
                        }
                    }
                    catch (Exception e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Incorrect entries");
                        alert.showAndWait();
                    }
                });
                applyButton.setDisable(false);
            }
        });


        deadlineButton.setOnAction(event -> {
            vBox.getChildren().clear();
            applyButton.setDisable(true);
            ComboBox<String> comboBox = new ComboBox<>();
            vBox.getChildren().add(comboBox);
            ObservableList<String> observableList = FXCollections.observableArrayList(DataLists.getInstance().getSubjectNameList());
            comboBox.setItems(observableList);
            comboBox.setPromptText("Choose subject...");
            comboBox.setOnHiding(event1 -> {
                if (comboBox.getValue() != null) {
                    vBox = cutVBox(vBox, 1, 3);
                    ComboBox<Integer> comboBox1 = new ComboBox<>();
                    vBox.getChildren().add(comboBox1);
                    ObservableList<Integer> observableList1 = FXCollections.observableArrayList();
                    for (int i = 0; i < DataLists.getInstance().getLabList(comboBox.getValue()).size(); i++)
                        observableList1.add(i + 1);
                    comboBox1.setItems(observableList1);
                    comboBox1.setPromptText("Choose lab number...");
                    comboBox1.setOnHiding(event2 -> {
                        if (comboBox1.getValue() != null) {
                            vBox = cutVBox(vBox, 2, 3);
                            TextField dayTextField = new TextField();
                            dayTextField.setPromptText("Enter day");
                            dayTextField.setPrefWidth(50);
                            TextField monthTextField = new TextField();
                            monthTextField.setPromptText("Enter month");
                            if (isEditInfo) {
                                if (DataLists.getInstance().getLabList(comboBox.getValue()).get(comboBox1.getValue() - 1).getDeadLineDate().getDay() != 0) {
                                    dayTextField.setText(Integer.toString(DataLists.getInstance().getLabList(comboBox.getValue()).get(comboBox1.getValue() - 1).getDeadLineDate().getDay()));
                                    monthTextField.setText(Integer.toString(DataLists.getInstance().getLabList(comboBox.getValue()).get(comboBox1.getValue() - 1).getDeadLineDate().getMonth()));
                                    applyButton.setDisable(false);
                                } else {
                                    dayTextField.setText("Do not have deadline yet");
                                    monthTextField.setText("Do not have deadline yet");
                                    dayTextField.setDisable(true);
                                    monthTextField.setDisable(true);
                                }

                            } else {
                                if (DataLists.getInstance().getLabList(comboBox.getValue()).get(comboBox1.getValue() - 1).getDeadLineDate().getDay() != 0) {
                                    dayTextField.setText("Already has deadline");
                                    monthTextField.setText("Already has deadline");
                                    dayTextField.setDisable(true);
                                    monthTextField.setDisable(true);
                                } else
                                    applyButton.setDisable(false);
                            }
                            monthTextField.setPrefWidth(50);
                            vBox.getChildren().addAll(dayTextField, monthTextField);
                            applyButton.setOnAction(event3 -> {
                                try {
                                    EventDate date = new EventDate(Integer.parseInt(monthTextField.getText()), Integer.parseInt((dayTextField.getText())));
                                    if (date.isCorrect()) {
                                        DataLists.getInstance().getLabList(comboBox.getValue()).get(comboBox1.getValue() - 1).getDeadLineDate().setDate(Integer.parseInt(monthTextField.getText()), Integer.parseInt(dayTextField.getText()));
                                        Timeline.getInstance().synchronize();
                                        MainWindow.getInstance().synchronize();
                                        DataLists.getInstance().synchronizeWithFile();
                                        stage.close();
                                    } else {
                                        throw new Exception();
                                    }
                                }
                                catch (Exception e) {
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setContentText("Incorrect entries");
                                    alert.showAndWait();
                                }
                            });
                        }
                    });
                }
            });
        });


        markButton.setOnAction(event -> {
            vBox.getChildren().clear();
            applyButton.setDisable(true);
            ComboBox<String> comboBox = new ComboBox<>();
            vBox.getChildren().add(comboBox);
            ObservableList<String> observableList = FXCollections.observableArrayList(DataLists.getInstance().getSubjectNameList());
            comboBox.setItems(observableList);
            comboBox.setPromptText("Choose subject...");
            comboBox.setOnHiding(event1 -> {
                if (comboBox.getValue() != null) {
                    vBox = cutVBox(vBox, 1, 3);
                    ComboBox<Integer> comboBox1 = new ComboBox<>();
                    vBox.getChildren().add(comboBox1);
                    ObservableList<Integer> observableList1 = FXCollections.observableArrayList();
                    for (int i = 0; i < DataLists.getInstance().getLabList(comboBox.getValue()).size(); i++)
                        observableList1.add(i + 1);
                    comboBox1.setItems(observableList1);
                    comboBox1.setPromptText("Choose lab number...");
                    comboBox1.setOnHiding(event2 -> {
                        if (comboBox1.getValue() != null) {
                            vBox = cutVBox(vBox, 2, 4);
                            TextField markTextField = new TextField();
                            markTextField.setPromptText("Enter mark");
                            markTextField.setPrefWidth(50);
                            TextField passDayTextField = new TextField();
                            passDayTextField.setPromptText("Enter pass day");
                            passDayTextField.setPrefWidth(50);
                            TextField passMonthTextField = new TextField();
                            passMonthTextField.setPromptText("Enter pass month");
                            passMonthTextField.setPrefWidth(50);
                            if (isEditInfo) {
                                if (DataLists.getInstance().getLabList(comboBox.getValue()).get(comboBox1.getValue() - 1).getPassDate().getDay() != 0) {
                                    passDayTextField.setText(Integer.toString(DataLists.getInstance().getLabList(comboBox.getValue()).get(comboBox1.getValue() - 1).getPassDate().getDay()));
                                    passMonthTextField.setText(Integer.toString(DataLists.getInstance().getLabList(comboBox.getValue()).get(comboBox1.getValue() - 1).getPassDate().getMonth()));
                                    markTextField.setText(DataLists.getInstance().getLabList(comboBox.getValue()).get(comboBox1.getValue() - 1).getMark());
                                    applyButton.setDisable(false);
                                } else {
                                    passDayTextField.setText("Do not have mark yet");
                                    passMonthTextField.setText("Do not have mark yet");
                                    markTextField.setText("Do not have mark yet");
                                    passDayTextField.setDisable(true);
                                    passMonthTextField.setDisable(true);
                                    markTextField.setDisable(true);
                                }
                            } else {
                                if (DataLists.getInstance().getLabList(comboBox.getValue()).get(comboBox1.getValue() - 1).getPassDate().getDay() != 0) {
                                    passDayTextField.setText("Already has mark");
                                    passMonthTextField.setText("Already has mark");
                                    markTextField.setText("Already has mark");
                                    passDayTextField.setDisable(true);
                                    passMonthTextField.setDisable(true);
                                    markTextField.setDisable(true);
                                } else
                                    applyButton.setDisable(false);
                            }
                            vBox.getChildren().addAll(passDayTextField, passMonthTextField, markTextField);
                            applyButton.setOnAction(event3 -> {
                                try {
                                    EventDate date = new EventDate(Integer.parseInt(passMonthTextField.getText()), Integer.parseInt((passDayTextField.getText())));
                                    if (date.isCorrect()) {
                                        DataLists.getInstance().getLabList(comboBox.getValue()).get(comboBox1.getValue() - 1).getPassDate().setDate(Integer.parseInt(passMonthTextField.getText()), Integer.parseInt(passDayTextField.getText()));
                                        DataLists.getInstance().getLabList(comboBox.getValue()).get(comboBox1.getValue() - 1).setMark(markTextField.getText());
                                        Timeline.getInstance().synchronize();
                                        MainWindow.getInstance().synchronize();
                                        DataLists.getInstance().synchronizeWithFile();
                                        stage.close();
                                    } else {
                                        throw new Exception();
                                    }
                                }
                                catch (Exception e) {
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setContentText("Incorrect entries");
                                    alert.showAndWait();
                                }
                            });
                        }
                    });
                }
            });
        });


        absenceButton.setOnAction(event -> {
            vBox.getChildren().clear();
            applyButton.setDisable(true);
            ComboBox<String> comboBox = new ComboBox<>();
            vBox.getChildren().add(comboBox);
            ObservableList<String> observableList = FXCollections.observableArrayList(DataLists.getInstance().getSubjectNameList());
            comboBox.setItems(observableList);
            comboBox.setPromptText("Choose subject...");
            if (isEditInfo) {
                comboBox.setOnHiding(event1 -> {
                    if (comboBox.getValue() != null) {
                        vBox = cutVBox(vBox, 1, 3);
                        ComboBox<String> comboBox1 = new ComboBox<>();
                        vBox.getChildren().add(comboBox1);
                        ObservableList<String> observableList1 = FXCollections.observableArrayList(DataLists.getInstance().getAbsenceStringList(comboBox.getValue()));
                        comboBox1.setItems(observableList1);
                        comboBox1.setPromptText("Choose absence date...");
                        comboBox1.setOnHiding(event2 -> {
                            if (comboBox1.getValue() != null) {
                                vBox = cutVBox(vBox, 2, 3);
                                TextField absenceDayTextField = new TextField();
                                absenceDayTextField.setPrefWidth(50);
                                absenceDayTextField.setPromptText("Enter day");
                                TextField absenceMonthTextField = new TextField();
                                absenceMonthTextField.setPrefWidth(50);
                                absenceMonthTextField.setPromptText("Enter month");
                                for (int i = 0; i < observableList1.size(); i++) {
                                    if (Objects.equals(observableList1.get(i), comboBox1.getValue())) {
                                        vBox.getChildren().addAll(absenceDayTextField, absenceMonthTextField);
                                        final int finalI = i;
                                        applyButton.setDisable(false);
                                        applyButton.setOnAction(event3 -> {
                                            try {
                                                EventDate date = new EventDate(Integer.parseInt(absenceMonthTextField.getText()), Integer.parseInt((absenceDayTextField.getText())));
                                                if (date.isCorrect()) {
                                                    DataLists.getInstance().getAbsenceList(comboBox.getValue()).get(finalI).setDate(Integer.parseInt(absenceMonthTextField.getText()), Integer.parseInt(absenceDayTextField.getText()));
                                                    Timeline.getInstance().synchronize();
                                                    MainWindow.getInstance().synchronize();
                                                    DataLists.getInstance().synchronizeWithFile();
                                                    stage.close();
                                                } else {
                                                    throw new Exception();
                                                }
                                            }
                                            catch (Exception e) {
                                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                                alert.setContentText("Incorrect entries");
                                                alert.showAndWait();
                                            }
                                        });
                                    }
                                }
                            }
                        });
                    }
                });
            } else
            {
                comboBox.setOnHiding(event1 -> {
                    if (comboBox.getValue() != null) {
                        vBox = cutVBox(vBox, 1, 2);
                        TextField absenceDayTextField = new TextField();
                        absenceDayTextField.setPrefWidth(50);
                        absenceDayTextField.setPromptText("Enter day");
                        TextField absenceMonthTextField = new TextField();
                        absenceMonthTextField.setPrefWidth(50);
                        absenceMonthTextField.setPromptText("Enter month");
                        vBox.getChildren().addAll(absenceDayTextField, absenceMonthTextField);
                        applyButton.setDisable(false);
                        applyButton.setOnAction(event3 -> {
                            try {
                                EventDate date = new EventDate(Integer.parseInt(absenceMonthTextField.getText()), Integer.parseInt((absenceDayTextField.getText())));
                                if (date.isCorrect()) {
                                    DataLists.getInstance().getAbsenceList(comboBox.getValue()).add(new EventDate(Integer.parseInt(absenceMonthTextField.getText()), Integer.parseInt(absenceDayTextField.getText())));
                                    Timeline.getInstance().synchronize();
                                    MainWindow.getInstance().synchronize();
                                    DataLists.getInstance().synchronizeWithFile();
                                    stage.close();
                                } else {
                                    throw new Exception();
                                }
                            }
                            catch (Exception e) {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setContentText("Incorrect entries");
                                alert.showAndWait();
                            }
                        });
                    }
                });
            }
        });

        noteButton.setToggleGroup(radioButtonGroup);
        noteButton.setLayoutX(25);
        noteButton.setLayoutY(20);
        deadlineButton.setToggleGroup(radioButtonGroup);
        deadlineButton.setLayoutX(115);
        deadlineButton.setLayoutY(20);
        markButton.setToggleGroup(radioButtonGroup);
        markButton.setLayoutX(220);
        markButton.setLayoutY(20);
        absenceButton.setToggleGroup(radioButtonGroup);
        absenceButton.setLayoutX(305);
        absenceButton.setLayoutY(20);

        cancelButton.setOnAction(event -> {
            stage.close();
        });

        root.getChildren().addAll(noteButton, deadlineButton, markButton, absenceButton, vBox, cancelButton, applyButton);

        stage.show();
    }

    private VBox cutVBox (VBox vBox, int from, int to)
    {
        for (int i = to; i >= from; i--)
        {
            if (vBox.getChildren().size() > i)
                vBox.getChildren().remove(i);
        }
        return vBox;
    }
}
