package Model;

import Model.Auto;
public class Bike extends Auto
{
    private int weight;
    private String type;

    public Bike(String _color, int _weight, String _type)
    {
        super(_color);
        weight = _weight;
        type = _type;
    }

    public int getWeight()
    {
        return weight;
    }

    public String getType()
    {
        return type;
    }

    public void setWeight(int _weight)
    {
        weight = _weight;
    }

    public void setType(String _type)
    {
        type = _type;
    }

    @Override
    public String toString()
    {
        return "Bike of type: " + type + ", color: " + color + ", with a weight of: " +  weight;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o instanceof Bike)
        {
            Bike b = (Bike)o;
            return weight == b.weight && type.equals(b.type);
        }
        return false;
    }
}
