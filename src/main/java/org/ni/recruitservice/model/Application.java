package org.ni.recruitservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.ni.recruitservice.enums.ApplicationStatus;

import javax.persistence.*;

/**
 * Created by nazmul on 10/6/2018.
 */
@Entity
@Data
@Table(name="APPLICATION", uniqueConstraints=@UniqueConstraint(columnNames={"candidateEmail", "offer_id"}))
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "application_id")
    private Long id;

    @Column(nullable=false)
    private String candidateEmail;

    @Column(nullable=false)
    private String resume;

    @Column(nullable=false)
    @Enumerated(EnumType.STRING)
    private ApplicationStatus applicationStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "offer_id", nullable=false)
    private Offer offer;
}
