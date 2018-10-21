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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import model.CloseRFile;
import model.Expressions.*;
import model.OpenRFile;
import model.ReadFile;
import model.Statement.*;
import repository.MyIRepository;
import utils.*;
import utils.adt.*;

import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML private Label dateTimeLabel;
    @FXML private ListView listView;
    @FXML private TextArea textArea;

    private List<String> expandedItems;

    private String pathToExpandedStmt =  "E:\\GitRepoCollege\\MAP\\JavaRepo - Toy language\\Lab11\\src\\Files\\expandedStatements.txt";
    private String pathToStatements = "E:\\GitRepoCollege\\MAP\\JavaRepo - Toy language\\Lab11\\src\\Files\\statements.txt";
    private int idxChosen;

    /**
     * When this method is called, it will change the scene to
     * MainTableView window
     */
    public void buttonPressed(ActionEvent event) throws IOException, DivideByZeroException, InvalidOperandException, NotExistingException, InterruptedException {
        int idxListView = listView.getSelectionModel().getSelectedIndex();
        if (idxListView != -1) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("MainTableView.fxml"));
            Parent mainTableViewParent = loader.load();
            Scene mainTableViewScene = new Scene(mainTableViewParent);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setScene(mainTableViewScene);
            window.show();

            this.idxChosen = idxListView;
            MainTableView ctrlNew = loader.getController();
            ctrlNew.initData(idxListView + 1);
        }
    }

    @FXML
    private String getCurrentDateTime()
    {
        String currentDate = "";
        currentDate += "Current date: ";

        currentDate += new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime());

        //String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        return currentDate;
    }

    public void readExpandedStatements(){
        this.expandedItems = new ArrayList<>();
        Boolean firstRun = true;
        try(BufferedReader br = new BufferedReader(new FileReader(pathToExpandedStmt))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null){
                if (line.equals("[statement]")){
                    if (firstRun){
                        firstRun = false;
                    }
                    else{
                        this.expandedItems.add(sb.toString());
                        sb.setLength(0);
                    }
                    line = br.readLine();
                    continue;
                }

                sb.append(line).append('\n');
                line = br.readLine();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * This method will initialize all the objects
     * It's auto-called
     */
    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.readExpandedStatements();
        // Showing current datetime
        dateTimeLabel.setText(this.getCurrentDateTime());

        this.listView.setItems(this.getNonExpandedISTMTS());

        // Select item at index = 1
        //this.listView.getSelectionModel().selectIndices(0);

        this.listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                textArea.setText(expandedItems.get(listView.getSelectionModel().getSelectedIndex()));
            }
        });



    }

    /**
     * This method reads all the statements from a file (as a string)
     * @retrun - array of strings
     */
    private ArrayList<String> readFileStatements(){
        try(BufferedReader br = new BufferedReader(new FileReader(pathToStatements))) {
            ArrayList<String> list = new ArrayList<>();
            String line = br.readLine();

            while (line != null) {
                if (!String.valueOf(line.charAt(0)).equals("/")) {
                    list.add(line);
                }
                line = br.readLine();
            }

            return list;
        } catch (IOException e) {
            // TO DO
            // ADD SOME EXCEPTION BOX
            throw new RuntimeException("Fuq");
        }
    }

    private ObservableList<String> getNonExpandedISTMTS(){
        ArrayList<String> list = this.readFileStatements();

        ObservableList<String> listStr = FXCollections.observableArrayList(list);
        return listStr;
    }

    public int getIdxChosen() {
        return idxChosen;
    }
}
