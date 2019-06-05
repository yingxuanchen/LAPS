package sg.edu.nus.laps.repo;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import sg.edu.nus.laps.model.EmpLeave;
import sg.edu.nus.laps.model.Employee;

@Repository
public interface EmpLeaveRepository extends JpaRepository<EmpLeave, Integer>{
	
	@Query("SELECT el FROM EmpLeave el WHERE el.employee=:employee AND el.leaveType=:leaveType")
	EmpLeave findByEmployeeAndLeaveType(Employee employee, String leaveType);
	
	@Query("SELECT el FROM EmpLeave el WHERE el.employee=:employee")
	Set<EmpLeave> findByEmployee(Employee employee);
}
