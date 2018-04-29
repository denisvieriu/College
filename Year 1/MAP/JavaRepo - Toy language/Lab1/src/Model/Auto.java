package Model;

public abstract class Auto
{
    String color;

    public Auto()
    {
        color = "";
    }

    public Auto(String _color)
    {
        color = _color;
    }

    public String getColor()
    {
        return color;
    }
}
