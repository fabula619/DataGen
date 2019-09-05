# DataGen
object Main respond for generating data and delegating work with DB2 instance to LoadData class
class Product fully contains definition of the product
java-class LoadData  is responsible for executing SQL-queries

For a right configuration you shoud tune are two environment variables
1)JDBC_IBM_DB2 -jdbc url with your user_name,password,and value of existing SSL
2)TABLE_NAME - name of new table
