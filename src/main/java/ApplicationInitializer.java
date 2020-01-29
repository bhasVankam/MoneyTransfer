
import javax.sql.DataSource;
import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcConnectionPool;
import route.MoneyTransferRoute;

/* Application Initializer */
public class ApplicationInitializer
{

    private static final String PORT = "8081";


    public static void main(String args[])
    {

        final DataSource datasource = JdbcConnectionPool.create("jdbc:h2:~/transfer-details", "sa", "");
        flyWayMigration(datasource);

        int port = Integer.parseInt(System.getProperty("server.port", PORT));
        MoneyTransferRoute moneyTransferRoute = new MoneyTransferRoute(datasource, port);
        moneyTransferRoute.startServer();

    }


    private static void flyWayMigration(DataSource datasource)
    {
        Flyway flyway = Flyway.configure().dataSource(datasource).load();
        flyway.migrate();
    }
}
