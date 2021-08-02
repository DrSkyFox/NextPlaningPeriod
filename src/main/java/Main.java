import org.firebirdsql.ds.FBSimpleDataSource;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Main {
    private static final Logger LOG = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws ClassNotFoundException, IOException {
        String path = new File(".").getCanonicalPath();
        Handler handler = new FileHandler(path + "\\" + "logs.log", 5000, 5, true);
        handler.setFormatter(new SimpleFormatter());
        LOG.addHandler(handler);

        String url ="//127.0.0.1:3080/D:/Sborka/__FITNESS/IBDATA/FITNESS145.FDB";
        String login = "ucs";
        String pass = "ucs";



        Iterator<String> params = Arrays.stream(args).iterator();

        while (params.hasNext()) {
            if(params.next().equalsIgnoreCase("-url")) {
                url = params.next();
                break;
            }
        }


        FBSimpleDataSource dataSource = new FBSimpleDataSource();
        dataSource.setDatabase(url);
        dataSource.setUserName(login);
        dataSource.setPassword(pass);
        dataSource.setEncoding("WIN1251");

        try {
            NextPlan nextPlan = new NextPlan(dataSource.getConnection());
            nextPlan.setLOG(handler);
            nextPlan.doStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
