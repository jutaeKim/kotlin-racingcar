package racingcar.racing

import racingcar.racing.car.Car

data class RacingTrial(
    val order: TrialOrder,
    val records: List<Record>
) {
    val leadRecords: List<Record>
        get() = records.filter { it.location == oneOfLeadRecord.location }

    private val oneOfLeadRecord: Record
        get() = records.maxBy { it.location } ?: throw IllegalStateException("records is empty!")

    companion object {
        fun of(order: Int, cars: Collection<Car>): RacingTrial {
            return RacingTrial(TrialOrder(order), cars.map { Record(it.name, it.location) })
        }
    }
}