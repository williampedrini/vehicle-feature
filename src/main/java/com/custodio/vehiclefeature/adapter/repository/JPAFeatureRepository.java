package com.custodio.vehiclefeature.adapter.repository;

import com.custodio.vehiclefeature.domain.entity.Feature;
import com.custodio.vehiclefeature.domain.port.FeatureRepository;
import org.springframework.data.jpa.repository.JpaRepository;

interface JPAFeatureRepository extends JpaRepository<Feature, Long>, FeatureRepository {
}
