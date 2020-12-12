package com.custodio.vehiclefeature.adapter.repository;

import com.custodio.vehiclefeature.domain.entity.Vehicle;
import com.custodio.vehiclefeature.domain.port.VehicleRepository;
import org.springframework.data.jpa.repository.JpaRepository;

interface JPAVehicleRepository extends JpaRepository<Vehicle, Long>, VehicleRepository {
}