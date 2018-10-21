package utilsTableView;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class SymbTableView {
    private SimpleStringProperty varname;
    private SimpleIntegerProperty value;

    public SymbTableView(String _varname, int _value){
        this.varname = new SimpleStringProperty(_varname);
        this.value = new SimpleIntegerProperty(_value);
    }

    public String getVarname() {
        return varname.get();
    }

    public SimpleStringProperty varnameProperty() {
        return varname;
    }

    public void setVarname(String varname) {
        this.varname.set(varname);
    }

    public int getValue() {
        return value.get();
    }

    public SimpleIntegerProperty valueProperty() {
        return value;
    }

    public void setValue(int value) {
        this.value.set(value);
    }
}
