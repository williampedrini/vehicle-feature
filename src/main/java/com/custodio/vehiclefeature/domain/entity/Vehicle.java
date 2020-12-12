package com.custodio.vehiclefeature.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toUnmodifiableMap;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.IDENTITY;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
@Entity
@Table(name = "vehicle")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "vin", nullable = false, unique = true)
    private String vin;

    @ManyToMany(cascade = ALL)
    @JoinTable(
            name = "vehicle_hardware",
            joinColumns = @JoinColumn(name = "vehicle_id"),
            inverseJoinColumns = @JoinColumn(name = "hardware_id"))
    private Set<Hardware> hardwares;

    @ManyToMany(cascade = ALL)
    @JoinTable(
            name = "vehicle_software",
            joinColumns = @JoinColumn(name = "vehicle_id"),
            inverseJoinColumns = @JoinColumn(name = "software_id"))
    private Set<Software> softwares;

    /**
     * Create a relation between the current {@link Vehicle} with a certain {@link Hardware}.
     *
     * @param hardware The hardware to be added to the relationship.
     */
    @NotNull
    public Vehicle add(@NotNull final Hardware hardware) {
        requireNonNull(hardware, "It is not possible to add a null hardware.");
        hardwares = ofNullable(hardwares)
                            .orElseGet(HashSet::new);
        hardwares.add(hardware);
        return this;
    }

    /**
     * Create a relation between the current {@link Vehicle} with a certain {@link Software}.
     *
     * @param software The software to be added to the relationship.
     */
    @NotNull
    public Vehicle add(@NotNull final Software software) {
        requireNonNull(software, "It is not possible to add a null software.");
        softwares = ofNullable(softwares)
                            .orElseGet(HashSet::new);
        softwares.add(software);
        return this;
    }

    /**
     * Verifies whether the current {@link Vehicle} has a {@link Feature} or not.
     *
     * @param feature The feature information to be used for the verification.
     * @return <p>{@code true}: The current vehicle has the feature.</p>
     *         <p>{@code false}: The current vehicle does not have the feature.</p>
     */
    public boolean isInstallableFeature(@NotNull final Feature feature) {
        requireNonNull(feature, "The feature is mandatory for the comparison.");
        final var hardwares = this.hardwares
                                      .stream()
                                      .collect(toUnmodifiableMap(Hardware::getCode, hardware -> hardware));
        final var softwares = this.softwares
                                      .stream()
                                      .collect(toUnmodifiableMap(Software::getCode, software -> software));
        return hasNotExcludedAnyExcludedRequirement(feature, hardwares, softwares)
                       && hasAllRequiredRequirements(feature, hardwares, softwares);
    }

    /**
     * @see Vehicle#isInstallableFeature(Feature).
     */
    public boolean isIncompatibleFeature(@NotNull final Feature feature) {
        return !isInstallableFeature(feature);
    }

    /**
     * Verifies whether the current {@link Vehicle} does not have any excluded requirement defined by a {@link Feature}.
     *
     * @param feature The feature information to be used for the verification.
     * @param hardwares A map containing the hardwares, where the key is the hardware code.
     * @param softwares A map containing the softwares, where the key is the software code.
     * @return <p>{@code true}: The current vehicle has not any excluded requirement.</p>
     *         <p>{@code false}: The current vehicle does have a excluded requirement.</p>
     */
    private static boolean hasNotExcludedAnyExcludedRequirement(final Feature feature,
                                                                final Map<String, Hardware> hardwares,
                                                                final Map<String, Software> softwares) {
        final var hasNotAnyExcludedHardware = feature.getExcludedHardwares()
                                                      .stream()
                                                      .map(Hardware::getCode)
                                                      .noneMatch(hardwares::containsKey);
        final var hasNotAnyExcludedSoftware = feature.getExcludedSoftwares()
                                                      .stream()
                                                      .map(Software::getCode)
                                                      .noneMatch(softwares::containsKey);
        return hasNotAnyExcludedHardware && hasNotAnyExcludedSoftware;
    }

    /**
     * Verifies whether the current {@link Vehicle} does have all requirements defined by a {@link Feature}.
     *
     * @param feature The feature information to be used for the verification.
     * @param hardwares A map containing the hardwares, where the key is the hardware code.
     * @param softwares A map containing the softwares, where the key is the software code.
     * @return <p>{@code true}: The current vehicle has all requirements.</p>
     *         <p>{@code false}: The current vehicle does not have all requirement.</p>
     */
    private static boolean hasAllRequiredRequirements(final Feature feature,
                                                      final Map<String, Hardware> hardwares,
                                                      final Map<String, Software> softwares) {
        final var hasAllNecessaryHardware = feature.getIncludedHardwares()
                                                    .stream()
                                                    .map(Hardware::getCode)
                                                    .allMatch(hardwares::containsKey);
        final var hasAllNecessarySoftware = feature.getIncludedSoftwares()
                                                    .stream()
                                                    .map(Software::getCode)
                                                    .allMatch(softwares::containsKey);
        return hasAllNecessaryHardware && hasAllNecessarySoftware;
    }
}
