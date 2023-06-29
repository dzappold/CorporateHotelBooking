package eu.grand.hotel.bookingpolicyservice

import dev.forkhandles.result4k.Result4k
import eu.grand.hotel.core.CompanyId
import eu.grand.hotel.core.EmployeeId
import eu.grand.hotel.core.RoomType
import eu.grand.hotel.core.RoomTypes

interface BookingPolicyService {
    fun setCompanyPolicy(companyId: CompanyId, roomTypes: RoomTypes)
    fun setEmployeePolicy(employeeId: EmployeeId, roomTypes: RoomTypes)

    fun isBookingAllowed(employeeId: EmployeeId, roomType: RoomType): Result4k<Boolean, BookingPolicyServiceError>
}
