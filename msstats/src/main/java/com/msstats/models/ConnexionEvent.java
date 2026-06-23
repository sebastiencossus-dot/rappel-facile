// ConnexionEvent.java
package com.msstats.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "connexions")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ConnexionEvent {
    @Id
    private String id;
    private String userEmail;
    private LocalDateTime timestamp;
    private String ipAddress;
}