package repository;

import io.restassured.RestAssured;
import java.util.Objects;
import javax.sql.DataSource;
import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcConnectionPool;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import route.MoneyTransferRoute;
import spark.Spark;

public abstract class RepoTest
{

    private static final DataSource dataSource = JdbcConnectionPool
        .create("jdbc:h2:mem:transfer-details", "sa", "");

    private static final int port = 8082;
    public static final DSLContext context = DSL.using(dataSource, SQLDialect.H2);
    private static MoneyTransferRoute transferRoute;

    static
    {
        migrate();
        RestAssured.baseURI = "http://localhost:" + port;
        RestAssured.basePath = "/api/revoult";
    }

    @BeforeAll
    static void start()
    {
        transferRoute = new MoneyTransferRoute(dataSource, port);
        transferRoute.startServer();
        Spark.awaitInitialization();
    }


    @AfterAll
    static void stop()
    {
        if (Objects.nonNull(transferRoute))
        {
            transferRoute.stopServer();
        }
    }


    private static void migrate()
    {
        Flyway flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.migrate();
    }
}
