// RdvStatRequest.java
package com.msstats.models;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class RdvStatRequest {
    private String userEmail;
    private Integer rdvId;
    private Integer nbAlertes;
}