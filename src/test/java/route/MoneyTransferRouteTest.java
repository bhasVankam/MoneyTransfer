package route;

import dto.AccountRequest;
import dto.TransferDetailsRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import model.Account;
import model.TransferDetails;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.jupiter.api.Test;
import repository.AccountRepository;
import repository.MoneyTransferRepository;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

class MoneyTransferRouteTest extends IntegrationTest
{

    private AccountRepository accountRepository = new AccountRepository(context);
    private MoneyTransferRepository moneyTransferRepository = new MoneyTransferRepository(context);


    @Test
    void test_register_account()
    {
        BigDecimal balance = BigDecimal.valueOf(1353);
        String name = "abc";
        String id = registerAccount(balance, name);

        UUID accountId = UUID.fromString(id);
        Account account = accountRepository.findById(accountId);
        assertThat(account.getId()).isEqualTo(accountId);
        assertThat(account.getBalance()).isEqualTo(balance);
        assertThat(account.getName()).isEqualToIgnoringCase(name);
    }


    @Test
    void test_get_account_using_id()
    {
        BigDecimal balance = BigDecimal.valueOf(12);
        String name = "KA";
        String id = registerAccount(balance, name);

        UUID accountId = UUID.fromString(id);
        Account account = accountRepository.findById(accountId);
        assertThat(account.getId()).isEqualTo(accountId);

    }


    @Test
    void test_invalid_account()
    {
        given()
            .pathParam("id", UUID.randomUUID())
            .when()
            .get("/account/{id}").prettyPeek()
            .then()
            .statusCode(HttpStatus.NOT_FOUND_404);
    }


    @Test
    void test_transfer_between_accounts()
    {
        UUID sourceId = UUID.fromString(registerAccount(BigDecimal.valueOf(400), "Hi"));
        UUID destinationId = UUID.fromString(registerAccount(BigDecimal.valueOf(0), "Hello"));
        BigDecimal amount = BigDecimal.valueOf(10);
        TransferDetailsRequest detailsRequest = new TransferDetailsRequest(destinationId, sourceId, amount);

        given()
            .body(detailsRequest)
            .when()
            .post("/account/transfer").prettyPeek()
            .then()
            .statusCode(HttpStatus.OK_200);

        Account sender = accountRepository.findById(sourceId);
        assertThat(sender.getBalance()).isEqualTo(BigDecimal.valueOf(390));

        Account receiver = accountRepository.findById(destinationId);
        assertThat(receiver.getBalance()).isEqualTo(BigDecimal.valueOf(10));

    }


    @Test
    void test_transfer_to_same_account()
    {
        UUID id = UUID.randomUUID();
        TransferDetailsRequest detailsRequest = new TransferDetailsRequest(id, id, BigDecimal.TEN);

        given()
            .body(detailsRequest)
            .when()
            .post("/account/transfer").prettyPeek()
            .then()
            .statusCode(HttpStatus.BAD_REQUEST_400);
    }


    @Test
    void test_transfer_between_accounts_declined_status()
    {
        UUID sourceId = UUID.fromString(registerAccount(BigDecimal.valueOf(40), "Hi"));
        UUID destinationId = UUID.fromString(registerAccount(BigDecimal.valueOf(0), "Hello"));
        BigDecimal amount = BigDecimal.valueOf(50);
        TransferDetailsRequest detailsRequest = new TransferDetailsRequest(destinationId, sourceId, amount);

        given()
            .body(detailsRequest)
            .when()
            .post("/account/transfer").prettyPeek()
            .then()
            .statusCode(HttpStatus.BAD_REQUEST_400);

        Account sender = accountRepository.findById(sourceId);
        assertThat(sender.getBalance()).isEqualTo(BigDecimal.valueOf(40));
        Account receiver = accountRepository.findById(destinationId);
        assertThat(receiver.getBalance()).isEqualTo(BigDecimal.valueOf(0));

    }


    @Test
    void test_get_all_transfer_details_for_id()
    {
        BigDecimal balance = BigDecimal.valueOf(12);
        String name = "AP";
        String id = registerAccount(balance, name);

        given()
            .pathParam("id", id)
            .when()
            .get("/account/{id}/transfer").prettyPeek()
            .then()
            .statusCode(HttpStatus.OK_200);

        List<TransferDetails> transferDetails = moneyTransferRepository.findAllTransfersBySource(UUID.fromString(id));

        assertThat(transferDetails.size()).isEqualTo(0);

    }


    private String registerAccount(BigDecimal balance, String name)
    {
        AccountRequest request = new AccountRequest(balance, name);

        return given()
            .body(request)
            .when()
            .post("/account").prettyPeek()
            .then()
            .statusCode(HttpStatus.CREATED_201)
            .body("id", notNullValue())
            .body("balance", notNullValue())
            .body("name", notNullValue())
            .extract()
            .jsonPath()
            .get("id");

    }

}

