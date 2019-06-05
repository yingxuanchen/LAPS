package sg.edu.nus.laps.repo;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import sg.edu.nus.laps.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
	
	@Query("SELECT e FROM Employee e WHERE e.empId=:empId AND e.pwd=:pwd")
	Employee findByEmpIdAndPwd(String empId, String pwd);
	
	@Query("SELECT e FROM Employee e WHERE e.manager=:manager")
	Set<Employee> findSubordinatesByManager(Employee manager);
}
