import scala.Array;
import scala.Int;
import scala.collection.immutable.List;
import scala.collection.mutable.ArrayBuffer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;

class ConnectDB {
    Connection connection;
    final int amountMoneyColumns = 12;

    public ConnectDB() throws Exception {
        connection = DriverManager.getConnection("jdbc:db2://dashdb-txn-sbox-yp-lon02-01.services.eu-gb.bluemix.net:50001/BLUDB:user=nwn80970;password=mg^hv694qsv36km4;sslConnection=true;");
        Statement stmt=connection.createStatement();
    }

    public void connect()
    {
        try {
            // Class.forName("сom.ibm.db2.jcc.DB2Driver");
            connection = DriverManager.getConnection("jdbc:db2://dashdb-txn-sbox-yp-lon02-01.services.eu-gb.bluemix.net:50001/BLUDB:user=nwn80970;password=mg^hv694qsv36km4;sslConnection=true;");
            Statement stmt=connection.createStatement();
            StringBuilder sql =  new StringBuilder();
            sql.append("CREATE TABLE Products2" +
                    "(product_id INTEGER not NULL, " +
                    " product_group INTEGER, " +
                    " year INTEGER not NULL, ");
            for(int i =1; i<amountMoneyColumns+1; i++)
            {
                sql.append("amount"+i+" INTEGER, ");
            }
            sql.append( " PRIMARY KEY ( product_id,year ))");
            stmt.executeUpdate(sql.toString());
            int t= 4;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            System.out.println("Oh my God");
        }
    }
    public void loadData(ArrayBuffer<Product> arrProduct)
    {
        List<Product> productList = arrProduct.toList();
        StringBuilder query = new StringBuilder("INSERT INTO Products2 ("
                + " product_id,"
                + " product_group,"
                + " year,");
        for(int i=1; i<amountMoneyColumns; i++)
            query.append("amount"+i+",");
        query.append("amount"+amountMoneyColumns+") VALUES (");
        for(int i =1; i<amountMoneyColumns; i++)
            query.append("?, ");
        query.append("?)");
        try {
            PreparedStatement st = connection.prepareStatement(query.toString());
            var iter = arrProduct.iterator();
              while(iter.hasNext())
              {
                  var product = iter.next();
                  var iteratorArr = product.arrPurchases().iterator();
                  st.setInt(1,product.id().id());
                  st.setInt(2,product.group());
                  st.setInt(3,product.id().year());
                  //1) композиция указать тип ?
                  //2) как правильно совмещать java и scala код
                  for(int i=0; i<amountMoneyColumns; i++)
                  {
                      var purchase = iteratorArr.next();
                      st.setInt(i+4, ((Integer) purchase));
                  }
              }
//            for(product <-arrProduct)
//            st.setString(6, user.getPrivacy());
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

}
