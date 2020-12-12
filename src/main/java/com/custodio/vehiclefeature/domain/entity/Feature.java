package com.custodio.vehiclefeature.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.IDENTITY;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
@Entity
@Table(name = "feature")
public class Feature {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @ManyToMany(cascade = ALL)
    @JoinTable(
            name = "feature_hardware_included",
            joinColumns = @JoinColumn(name = "feature_id"),
            inverseJoinColumns = @JoinColumn(name = "hardware_id"))
    private Set<Hardware> includedHardwares;

    @ManyToMany(cascade = ALL)
    @JoinTable(
            name = "feature_hardware_excluded",
            joinColumns = @JoinColumn(name = "feature_id"),
            inverseJoinColumns = @JoinColumn(name = "hardware_id"))
    private Set<Hardware> excludedHardwares;

    @ManyToMany(cascade = ALL)
    @JoinTable(
            name = "feature_software_included",
            joinColumns = @JoinColumn(name = "feature_id"),
            inverseJoinColumns = @JoinColumn(name = "software_id"))
    private Set<Software> includedSoftwares;

    @ManyToMany(cascade = ALL)
    @JoinTable(
            name = "feature_software_excluded",
            joinColumns = @JoinColumn(name = "feature_id"),
            inverseJoinColumns = @JoinColumn(name = "software_id"))
    private Set<Software> excludedSoftwares;
}
