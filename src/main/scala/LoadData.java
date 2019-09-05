import scala.Array;
import scala.Int;
import scala.collection.immutable.List;
import scala.collection.mutable.ArrayBuffer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;

class LoadData{
    static private Connection connection;
    final int amountMoneyColumns = 12;
    private String tableName;
    static private String jdbc_url;
    public LoadData(String tableName) throws Exception{
        this.tableName = tableName;
        Map<String, String> env = System.getenv();
        jdbc_url = env.getOrDefault("JDBC_IBM_DB2","ERR");
        if(jdbc_url.equals("ERR"))
            throw new Exception("no required variable");
    }
    public boolean closeConnection()
    {
        try {
            connection.close();
        }
        catch(Exception ex) {
            return  false;
        }
        return true;
    }
    private static  void initConnection() throws Exception {
        connection = DriverManager.getConnection(jdbc_url);
        Statement stmt=connection.createStatement();
    }
    public void createSpecificTable() {
        try
        {
            // Class.forName("сom.ibm.db2.jcc.DB2Driver");
            if(connection == null)
              initConnection();
            Statement stmt = connection.createStatement();
            StringBuilder sql =  new StringBuilder();
            sql.append("CREATE TABLE " + tableName+
                    "(product_id INTEGER not NULL, " +
                    " product_group INTEGER, " +
                    " year INTEGER not NULL, ");
            for(int i = 1; i < amountMoneyColumns+1; i++)
            {
                sql.append("amount"+i+" INTEGER, ");
            }
            sql.append( " PRIMARY KEY ( product_id,year ))");
            stmt.executeUpdate(sql.toString());
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            System.out.println("Oh my God");
        }
    }
    public void loadData(ArrayBuffer<Product> arrProduct) {
        StringBuilder query = new StringBuilder("INSERT INTO " + tableName+ " ("
                + " product_id,"
                + " product_group,"
                + " year,");
        for(int i=1; i<amountMoneyColumns; i++)
            query.append("amount"+i+",");
        query.append("amount"+amountMoneyColumns+") VALUES (");
        for(int i =1; i<amountMoneyColumns+3; i++)
            query.append("?, ");
        query.append("?)");
        try
        {
            if(connection == null)
               initConnection();
            PreparedStatement st = connection.prepareStatement(query.toString());
            var iter = arrProduct.iterator();
            while(iter.hasNext())
            {
                var product = iter.next();
                var iteratorArr = product.arrPurchases().iterator();
                st.setInt(1,product.id());
                st.setInt(2,product.group());
                st.setInt(3,product.year());
                for(int i=0; i<amountMoneyColumns; i++)
                {
                    var purchase = iteratorArr.next();
                    st.setInt(i+4, ((Integer) purchase));
                }
                st.execute();
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

}

