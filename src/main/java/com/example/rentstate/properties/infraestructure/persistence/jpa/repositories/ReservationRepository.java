package com.example.rentstate.properties.infraestructure.persistence.jpa.repositories;

import com.example.rentstate.properties.domain.model.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    List<Reservation> findAllByPropertyId(Long propertyId);
}
