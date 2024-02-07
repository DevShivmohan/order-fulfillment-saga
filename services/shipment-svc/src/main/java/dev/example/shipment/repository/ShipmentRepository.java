package dev.example.shipment.repository;

import dev.example.shipment.entity.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment,Long> {
    @Query(nativeQuery = true,value = "select * from shipment where order_id=?1")
    List<Shipment> findAllShipmentByOrderId(Long orderId);
}
