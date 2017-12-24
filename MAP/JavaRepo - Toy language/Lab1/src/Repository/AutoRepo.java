package Repository;
import Model.Auto;

public interface AutoRepo
{
    public boolean addAuto(Auto v);
    public boolean removeAuto(Auto v);
    public Auto[] getAuto();
}

