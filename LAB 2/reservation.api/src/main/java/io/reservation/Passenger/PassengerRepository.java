package io.reservation.Passenger;

import org.springframework.data.repository.CrudRepository;

public interface PassengerRepository extends CrudRepository<Passenger, String>{
	public Passenger findByPhone(String phone);

	//public Passenger findById(int id);

	public void deleteById(int id);

	public Passenger findByGenId(int passengerId);

}
