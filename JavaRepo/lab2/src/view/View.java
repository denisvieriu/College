package view;

import controller.Controller;
import exceptions.DivideByZeroException;
import exceptions.InvalidOperandException;
import exceptions.NotExistingException;
import model.*;
import model.Expressions.*;
import model.Statement.*;
import utils.*;
import utils.adt.*;

import java.util.Arrays;
import java.util.Scanner;

class View {
    private Controller ctrl;

    View(Controller _ctrl){
        ctrl = _ctrl;
    }

    private String makeString(int n){
        char []chars = new char[n];
        Arrays.fill(chars, '-');
        return new String(chars);
    }

    private void mainMenu() {
        String del = this.makeString(40);
        System.out.println(del);
        System.out.println("Main menu");
        System.out.println("Here are some hardcoded examples:");

        System.out.println("1 - Example 1:");
        System.out.println("\tv=2;Print(v)");

        System.out.println("2 - Example 2:");
        System.out.println("\ta=2+3*5;b=a+1;Print(b)");

        System.out.println("3 - Example 3:");
        System.out.println("\ta=2-2;");
        System.out.println("\t(If a Then v = 2 Else v = 3);");
        System.out.println("\tPrint(v)");

        System.out.println("4 - Example 4:");
        //System.out.println("\t");

        System.out.println("5 - Example 5:");

        System.out.println("6 - Example 6:");
        System.out.println("\tv=10;new(v,20);new(a,22);print(v) ");

        System.out.println("7 - Example 7:");
        System.out.println("\tv=10;new(v,20);new(a,22);print(100+rH(v));print(100+rH(a))");

        System.out.println("8 - Example 8:");
        System.out.println("\tv=10;new(v,20);new(a,22);wH(a,30);print(a);print(rH(a))");

        System.out.println("9 - Example 9:");
        System.out.println("\tv=10;new(v,20);new(a,22);wH(a,30);print(a);print(rH(a));a=0");

        System.out.println("10 - Example 10:");
        System.out.println("\t10 + (2<6) = 11");

        System.out.println("11 - Example 11");
        System.out.println("\t(10 + 2)<6 = 0");

        System.out.println("12 - Example 12");
        System.out.println("\tv=6; (while (v-4) print(v);v=v-1);print(v)");
        System.out.println(del);
    }

    private void executeCommand(int command) throws NotExistingException, DivideByZeroException, InvalidOperandException {
        MyIStack<IStmt> exeStack = new MyStack<>();
        MyIList<Integer> out = new MyList<>();
        MyIDictionary<String, Integer> dict = new MyDictionary<>();
        MyIFileTable<Integer, FileData> fileTable = new MyFileTable<>();
        MyIHeap<Integer, Integer> heap = new MyHeap<>();
        IStmt stmt = null;
        switch (command){
            case 1:
            {
                stmt = new CompStmt(new AssignStmt("v", new ConstExp(2)), new PrintStmt(new VarExp("v")));
                break;
            }
            case 2:
            {
                stmt = new CompStmt(new AssignStmt("a", new ArithExp('+',new ConstExp(2),new
                    ArithExp('*',new ConstExp(3), new ConstExp(5)))),
                    new CompStmt(new AssignStmt("b",new ArithExp('+',new VarExp("a"), new
                            ConstExp(1))), new PrintStmt(new VarExp("b"))));
                break;
            }
            case 3:
            {
                stmt = new CompStmt(new AssignStmt("a", new ArithExp('-', new ConstExp(2), new ConstExp(2))),
                        new CompStmt(new IfStmt(new VarExp("a"), new AssignStmt("v", new ConstExp(2)), new AssignStmt("v", new ConstExp(3))),
                                new PrintStmt(new VarExp("v"))));
                break;
            }
            case 4:
            {
                stmt =  new CompStmt(
                            new OpenRFile("var_f", "E:\\JavaRepo\\lab2\\src\\test.in"),
                            new CompStmt(
                                new ReadFile(new VarExp("var_f"), "var_c"),
                                new CompStmt(
                                        new PrintStmt(new VarExp("var_c")),
                                        new CompStmt(
                                                new IfStmt(
                                                    new VarExp("var_c"),
                                                    new CompStmt(new ReadFile(new VarExp("var_f"), "var_c"), new PrintStmt(new VarExp("var_c"))),
                                                    new PrintStmt(new ConstExp(0))),
                                                new CloseRFile(new VarExp("var_f"))))));
                break;
            }
            case 5:
            {
                stmt =  new CompStmt(
                        new OpenRFile("var_f", "E:\\JavaRepo\\lab2\\src\\test.in"),
                        new CompStmt(
                                new ReadFile(new VarExp("var_f"), "var_c"),
                                new CompStmt(
                                        new ReadFile(new VarExp("var_f"), "var_c"),
                                        new CompStmt(
                                                new PrintStmt(new VarExp("var_c")),
                                                new CompStmt(
                                                        new IfStmt(
                                                                new VarExp("var_c"),
                                                                new CompStmt(new ReadFile(new VarExp("var_f"), "var_c"), new PrintStmt(new VarExp("var_c"))),
                                                                new PrintStmt(new ConstExp(0))),
                                                        new CloseRFile(new VarExp("var_f")))))));
                break;
            }
            case 6:
            {
                // heap allocation
                stmt =  new CompStmt(
                        new AssignStmt("v", new ConstExp(10)),
                        new CompStmt(
                                new NewH("v", new ConstExp(20)),
                                new CompStmt(
                                        new NewH("a", new ConstExp(22)),
                                        new PrintStmt(new VarExp("v")))));
                break;
            }
            case 7:
            {
                //heap reading
                stmt = new CompStmt(
                        new AssignStmt("v", new ConstExp(10)),
                        new CompStmt(
                                new NewH("v", new ConstExp(20)),
                                new CompStmt(
                                        new NewH("a", new ConstExp(22)),
                                        new CompStmt(
                                                new PrintStmt(new ArithExp('+', new ConstExp(100), new ReadH("v"))),
                                                new PrintStmt(new ArithExp('+', new ConstExp(100), new ReadH("a")))
                                        ))));
                break;
            }
            case 8:
            {
                // heap writing
                stmt = new CompStmt(
                        new AssignStmt("v", new ConstExp(10)),
                        new CompStmt(
                                new NewH("v", new ConstExp(20)),
                                new CompStmt(
                                        new NewH("a", new ConstExp(22)),
                                        new CompStmt(
                                                new WriteH("a", new ConstExp(30)),
                                                new CompStmt(
                                                        new PrintStmt(new VarExp("a")),
                                                        new PrintStmt(new ReadH("a"))
                                                )
                                        )
                                )
                        )
                );
                break;
            }
            case 9:
            {
                // garbage collector
                stmt =  new CompStmt(
                        new AssignStmt("v", new ConstExp(10)),
                        new CompStmt(
                                new NewH("v", new ConstExp(20)),
                                new CompStmt(
                                        new NewH("a", new ConstExp(22)),
                                        new CompStmt(
                                                new WriteH("a", new ConstExp(30)),
                                                new CompStmt(
                                                        new PrintStmt(new ReadH("a")),
                                                        new AssignStmt("a", new ConstExp(0))
                                                )
                                        )
                                )
                        )
                );

                break;
            }
            case 10:
            {
                break;
            }
            case 11:
            {
                break;
            }
            case 12:
            {
                stmt = new CompStmt(
                        new AssignStmt("v", new ConstExp(6)),
                        new CompStmt(
                                new WhileStmt(
                                        new BooleanExp("!=", new ArithExp('-', new VarExp("v"), new ConstExp(4)), new ConstExp(0)),
                                        new CompStmt(new PrintStmt(new VarExp("v")),
                                                     new AssignStmt("v", new ArithExp('-', new VarExp("v"), new ConstExp(1))))),
                                new PrintStmt(new VarExp("v"))
                        ));

                break;
            }
            default:
                throw new NotExistingException("Invalid command.");
        }
        PrgState prgState = new PrgState(exeStack, dict, out, fileTable, heap, stmt);

        ctrl.addPrgState(prgState);
        ctrl.allStep();
    }

    public void executeMainMenu(){
        Scanner sc = new Scanner(System.in);
        String str;
        int command;
        this.mainMenu();

        System.out.println("Enter your command:");
        command = sc.nextInt();
        try {
            this.executeCommand(command);
        } catch (NotExistingException | InvalidOperandException | DivideByZeroException e) {
            System.out.println(e);
        }

    }


}
