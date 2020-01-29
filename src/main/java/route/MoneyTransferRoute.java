package route;

import javax.sql.DataSource;
import lombok.AllArgsConstructor;
import service.MoneyTransferService;

import static spark.Spark.after;
import static spark.Spark.before;
import static spark.Spark.get;
import static spark.Spark.path;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.stop;

@AllArgsConstructor
public class MoneyTransferRoute
{

    private MoneyTransferService moneyTransferService;
    private int port;


    public MoneyTransferRoute(DataSource datasource, int port)
    {
        this.port = port;
        moneyTransferService = new MoneyTransferService(datasource);
    }


    public void startServer()
    {
        System.out.println(String.format("Starting spark engine on port - {}", port));
        port(port);
        path("/api/revoult", () -> {
            post("/account", (req, res) -> moneyTransferService.registerAccount(req, res));
            get("/account/:id", (req, res) -> moneyTransferService.getAccountById(req.params(":id"), res));
            post("/account/transfer", (req, res) -> moneyTransferService.saveTransferDetails(req, res));
            get("/account/:id/transfer", (req, res) -> moneyTransferService.getAllById(req.params(":id"), res));
            after((request, response) -> response.type("application/json"));
            before((request, response) -> System.out.println(String.format("{} {}", request.requestMethod(), request.url())));
        });

    }


    public void stopServer()
    {
        stop();
    }
}
