package Controller;

import Repository.AutoRepo;
import Model.Auto;

public class Controller
{
    private AutoRepo repo;
    public Controller(AutoRepo _repo)
    {
        repo = _repo;
    }

    public boolean addAuto(Auto v)
    {
        return repo.addAuto(v);
    }

    public boolean removeAuto(Auto v)
    {
        return repo.removeAuto(v);
    }

    public Auto[] getAuto()
    {
        return repo.getAuto();
    }

    public void display(String _color)
    {
        Auto vechs[] = repo.getAuto();
        for (int i = 0; i < vechs.length; i++)
        {
            if (vechs[i].getColor().equals(_color))
            {
                System.out.println(vechs[i]);
            }
        }
    }

    public void displayAll()
    {
        Auto vechs[] = repo.getAuto();
        for (int i = 0; i < vechs.length; i++)
        {
           System.out.println(vechs[i]);
        }
    }


}
