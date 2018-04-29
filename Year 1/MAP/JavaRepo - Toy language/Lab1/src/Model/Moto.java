package Model;

import Model.Auto;
public class Moto extends Auto
{
    private String speed;

    public Moto(String _color, String _speed)
    {
        super(_color);
        speed = _speed;
    }

    public String getSpeed()
    {
        return speed;
    }

    public void setSpeed(String _speed)
    {
        speed = _speed;
    }

    @Override
    public String toString()
    {
        return "Moto of color: " + color + ", with a max speed of: " + speed;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o instanceof Moto)
        {
            Moto m = (Moto)o;
            return speed.equals(m.speed);
        }
        return false;
    }
}

