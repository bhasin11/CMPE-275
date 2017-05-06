package io.reservation.Reservation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.reservation.Flight.Flight;

@RestController
public class ReservationController {

	@Autowired
	private ReservationService reservationService;
	
	@RequestMapping(value="/reservation/{number}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getReservation(@PathVariable int number) {
		
		 return reservationService.getReservation(number);
	}
	
	@RequestMapping(value="/reservation", method=RequestMethod.POST, produces=MediaType.APPLICATION_XML_VALUE)// Chaged it form Applicatiion_JSON
	public ResponseEntity<?> addReservation(
			@RequestParam int passengerId, 
			@RequestParam("flightLists") List<Flight> flightLists
			) {
		
		 return reservationService.addReservation(passengerId, flightLists);
	}
	
	@RequestMapping(value="/reservation/{number}", method=RequestMethod.DELETE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteReservation(@PathVariable int number) {
		
		 return reservationService.deleteReservation(number);
	}

	@RequestMapping(value="/reservation/{number}", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateReservaton(
			@PathVariable int number, 
			@RequestParam(value="flightsAdded", required=false) List<Flight> flightsAdded, 
			@RequestParam(value="flightsRemoved", required=false) List<Flight> flightsRemoved ){
		
		if(flightsRemoved != null){
			ResponseEntity<?> obj = reservationService.updateReservatonRemoveFlights(number, flightsRemoved);
			if(obj != null) return obj;
		}
		if(flightsAdded!=null){
			ResponseEntity<?> response = reservationService.updateReservationAddFlights(number, flightsAdded); 
			if(response.getStatusCode() == HttpStatus.NOT_FOUND){
				System.out.print("inside if of updateReservation() "); 
				
				return response;
			}
		}
		return reservationService.getReservation(number);
	}

	@RequestMapping(value="/reservation", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> findReservation(
			@RequestParam(value="passengerId", required=false, defaultValue = "-1") String passengerId, 
			@RequestParam(value="from", required=false, defaultValue = "defaultFrom") String from, 
			@RequestParam(value="to", required=false, defaultValue = "defaultTo") String to, 
			@RequestParam(value="flightNumber", required=false, defaultValue = "defaultFlightNumber") String flightNumber			
			){
		
		return reservationService.findReservation(passengerId, from, to, flightNumber);
	}
}