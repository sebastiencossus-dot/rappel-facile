// ConnexionRequest.java
package com.msstats.models;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ConnexionRequest {
    private String userEmail;
    private String ipAddress;
}