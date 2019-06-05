package sg.edu.nus.laps.repo;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import sg.edu.nus.laps.model.Employee;
import sg.edu.nus.laps.model.LeaveRecord;

@Repository
public interface LeaveRecordRepository extends JpaRepository<LeaveRecord, Integer>{
	
	@Query("SELECT l FROM LeaveRecord l WHERE l.employee=:employee")
	Set<LeaveRecord> findByEmployee(Employee employee);
	
	@Query("SELECT l FROM LeaveRecord l WHERE l.employee=:employee AND l.leaveStatus IN ('Applied','Updated','Approved')")
	Set<LeaveRecord> findValidLeavesByEmployee(Employee employee);
}
