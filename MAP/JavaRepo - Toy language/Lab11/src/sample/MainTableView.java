package sample;

import controllerrepo.ControllerRepo;
import exceptions.DivideByZeroException;
import exceptions.InvalidOperandException;
import exceptions.NotExistingException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.CloseRFile;
import model.Expressions.*;
import model.OpenRFile;
import model.ReadFile;
import model.Statement.*;
import repository.MyIRepository;
import repository.MyRepository;
import utils.*;
import utils.adt.*;
import utilsTableView.FileTableView;
import utilsTableView.HeapTableView;
import utilsTableView.SymbTableView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainTableView implements Initializable {
    @FXML private TextField noOfPrgStatesTextField;

    // Table view for heap
    @FXML private TableView<HeapTableView> tableView;
    @FXML private TableColumn<HeapTableView, Integer> adressColumn;
    @FXML private TableColumn<HeapTableView, Integer> valueColumn;

    // Table view for symbol table
    @FXML private TableView<SymbTableView> symbolTableView;
    @FXML private TableColumn<SymbTableView, String> varnameSymbColumn;
    @FXML private TableColumn<SymbTableView, Integer> valueSymbColumn;

    // Table view for file table
    @FXML private TableView<FileTableView> fileTableView;
    @FXML private TableColumn<FileTableView, Integer> identifierFileColumn;
    @FXML private TableColumn<FileTableView, Integer> fileNameFileColumn;

    @FXML private ListView exeStackView;
    @FXML private ListView outView;
    @FXML private ListView prgStateId;

    private int outNr;

    private ControllerRepo ctrl;
    private MyIRepository repo;

    private void setNoOfPrgStates(){
        int nr = this.ctrl.getNoOfPrgState();
        this.noOfPrgStatesTextField.setText(Integer.toString(nr));
    }

    private PrgState getPrgStateByIdx(int idx){
        List<PrgState> listPrgStates = this.ctrl.getPrgStateCtrl();
        for (PrgState prg : listPrgStates){
            if (prg.getId() == idx){
                return prg;
            }
        }
        return null;
    }

    public void setExeStackView(int idx) {
        PrgState p = getPrgStateByIdx(idx);

        this.exeStackView.getItems().clear();
        this.exeStackView.getItems().addAll(getExeStack(p));
    }

    public List<String> getExeStack(PrgState p){
        List<String> list = new ArrayList<>();
        MyIStack<IStmt> exeStack = p.getExeStack();

        for (IStmt stmt : exeStack.getAll()){
            list.add(stmt.toString());
        }

        return list;
    }

    public void setFileTable(){
        this.identifierFileColumn.setCellValueFactory(new PropertyValueFactory<>("identifier"));
        this.fileNameFileColumn.setCellValueFactory(new PropertyValueFactory<>("fileName"));

        this.fileTableView.getItems().clear();
        this.fileTableView.getItems().setAll(getFileTable());
    }

    public ObservableList<FileTableView> getFileTable(){
        ObservableList<FileTableView> fileTableObs = FXCollections.observableArrayList();
        MyIFileTable<Integer, FileData> fileTableCtrl = this.ctrl.getFileTableCtrl();

        for (int id : fileTableCtrl.getAll()){
            fileTableObs.add(new FileTableView(id, fileTableCtrl.get(id).getFilename()));
        }

        return fileTableObs;
    }


    public void setSymbTable(int idx)
    {
        this.varnameSymbColumn.setCellValueFactory(new PropertyValueFactory<>("varname"));
        this.valueSymbColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        PrgState p = getPrgStateByIdx(idx);

        this.symbolTableView.getItems().clear();
        this.symbolTableView.getItems().setAll(getSymbTable(p));
    }

    public ObservableList<SymbTableView> getSymbTable(PrgState p){
        ObservableList<SymbTableView> symbTableObs = FXCollections.observableArrayList();
        MyIDictionary<String, Integer> symbTable = p.getSymTable();

        for (String key : symbTable.getAll())
        {
            symbTableObs.add(new SymbTableView(key, symbTable.get(key)));
        }

        return  symbTableObs;
    }

    private void setHeapTable() {
        this.adressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        this.valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));

        tableView.getItems().clear();
        tableView.getItems().setAll(getHeap());
    }

    public ObservableList<HeapTableView> getHeap(){
        ObservableList<HeapTableView> hp = FXCollections.observableArrayList();
        MyIHeap<Integer, Integer> heapCtrl = ctrl.getHeapCtrl();

        for (int address : heapCtrl.getAll()) {
            hp.add(new HeapTableView(address, heapCtrl.get(address)));
        }

        return hp;
    }

    public void initData(int idx) throws InterruptedException, DivideByZeroException, NotExistingException, InvalidOperandException {
        this.repo = new MyRepository();
        this.ctrl = new ControllerRepo(repo);
        MyIStack<IStmt> exeStack = new MyStack<>();
        MyIList<Integer> out = new MyList<>();
        MyIDictionary<String, Integer> dict = new MyDictionary<>();
        MyIFileTable<Integer, FileData> fileTable = new MyFileTable<>();
        MyIHeap<Integer, Integer> heap = new MyHeap<>();

        this.addStmtToController(idx, exeStack, out, dict, fileTable, heap);

        this.outNr = 0;
        this.ctrl.initController();
        initDataMain();
    }

    public void setOut(){
        MyIList<Integer> out = this.ctrl.getOutCtrl();
        List<String> list = new ArrayList<>();

        int idx = 1;
        for (int v : out.getAll()) {
            if (idx > this.outNr) {
                list.add(Integer.toString(v));
                this.outNr = idx;
            };
            idx++;
        }

        this.outView.getItems().addAll(list);
    }

    public void setPrgStateId(){

        List<PrgState> list = this.ctrl.getPrgStateCtrl();

        // Clear the list
        this.prgStateId.getItems().clear();
        List<String> listId = new ArrayList<>();

        for (PrgState p : list)
        {
            listId.add(Integer.toString(p.getId()));
        }

        this.prgStateId.getItems().addAll(listId);

        this.prgStateId.getSelectionModel().selectIndices(0);
    }

    public void initDataMain()
    {
        this.setNoOfPrgStates(); //done
        this.setHeapTable();     //done
        this.setOut();           //done
        this.setFileTable();     //
        this.setPrgStateId();    //done
}

    public void buttonPressed(ActionEvent event) throws IOException {
        this.ctrl.uninitController();
        Parent mainTableViewParent = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene mainTableViewScene = new Scene(mainTableViewParent);

        // This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(mainTableViewScene);
        window.show();
    }

    public void buttonPressedRun() throws InterruptedException {
        this.ctrl.oneStepForGui();
        if (this.ctrl.getNoOfPrgState() > 0) {
            this.initDataMain();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.prgStateId.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (prgStateId.getSelectionModel().getSelectedIndex() != -1) {
                String str = prgStateId.getSelectionModel().getSelectedItem().toString();
               int idx = Integer.parseInt(str);

               setSymbTable(idx);
               setExeStackView(idx);
                }
            }
        });
    }

    private void addStmtToController(
            int command,
            MyIStack<IStmt> exeStack,
            MyIList<Integer> out,
            MyIDictionary<String, Integer> dict,
            MyIFileTable<Integer, FileData> fileTable,
            MyIHeap<Integer, Integer> heap
    ) throws NotExistingException, DivideByZeroException, InvalidOperandException, InterruptedException {

        IStmt stmt = null;
        switch (command){
            case 1:
            {
                stmt = new CompStmt(new AssignStmt("v", new ConstExp(2)), new PrintStmt(new VarExp("v")));
                break;
            }
            case 2:
            {
                stmt = new CompStmt(new AssignStmt("a", new ArithExp('+',new ConstExp(2),new
                        ArithExp('*',new ConstExp(3), new ConstExp(5)))),
                        new CompStmt(new AssignStmt("b",new ArithExp('+',new VarExp("a"), new
                                ConstExp(1))), new PrintStmt(new VarExp("b"))));
                break;
            }
            case 3:
            {
                stmt = new CompStmt(new AssignStmt("a", new ArithExp('-', new ConstExp(2), new ConstExp(2))),
                        new CompStmt(new IfStmt(new VarExp("a"), new AssignStmt("v", new ConstExp(2)), new AssignStmt("v", new ConstExp(3))),
                                new PrintStmt(new VarExp("v"))));
                break;
            }
            case 4:
            {
                stmt =  new CompStmt(
                        new OpenRFile("var_f", "E:\\GitRepoCollege\\MAP\\JavaRepo - Toy language\\lab2\\Input"),
                        new CompStmt(
                                new ReadFile(new VarExp("var_f"), "var_c"),
                                new CompStmt(
                                        new PrintStmt(new VarExp("var_c")),
                                        new CompStmt(
                                                new IfStmt(
                                                        new VarExp("var_c"),
                                                        new CompStmt(new ReadFile(new VarExp("var_f"), "var_c"), new PrintStmt(new VarExp("var_c"))),
                                                        new PrintStmt(new ConstExp(0))),
                                                new CloseRFile(new VarExp("var_f"))))));
                break;
            }
            case 5:
            {
                stmt =  new CompStmt(
                        new OpenRFile("var_f", "E:\\JavaRepo\\lab2\\src\\test.in"),
                        new CompStmt(
                                new ReadFile(new VarExp("var_f"), "var_c"),
                                new CompStmt(
                                        new ReadFile(new VarExp("var_f"), "var_c"),
                                        new CompStmt(
                                                new PrintStmt(new VarExp("var_c")),
                                                new CompStmt(
                                                        new IfStmt(
                                                                new VarExp("var_c"),
                                                                new CompStmt(new ReadFile(new VarExp("var_f"), "var_c"), new PrintStmt(new VarExp("var_c"))),
                                                                new PrintStmt(new ConstExp(0))),
                                                        new CloseRFile(new VarExp("var_f")))))));
                break;
            }
            case 6:
            {
                // heap allocation
                stmt =  new CompStmt(
                        new AssignStmt("v", new ConstExp(10)),
                        new CompStmt(
                                new NewH("v", new ConstExp(20)),
                                new CompStmt(
                                        new NewH("a", new ConstExp(22)),
                                        new PrintStmt(new VarExp("v")))));
                break;
            }
            case 7:
            {
                //heap reading
                stmt = new CompStmt(
                        new AssignStmt("v", new ConstExp(10)),
                        new CompStmt(
                                new NewH("v", new ConstExp(20)),
                                new CompStmt(
                                        new NewH("a", new ConstExp(22)),
                                        new CompStmt(
                                                new PrintStmt(new ArithExp('+', new ConstExp(100), new ReadH("v"))),
                                                new PrintStmt(new ArithExp('+', new ConstExp(100), new ReadH("a")))
                                        ))));
                break;
            }
            case 8:
            {
                // heap writing
                stmt = new CompStmt(
                        new AssignStmt("v", new ConstExp(10)),
                        new CompStmt(
                                new NewH("v", new ConstExp(20)),
                                new CompStmt(
                                        new NewH("a", new ConstExp(22)),
                                        new CompStmt(
                                                new WriteH("a", new ConstExp(30)),
                                                new CompStmt(
                                                        new PrintStmt(new VarExp("a")),
                                                        new PrintStmt(new ReadH("a"))
                                                )
                                        )
                                )
                        )
                );
                break;
            }
            case 9:
            {
                // garbage collector
                stmt =  new CompStmt(
                        new AssignStmt("v", new ConstExp(10)),
                        new CompStmt(
                                new NewH("v", new ConstExp(20)),
                                new CompStmt(
                                        new NewH("a", new ConstExp(22)),
                                        new CompStmt(
                                                new WriteH("a", new ConstExp(30)),
                                                new CompStmt(
                                                        new PrintStmt(new ReadH("a")),
                                                        new AssignStmt("a", new ConstExp(0))
                                                )
                                        )
                                )
                        )
                );

                break;
            }
            case 10:
            {
                break;
            }
            case 11:
            {
                break;
            }
            case 12:
            {
                stmt = new CompStmt(
                        new AssignStmt("v", new ConstExp(6)),
                        new CompStmt(
                                new WhileStmt(
                                        new BooleanExp("!=", new ArithExp('-', new VarExp("v"), new ConstExp(4)), new ConstExp(0)),
                                        new CompStmt(new PrintStmt(new VarExp("v")),
                                                new AssignStmt("v", new ArithExp('-', new VarExp("v"), new ConstExp(1))))),
                                new PrintStmt(new VarExp("v"))
                        ));

                break;
            }
            case 13:
            {
                stmt = new CompStmt(
                        new AssignStmt("v", new ConstExp(10)),
                        new CompStmt(
                                new NewH("a", new ConstExp(22)),
                                new CompStmt(
                                        new ForkStmt(new CompStmt(
                                                new WriteH("a", new ConstExp(30)),
                                                new CompStmt(
                                                        new AssignStmt("v", new ConstExp(32)),
                                                        new CompStmt(
                                                                new PrintStmt(new VarExp("v")),
                                                                new PrintStmt(new ReadH("a"))
                                                        )
                                                )
                                        )),
                                        new CompStmt(
                                                new PrintStmt(new VarExp("v")),
                                                new PrintStmt(new ReadH("a"))
                                        ))
                        )
                );
                break;
            }
            default:
                throw new NotExistingException("Invalid command.");
        }

        PrgState prgState = new PrgState(exeStack, dict, out, fileTable, heap, stmt);
        this.ctrl.addPrgState(prgState);
    }
}
