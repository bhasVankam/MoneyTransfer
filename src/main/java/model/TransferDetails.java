package model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class TransferDetails
{

    private UUID id;
    private UUID receiver_id;
    private UUID sender_id;
    private Status status;
    private BigDecimal amount;
    private OffsetDateTime createdOn;

}
