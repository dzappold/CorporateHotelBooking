package actors

import eu.grand.hotel.core.CompanyId
import eu.grand.hotel.core.EmployeeId
import eu.grand.hotel.core.RoomType
import eu.grand.hotel.core.RoomTypes

fun CompanyAdmin.Companion.DomainCompanyAdmin(): CompanyAdmin = object : CompanyAdmin {
    private val employees = mutableListOf<EmployeeId>()
    private val companyPolicies = mutableSetOf<RoomType>()

    override val companyId: CompanyId
        get() = TODO("Not yet implemented")

    override fun addEmployee(employeeId: EmployeeId) {
        employees += employeeId
    }

    override fun deleteEmployee(employeeId: EmployeeId) {
        TODO("Not yet implemented")
    }

    override fun setCompanyBookingPolicy(roomTypes: RoomTypes) {
        companyPolicies.addAll(roomTypes)
    }

    override fun setEmployeeBookingPolicy(employeeId: EmployeeId, roomTypes: RoomTypes) {
        TODO("Not yet implemented")
    }
}
