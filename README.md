# Car Park API

A small in-memory REST API for managing a car park. It parks vehicles in the first free space, tracks how full the car park is, and works out the charge when a vehicle leaves.

Charging: per-minute rate (10p small / 20p medium / 40p large) plus £1 for every full 5 minutes parked.

## Endpoints

- `GET /parking` - number of available and occupied spaces.
- `POST /parking` - park a vehicle in the first free space. Body `{ "vehicleReg", "vehicleType" }` (type 1/2/3 = small/medium/large). Returns the vehicle and its space number.
- `POST /parking/bill` - free the vehicle's space and return its final charge. Body `{ "vehicleReg" }`.
