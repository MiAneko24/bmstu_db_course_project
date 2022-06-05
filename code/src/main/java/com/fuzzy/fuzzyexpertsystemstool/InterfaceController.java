package com.fuzzy.fuzzyexpertsystemstool;

import com.fuzzy.fuzzyexpertsystemstool.database.DataController;
import com.fuzzy.fuzzyexpertsystemstool.database.State;
import com.fuzzy.fuzzyexpertsystemstool.dbmodel.OutputResult;
import com.fuzzy.fuzzyexpertsystemstool.model.*;
import com.fuzzy.fuzzyexpertsystemstool.types.*;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import javafx.util.StringConverter;

import java.util.*;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class InterfaceController {
//    public ListView lstview;
    @FXML
    private Button addRule;
    @FXML
    private Button addVariable;
    @FXML
    private Button addMf;
    @FXML
    private Button addConsequent;
    @FXML
    private Button addSystem;
    @FXML
    private Button deleteSystem;
    @FXML
    private Button deleteRule;
    @FXML
    private Button deleteVariable;
    @FXML
    private Button deleteMf;
    @FXML
    private Button addAnt;
    @FXML
    private Button deleteAnt;

    @FXML
    private Button deleteConsequent;
    @FXML
    private Button changeMf;
    @FXML
    private Button changeConsequent;
    @FXML
    private Button changeAntecedent;
    @FXML
    private Button changeSystem;
    @FXML
    private MenuItem login;
    @FXML
    private TextField sNameInput;
    @FXML
    private ChoiceBox<String> sTypeBox;
    @FXML
    private ChoiceBox<String> sSpecBox;
    @FXML
    private Label mfParamsLabel;
    @FXML
    private Label mfTermLabel;
    @FXML
    private Label mfTypeLabel;
    @FXML
    private Label mfVariableLabel;
    @FXML
    private Label mfParam1Label_pid;
    @FXML
    private Label mfParam2Label_bar;
    @FXML
    private Label mfParam3Label;
    @FXML
    private Label mfParam4Label;
    @FXML
    private Label mfActiveLabel;
    @FXML
    private TextField termInput;
    @FXML
    private ChoiceBox<String> typeBox;
    @FXML
    private ChoiceBox<String> barBox;
    @FXML
    private ChoiceBox<String> pidBox;
    @FXML
    private ChoiceBox<String> varBox;
    @FXML
    private TextField par1Field;
    @FXML
    private TextField par2Field;
    @FXML
    private TextField par3Field;
    @FXML
    private TextField par4Field;
    @FXML
    private CheckBox activeCheck;
    @FXML
    private Button getOutputButton;
    @FXML
    private TableView<OutputResult> outputTable;
    @FXML
    private TableColumn<OutputResult, String> varNameColumn;
    @FXML
    private TableColumn<OutputResult, Double> varValueColumn;
    @FXML
    private Button varValueOk;
    @FXML
    private TextField variableValue;
    @FXML
    private TextField variableMinValue;
    @FXML
    private TextField variableMaxValue;
    @FXML
    private Label variableNameLabel;
    @FXML
    private Label variableValueLabel;
    @FXML
    private Label minValueLabel;
    @FXML
    private Label maxValueLabel;
    @FXML
    private TabPane tabs;
    @FXML
    private Label isLabel;
    @FXML
    private Label antTermLabel;
    @FXML
    private ChoiceBox<String> antTermBox;
    @FXML
    private Label antConLabel;
    @FXML
    private ChoiceBox<String> antConBox;
    @FXML
    private Label antVarLabel;
    @FXML
    private ChoiceBox<String> antVarBox;
    @FXML
    private Label conVarLabel;
    @FXML
    private ChoiceBox<String> conVarBox;
    @FXML
    private Label isEqLabel;
    @FXML
    private Label conCoeffLabel;
    @FXML
    private TextField conCoeffInput;
    @FXML
    private Label conVarTermLabel;
    @FXML
    private ChoiceBox<String> conVarTermBox;
    @FXML
    private Label conLabel;
    @FXML
    private ListView<String> consList;
//    Tab rulesTab;
//    @FXML
//    Tab variablesTab;
    @FXML
    private ListView<String> systemsview;

    @FXML
    private ListView<String> rulesView;

    @FXML
    private ListView<String> variablesView;
    @FXML
    private Label mfAntecedentsLabel;

    @FXML
    private ListView<String> membershipFunctionsView;
    @FXML
    private LineChart<Number, Number> mfGraph;
    @FXML
    private TextField variableName;
    @FXML
    private Label ruleWeightLabel;
    @FXML
    private TextField ruleWeightInput;

    private ObservableList<String> systems = FXCollections.observableArrayList();
    private ObservableList<String> rules = FXCollections.observableArrayList();
    private ObservableList<String> variables = FXCollections.observableArrayList();
    private ObservableList<String> membershipFunctions = FXCollections.observableArrayList();
    private ObservableList<OutputResult> outputResults = FXCollections.observableArrayList();
    private ObservableList<String> mfTypes = FXCollections.observableArrayList();
    private ObservableList<String> mfParents = FXCollections.observableArrayList();
    private ObservableList<String> mfBarriers = FXCollections.observableArrayList();
    private ObservableList<String> mfVariables = FXCollections.observableArrayList();
    private ObservableList<String> sTypes = FXCollections.observableArrayList();
    private ObservableList<String> sSpecs = FXCollections.observableArrayList();
    private ObservableList<String> antVars = FXCollections.observableArrayList();
    private ObservableList<String> antTerms = FXCollections.observableArrayList();
    private ObservableList<String> antConnections = FXCollections.observableArrayList();
    private ObservableList<String> conVars = FXCollections.observableArrayList();
    private ObservableList<String> conVarTerms = FXCollections.observableArrayList();
    private ObservableList<String> consequents = FXCollections.observableArrayList();
    private ObservableList<XYChart.Series<Number, Number>> graphData = FXCollections.observableArrayList();
    private DataController worker = null;
    private Integer currentSystem = null;
    private Rule currentRule = null;
    private Integer currentVariable = null;
    private Integer currentAntecedent = null;
    private Integer currentConsequent = null;
    private boolean isEditable = false;

    private MembershipFunction function = null;

    private EventHandler<MouseEvent> mfAntsViewClicked = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
        }
    };

    public void addSystem() {
        FSystem system = new FSystem("Система_" + UUID.randomUUID().toString().substring(0, 20), SystemType.Mamdani, SpecializationType.Physics);
        List<String> newData = worker.save(system);
        if (newData != null) {
            systems.clear();
            systems.addAll(newData);
        }
    }

    public void deleteSystem() {
        if (currentSystem != null) {
            List<String> newData = worker.delete(worker.getSystemData(currentSystem));
            if (newData != null) {
                systems.clear();
                systems.addAll(newData);
            }
        }
    }


    public void setWorker(DataController w) {
        worker = w;
        showSystems();
    }

    private void showSystems() {
        systems.clear();
        List<String> s = worker.getSystems();
//        for (String s : systems) {
//            System.out.println("v_id = " + a.getId() + ", name = " + a.getName());
        this.systems.addAll(s);
        systemsview.setItems(this.systems);
        systemsview.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    System.out.println("Click!");
                    int currentItemSelected = systemsview.getSelectionModel().getSelectedIndex();
                    if (currentItemSelected >= 0)
                        selectSystem(currentItemSelected);
                }
            }
        });
        membershipFunctionsView.setVisible(false);
        hideFuncParams();
        mfAntecedentsLabel.setVisible(false);
    }

    public void addVariable() {
        if (currentSystem != null) {
            FSystem system = worker.getSystemData(currentSystem);
            Variable variable = new Variable("Переменная", 0.0, 0.0, null, system.getId());
            List<String> newData = worker.save(variable);
            if (newData != null) {
                hideFuncParams();
                variables.clear();
                variables.addAll(newData);
                selectVariable(variables.size() - 1);
            }
        }
    }

    public void deleteVariable() {
        if (currentVariable != null) {
            List<String> newData = worker.delete(worker.getVariableData(currentVariable));
            if (newData != null) {
                variables.clear();
                variables.addAll(newData);
                hideFuncParams();
            }
        }
    }


    public void initialize() {
        tabs.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
                currentRule = null;
                hideFuncParams();
                hideRuleParams();
                if (Objects.equals(newValue.getText(), "Правила")) {
//                    rulesView.setOnMouseClicked(new EventHandler<MouseEvent>() {
//                        @Override
//                        public void handle(MouseEvent event) {
//                            currentRule = rulesView.getSelectionModel().getSelectedIndex();
//                            selectRule();
//                        }
//                    });
                    getOutputButton.setVisible(true);
                    mfAntecedentsLabel.setVisible(false);
//                    mfAntecedentsLabel.setText("Антецеденты");
                    outputTable.setVisible(true);
                    if (currentRule != null)
                        consList.setVisible(true);
                }
                else {
                    addVariable.setVisible(isEditable);
                    deleteVariable.setVisible(isEditable);
                    getOutputButton.setVisible(false);
                    outputTable.setVisible(false);
                    variablesView.setItems(variables);
                    mfAntecedentsLabel.setText("Функции принадлежности");
                }
            }
        });
        rulesView.setItems(rules);
        rulesView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int i = rulesView.getSelectionModel().getSelectedIndex();
                if (i >= 0)
                    selectRule(i);
            }
        });
        hideRuleParams();
        getOutputButton.setOnMouseClicked(event -> {
            showOutput();
        });
        outputTable.setItems(outputResults);
        varNameColumn.setCellValueFactory(new PropertyValueFactory<OutputResult, String>("name"));
        varValueColumn.setCellValueFactory(new PropertyValueFactory<OutputResult, Double>("value"));
        getOutputButton.setVisible(false);
        outputTable.setVisible(false);
        mfTypes.addAll(FunctionType.getFunctionTypes());
        addSystem.setVisible(isEditable);
        deleteSystem.setVisible(isEditable);
        changeSystem.setVisible(isEditable);
        addRule.setVisible(isEditable);
        deleteRule.setVisible(isEditable);
        addVariable.setVisible(isEditable);
        deleteVariable.setVisible(isEditable);
        addConsequent.setVisible(isEditable);
        deleteConsequent.setVisible(isEditable);
        changeConsequent.setVisible(isEditable);
        addMf.setVisible(isEditable);
        deleteMf.setVisible(isEditable);
        changeAntecedent.setVisible(isEditable);
        changeMf.setVisible(isEditable);
        conCoeffInput.setTextFormatter(getTextFormatter());
        ruleWeightInput.setTextFormatter(getTextFormatter());
        varBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue != null) {
                    String value = varBox.getValue();
                    if (!Objects.equals(function.getVariable().getName(), value) && value != null) {
                        System.out.println("new var = " + value);
                        function.setVariable(worker.getVariableByName(value, currentSystem));
                        setUpViewByFunctionType(function.getmType());
                    }
                }
            }
        });
        membershipFunctionsView.setOnMouseClicked(mfAntsViewClicked);

        conVarBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue != null) {
                    Variable v = worker.getVariableByName(newValue, currentSystem);
                    conVarTerms.clear();
                    FSystem s = worker.getSystemData(currentSystem);
                    if (s.getType() == SystemType.Sugeno) {
                        conVarTerms.addAll(worker.getVariables(currentSystem));
                        conVarTerms.remove(v.getName());
                    } else
                       conVarTerms.addAll(worker.getMembershipFunctionsById(v.getId()));
                }
            }
        });

        consList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int i = consList.getSelectionModel().getSelectedIndex();
                if (i >= 0)
                    selectConsequent(i);
            }
        });

        antVarBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue != null) {
                    Variable v = worker.getVariableByName(newValue, currentSystem);
                    antTerms.clear();
                    antTerms.addAll(worker.getMembershipFunctionsById(v.getId()));
                }
            }
        } );

        variablesView.setOnMouseClicked(event -> {
            int cur = variablesView.getSelectionModel().getSelectedIndex();
            if (cur >= 0)
                selectVariable(cur);
        });
        typeBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue != null && !Objects.equals(oldValue, newValue) && !newValue.equals(function.getmType().toString()))
                    setUpViewByFunctionType(FunctionType.getFunctionType(newValue));
            }
        });
//        membershipFunctionsView.setVisible(false);
//        mfAntecedentsLabel.setVisible(false);
//        }
//        worker.disconnect();
    }

    @FXML
    public void login() {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Авторизация");
        dialog.setHeaderText(null);

// Set the button types.
        ButtonType loginButtonType = new ButtonType("Ок", ButtonBar.ButtonData.OK_DONE);
        ButtonType loginCancelType = new ButtonType("Отмена", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, loginCancelType);

// Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField username = new TextField();
        username.setPromptText("Логин");
        PasswordField password = new PasswordField();
        password.setPromptText("Пароль");

        grid.add(new Label("Логин:"), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("Пароль:"), 0, 1);
        grid.add(password, 1, 1);

// Enable/Disable login button depending on whether a username was entered.
        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

// Do some validation (using the Java 8 lambda syntax).
        username.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

// Request focus on the username field by default.
        Platform.runLater(() -> username.requestFocus());

// Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(username.getText(), password.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(usernamePassword -> {
            State state = worker.connect(usernamePassword.getKey(), usernamePassword.getValue());
            if (state == State.Fail) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setHeaderText(null);
                alert.setContentText("Не удалось подключиться к базе данных");
                alert.showAndWait();
                addSystem.setVisible(false);
                deleteSystem.setVisible(false);
                if (currentSystem != null)
                    selectSystem(currentSystem);
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Успех");
                alert.setHeaderText(null);
                alert.setContentText("Авторизация успешна");
                alert.showAndWait();
                if (worker.isAdmin()) {
                    addSystem.setVisible(true);
                    deleteSystem.setVisible(true);
                }
                else {
                    addSystem.setVisible(false);
                    deleteSystem.setVisible(false);
                }
                if (currentSystem != null)
                    selectSystem(currentSystem);
            }
        });
    }

    public TextFormatter<Double> getTextFormatter() {
        Pattern validEditingState = Pattern.compile("-?(([1-9][0-9]*)|0)?(\\.[0-9]*)?");
        UnaryOperator<TextFormatter.Change> filter = c -> {
            String text = c.getControlNewText();
            if (validEditingState.matcher(text).matches()) {
                return c ;
            } else {
                return null ;
            }
        };

        StringConverter<Double> converter = new StringConverter<Double>() {

            @Override
            public Double fromString(String s) {
                if (s.isEmpty() || "-".equals(s) || ".".equals(s) || "-.".equals(s)) {
                    return 0.0 ;
                } else {
                    return Double.valueOf(s);
                }
            }


            @Override
            public String toString(Double d) {
                return d.toString();
            }
        };

        return new TextFormatter<>(converter, 0.0, filter);
    }

    public void selectSystem(int i) {
        if (i < 0)
            return;
        addRule.setVisible(false);
        deleteRule.setVisible(false);
        hideFuncParams();
        hideRuleParams();
        currentRule = null;
        currentVariable = null;
        function = null;
        membershipFunctions.clear();
        currentSystem = i;
        outputTable.setVisible(true);
        outputResults.clear();
        getOutputButton.setVisible(true);
        sTypes.clear();
        sSpecs.clear();
        FSystem s = worker.getSystemData(i);
        addVariable.setVisible(isEditable);
        deleteVariable.setVisible(isEditable);
        addRule.setVisible(isEditable);
        deleteRule.setVisible(isEditable);
        if (s != null) {
            isEditable = worker.isEditable(s);
            System.out.println("Editable = " + isEditable);
            sNameInput.setText(s.getName());
            sNameInput.setEditable(isEditable);
            if (isEditable) {
                sTypes.addAll(SystemType.getSystemTypes());
                sTypeBox.setItems(sTypes);
                sTypeBox.setValue(s.getType().toString());
                sSpecs.addAll(SpecializationType.getSpecializationTypes());
                sSpecBox.setItems(sSpecs);
                sSpecBox.setValue(s.getSpecialization().toString());
                changeSystem.setVisible(true);
                changeSystem.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        FSystem system = new FSystem(s.getId(),
                                sNameInput.getText(),
                                SystemType.getSystemType(sTypeBox.getValue()),
                                SpecializationType.getSpecializationType(sSpecBox.getValue()));
                        List<String> newData = worker.save(system);
                        if (newData != null) {
                            systems.clear();
                            systems.addAll(newData);
                        }
                    }
                });

            } else {
                sTypes.add(s.getType().toString());
                sTypeBox.setItems(sTypes);
                sTypeBox.setValue(sTypes.get(0));
                sSpecs.add(s.getSpecialization().toString());
                sSpecBox.setItems(sSpecs);
                sSpecBox.setValue(sSpecs.get(0));
            }
        }
        List<String> r = worker.getRules(i);
        if (r != null) {
            rules.clear();
            rules.addAll(r);
            rulesView.setItems(rules);
        }

        List<String> v = worker.getVariables(i);
        if (v != null) {
            variables.clear();
            variables.addAll(v);
            variablesView.setItems(variables);
        }

    }

    public void addMf() {
        System.out.println("WElp");
        if (currentVariable !=null) {
            System.out.println("Try to save");
            MembershipFunction f = new MembershipFunction(worker.getVariableData(currentVariable));
            List<String> newData = worker.save(f);
            if (newData != null) {
                membershipFunctions.clear();
                membershipFunctions.addAll(newData);
                selectMembershipFunction(membershipFunctions.size() - 1);
            }
        }
    }

    public void deleteMf() {
        if (function != null) {
            List<String> newData = worker.delete(function);
            if (newData != null) {
                hideEditMf();
                membershipFunctions.clear();
                membershipFunctions.addAll(newData);
            }
        }
    }

    private void hideVarParams() {
        hideFuncParams();
        variableName.setVisible(false);
        variableNameLabel.setVisible(false);
        variableValue.setVisible(false);
        variableValueLabel.setVisible(false);
        variableMinValue.setVisible(false);
        minValueLabel.setVisible(false);
        variableMaxValue.setVisible(false);
        maxValueLabel.setVisible(false);
    }
    public void selectVariable(int i) {
        currentVariable = i;
        hideVarParams();
        function = null;
        List<String> mf = worker.getMembershipFunctions(i);
        if (mf != null) {
            variableName.setVisible(true);
            variableNameLabel.setVisible(true);
            variableValue.setVisible(true);
            variableValueLabel.setVisible(true);
            variableMinValue.setVisible(true);
            minValueLabel.setVisible(true);
            variableMaxValue.setVisible(true);
            maxValueLabel.setVisible(true);
            membershipFunctions.clear();
            mfAntecedentsLabel.setVisible(true);
            membershipFunctionsView.setVisible(true);
            membershipFunctions.addAll(mf);
            membershipFunctionsView.setItems(membershipFunctions);


            mfAntsViewClicked = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    int cur = membershipFunctionsView.getSelectionModel().getSelectedIndex();
                    System.out.println("blablabla = " + cur + ", text = " + membershipFunctionsView.getSelectionModel().getSelectedItem());
                    hideFuncParams();
                    selectMembershipFunction(cur);
                }
            };
            membershipFunctionsView.onMouseClickedProperty().setValue(mfAntsViewClicked);
            Variable variable = worker.getVariableData(i);
            String val;
            if (variable.getValue() != null)
                val = variable.getValue().toString();
            else
                val = "null";
            TextFormatter<Double> tfVal = getTextFormatter();
            variableName.setText(variable.getName());
            variableValue.setTextFormatter(tfVal);
            variableValue.setText(val);
//            System.out.println(variable.getName() + "| min " + variable.getMinValue() + " | max " + variable.getMaxValue() + " | val " + variable.getValue());
//
            TextFormatter<Double> tfMinVal = getTextFormatter();
            variableMinValue.setTextFormatter(tfMinVal);
            variableMinValue.setText(variable.getMinValue()+"");
            TextFormatter<Double> tfMaxVal = getTextFormatter();
            variableMaxValue.setTextFormatter(tfMaxVal);
            variableMaxValue.setText(variable.getMaxValue()+"");
            variableName.setEditable(isEditable);
            variableMinValue.setEditable(isEditable);
            variableMaxValue.setEditable(isEditable);
            varValueOk.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    String name = variable.getName();
                    Double minValue = variable.getMinValue();
                    Double maxValue = variable.getMaxValue();
                    if (isEditable) {
                        name = variableName.getText();
                        minValue = (Double) variableMinValue.getTextFormatter().getValue();
                        maxValue = (Double) variableMaxValue.getTextFormatter().getValue();
                        if (name == null || minValue == null || maxValue == null || maxValue < minValue) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Ошибка");
                            alert.setHeaderText(null);
                            alert.setContentText("Поля переменной не могут быть пустыми, минимальное значение должно быть не больше максимального");
                            alert.showAndWait();
                            return;
                        }
                    }
                    Double data = (Double) variableValue.getTextFormatter().getValue();
//                    variable.setValue(data);
                    if (data != null && data >= minValue && data <= maxValue) {
                        Variable newVar = new Variable(variable.getId(), name, minValue, maxValue, data, variable.getSystemId());
                        List<String> newData = worker.save(newVar);
                        if (newData != null) {
                            variables.clear();
                            variables.addAll(newData);
                        }
                        if (!newVar.getName().equals(variable.getName())) {
                            rules.clear();
                            rules.addAll(worker.getRules(currentSystem));
                        }
                    }
                    else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Ошибка");
                        alert.setHeaderText(null);
                        alert.setContentText("Значение переменной должно быть больше минимального и меньше максимального");
                        alert.showAndWait();
                    }
                }
            });
            addMf.setVisible(isEditable);
            deleteMf.setVisible(isEditable);

        }
    }

    private void showOutput() {
        hideFuncParams();
        List<OutputResult> output = worker.getOutput(currentSystem);
        if (output == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText(null);
            alert.setContentText("Не заданы значения некоторых переменных");
            alert.showAndWait();
        }
        else {
            outputResults.clear();
            outputResults.addAll(output);
        }

    }

    public void setMfGraph() {
        List<List<Double>> data = worker.getGraphData(function);
//        mfGraph.getData().clear();
        graphData.clear();
        mfGraph.setData(graphData);
        XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
        ObservableList<XYChart.Data<Number, Number>> chartData = FXCollections.observableArrayList();
        Double xmin = data.get(0).get(0);
        Double xmax = data.get(0).get(0);
        Double ymin = data.get(1).get(0);
        Double ymax = data.get(1).get(0);
        for (int j = 0; j < data.get(0).size(); j++) {
            Double x = data.get(0).get(j);
            Double y = data.get(1).get(j);
            chartData.add(new XYChart.Data<Number, Number>(x, y));
            if (x > xmax)
                xmax = x;
            else if (x < xmin)
                xmin = x;
            if (y > ymax)
                ymax = y;
            else if (y < ymin)
                ymin = y;
//            System.out.println(series.getData().get(series.getData().size() - 1));
        }
        series.setData(chartData);
        graphData.add(series);
        mfGraph.setData(graphData);
//        mfGraph.getData().add(series);
        mfGraph.setTitle(function.getTerm());
        mfGraph.setLegendVisible(false);
        mfGraph.getYAxis().setAutoRanging(false);
        Double dy = 0.1 * (ymax - ymin);
        ((NumberAxis) mfGraph.getYAxis()).setLowerBound(-0.1);
        ((NumberAxis) mfGraph.getYAxis()).setUpperBound(1.1);
        ((NumberAxis) mfGraph.getYAxis()).setTickUnit(0.1);
        mfGraph.getXAxis().setAutoRanging(false);
        Double dx = 0.1 * (xmax - xmin);
        ((NumberAxis) mfGraph.getXAxis()).setLowerBound(xmin - dx);
        ((NumberAxis) mfGraph.getXAxis()).setUpperBound(xmax + dx);
        mfGraph.setDisable(false);
        mfGraph.setVisible(true);
    }

    private void hideEditMf() {
        mfTermLabel.setVisible(false);
        termInput.setVisible(false);
        typeBox.setVisible(false);
        mfTypeLabel.setVisible(false);
        mfVariableLabel.setVisible(false);
        varBox.setVisible(false);
        mfParam1Label_pid.setVisible(false);
        mfParam2Label_bar.setVisible(false);
        mfParam3Label.setVisible(false);
        mfParam4Label.setVisible(false);
        par1Field.setVisible(false);
        par2Field.setVisible(false);
        par3Field.setVisible(false);
        par4Field.setVisible(false);
        pidBox.setVisible(false);
        barBox.setVisible(false);
        mfActiveLabel.setVisible(false);
        activeCheck.setVisible(false);
        mfGraph.setVisible(false);
        changeMf.setVisible(false);
    }

    private void hideFuncParams() {
//        function = null;

        addMf.setVisible(false);
        deleteMf.setVisible(false);
        mfParamsLabel.setVisible(false);
//        membershipFunctionsView.setVisible(false);
        addMf.setVisible(false);
        deleteMf.setVisible(false);
        hideEditMf();
    }

    private void selectRule(int i) {
        addAnt.setVisible(isEditable);
        deleteAnt.setVisible(isEditable);
        addConsequent.setVisible(isEditable);
        deleteConsequent.setVisible(isEditable);
        showRuleData(i);
    }

    private void showRuleData(int i) {
        mfAntecedentsLabel.setVisible(true);
        membershipFunctionsView.setVisible(true);
        mfAntecedentsLabel.setText("Антецеденты");
        membershipFunctions.clear();
        currentRule = worker.getRuleData(i);
        System.out.println("WorkId = " + currentRule.getId());
        membershipFunctions.addAll(currentRule.getAntecedentsText());
        membershipFunctionsView.setItems(membershipFunctions);
        conLabel.setVisible(true);
        consList.setVisible(true);
        consequents.clear();
        consequents.addAll(currentRule.getConsequentsText());
        consList.setItems(consequents);
        mfAntsViewClicked = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int i = membershipFunctionsView.getSelectionModel().getSelectedIndex();
                if (i >= 0)
                    selectAntecedent(i);
            }
        };
        membershipFunctionsView.onMouseClickedProperty().setValue(mfAntsViewClicked);
    }

    public void addConsequent() {
        if (currentRule != null) {
            Consequent consequent = new Consequent(null, null, "consequent");
            currentRule.getConsequents().add(consequent);
            List<String> newData = worker.save(currentRule);
            if (newData != null) {
                rules.clear();
                rules.addAll(newData);
                consequents.clear();
                consequents.addAll(currentRule.getConsequentsText());
                hideConsequentParams();
                deleteConsequent.setVisible(true);
                addConsequent.setVisible(true);
            }
        }
    }

    //TODO clean after rule deletion!!!!! Reddis!1! Script!@!@!@!
    public void deleteConsequent() {
        if (currentRule != null && currentConsequent != null) {
            currentRule.getConsequents().remove(currentConsequent.intValue());
            currentConsequent = null;
            List<String> newData = worker.save(currentRule);
            if (newData != null) {
                rules.clear();
                rules.addAll(newData);
                consequents.clear();
                consequents.addAll(currentRule.getConsequentsText());
                hideConsequentParams();
                addConsequent.setVisible(true);
                deleteConsequent.setVisible(true);
            }
        }
    }

    public void changeConsequent() {
        if (currentRule != null && currentConsequent != null && isEditable) {
            Consequent c = currentRule.getConsequents().get(currentConsequent);
            FSystem system = worker.getSystemData(currentSystem);
            boolean isSugeno = system.getType() == SystemType.Sugeno;
            Consequent changed = null;
            if (isSugeno) {
                Variable v = worker.getVariableByName(conVarBox.getValue(), currentSystem);
                Double coeff = (Double) conCoeffInput.getTextFormatter().getValue();
                String f = conVarTermBox.getValue();
                Variable mfvar = (Objects.equals(f, "None")) ? null : worker.getVariableByName(f, currentSystem);
                MembershipFunction mf = new MembershipFunction(null,
                        (mfvar == null) ? FunctionType.Crisp : FunctionType.Linear,
                        mfvar,
                        coeff,
                        null,
                        null,
                        null,
                        null,
                        BarrierType.Nothing,
                        true
                        );
                changed = new Consequent(c.getId(),
                        mf,
                        v,
                        (v != null)
                ? (mf.getVariable() != null)
                        ? (v.getName() + " = " + mf.getParameter1() + " " + mf.getVariable().getName())
                        : (v.getName() + " = " + mf.getParameter1())
                : "consequent");
            } else {
                MembershipFunction mf = worker.getMembershipFunctionByName(conVarTermBox.getValue());
                changed = new Consequent(
                        c.getId(),
                        mf,
                        null,
                        (mf != null)
                            ? (mf.getVariable().getName() + " is " + mf.getTerm())
                            : "consequent"
                );
            }
            currentRule.getConsequents().set(currentConsequent, changed);
            List<String> newData = worker.save(currentRule);
            if (newData == null) {
                hideConsequentParams();
                addConsequent.setVisible(true);
                deleteConsequent.setVisible(true);
            } else {
                rules.clear();
                rules.addAll(newData);
                consequents.clear();
                consequents.addAll(currentRule.getConsequentsText());
            }
        }
    }

    //TODO edit mode select consequents
    private void selectConsequent(int i) {
        conVarLabel.setVisible(true);
        Consequent consequent = currentRule.getConsequents().get(i);
        currentConsequent = i;
        FSystem system = worker.getSystemData(currentSystem);
        boolean isSugeno = system.getType() == SystemType.Sugeno;
        MembershipFunction functionLocal = consequent.getMembershipFunction();
        Variable variable = (isSugeno) ? consequent.getVariable() : (functionLocal != null) ? functionLocal.getVariable() : null;
        conVars.clear();
        if (isEditable) {
            conVars.addAll(worker.getVariables(currentSystem));
        } else if (variable != null) {
            conVars.add(variable.getName());

        }
        conVarBox.setVisible(true);
        conVarBox.setItems(conVars);
        if (variable != null)
            conVarBox.setValue(variable.getName());
        isEqLabel.setVisible(true);
        conVarTermLabel.setVisible(true);
        conVarTerms.clear();
        addConsequent.setVisible(isEditable);
        deleteConsequent.setVisible(isEditable);
        changeConsequent.setVisible(isEditable);
        if (isSugeno) {
            isEqLabel.setText("=");
            conCoeffLabel.setVisible(true);
            conCoeffInput.setVisible(true);
            if (functionLocal != null)
                conCoeffInput.setText(functionLocal.getParameter1().toString());
            conCoeffInput.setEditable(isEditable);
            conVarTermLabel.setText("Переменная");
            if (isEditable) {
                conVarTerms.addAll(worker.getVariables(currentSystem));
                if (variable != null)
                    conVarTerms.remove(variable.getName());
            } else if (functionLocal != null)
                conVarTerms.add((functionLocal.getVariable() != null)
                    ? functionLocal.getVariable().getName()
                    : "None");

            conVarTermBox.setValue((functionLocal != null && functionLocal.getVariable() != null)
                    ? functionLocal.getVariable().getName()
                    : "None");
        } else {
            isEqLabel.setText("is");
            conVarTermLabel.setText("Терм:");
            if (functionLocal != null) {
                if (isEditable)
                    conVarTerms.addAll(worker.getMembershipFunctionsById(variable.getId()));
                else
                    conVarTerms.add(functionLocal.getTerm());
                conVarTermBox.setValue(functionLocal.getTerm());
            }
        }
        conVarTermBox.setVisible(true);
        conVarTermBox.setItems(conVarTerms);
    }

    private void hideConsequentParams() {
        conVarLabel.setVisible(false);
        conVarBox.setVisible(false);
        isEqLabel.setVisible(false);
        conCoeffLabel.setVisible(false);
        conCoeffInput.setVisible(false);
        conVarTermLabel.setVisible(false);
        conVarTermBox.setVisible(false);
        changeConsequent.setVisible(false);
    }

    public void addAntecedent() {
        if (currentRule != null) {
            Antecedent antecedent = new Antecedent(null, "antecedent");
            currentRule.getAntecedents().add(antecedent);
            List<String> newData = worker.save(currentRule);
            if (newData != null) {
                rules.clear();
                rules.addAll(newData);
                membershipFunctions.clear();
                membershipFunctions.addAll(currentRule.getAntecedentsText());
                hideAntecedentParams();
                addAnt.setVisible(true);
                deleteAnt.setVisible(true);

            }
        }
    }

    public void deleteAntecedent() {
        if (currentRule != null && currentAntecedent != null) {
            currentRule.getAntecedents().remove(currentAntecedent.intValue());
            currentAntecedent = null;
            List<String> newData = worker.save(currentRule);
            if (newData != null) {
                rules.clear();
                rules.addAll(newData);
                membershipFunctions.clear();
                membershipFunctions.addAll(currentRule.getAntecedentsText());
                hideAntecedentParams();
                addAnt.setVisible(true);
                deleteAnt.setVisible(true);
            }
        }
    }

    public void changeAntecedent() {
        if (currentRule != null && currentAntecedent != null && isEditable) {
            Variable variable = worker.getVariableByName(antVarBox.getValue(), currentSystem);
            MembershipFunction term = worker.getMembershipFunctionByName(antTermBox.getValue());
            Antecedent a = currentRule.getAntecedents().get(currentAntecedent);
            currentRule.getAntecedents().set(currentAntecedent, new Antecedent(a.getId(),
                    term,
                    variable.getName() + " is " + term.getTerm()));
            Double weight = (Double) ruleWeightInput.getTextFormatter().getValue();
            if (weight == null || weight < 0 || weight  > 1) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setHeaderText(null);
                alert.setContentText("Вес правила должен быть вещественным числом в диапазоне [0; 1]");
                alert.showAndWait();
                return;
            }
            currentRule = new Rule(currentRule.getId(),
                    currentRule.getAntecedents(),
                    currentRule.getConsequents(),
                    AntecedentConnectionType.getAntecedentConnectionType(antConBox.getValue()),
                    weight,
                    currentRule.getSystemId());
            List<String> newData = worker.save(currentRule);
            if (newData == null) {
                hideAntecedentParams();
                addAnt.setVisible(true);
                deleteAnt.setVisible(true);
            } else {
                rules.clear();
                rules.addAll(newData);
                membershipFunctions.clear();
                membershipFunctions.addAll(currentRule.getAntecedentsText());
            }
        }
    }


    public void addRule() {
        if (currentSystem != null) {
            FSystem system = worker.getSystemData(currentSystem);
            Rule rule = new Rule(new ArrayList<>(), new ArrayList<>(), AntecedentConnectionType.And, 1, system.getId());
            List<String> newData = worker.save(rule);
            if (newData != null) {
                hideFuncParams();
                rules.clear();
                rules.addAll(newData);
            }
        }
    }

    public void deleteRule() {
        if (currentRule != null) {
            List<String> newData = worker.delete(currentRule);
            if (newData != null) {
                hideFuncParams();
                rules.clear();
                rules.addAll(newData);
                currentRule = null;
            }
        }
    }

    private void selectAntecedent(int i) {
        antVarLabel.setVisible(true);
        antVars.clear();
        currentAntecedent = i;
        Antecedent antecedent = currentRule.getAntecedents().get(i);
        MembershipFunction function = antecedent.getMembershipFunction();
        Variable var = (function != null ) ? function.getVariable() : null;
        antVarBox.setVisible(true);
        changeAntecedent.setVisible(isEditable);
        antVarBox.setItems(antVars);
        isLabel.setVisible(true);
        antTermLabel.setVisible(true);
        antTerms.clear();
        antTermBox.setVisible(true);
        antTermBox.setItems(antTerms);
        antConLabel.setVisible(true);
        antConnections.clear();
        ruleWeightInput.setVisible(true);
        ruleWeightInput.setEditable(isEditable);
        ruleWeightLabel.setVisible(true);
        ruleWeightInput.setText(currentRule.getWeight() + "");
        antConBox.setVisible(true);
        antConBox.setItems(antConnections);
        if (isEditable) {
            antVars.addAll(worker.getVariables(currentSystem));
            System.out.println("welp " + antVars.size());
            if (function != null) {
                antVarBox.setValue(var.getName());
                antTerms.addAll(worker.getMembershipFunctionsById(var.getId()));
                antTermBox.setValue(function.getTerm());
            }
            antConnections.addAll(AntecedentConnectionType.getConnectionTypes());
            antConBox.setValue(currentRule.getAntecedentConnectionType().toString());
        } else {
            if (function != null) {
                antVars.add(var.getName());
                antVarBox.setValue(var.getName());
                antTerms.add(function.getTerm());
                antTermBox.setValue(function.getTerm());
            }
            antConnections.add(currentRule.getAntecedentConnectionType().toString());
            antConBox.setValue(currentRule.getAntecedentConnectionType().toString());
        }
    }

    private void hideAntecedentParams() {
        currentAntecedent = null;
        addAnt.setVisible(false);
        deleteAnt.setVisible(false);
        changeAntecedent.setVisible(false);
        antVarLabel.setVisible(false);
        antVarBox.setVisible(false);
        isLabel.setVisible(false);
        antTermLabel.setVisible(false);
        antTermBox.setVisible(false);
        antConLabel.setVisible(false);
        antConBox.setVisible(false);
        ruleWeightLabel.setVisible(false);
        ruleWeightInput.setVisible(false);
    }

    private void hideRuleParams() {
        hideAntecedentParams();
        hideConsequentParams();
        mfAntecedentsLabel.setVisible(false);
        membershipFunctionsView.setVisible(false);
        antVarLabel.setVisible(false);
        antVarBox.setVisible(false);
        isLabel.setVisible(false);
        antTermLabel.setVisible(false);
        antTermBox.setVisible(false);
        antConLabel.setVisible(false);
        antConBox.setVisible(false);
        conVarLabel.setVisible(false);
        conVarBox.setVisible(false);
        isEqLabel.setVisible(false);
        conCoeffLabel.setVisible(false);
        conCoeffInput.setVisible(false);
        conVarTermLabel.setVisible(false);
        conVarTermBox.setVisible(false);
        conLabel.setVisible(false);
        consList.setVisible(false);
        addConsequent.setVisible(false);
        deleteConsequent.setVisible(false);
        changeConsequent.setVisible(false);
    }

    private void setUpViewByFunctionType(FunctionType type) {
        System.out.println("Welp new type is " + type);
        TextFormatter<Double> formatter;
        mfParam1Label_pid.setVisible(false);
        par1Field.setVisible(false);
        mfParam2Label_bar.setVisible(false);
        par2Field.setVisible(false);
        mfParam3Label.setVisible(false);
        par3Field.setVisible(false);
        mfParam4Label.setVisible(false);
        par4Field.setVisible(false);
        barBox.setVisible(false);
        pidBox.setVisible(false);
        switch (type) {
            case Linguistic:
                mfParents.clear();
                if (isEditable) {
                    mfParents.addAll(worker.getMembershipFunctionsById(function.getVariable().getId()));
                    mfParents.remove(function.getTerm());
                    mfBarriers.addAll(BarrierType.getBarriers());
                }
                else {
                    if (function.getParent() != null)
                        mfParents.add(function.getParent().getTerm());
                    mfBarriers.add(function.getBarrier().toString());
                }
                pidBox.setVisible(true);
                pidBox.setItems(mfParents);
                if (function.getParent() != null)
                    pidBox.setValue(function.getParent().getVariable().equals(function.getVariable())
                            ? function.getParent().getTerm()
                            : mfParents.get(0));
                mfParam1Label_pid.setText("Функция:");
                mfParam1Label_pid.setVisible(true);
                mfBarriers.clear();
                mfParam2Label_bar.setVisible(true);
                mfParam2Label_bar.setText("Барьер:");
                barBox.setVisible(true);
                barBox.setItems(mfBarriers);
                barBox.setValue(function.getBarrier().toString());
                changeMf.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        String term = termInput.getText();
                        FunctionType type = FunctionType.getFunctionType(typeBox.getValue());
                        Variable variable = worker.getVariableByName(varBox.getValue(), currentSystem);
                        MembershipFunction parent = worker.getMembershipFunctionByName(pidBox.getValue());
                        BarrierType barrierType = BarrierType.getBarrierType(barBox.getValue());
                        if (isEditable) {
                            function = new MembershipFunction(
                                    function.getId(),
                                    term,
                                    type,
                                    variable,
                                    null,
                                    null,
                                    null,
                                    null,
                                    parent,
                                    barrierType,
                                    activeCheck.isSelected());
                            worker.save(function);
                            if (!worker.getVariableData(currentVariable).equals(function.getVariable())) {
                                hideFuncParams();
                                selectVariable(currentVariable);
                            } else {
                                membershipFunctions.clear();
                                membershipFunctions.addAll(worker.getMembershipFunctions(currentVariable));

                            }
                        }
                    }
                });
                break;
            case Gauss:
                mfParam1Label_pid.setVisible(true);
                mfParam1Label_pid.setText("Параметр 1:");
                formatter = getTextFormatter();
                par1Field.setTextFormatter(formatter);
                par1Field.setVisible(true);
                par1Field.setEditable(isEditable);
                if (function.getParameter1() != null)
                    par1Field.setText(function.getParameter1().toString());
                mfParam2Label_bar.setVisible(true);
                mfParam2Label_bar.setText("Параметр 2:");
                formatter = getTextFormatter();
                par2Field.setTextFormatter(formatter);
                par2Field.setVisible(true);
                par2Field.setEditable(isEditable);
                if (function.getParameter2() != null)
                    par2Field.setText(function.getParameter2().toString());
                changeMf.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    String term = termInput.getText();
                    FunctionType type = FunctionType.getFunctionType(typeBox.getValue());
                    Variable variable = worker.getVariableByName(varBox.getValue(), currentSystem);
                    Double parameter1 = (Double) par1Field.getTextFormatter().getValue();
                    Double parameter2 = (Double) par2Field.getTextFormatter().getValue();
                    if (isEditable) {
                        if (parameter1 == null || parameter2 == null) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Ошибка");
                            alert.setHeaderText(null);
                            alert.setContentText("Параметры функции принадлежности не могут быть пустыми");
                            alert.showAndWait();
                        } else {
                            worker.save(new MembershipFunction(
                                    function.getId(),
                                    term,
                                    type,
                                    variable,
                                    parameter1,
                                    parameter2,
                                    null,
                                    null,
                                    null,
                                    BarrierType.Nothing,
                                    activeCheck.isSelected()
                            ));
                            membershipFunctions.clear();
                            membershipFunctions.addAll(worker.getMembershipFunctions(currentVariable));
                        }
                    }
                }
                });
                break;
            case Shoulder:
            case Triangle:
                mfParam1Label_pid.setVisible(true);
                mfParam1Label_pid.setText("Параметр 1:");
                formatter = getTextFormatter();
                par1Field.setTextFormatter(formatter);
                par1Field.setVisible(true);
                par1Field.setEditable(isEditable);
                if (function.getParameter1() != null)
                    par1Field.setText(function.getParameter1().toString());
                mfParam2Label_bar.setVisible(true);
                mfParam2Label_bar.setText("Параметр 2:");
                formatter = getTextFormatter();
                par2Field.setTextFormatter(formatter);
                par2Field.setVisible(true);
                par2Field.setEditable(isEditable);
                if (function.getParameter2() != null)
                    par2Field.setText(function.getParameter2().toString());
                mfParam3Label.setVisible(true);
                formatter = getTextFormatter();
                par3Field.setTextFormatter(formatter);
                par3Field.setVisible(true);
                par3Field.setEditable(isEditable);
                if (function.getParameter3() != null)
                    par3Field.setText(function.getParameter3().toString());
                changeMf.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        String term = termInput.getText();
                        FunctionType type = FunctionType.getFunctionType(typeBox.getValue());
                        Variable variable = worker.getVariableByName(varBox.getValue(), currentSystem);
                        Double parameter1 = (Double) par1Field.getTextFormatter().getValue();
                        Double parameter2 = (Double) par2Field.getTextFormatter().getValue();
                        Double parameter3 = (Double) par3Field.getTextFormatter().getValue();
                        if (isEditable) {
                            if (parameter1 == null || parameter2 == null || parameter3 == null) {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Ошибка");
                                alert.setHeaderText(null);
                                alert.setContentText("Параметры функции принадлежности не могут быть пустыми");
                                alert.showAndWait();
                            }
                            else {
                                worker.save(new MembershipFunction(
                                        function.getId(),
                                        term,
                                        type,
                                        variable,
                                        parameter1,
                                        parameter2,
                                        parameter3,
                                        null,
                                        null,
                                        BarrierType.Nothing,
                                        activeCheck.isSelected()
                                ));
                                membershipFunctions.clear();
                                membershipFunctions.addAll(worker.getMembershipFunctions(currentVariable));
                            }
                        }
                    }
                });
                break;
            case Trapezoidal:
                mfParam1Label_pid.setVisible(true);
                mfParam1Label_pid.setText("Параметр 1:");
                formatter = getTextFormatter();
                par1Field.setTextFormatter(formatter);
                par1Field.setVisible(true);
                par1Field.setEditable(isEditable);
                if (function.getParameter1() != null)
                    par1Field.setText(function.getParameter1().toString());
                mfParam2Label_bar.setVisible(true);
                mfParam2Label_bar.setText("Параметр 2:");
                formatter = getTextFormatter();
                par2Field.setTextFormatter(formatter);
                par2Field.setVisible(true);
                par2Field.setEditable(isEditable);
                if (function.getParameter2() != null)
                    par2Field.setText(function.getParameter2().toString());
                mfParam3Label.setVisible(true);
                formatter = getTextFormatter();
                par3Field.setTextFormatter(formatter);
                par3Field.setVisible(true);
                par3Field.setEditable(isEditable);
                if (function.getParameter3() != null)
                    par3Field.setText(function.getParameter3().toString());
                mfParam4Label.setVisible(true);
                formatter = getTextFormatter();
                par4Field.setTextFormatter(formatter);
                par4Field.setVisible(true);
                par4Field.setEditable(isEditable);
                if (function.getParameter4() != null)
                    par4Field.setText(function.getParameter4().toString());
                changeMf.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        String term = termInput.getText();
                        FunctionType type = FunctionType.getFunctionType(typeBox.getValue());
                        Variable variable = worker.getVariableByName(varBox.getValue(), currentSystem);
                        Double parameter1 = (Double) par1Field.getTextFormatter().getValue();
                        Double parameter2 = (Double) par2Field.getTextFormatter().getValue();
                        Double parameter3 = (Double) par3Field.getTextFormatter().getValue();
                        Double parameter4 = (Double) par4Field.getTextFormatter().getValue();
                        if (isEditable) {
                            if (parameter1 == null || parameter2 == null || parameter3 == null || parameter4 == null) {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Ошибка");
                                alert.setHeaderText(null);
                                alert.setContentText("Параметры функции принадлежности не могут быть пустыми");
                                alert.showAndWait();
                            }
                            else {
                                worker.save(new MembershipFunction(
                                        function.getId(),
                                        term,
                                        type,
                                        variable,
                                        parameter1,
                                        parameter2,
                                        parameter3,
                                        parameter4,
                                        null,
                                        BarrierType.Nothing,
                                        activeCheck.isSelected()
                                ));
                                membershipFunctions.clear();
                                membershipFunctions.addAll(worker.getMembershipFunctions(currentVariable));
                            }
                        }
                    }
                });
                break;
            default:
                break;
        }
    }
    private void selectMembershipFunction(int i) {
        function = null;
        function = worker.getMembershipFunctionData(i);
        if (function != null) {
            hideFuncParams();
            addMf.setVisible(isEditable);
            deleteMf.setVisible(isEditable);
            mfGraph.setVisible(true);
            mfAntecedentsLabel.setVisible(true);
            membershipFunctionsView.setVisible(true);
            mfAntecedentsLabel.setText("Функции принадлежности");
            System.out.println("selected " + function.getTerm());
            changeMf.setVisible(isEditable);
            setMfGraph();
            mfParamsLabel.setVisible(true);
            mfTermLabel.setVisible(true);
            termInput.setVisible(true);
            termInput.setText(function.getTerm());
            termInput.setEditable(isEditable);
            typeBox.setVisible(true);
            mfTypeLabel.setVisible(true);
            mfVariables.clear();
            mfTypes.clear();
            if (isEditable) {
                mfTypes.addAll(FunctionType.getFunctionTypes());
                mfVariables.addAll(worker.getVariables(currentSystem));
            } else {
                mfTypes.add(function.getmType().toString());
                mfVariables.add(function.getVariable().getName());
            }
            typeBox.setItems(mfTypes);
            varBox.setItems(mfVariables);
            typeBox.setValue(function.getmType().toString());

            setUpViewByFunctionType(function.getmType());
            activeCheck.setVisible(true);
            mfActiveLabel.setVisible(true);
            activeCheck.setSelected(function.isActive());
            mfVariableLabel.setVisible(true);
            varBox.setVisible(true);
            varBox.setItems(mfVariables);
            varBox.setValue(function.getVariable().getName());


//                    getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
//                @Override
//                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//                    if (!Objects.equals(oldValue, newValue) && !Objects.equals(function.getVariable().getName(), newValue) && newValue != null) {
//                        System.out.println("new var = " + newValue);
//                        function.setVariable(worker.getVariableByName(newValue, currentSystem));
//                        setUpViewByFunctionType(function, function.getmType());
//                    }
//                }
//            });
//            System.out.println(variable.getName() + "| min " + variable.getMinValue() + " | max " + variable.getMaxValue() + " | val " + variable.getValue());
//
        }

    }

}
