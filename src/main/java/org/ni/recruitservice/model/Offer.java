package org.ni.recruitservice.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by nazmul on 10/6/2018.
 */
@Entity
public @Data class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String title;

    private Date startDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "offer")
    private List<Application> applications;

    @Transient
    private Integer numberOfApplications;

    @PostLoad
    public void setNumberOfApplications() {
        this.numberOfApplications = applications.size();
    }
}