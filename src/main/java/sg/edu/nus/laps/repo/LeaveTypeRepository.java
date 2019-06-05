package sg.edu.nus.laps.repo;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import sg.edu.nus.laps.model.LeaveType;

@Repository
public interface LeaveTypeRepository extends JpaRepository<LeaveType, Integer>{

	@Query("SELECT DISTINCT lt.leaveType FROM LeaveType lt")
	Set<String> findAllLeaveTypes();
}
