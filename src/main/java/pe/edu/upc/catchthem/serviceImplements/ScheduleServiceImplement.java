package pe.edu.upc.catchthem.serviceImplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.catchthem.entities.Schedule;
import pe.edu.upc.catchthem.repositories.LoanRepository;
import pe.edu.upc.catchthem.repositories.ScheduleRepository;
import pe.edu.upc.catchthem.serviceInterfaces.ScheduleService;

import java.util.List;
import java.util.Optional;

@Service
public class ScheduleServiceImplement implements ScheduleService {
    @Autowired
    private ScheduleRepository ScheduleRepository;

    @Override
    public void insert(Schedule schedule) {
        ScheduleRepository.save(schedule);
    }

    @Override
    public List<Schedule> findAll() {
        return ScheduleRepository.findAll();
    }

    @Override
    public void update(Schedule schedule) {
        ScheduleRepository.save(schedule);
    }

    @Override
    public Schedule findById(Long id) {
        Optional<Schedule> optionalSchedule = ScheduleRepository.findById(id);
        return optionalSchedule.orElse(null);
    }

    @Override
    public List<Schedule> findSchedulesByLoanId(Long loanId) {
        return ScheduleRepository.buscarporidload(loanId);
    }
}
