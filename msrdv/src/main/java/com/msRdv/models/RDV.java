package com.msRdv.models;




import lombok.*;
import org.apache.catalina.User;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RDV {


    private Integer id;
    private LocalDateTime dateRdv;
    private Integer isOK;
    private String motif;

    private Prestataires prestataires;
    private Professions professions;
    private Adresses adresses;
    private User user;


}