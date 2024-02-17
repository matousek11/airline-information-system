# Airline information system
**Can be used FOR SIMULATION FLIGHTS ONLY!**

system roles:
 - admin
 - planner
 - pilot
 - flight attendant(FA)
 - technician

system can:
- save planes in airline
- save staff names and their purpose
- plan flights with each plane
  - generate flight route
  - display time graph with busy planes
- admin
  - can add pilots, flight attendants and technicians
- planner
  - can add new flights
- pilot briefing
  - loged in pilot can look at his flights
  - check metar at airports
  - see flight plan
  - see expected fuel to load
  - number of passengers
  - total planned weight of plane
- FA
  - can se gate and departure time(same for arrival)
- technician
  - can log aircraft repair

Business entities:
- users
- planes
- flights
- maintenance logs of each plane

Tech stack:
- Java Spring Boot
- React
- Material UI
- less

Used api:
- [weather](https://aviationweather.gov/data/api/#/Data/dataMetars)
- [flight routes generation](https://flightplandatabase.com/dev/api)