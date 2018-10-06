package org.ni.recruitservice.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Created by nazmul on 10/6/2018.
 */
@Entity
public @Data class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true,nullable=false)
    private String title;

    @Column(nullable=false)
    private Date startDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "offer", fetch = FetchType.LAZY)
    private Set<Application> applications;

    @Transient
    private Integer numberOfApplications;

}