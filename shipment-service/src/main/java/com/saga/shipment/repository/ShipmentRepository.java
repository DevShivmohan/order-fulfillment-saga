package com.saga.shipment.repository;

import com.saga.shipment.entity.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, String> {

    @Query(nativeQuery = true, value = "select * from shipment where order_id=?1 AND status='SUCCESS'")
    Optional<Shipment> fetchShipmentByOrderIdWithSuccess(final String orderId);

    @Query(nativeQuery = true, value = "select * from shipment where order_id=?1 AND status='FAILED'")
    Optional<Shipment> fetchShipmentByOrderIdWithFailed(final String orderId);

    @Query(nativeQuery = true, value = "select * from shipment where order_id=?1")
    Optional<Shipment> fetchShipmentByOrderId(final String orderId);
}
