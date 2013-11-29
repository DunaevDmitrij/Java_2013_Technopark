package Global.Imps;

/**
 * Created with IntelliJ IDEA.
 * User: Sidorov Vadim
 * Date: 20.11.13
 * Time: 13:39
 */


import Global.Address;
import Global.DBService;
import Global.MessageSystem;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Thread.sleep;

public class DBServiceImp implements DBService {

    private final Address address;
    private final MessageSystem ms;

    //параметры подключения к базе
    private static final String serverName = "localhost";
    private static final String database = "PlaneDB";
    private static final String dbUser = "PlaneDB";
    private static final String dbPassword = "PlaneDB";
    private static final String dbUrl = "jdbc:mysql://" + serverName + "/" + database;

    private static final String SQL_DIR = "sql";
    private static final Configuration CFG = new Configuration();
    private Map<String, Object> queryVariables;

    private Connection connect;

    private static class QueryWrapper {
        private ResultSet rs;
        private String queryString;

        private QueryWrapper(String queryString) {
            super();
            this.queryString = queryString;
        }

        public static QueryWrapper query(String queryString, Connection connect) throws SQLException {
            QueryWrapper qrw = new QueryWrapper(queryString);
            qrw.internalQuery(connect);
            return qrw;
        }

        private void internalQuery(Connection connect) throws SQLException {
            Statement statement = connect.createStatement();
            this.rs = statement.executeQuery(this.queryString);
        }

        public int rowCounts() throws SQLException {
            this.rs.last();
            final int rowCounts = this.rs.getRow();
            this.rs.beforeFirst();
            return rowCounts;
        }

        public ResultSet getResultSet() {
            return this.rs;
        }
    }

    public DBServiceImp(MessageSystem ms) throws SQLException {
        super();
        this.ms = ms;
        this.address = new Address();
        //регистрируем DBServiceImp в MsgSystem
        ms.addService(this);
        //регестрируем DBServiceImp в AddressService, чтобы каждый мог обратиться к DBService
        ms.getAddressService().setAccountService(this.address);

        this.connect = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    @Override
    public Long getUserIdByUserName(String login, String password) {
        this.queryVariables = dataToKey(new String[] {"login", "password"},
                                                       login,   password);
        String queryString = generateSQL("userid_by_name.sql", this.queryVariables);

        System.out.println(queryString);

        try {
            QueryWrapper qrw = QueryWrapper.query(queryString, this.connect);
            if (qrw.rowCounts() == 1)
            {
                qrw.getResultSet().next();
                return qrw.getResultSet().getLong("idUser"); //возвращает ID пользователя
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return USER_NOT_EXIST;
    }


    private static String generateSQL(String templateName, Map<String, Object> context) {
        Writer stream = new StringWriter();
        try {
            Template template = CFG.getTemplate(SQL_DIR + File.separator + templateName);
            template.process(context, stream);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
            return null;
        }
        return stream.toString();
    }

    private static Map<String, Object> dataToKey(String[] keys, Object ... values) {
        Map<String, Object> map = new HashMap<>();
        // Цикл по элементам массива ключей
        for (int I = 0; I < keys.length; ++I) {
            map.put(keys[I], values[I]);
        }
        return map;
    }

    @Override
    public void run() {
        while(true){
            this.ms.execForAbonent(this);

            try {
                sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Address getAddress() {
        return this.address;
    }

    @Override
    public MessageSystem getMessageSystem() {
        return this.ms;
    }
}
