// RdvStatEvent.java
package com.msstats.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "rdv_stats")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class RdvStatEvent {
    @Id
    private String id;
    private String userEmail;
    private Integer rdvId;
    private Integer nbAlertes;
    private LocalDateTime timestamp;
}