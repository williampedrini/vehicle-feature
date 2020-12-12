package com.custodio.vehiclefeature.adapter.repository;

import com.custodio.vehiclefeature.domain.entity.Software;
import com.custodio.vehiclefeature.domain.port.SoftwareRepository;
import org.springframework.data.jpa.repository.JpaRepository;

interface JPASoftwareRepository extends JpaRepository<Software, Long>, SoftwareRepository {
}