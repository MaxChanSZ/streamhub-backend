package externalServices.email_service.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class WatchPartyInvitationRequest {

    private Long partyId;
    private List<String> emailAddresses;
    private String subject;
    private String body;

}


