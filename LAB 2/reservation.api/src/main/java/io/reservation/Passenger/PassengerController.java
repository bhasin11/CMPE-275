package io.reservation.Passenger;

import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.reservation.Reservation.ReservationService;

@RestController
public class PassengerController {

	@Autowired
	private PassengerService passengerService;
	
	@Autowired
	private ReservationService reservationService;
	
	@RequestMapping(value="/passenger/{id}", method=RequestMethod.GET, produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<?> getPassenger(@PathVariable String id, 
			@RequestParam(value = "json", required = false) String json, @RequestParam(value = "xml", required=false) String xml){
		// @RequestParam to get a particular parameter value and 
		// store it in type
		System.out.println("inside getPassenger("+ id  +")");
		if(json != null && json.equals("true")){ // ?json=true
			String responseType="json";
			//String a = passengerService.getPassenger(id, responseType);
			return passengerService.getPassenger(id, responseType);
			//return  new ResponseEntity<>(a,HttpStatus.OK);
		}
		else if(xml != null && xml.equals("true")){ // ?xml=true
			String responseType="xml";
			//String a = passengerService.getPassenger(id, responseType); 
			//JSONObject jObject  = new JSONObject(a); // json
			return passengerService.getPassenger(id, responseType); 
			//return  new ResponseEntity<>(XML.toString(jObject),HttpStatus.OK);
		}
		return (new ResponseEntity<>("{\"BadRequest\":{"
							+ "\"code\":\"404\","
						+ "\"msg\":\"Please provide json=true or xml=true\"}}",HttpStatus.NOT_FOUND));
	}
	
	@RequestMapping(value="/passenger", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addPassenger(
			@RequestParam("firstname") String firstname,
			@RequestParam("lastname") String lastname,
			@RequestParam("age") String age,
			@RequestParam("gender") String gender,
			@RequestParam("phone") String phone
			) {
		
		 return passengerService.addPassenger(firstname, 
				lastname, age, gender, phone);
	}
	
	@RequestMapping(value="/passenger/{id}", method=RequestMethod.PUT, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updatePassenger(
			@PathVariable String id, 
			@RequestParam("firstname") String firstname,
			@RequestParam("lastname") String lastname,
			@RequestParam("age") String age,
			@RequestParam("gender") String gender,
			@RequestParam("phone") String phone
			) {
		
		 return passengerService.updatePassenger(id, firstname, 
				lastname, age, gender, phone);
	}
	
	@RequestMapping(value="/passengers", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public String getAllPassengers(){
		
		passengerService.getAllPassengers();
		
		return "";
	}
	
	@RequestMapping(value="/passenger/{id}", method=RequestMethod.DELETE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deletePassenger(@PathVariable String id) {
		
		
		return passengerService.deletePassenger(id);
	}
}
