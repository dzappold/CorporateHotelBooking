package eu.grand.hotel.bookingpolicyservice

import dev.forkhandles.result4k.Result4k
import eu.grand.hotel.core.company.CompanyId
import eu.grand.hotel.core.company.EmployeeId
import eu.grand.hotel.core.hotel.RoomType
import eu.grand.hotel.core.hotel.RoomTypes

interface BookingPolicyService {
    fun setCompanyPolicy(companyId: CompanyId, roomTypes: RoomTypes)
    fun setEmployeePolicy(employeeId: EmployeeId, roomTypes: RoomTypes)

    fun isBookingAllowed(employeeId: EmployeeId, roomType: RoomType): Result4k<Boolean, BookingPolicyServiceError>
}
