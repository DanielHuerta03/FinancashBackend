package pe.edu.upc.catchthem.serviceInterfaces;

import pe.edu.upc.catchthem.entities.Loan;
import pe.edu.upc.catchthem.entities.Schedule;

import java.util.List;

public interface ScheduleService {
    public void insert(Schedule schedule);
    public List<Schedule> findAll();
    public void update(Schedule schedule);
    public Schedule findById(Long id);
    List<Schedule> findSchedulesByLoanId(Long loanId);

}
