package sg.edu.nus.laps.repo;

import java.util.Date;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import sg.edu.nus.laps.model.PublicHoliday;

@Repository
public interface PublicHolidayRepository extends JpaRepository<PublicHoliday,Date> {
	
	@Query("SELECT ph.phDate FROM PublicHoliday ph")
	Set<Date> findAllPublicHolidayDates();
}
