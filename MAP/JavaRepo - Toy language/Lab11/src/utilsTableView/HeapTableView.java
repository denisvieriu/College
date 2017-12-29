package utilsTableView;

import javafx.beans.property.SimpleIntegerProperty;

public class HeapTableView {
    private SimpleIntegerProperty address, value;

    public HeapTableView(int _address, int _value) {
        this.address = new SimpleIntegerProperty(_address);
        this.value = new SimpleIntegerProperty(_value);
    }


    public int getAddress() {
        return address.get();
    }

    public SimpleIntegerProperty addressProperty() {
        return address;
    }

    public void setAddress(int address) {
        this.address.set(address);
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
