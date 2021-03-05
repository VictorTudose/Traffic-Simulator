Student : Victor Tudose

================================================================

My Task Specific Classes
	-> CarEntry
		<singleton>
		- barrier that ensures all cars arrived
	-> CrosswalkPrinter
		<singleton>
	-> PathCombiner
		<singleton>
		- takes more paths and combines them in only one
	-> Priority Road
		<singleton>
	-> RailRoad
		<singleton>

................................................................

CrosswalkPrinter
	* state machine that stores the state (green/red)
	* prints green and red when necessary
	* cars subscribe to it
	* iterates though cars and print for them
	* is linked with a semaphore to pedestrian object

................................................................
PathCombiner
	for task8
		2 semaphores
		lan0 acquires the first and releases the second
		lan1 acquires the second and releases the first

................................................................

Priority Road
	cars with high priority go directly and increment a counter
	cars with low priority wait until the counter is zero and then instantly go

................................................................

RailRoad
	the train waits for all the cars to arrive
	the train passes
	the train notifies the car in the order they come
		the order is kept using a queue

----------------------------------------------------------------

My other Classes:
	-> LaneMaxim
		<singleton>
		- ensures that no more than n cars pass through lane
	-> LaneEqual
		<singleton>
		- ensures that exactly n cars pass through lane
		- should be used with a LaneMax to properly work
	-> SingleEqual
		<singleton>
		- ensures that exactly n cars pass through all the intersection
		- should be used with a SingleMaxim to properly work
	-> SingleMaxim
		<singleton>
		- ensures that no more than n cars pass through all the intersection
	-> Prints
		<singleton>
		- chooses what to print based on handler

................................................................

SingleMaxim and SingleEqual are 1 object(barrier/semaphore) used for every cars
LaneMaxim and LaneEqual are an array of objects(barrier/semaphore) used for every lane

->Usage
	LaneMaxim and SingleMaxim
		car aquires the permit , car does its task, car releases the permit
	LaneEqual and SingleEqual
		car waits for other, car does its task

----------------------------------------------------------------

Modified classed
	* Pedeastrians
		* bool -> AtomicBoolean
	* Main
		* created a Railroad thread
	* Car
		* computed the number of lanes as cars where added

================================================================

Task 1
	cars pass
Task 2
	ReaderHandlerFactory
		set the SingleMaxim
	IntersectionHandlerFactory
		use the SingleMaxim
Task 3
	ReaderHandlerFactory
		set the LaneMaxim and SingleEqual
	IntersectionHandlerFactory
		use booth LaneMaxim and SingleEqual
Task 4
	ReaderHandlerFactory
		set the LaneMaxim,LaneEqual and SingleEqual
	IntersectionHandlerFactory
		use all the LaneMaxim,LaneEqual and SingleEqual
Task 5
	ReaderHandlerFactory
		set the LaneMaxim and SingleEqual
	IntersectionHandlerFactory
		use booth the LaneMaxim and SingleEqual
Task 6
	ReaderHandlerFactory
		-
	IntersectionHandlerFactory
		pass the control to the PriorityRoad 
Task 7
	ReaderHandlerFactory
		set the CrosswalkPrinter
	IntersectionHandlerFactory
		wait until all the pedestrians are finished
			if the semaphore is green -> printGreen
			if the semaphore is red -> printRed
Task 8
	ReaderHandlerFactory
		set the PathCombiner
	IntersectionHandlerFactory
		pass the control to the PathCombiner
Task 10
	ReaderHandlerFactory
		set the RailRoad
	IntersectionHandlerFactory
		pass the control to the RailRoad

================================================================