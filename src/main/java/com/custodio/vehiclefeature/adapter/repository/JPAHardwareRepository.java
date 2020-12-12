package com.custodio.vehiclefeature.adapter.repository;

import com.custodio.vehiclefeature.domain.entity.Hardware;
import com.custodio.vehiclefeature.domain.port.HardwareRepository;
import org.springframework.data.jpa.repository.JpaRepository;

interface JPAHardwareRepository extends JpaRepository<Hardware, Long>, HardwareRepository {
}