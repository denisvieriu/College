import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class main {
    public static void main(String[] args) throws ParseException {
        String dateTimeString = "2017/03/0411:42:22";
        Date result = null;

        Date d2;




        try {
            d2 = new SimpleDateFormat("yyyy-mm-ddhh:mm:ss").parse(dateTimeString);
            java.sql.Date d1= new java.sql.Date(d2.getTime());
            String s = d1.toString();
            System.out.println(s);

        } catch (ParseException e) {
            e.printStackTrace();
        }


    }
}
