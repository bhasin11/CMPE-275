package io.reservation.Reservation;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import io.reservation.Passenger.Passenger;

public interface ReservationRepository extends CrudRepository<Reservation, Integer> {

	Reservation findByGenOrderNumber(int number);

	List<Reservation> findByPassenger(Passenger passenger);

	
}
