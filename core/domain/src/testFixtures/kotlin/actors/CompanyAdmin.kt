package actors

import eu.grand.hotel.core.CompanyId
import eu.grand.hotel.core.EmployeeId
import eu.grand.hotel.core.RoomTypes

// knows her/his company and employees
interface CompanyAdmin {
    val companyId: CompanyId
    fun addEmployee(employeeId: EmployeeId)
    fun deleteEmployee(employeeId: EmployeeId)
    fun setCompanyBookingPolicy(roomTypes: RoomTypes)
    fun setEmployeeBookingPolicy(employeeId: EmployeeId, roomTypes: RoomTypes)

    companion object
}
