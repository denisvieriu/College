package utilsTableView;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class FileTableView {
    private SimpleIntegerProperty identifier;
    private SimpleStringProperty fileName;

    public FileTableView(int _identifier, String _fileName){
        this.identifier = new SimpleIntegerProperty(_identifier);
        this.fileName = new SimpleStringProperty(_fileName);
    }

    public int getIdentifier() {
        return identifier.get();
    }

    public SimpleIntegerProperty identifierProperty() {
        return identifier;
    }

    public void setIdentifier(int identifier) {
        this.identifier.set(identifier);
    }

    public String getFileName() {
        return fileName.get();
    }

    public SimpleStringProperty fileNameProperty() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName.set(fileName);
    }
}
