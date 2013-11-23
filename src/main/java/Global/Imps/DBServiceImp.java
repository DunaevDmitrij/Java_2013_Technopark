package Global.Imps;

/**
 * Created with IntelliJ IDEA.
 * User: Sidorov Vadim
 * Date: 20.11.13
 * Time: 13:39
 * To change this template use File | Settings | File Templates.
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

    public static final Long USER_NOT_EXIST = -1L;

    private class QueryResultWrapper {
        private ResultSet rs;
        QueryResultWrapper(ResultSet resultSet) {
            rs = resultSet;
        }

        public int rowCounts() throws SQLException {
            rs.last();
            final int rowCounts = rs.getRow();
            rs.beforeFirst();
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
        //регестрируем DBServiceImp в AddressService, что бы каждый мог обратиться к DBService
        ms.getAddressService().setAccountService(this.address);

        connect = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    @Override
    public Long getUserIdByUserName(String login, String password) {
        String queryString = "SELECT `idUser` FROM `User` WHERE `Login` = '"+login+"' AND `Password` = md5('"+password+"') LIMIT 1";

        try {
            QueryResultWrapper qrw = this.query(queryString);

            if (qrw.rowCounts() == 1)
            {
                qrw.getResultSet().next();
                return qrw.getResultSet().getLong("idUser"); //возвращает ID пользователя
            }
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return USER_NOT_EXIST;
    }

    private QueryResultWrapper query(String queryString) throws SQLException {
        Statement statement = connect.createStatement();
        return new QueryResultWrapper(statement.executeQuery(queryString));
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
