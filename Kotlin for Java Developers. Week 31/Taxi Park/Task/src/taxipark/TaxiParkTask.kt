package taxipark

/*
 * Task #1. Find all the drivers who performed no trips.
 */
fun TaxiPark.findFakeDrivers(): Set<Driver> =
        this.allDrivers.subtract(this.trips.map {t -> t.driver}.distinct());

/*
 * Task #2. Find all the clients who completed at least the given number of trips.
 */
fun TaxiPark.findFaithfulPassengers(minTrips: Int): Set<Passenger> =
        if (minTrips == 0)
            this.allPassengers
        else
            this.trips.map {t -> t.passengers}.flatten().groupBy {p -> p}.filter { p -> p.value.size >= minTrips }.map { p -> p.key}.toHashSet()

/*
 * Task #3. Find all the passengers, who were taken by a given driver more than once.
 */
fun TaxiPark.findFrequentPassengers(driver: Driver): Set<Passenger> =
        this.trips.filter { t -> t.driver == driver }.map {t -> t.passengers}.flatten().groupBy {p -> p}.filter { p -> p.value.size > 1 }.map { p -> p.key}.toHashSet()

/*
 * Task #4. Find the passengers who had a discount for majority of their trips.
 */
fun TaxiPark.findSmartPassengers(): Set<Passenger> =
        this.allPassengers.filter { p -> trips.filter { t -> t.passengers.contains(p) }.count { t -> (t?.discount ?: 0.0) > 0 } > trips.filter { t -> t.passengers.contains(p) }.count { t -> (t?.discount ?: 0.0) <= 0 } }.toHashSet()

/*
 * Task #5. Find the most frequent trip duration among minute periods 0..9, 10..19, 20..29, and so on.
 * Return any period if many are the most frequent, return `null` if there're no trips.
 */
fun TaxiPark.findTheMostFrequentTripDurationPeriod(): IntRange? {
    fun getIntRange(x: Int) : IntRange {
        val rangeStart: Int = (x/10) * 10;
        return IntRange( rangeStart, rangeStart + 9);
    }
    return this.trips.map { t -> getIntRange(t.duration)}.groupBy { r -> r }.maxBy { p -> p.value.size }?.component1()
}

/*
 * Task #6.
 * Check whether 20% of the drivers contribute 80% of the income.
 */
fun TaxiPark.checkParetoPrinciple(): Boolean {

    if (trips.isEmpty()) {
        return false;
    }
    val top20Income = trips.groupBy { t -> t.driver }
                           .map { e -> e.value.sumByDouble { x -> x.cost } }
                           .sortedDescending().take((allDrivers.count() * 0.2).toInt())
                           .sum();
    return top20Income >= trips.sumByDouble { t -> t.cost } * 0.8;
}