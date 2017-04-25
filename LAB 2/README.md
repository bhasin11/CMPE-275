

Lab 2 - REST API, Persistence, and Transactions

In this lab, I built a REST API to implement a simple airline reservation system through create, get, update, and delete. This system was hosted on Amazon EC2. I used Spring’s @RestController for the implementation and use JPA for the persistence.

<h3>Constraints:</h3>


~~~
* Each passenger can make one or more reservations. Time overlap is not allowed among any of his reservation.
* Each reservation may consist of one or more flights.
* Each flight can carry one or more passengers.
* Each flight uses one plane, which is an embedded object with four fields mapped to the corresponding four columns in the airline table.
* The total amount of passengers can not exceed the capacity of an plane.
* When a passenger is deleted, all reservation made by him are automatically canceled for him.
* A flight can not be deleted if it needs to carry at least one passenger.
~~~

<h3>Incomplete definitions of passenger, reservation, flight and plane are given below.</h3>

```java
package edu.sjsu.cmpe275.lab2;<br>

public class Passenger {
    private String id;
    private String firstname;
    private String lastname;
    private int age;
    private String gender;
    private String phone; // Phone numbers must be unique<br>
    ...
}

public class Reservation {
    private String orderNumber;
    private Passenger passenger;
    private int price; // sum of each flight’s price.
    private List<Flight> flights;
    ...
}

public class Flight {
    private String number; // Each flight has a unique flight number.
    private int price;
    private String from;
    private String to;
    private Date departureTime;
    private Date arrivalTime;
    private int seatsLeft;
    private String description;
    private Plane plane;  // Embedded
    private List<Passenger> passengers;
    ...
}

public class Plane {
    private int capacity;
    private String model;
    private String manufacturer;
    private int yearOfManufacture;
}
```
<h3>Requirements</h3>
<h4>(1) Get a passenger back as JSON<br></h4>

URL: https://hostname/passenger/id?json=true <br>
Method: GET<br>
Return:<br>
If the passenger can be found with the given ID, return the passenger's record in JSON format:
```
{
	"passenger": {
		"id": " 123 ",
		"firstname": " John ",
		"lastname": " Oliver ",
		"age": " 21 ",
		"gender": " male ",
		"phone": " 4445556666 ",
		"reservations": {
			"reservation": [
				{
					"orderNumber": "123",
					"price": "240",
					"flights": {
						"flight": [
							{
								"number": " GH2Z1 ",
								"price": "120",
								"from": "Seattle, WA",
								"to": "San Jose, CA",
								"departureTime": "2017-04-12-09 ",
								"arrivalTime": "2017-04-12-14",
								"description": "xxxx",
								"plane": {
									"capacity": "120",
									"model": "Boeing 757",
									"manufacturer": "Boeing",
									"yearOfManufacture": "1998"
								}
							},
							{
								"number": " HZ124 ",
								"price": "120",
								"from": "San Jose, CA",
								"to": "Seattle, WA",
								"departureTime": "2017-04-14-09 ",
								"arrivalTime": "2017-04-14-14",
								"description": "xxxx",
								"plane": {
									"capacity": "120",
									"model": "Boeing 757",
									"manufacturer": "Boeing",
									"yearOfManufacture": "1998"
								}
							}
						]
					}
				},
				{
					"orderNumber": "345",
					"price": "100",
					"flights": {
						"flight": {
							"number": " KJ124 ",
							"price": "100",
							"from": "San Jose, CA",
							"to": "Washton, DC",
							"departureTime": "2017-04-15-09 ",
							"arrivalTime": "2017-04-15-15",
							"description": "xxxx",
							"plane": {
								"capacity": "100",
								"model": "Boeing 757",
								"manufacturer": "Boeing Airplanes",
								"yearOfManufacture": "1999"
							}
						}
					}
				}
			]
		}
	}
}
```

Otherwise, return:
```
{
	"BadRequest": {
		"code": " 404 ",
		"msg": " Sorry, the requested passenger with id XXX does not exist"
	}
}
```
Note: <br>
XXX is the ID specified in the request, and you must return HTTP error code 404 as well.<br>
This JSON is meant for read-only and is not an HTML page or form. <br>
All error message will use the consistent JSON format. <br><br>

<h4>(2) Get a passenger back as XML</h4>

URL: https://hostname/passenger/id?xml=true  <br>
Method: GET<br>
Return: <br>
If the passenger can be found with the given ID, return the passenger's record in XML format:<br>
```
<passenger>
	<id> 123 </id>
	<firstname> John </firstname>
	<lastname> Oliver </lastname>
	<age> 21 </age>
	<gender> male </gender>
	<phone> 4445556666 </phone>
	<reservations>
		<reservation>
			<orderNumber>123</orderNumber>
			<price>240</price>
			<flights>
				<flight>
					<number> GH2Z1 </number>
					<price>120</price>
					<from>Seattle, WA</from>
					<to>San Jose, CA</to>
					<departureTime>
                                                               2017-04-12-09 
                                                           </departureTime>
					<arrivalTime>2017-04-12-14</arrivalTime>
					<description>xxxx</description>
					<plane>
						<capacity>120</capacity>
						<model>Boeing 757</model>
						<manufacturer>Boeing</manufacturer>
						<yearOfManufacture>
                                                                         1998
                                                                       </yearOfManufacture>
					</plane>
				</flight>
				<flight>
					<number> HZ124 </number>
					<price>120</price>
					<from>San Jose, CA</from>
					<to>Seattle, WA</to>
					<departureTime>
                                                                2017-04-14-09 
                                                           </departureTime>
					<arrivalTime>2017-04-14-14</arrivalTime>
					<description>xxxx</description>
					<plane>
						<capacity>120</capacity>
						<model>Boeing 757</model>
						<manufacturer>Boeing</manufacturer>
						<yearOfManufacture>
                                                                             1998
                                                                       </yearOfManufacture>
					</plane>
				</flight>
			</flights>
		</reservation>
		<reservation>
			<orderNumber>345</orderNumber>
			<price>100</price>
			<flights>
				<flight>
					<number> KJ124 </number>
					<price>100</price>
					<from>San Jose, CA</from>
					<to>Washton, DC</to>
					<departureTime>
                                                                 2017-04-15-09 
                                                            </departureTime>
					<arrivalTime>2017-04-15-15</arrivalTime>
					<description>xxxx</description>
					<plane>
						<capacity>100</capacity>
						<model>Boeing 757</model>
						<manufacturer>Boeing</manufacturer>
						<yearOfManufacture>
                                                                            1999
                                                                       </yearOfManufacture>
					</plane>
				</flight>
			</flights>
		</reservation>
	</reservations>
</passenger>
```
Otherwise return:<br>
```
{
	"BadRequest": {
		"code": " 404 ",
		"msg": " Sorry, the requested passenger with id XXX does not exist"
	}
}
```
Note: <br>
XXX is the ID specified in the request, and you must return HTTP error code 404 as well. <br>
This XML is meant for readonly and is not an HTML page or form. <br>

<h4>(3) Create a passenger</h4>

URL: https://hostname/passenger?firstname=XX&lastname=YY&age=11&gender=famale&phone=123<br>
Method: POST<br>
Return:<br>
If the passenger is created successfully, the request returns the newly created/updated passenger in Json, the same as GET https://hostname/passenger/id?json=true.

Otherwise, return proper HTTP error code and an error message of the following format<br>
```
{
       "BadRequest": {
              "code": "400",
               "msg": "xxx”
       }
}
```
Note: <br>
xxx here is the failure reason; e.g., “another passenger with the same number already exists.” <br>
This request creates a passenger’s record in the system.<br>
The uniqueness of phone numbers must be enforced here.<br>

<h4>(4) Update a passenger</h4>

URL: https://hostname/passenger/id?firstname=XX&lastname=YY&age=11&gender=famale&phone=123<br>
Method: PUT<br>
Return:<br>
If the passenger is updated successfully, the request returns the newly updated passenger in Json, the same as GET https://hostname/passenger/id?json=true .

```
Otherwise, return
{
       "BadRequest": {
              "code": "404 ",
              "msg": "xxx"
       }
}
```
Description:<br>
This request updates a passenger’s record in the system.<br>

<h4>(5) Delete a passenger<br></h4>

URL: https://hostname/passenger/id<br>
Method: DELETE<br>
Return:<br>
If the passenger does not exist, return:
```
{
       "BadRequest": {
              "code": "404 ",
              "msg": "Passenger with id XXX does not exist"
       }
}
```
You must return HTTP error code 404 as well.<br>

Otherwise, return:<br>
```
<Response>
           <code> 200 </code>
           <msg> Passenger with id XXX is deleted successfully  </msg>
</Response>
```
Note: <br>
xxx here is the given ID in the request.<br>
Description:<br>
This request deletes the user with the given user ID.<br>
The reservation made by the passenger should also be deleted.<br>
You must update the number of available seats for the involved flights.<br>
All successful response will use the same XML format.<br>

<h4>(6) Get a reservation back as JSON</h4>
	
URL: https://hostname/reservation/number<br>
Method: GET<br>
Return:<br>
If the reservation can not be found with the given number, return:
```
{
	"BadRequest": {
		"code": " 404 ",
		"msg": " Reserveration with number XXX does not exist "
	}
}
```
You must return HTTP error code 404 as well.

Otherwise, return:
```
{
	"reservation": {
		"orderNumber": "123",
		"price": "240",
		"passenger": {
			"id": " 123 ",
			"firstname": " John ",
			"lastname": " Oliver ",
			"age": " 21 ",
			"gender": " male ",
			"phone": " 4445556666 "
		},
		"flights": {
			"flight": [
				{
					"number": " GH2Z1 ",
					"price": "120",
					"from": "Seattle, WA",
					"to": "San Jose, CA",
					"departureTime": "2017-04-12-09 ",
					"arrivalTime": "2017-04-12-14",
					"seatsLeft": "15",
					"description": "xxxx",
					"plane": {
						"capacity": "120",
						"model": "Boeing 757",
						"manufacturer": "Boeing Airplanes",
						"yearOfManufacture": "1998"
					}
				},
				{
					"number": " HZ124 ",
					"price": "120",
					"from": "San Jose, CA",
					"to": "Seattle, WA",
					"departureTime": "2017-04-14-09 ",
					"arrivalTime": "2017-04-14-14",
					"seatsLeft": "15",
					"description": "xxxx",
					"plane": {
						"capacity": "120",
						"model": "Boeing 757",
						"manufacturer": "Boeing Airplanes",
						"yearOfManufacture": "1998"
					}
				}
			]
		}
	}
}
```

Description:<br>
This JSON is meant for readonly, and is not an HTML page or form. 

<h4>(7) Make a reservation</h4>

URL: https://hostname/reservation/number?passengerId=XX&flightLists=AA,BB,CC<br>
Method: POST<br>
Return:<br>
If the reservation is created successfully, the request returns the newly created reservation’s record in XML, like:
```
<reservation>
	<orderNumber>123</orderNumber>
	<price>240</price>
	<passenger>
		<id> 123 </id>
		<firstname> John </firstname>
		<lastname> Oliver </lastname>
		<age> 21 </age>
		<gender> male </gender>
		<phone> 4445556666 </phone>
	</passenger>
	<flights>
		<flight>
			<number> GH2Z1 </number>
			<price>120</price>
			<from>Seattle, WA</from>
			<to>San Jose, CA</to>
			<departureTime>2017-04-12-09 </departureTime>
			<arrivalTime>2017-04-12-14</arrivalTime>
			<seatsLeft>15</seatsLeft>
			<description>xxxx</description>
			<plane>
				<capacity>120</capacity>
				<model>Boeing 757</model>
				<manufacturer>
                                                   Boeing Airplanes
				</manufacturer>
				<yearOfManufacture>
				      1998
				</yearOfManufacture>
			</plane>
		</flight>
		<flight>
			<number> HZ124 </number>
			<price>120</price>
			<from>San Jose, CA</from>
			<to>Seattle, WA</to>
			<departureTime>2017-04-14-09 </departureTime>
			<arrivalTime>2017-04-14-14</arrivalTime>
			<seatsLeft>15</seatsLeft>
			<description>xxxx</description>
			<plane>
				<capacity>120</capacity>
				<model>Boeing 757</model>
				<manufacturer>Boeing 							Airplanes</manufacturer>
				<yearOfManufacture>
				      1998
				</yearOfManufacture>
			</plane>
		</flight>
	</flights>
</reservation>
```
Otherwise, return:
```
{
	   "BadRequest": {
		  "code": "404 ",
		   "msg": "xxx"
	   }
}
```
Note: <br>
xxx here is the failure reason, and you must return HTTP error code 404 as well.<br>
Description<br>
This request makes a reservation for a passenger. <br>
Time-Overlap is not allowed for a certain passenger.<br>
The total amount of passengers can not exceed the capacity of the reserved plane.<br>
You would receive a list of flights as input.<br>

<h4>(8) Update a reservation</h4>

URL: https://hostname/reservation/number?flightsAdded=AA,BB,CC&flightsRemoved=XX,YY<br>
Method: POST<br>
Return:<br>
If the reservation is updated successfully, the request returns the newly updated reservation in Json, the same as GET https://hostname/reservation/number.
<br>
Otherwise, return:
```
{
	   "BadRequest": {
		  "code": "404 ",
		   "msg": "xxx"
	   }
}
```
Note: <br>
xxx here is the failure reason, and you must return HTTP error code 404 as well.<br>
This request update a reservation by adding and/or removing some flights. <br>
If flightsAdded (or flightsRemoved) param exists, then its list of values cannot be empty.<br>
Flights to be added or removed can be null.<br>
If both additions and removals exist, the non-overlapping conatraint should not consider the flights to be removed.<br>

<h4>(9) Search for reservations</h4>
URL: https://hostname/reservation?passengerId=XX&from=YY&to=ZZ&flightNumber=123<br>
Method: GET<br>
Return:<br>
Return the search result in XML format:
```
<reservations>
	<reservation>
		<orderNumber>123</orderNumber>
		<price>240</price>
		<passenger>
			<id> 123 </id>
			<firstname> John </firstname>
			<lastname> Oliver </lastname>
			<age> 21 </age>
			<gender> male </gender>
			<phone> 4445556666 </phone>
		</passenger>
		<flights>
			<flight>
				<number> GH2Z1 </number>
				<price>120</price>
				<from>Seattle, WA</from>
				<to>San Jose, CA</to>
				<departureTime>
                                                      2017-04-12-09 
                                               </departureTime>
				<arrivalTime>2017-04-12-14</arrivalTime>
				<description>xxxx</description>
				<plane>
					<capacity>120</capacity>
					<model>Boeing 757</model>
					<manufacturer>
                                                               Boeing
                                                           </manufacturer>
					<yearOfManufacture>
                                                                1998
                                                           </yearOfManufacture>
				</plane>
			</flight>
		</flights>
	</reservation>
</reservations>
```

Description:<br>
This request allow to search for reservations by any combination of single passenger ID, departing city, arrival city, and flight number.<br>
You can assume that at least one request parameter is specified.<br>
 
<h4>(10) Cancel a reservation<br></h4>

URL: https://hostname/reservation/number<br>
Method: DELETE<br>
Return:<br>
If the reservation does not exist, return:
```
{
	"BadRequest": {
		"code": " 404 ",
		"msg": " Reservation with number XXX does not exist "
	}
}
```
You must return HTTP error code 404 as well.
<br>
Otherwise, return:
```
<Response>
           <code> 200 </code>
           <msg> Reservation with number XXX is canceled successfully  </msg>
</Response>
```
Note:<br>
xxx here is the given number in the request.<br> 
This request cancel a reservation for a passenger.<br>
You need to update the number of available seats for the involved flight.<br>

<h4>(11) Get a flight back as JSON</h4>

URL: https://hostname/flight/flightNumber?json=true<br>
Method: GET<br>
Return:<br>
The flight record with given flight number in JSON format. 
```
{
	"flight": {
		"flightNumber": " HX837 ",
		"price": "120",
		"from": " San Jose, CA ",
		"to": " Seattle, WA ",
		"departureTime": " 2017-03-12-09 ",
		"arrivalTime": " 2017-03-12-14 ",
		"description": " xxx ",
		"seatsLeft": " 11 ",
		"plane": {
			"capacity": " 150 ",
			"model": " Boeing 747 ",
			"manufacturer": "Boeing Commercial Airplanes ",
			"yearOfManufacture": "1997"
		},
		"passengers": {
			"passenger": [
				{
					"id": "123",
					"firstname": " John ",
					"lastname": " Oliver ",
					"age": " 21 ",
					"gender": " male ",
					"phone": " 4445556666 "
				},
				{
					"id": "234",
					"firstname": " Ali ",
					"lastname": " Swan ",
					"age": " 30 ",
					"gender": " female ",
					"phone": " 444555777 "
				}
			]
		}
	}
}
```
If the flight can not be found with the given number, return:
```
{
	"BadRequest": {
		"code": " 404 ",
		"msg": " Sorry, the requested flight with number XXX does not exist"
	}
}
```
You must return HTTP error code 404 as well.<br>
This JSON is meant for read-only, and is not an HTML page or form. <br>

<h4>(12) Get a flight back as XML<br></h4>
URL: https://hostname/flight/flightNumber?xml=true<br>
Method: GET<br>
Return:<br>
The flight record with given flight number in XML format. 
```
<flight>
	<flightNumber> HX837 </flightNumber>
	<price>120</price>
	<from> San Jose, CA </from>
	<to> Seattle, WA </to>
	<departureTime> 2017-03-12-09 </departureTime>
	<arrivalTime> 2017-03-12-14 </arrivalTime>
	<description> xxx </description>
	<seatsLeft> 11 </seatsLeft>
	<plane>
		<capacity> 150 </capacity>
		<model> Boeing 747 </model>
		<manufacturer>Boeing Commercial Airplanes </manufacturer>
		<yearOfManufacture>1997</yearOfManufacture>
	</plane>
	<passengers>
		<passenger>
			<id>123</id>
			<firstname> John </firstname>
			<lastname> Oliver </lastname>
			<age> 21 </age>
			<gender> male </gender>
			<phone> 4445556666 </phone>
		</passenger>
		<passenger>
			<id>234</id>
			<firstname> Ali </firstname>
			<lastname> Swan </lastname>
			<age> 30 </age>
			<gender> female </gender>
			<phone> 444555777 </phone>
		</passenger>
	</passengers>
</flight>
```
If the flight can not be found with the given number, return:
```
{
	"BadRequest": {
		"code": " 404 ",
		"msg": " Sorry, the requested flight with number XXX does not exist"
	}
}
```
You must return HTTP error code 404 as well.<br>
Note:<br>
This XML is meant for readonly, and is not an HTML page or form. 

<h4>(13) Create or update a flight<br></h4>

URL: https://hostname/flight/flightNumber?price=120&from=AA&to=BB&departureTime=CC&arrivalTime=DD&description=EE&capacity=GG&model=HH&manufacturer=II&yearOfManufacture=1997<br>
Method: POST<br>
Return:<br>
If the flight is created/updated successfully, it should return the newly created/updated flight in XML, the same as GET https://hostname/flight/flightNumber?xml=true

Otherwise, return the appropriate error code, 400 or 404, and error message, e.g., 
```
{
	"BadRequest": {
		"code": " 404 ",
		"msg": "xxx"
	}
}
```
Note: <br>
xxx here is the failure reason, and you must return HTTP error code as well.<br>
This request creates/updates a new flight for the system. <br>
For simplicity, all the fields are passed as query parameters, and you can assume the request always comes with all the fields specified. <br>
The corresponding flight should be created/updated accordingly.<br>
You may need to update the seatsLeft when capacity is modified. <br>
If change of a flight’s departure and/or arrival time causes a passenger to have overlapping flight time, this update cannot proceed and hence fails with error code 400.<br>

<h4>(14) Delete a flight<br></h4>

URL: https://hostname/airline/flightNumber<br>
Method: DELETE<br>
Return: <br>
If the flight with the given flight number does not exist, return:
```
{
	"BadRequest": {
		"code": " 404 ",
		"msg": " xxx "
	}
}
```
Note:<br>
xxx here is the reason why you can not delete the flight. <br>
You must return HTTP error code 404 as well.<br>

Otherwise, return:
```
<Response>
           <code> 200 </code>
           <msg> Flight with number XXX is deleted successfully  </msg>
</Response>
```
Note: <br>
xxx here is the given number in the request.<br>
This request deletes the flight for the given flight number. <br>
You can not delete a flight that has one or more reservation, in which case, the deletion should fail with error code 400. <br>

<h4>Additional Requirements:</h4>

~~~
* All these operations should be transactional.
* You must use JPA and persist the user data into a database. If you are on Amazon EC2, you need to use MySQL; For GCP, you can use either Cloud Datastore, or Cloud SQL.
* Please add proper JavaDoc comments.
* You must keep your server running for at least three weeks upon submission.
~~~


