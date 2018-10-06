package org.ni.recruitservice.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.ni.recruitservice.utils.JsonDateDeserializer;
import org.ni.recruitservice.utils.JsonDateSerializer;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by nazmul on 10/6/2018.
 */
@Entity
@Data
@Table(name="OFFER")
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "offer_id")
    private Long id;

    @Column(unique = true,nullable=false)
    private String title;

    @Column(nullable=false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using=JsonDateSerializer.class)
    @JsonDeserialize(using = JsonDateDeserializer.class)
    private Date startDate;

    /*@OneToMany(cascade = CascadeType.ALL, mappedBy = "offer", fetch = FetchType.LAZY)
    private Set<Application> applications = new HashSet<>();*/

    @Transient
    private Integer numberOfApplications = 0;

    /*@PostLoad
    public void setNumberOfApplications() {
        this.numberOfApplications = applications.size();
    }*/


}