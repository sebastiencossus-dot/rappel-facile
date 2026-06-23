// VisiteEvent.java
package com.msstats.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "visites")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class VisiteEvent {
    @Id
    private String id;
    private String userEmail;
    private String page;
    private LocalDateTime timestamp;
}