package sg.edu.nus.laps;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LapsApplication implements CommandLineRunner {

	// set system time zone to UTC?
	@PostConstruct
    public void init(){
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }
	
//	private EmployeeRepository eRepo;
//	@Autowired
//	public void seteRepo(EmployeeRepository eRepo) {
//		this.eRepo = eRepo;
//	}
	
	public static void main(String[] args) {
		SpringApplication.run(LapsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//		
//		Employee empA = new Employee();
//		empA.setEmpId("804592");
//		empA.setEmpName("yingxuan");
//		empA.setPwd("123");
//		LeaveRecord l1 = new LeaveRecord();
//		l1.setReason("tired");
//		l1.setStartDate(df.parse("2019-04-30"));
//		l1.setEndDate(df.parse("2019-05-02"));
//		empA.addLeave(l1);
//		LeaveRecord l2 = new LeaveRecord();
//		l2.setReason("again");
//		l2.setStartDate(df.parse("2019-05-30"));
//		l2.setEndDate(df.parse("2019-06-02"));
//		empA.addLeave(l2);
//		eRepo.save(empA);
	}
}
