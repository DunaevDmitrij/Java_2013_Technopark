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

import java.sql.*;

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

    private Connection connect;

    private static class QueryWrapper {
        private ResultSet rs;
        private String queryString;

        private QueryWrapper(String queryString) {
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
            return rs;
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
        String queryString = "SELECT `idUser` FROM `User` WHERE `Login` = '"+login+"' AND `Password` = md5('"+password+"') LIMIT 1";

        try {
            QueryWrapper qrw = QueryWrapper.query(queryString, this.connect);
            if (qrw.rowCounts() == 1)
            {
                qrw.getResultSet().next();
                return qrw.getResultSet().getLong("idUser"); //возвращает ID пользователя
            }
            // TODO: and otherwise? (!= 1)
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return USER_NOT_EXIST;
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
