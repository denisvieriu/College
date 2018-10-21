package Repository;

import Repository.AutoRepo;
import Model.Auto;
import Exception.WordContainsException;

public class ArrayAuto implements  AutoRepo
{
    private Auto vech[];
    private int nrVech;

    public ArrayAuto()
    {
        vech = new Auto[100];
        nrVech = 0;
    }

    public boolean addAuto(Auto v)
    {
        int i;
        for (i = 0; i < vech.length; i++)
        {
            if (v.equals(vech[i]))
                return false;
        }
        vech[nrVech++] = v;
        return true;
    }

    public boolean removeAuto(Auto v)
    {
        for (int i = 0; i < nrVech; i++)
        {
            if (vech[i].equals(v))
            {
                vech[i] = vech[nrVech-- - 1];
                return true;
            }
        }
        return false;
    }

    public Auto[] getAuto()
    {
        Auto v[] = new Auto[nrVech];
        for (int i=0; i < nrVech; i++)
        {
            v[i] = vech[i];
        }
        return v;
    }



}


