package com.fuzzy.fuzzyexpertsystemstool;

import com.fuzzy.fuzzyexpertsystemstool.database.DatabaseWorker;
import com.fuzzy.fuzzyexpertsystemstool.dbmodel.DBSystem;
import com.fuzzy.fuzzyexpertsystemstool.dbmodel.OutputResult;
import com.fuzzy.fuzzyexpertsystemstool.model.MembershipFunction;
import com.fuzzy.fuzzyexpertsystemstool.model.Rule;
import com.fuzzy.fuzzyexpertsystemstool.model.Variable;
import com.fuzzy.fuzzyexpertsystemstool.types.FunctionType;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;

import java.util.List;
import java.util.Objects;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class InterfaceController {
//    public ListView lstview;
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
    private LineChart<Double, Double> mfGraph;
    @FXML
    private TextField variableName;

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
    private ObservableList<String> conVars = FXCollections.observableArrayList();
    private ObservableList<String> conVarTerms = FXCollections.observableArrayList();
    private ObservableList<String> consequents = FXCollections.observableArrayList();
    private DatabaseWorker worker = null;
    private Integer currentSystem = null;
    private Integer currentRule = null;



    public void setWorker(DatabaseWorker w) {
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
                    selectSystem(currentItemSelected);
                }
            }
        });
        membershipFunctionsView.setVisible(false);
        hideFuncParams();
        mfAntecedentsLabel.setVisible(false);
    }


    public void initialize() {
        tabs.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
                currentRule = null;
                hideFuncParams();
                hideRuleParams();
                if (Objects.equals(newValue.getText(), "Rules")) {
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
                }
                else {
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
                currentRule = rulesView.getSelectionModel().getSelectedIndex();
                selectRule();
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

//        membershipFunctionsView.setVisible(false);
//        mfAntecedentsLabel.setVisible(false);
//        }
//        worker.disconnect();
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
        hideFuncParams();
        hideRuleParams();
        currentRule = null;
        membershipFunctions.clear();
        currentSystem = i;
        outputTable.setVisible(true);
        outputResults.clear();
        getOutputButton.setVisible(true);
        DBSystem s = worker.getSystemData(i);
        if (s != null) {
            sNameInput.setText(s.getName());
            sNameInput.setEditable(false);
            sTypes.clear();
            sTypes.add(s.getType().toString());
            sTypeBox.setItems(sTypes);
            sTypeBox.setValue(sTypes.get(0));
            sSpecs.clear();
            sSpecs.add(s.getSpecialization().toString());
            sSpecBox.setItems(sSpecs);
            sSpecBox.setValue(sSpecs.get(0));
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
            variablesView.setOnMouseClicked(event -> {
                int cur = variablesView.getSelectionModel().getSelectedIndex();
                selectVariable(cur);
            });
        }

    }

    public void selectVariable(int i) {
        hideFuncParams();
        List<String> mf = worker.getMembershipFunctions(i);
        if (mf != null) {
            membershipFunctions.clear();
            mfAntecedentsLabel.setVisible(true);
            membershipFunctionsView.setVisible(true);
            membershipFunctions.addAll(mf);
            membershipFunctionsView.setItems(membershipFunctions);
            membershipFunctionsView.setOnMouseClicked(event -> {
                int cur = membershipFunctionsView.getSelectionModel().getSelectedIndex();
                System.out.println("blablabla = " + cur + ", text = " + membershipFunctionsView.getSelectionModel().getSelectedItem());
                hideFuncParams();
                selectMembershipFunction(cur);
            });
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
            varValueOk.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    Double data = (Double) variableValue.getTextFormatter().getValue();
                    variable.setValue(data);
                    if (data >= variable.getMinValue() && data <= variable.getMaxValue())
                        worker.save(variable);
                    else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText("Ошибка");
                        alert.setContentText("Значение переменной должно быть больше минимального и меньше максимального");
                        alert.showAndWait();
                    }
                }
            });

        }
    }

    private void showOutput() {
        hideFuncParams();
        List<OutputResult> output = worker.getOutput(currentSystem);
        if (output == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Ошибка");
            alert.setContentText("Не заданы значения некоторых переменных");
            alert.showAndWait();
        }
        else {
            outputResults.clear();
            outputResults.addAll(output);
        }

    }

    public void setMfGraph(int i) {
        List<List<Double>> data = worker.getGraphData(i);
        mfGraph.getData().clear();
        XYChart.Series<Double, Double> series = new XYChart.Series<Double, Double>();
        for (int j = 0; j < data.get(0).size(); j++) {
            series.getData().add(new XYChart.Data<>(data.get(0).get(j), data.get(1).get(j)));
        }
        mfGraph.getData().add(series);
        mfGraph.setTitle(membershipFunctions.get(i));
        mfGraph.setLegendVisible(false);
        mfGraph.getYAxis().setAutoRanging(true);
        mfGraph.getXAxis().setAutoRanging(true);
    }

    private void hideFuncParams() {
        mfParamsLabel.setVisible(false);
        membershipFunctionsView.setVisible(false);
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
    }

    private void selectRule() {
        if (currentRule != null)
            showRuleData();
    }

    private void showRuleData() {
        mfAntecedentsLabel.setVisible(true);
        membershipFunctionsView.setVisible(true);
        mfAntecedentsLabel.setText("Антецеденты");
        membershipFunctions.clear();
        Rule rule = worker.getRuleData(currentRule);
        membershipFunctions.addAll(rule.getAntecedentsText());
        membershipFunctionsView.setItems(membershipFunctions);
        conLabel.setVisible(true);
        consList.setVisible(true);
        consequents.clear();
        consequents.addAll(rule.getConsequentsText());
        consList.setItems(consequents);

    }

    private void hideRuleParams() {
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
    }

    private void selectMembershipFunction(int i) {
        mfAntecedentsLabel.setVisible(true);
        mfAntecedentsLabel.setText("Функции принадлежности");
        membershipFunctionsView.setVisible(true);
        mfGraph.setVisible(true);
        setMfGraph(i);

        MembershipFunction function = worker.getMembershipFunctionData(i);
        String val;
        if (function != null) {
            mfParamsLabel.setVisible(true);
            mfTermLabel.setVisible(true);
            termInput.setVisible(true);
            termInput.setText(function.getTerm());
            typeBox.setVisible(true);
            mfTypeLabel.setVisible(true);
            mfTypes.clear();
            mfTypes.add(function.getmType().toString());
            typeBox.setItems(mfTypes);
            typeBox.setValue(mfTypes.get(0));
            activeCheck.setVisible(true);
            mfActiveLabel.setVisible(true);
            activeCheck.setVisible(true);
            activeCheck.setSelected(function.isActive());
            mfVariableLabel.setVisible(true);
            mfVariables.clear();
            mfVariables.add(function.getVariable().getName());
            varBox.setVisible(true);
            varBox.setItems(mfVariables);
            varBox.setValue(mfVariables.get(0));
            mfVariableLabel.setVisible(true);
            switch (function.getmType()) {
                case Linguistic:
                    mfParents.clear();
                    mfParents.add(function.getParent().getTerm());
                    pidBox.setVisible(true);
                    pidBox.setItems(mfParents);
                    pidBox.setValue(mfParents.get(0));
                    mfParam1Label_pid.setText("Функция:");
                    mfParam1Label_pid.setVisible(true);
                    mfBarriers.clear();
                    mfBarriers.add(function.getBarrier().toString());
                    mfParam2Label_bar.setVisible(true);
                    mfParam2Label_bar.setText("Барьер:");
                    barBox.setVisible(true);
                    barBox.setItems(mfBarriers);
                    barBox.setValue(mfBarriers.get(0));
                    break;
                case Gauss:
                    mfParam1Label_pid.setVisible(true);
                    mfParam1Label_pid.setText("Параметр 1:");
                    par1Field.setVisible(true);
                    par1Field.setEditable(false);
                    par1Field.setText(function.getParameter1().toString());
                    mfParam2Label_bar.setVisible(true);
                    mfParam2Label_bar.setText("Параметр 2:");
                    par2Field.setVisible(true);
                    par2Field.setEditable(false);
                    par2Field.setText(function.getParameter2().toString());
                    break;
                case Shoulder:
                case Triangle:
                    mfParam1Label_pid.setVisible(true);
                    mfParam1Label_pid.setText("Параметр 1:");
                    par1Field.setVisible(true);
                    par1Field.setEditable(false);
                    par1Field.setText(function.getParameter1().toString());
                    mfParam2Label_bar.setVisible(true);
                    mfParam2Label_bar.setText("Параметр 2:");
                    par2Field.setVisible(true);
                    par2Field.setEditable(false);
                    par2Field.setText(function.getParameter2().toString());
                    mfParam3Label.setVisible(true);
                    par3Field.setVisible(true);
                    par3Field.setEditable(false);
                    par3Field.setText(function.getParameter3().toString());
                    break;
                case Trapezoidal:
                    mfParam1Label_pid.setVisible(true);
                    mfParam1Label_pid.setText("Параметр 1:");
                    par1Field.setVisible(true);
                    par1Field.setEditable(false);
                    par1Field.setText(function.getParameter1().toString());
                    mfParam2Label_bar.setVisible(true);
                    mfParam2Label_bar.setText("Параметр 2:");
                    par2Field.setVisible(true);
                    par2Field.setEditable(false);
                    par2Field.setText(function.getParameter2().toString());
                    mfParam3Label.setVisible(true);
                    par3Field.setVisible(true);
                    par3Field.setEditable(false);
                    par3Field.setText(function.getParameter3().toString());
                    mfParam4Label.setVisible(true);
                    par4Field.setVisible(true);
                    par4Field.setEditable(false);
                    par4Field.setText(function.getParameter4().toString());
                    break;
                default:
                    break;
            }
//            System.out.println(variable.getName() + "| min " + variable.getMinValue() + " | max " + variable.getMaxValue() + " | val " + variable.getValue());
//
        }

    }

}
