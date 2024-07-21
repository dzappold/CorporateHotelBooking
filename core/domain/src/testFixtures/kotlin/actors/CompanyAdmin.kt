package actors

import eu.grand.hotel.core.company.CompanyId
import eu.grand.hotel.core.company.EmployeeId
import eu.grand.hotel.core.hotel.RoomTypes

// knows her/his company and employees
interface CompanyAdmin {
    val companyId: CompanyId
    fun addEmployee(employeeId: EmployeeId)
    fun deleteEmployee(employeeId: EmployeeId)
    fun setCompanyBookingPolicy(roomTypes: RoomTypes)
    fun setEmployeeBookingPolicy(employeeId: EmployeeId, roomTypes: RoomTypes)

    companion object
}
