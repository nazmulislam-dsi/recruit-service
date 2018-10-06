package org.ni.recruitservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.ni.recruitservice.enums.ApplicationStatus;

import javax.persistence.*;

/**
 * Created by nazmul on 10/6/2018.
 */
@Entity
@Table(name="APPLICATION", uniqueConstraints=@UniqueConstraint(columnNames={"candidateEmail", "offer_id"}))
public @Data class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable=false)
    private String candidateEmail;

    @Column(nullable=false)
    private String resume;

    @Column(nullable=false)
    @Enumerated(EnumType.STRING)
    private ApplicationStatus applicationStatus;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "offer_id", nullable=false)
    private Offer offer;
}
