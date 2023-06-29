interface CompanyAdmin {
    fun addEmployee(companyId: CompanyId, employeeId: EmployeeId)
    fun setCompanyPolicy(companyId: CompanyId, roomType: RoomType)
}
