package Model;

import Model.Auto;

public class Car extends Auto
{
    private String type;

    public Car(String color, String _type)
    {
        super(color);
        type = _type;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String _type)
    {
        type = _type;
    }

    @Override
    public String toString()
    {
        return "Car of type: " + type + " " + ", color: " + color;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o instanceof Car)
        {
            Car c = (Car)o;
            return type.equals(c.type);
        }
        return false;
    }
}
