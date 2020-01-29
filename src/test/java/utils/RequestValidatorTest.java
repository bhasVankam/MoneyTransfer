package utils;

import dto.AccountRequest;
import dto.TransferDetailsRequest;
import exception.NotValidRequestException;
import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RequestValidatorTest
{

    private RequestValidator validator;


    @BeforeEach
    void setup()
    {
        validator = new RequestValidator();
    }


    @Test
    void validate_account_request_balance_null()
    {
        AccountRequest accountRequest = new AccountRequest(null, "hi");
        Throwable exception = assertThrows(
            NotValidRequestException.class,
            () -> validator.validateRequest(accountRequest, AccountRequest.RULES)
        );

        assertEquals("Balance cannot be blank", exception.getMessage());
    }


    @Test
    void validate_account_request_balance_negative()
    {
        AccountRequest accountRequest = new AccountRequest(BigDecimal.valueOf(-10), "hi");
        Throwable exception = assertThrows(
            NotValidRequestException.class,
            () -> validator.validateRequest(accountRequest, AccountRequest.RULES)
        );

        assertEquals("Balance cannot be less than 0 ", exception.getMessage());
    }


    @Test
    void validate_account_request_name_blank()
    {
        AccountRequest accountRequest = new AccountRequest(BigDecimal.valueOf(10), null);
        Throwable exception = assertThrows(
            NotValidRequestException.class,
            () -> validator.validateRequest(accountRequest, AccountRequest.RULES)
        );

        assertEquals("Name cannot be blank", exception.getMessage());
    }


    @Test
    void validate_transfer_details_when_receiver_id_blank()
    {
        TransferDetailsRequest request = new TransferDetailsRequest(null, UUID.randomUUID(), BigDecimal.TEN);
        Throwable exception = assertThrows(
            NotValidRequestException.class,
            () -> validator.validateRequest(request, TransferDetailsRequest.RULES)
        );

        assertEquals("Receiver Account Id should be present", exception.getMessage());
    }


    @Test
    void validate_transfer_details_when_sender_id_blank()
    {
        TransferDetailsRequest request = new TransferDetailsRequest(UUID.randomUUID(), null, BigDecimal.TEN);
        Throwable exception = assertThrows(
            NotValidRequestException.class,
            () -> validator.validateRequest(request, TransferDetailsRequest.RULES)
        );

        assertEquals("Sender Account Id should be present", exception.getMessage());
    }


    @Test
    void validate_transfer_details_when_both_accounts_same()
    {
        UUID id = UUID.randomUUID();
        TransferDetailsRequest request = new TransferDetailsRequest(id, id, BigDecimal.TEN);
        Throwable exception = assertThrows(
            NotValidRequestException.class,
            () -> validator.validateRequest(request, TransferDetailsRequest.RULES)
        );

        assertEquals("Sender and receiver cannot be same", exception.getMessage());
    }


    @Test
    void validate_transfer_details_when_transfer_amount_is_null()
    {
        TransferDetailsRequest request = new TransferDetailsRequest(UUID.randomUUID(), UUID.randomUUID(), null);
        Throwable exception = assertThrows(
            NotValidRequestException.class,
            () -> validator.validateRequest(request, TransferDetailsRequest.RULES)
        );

        assertEquals("Amount cannot be null", exception.getMessage());
    }


    @Test
    void validate_transfer_details_when_transfer_amount_is_zero()
    {
        TransferDetailsRequest request = new TransferDetailsRequest(UUID.randomUUID(), UUID.randomUUID(), BigDecimal.ZERO);
        Throwable exception = assertThrows(
            NotValidRequestException.class,
            () -> validator.validateRequest(request, TransferDetailsRequest.RULES)
        );

        assertEquals("Amount cannot be zero", exception.getMessage());
    }


    @Test
    void validate_transfer_details_when_transfer_amount_is_negative()
    {
        TransferDetailsRequest request = new TransferDetailsRequest(UUID.randomUUID(), UUID.randomUUID(), BigDecimal.valueOf(-10));
        Throwable exception = assertThrows(
            NotValidRequestException.class,
            () -> validator.validateRequest(request, TransferDetailsRequest.RULES)
        );

        assertEquals("Amount cannot be negative", exception.getMessage());
    }
}

