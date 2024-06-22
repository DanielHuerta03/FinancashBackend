package pe.edu.upc.catchthem.serviceImplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.catchthem.entities.Loan;
import pe.edu.upc.catchthem.entities.Schedule;
import pe.edu.upc.catchthem.repositories.LoanRepository;
import pe.edu.upc.catchthem.repositories.ScheduleRepository;
import pe.edu.upc.catchthem.serviceInterfaces.LoanService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanServiceImplement  implements LoanService {
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;

    @Override
    public void insert(Loan loan) {
        loanRepository.save(loan);
    }

    @Override
    public Loan findById(Long id) {
        return loanRepository.findById(id).orElse(null);
    }

    @Override
    public List<Loan> findAll() {
        return loanRepository.findAll();
    }

    @Override
    public List<Loan> findLoansWithoutSchedules() {
        List<Loan> allLoans = loanRepository.findAll();
        List<Schedule> allSchedules = scheduleRepository.findAll();

        List<Long> loansWithSchedulesIds = allSchedules.stream()
                .map(schedule -> schedule.getLoanid().getId())
                .collect(Collectors.toList());

        return allLoans.stream()
                .filter(loan -> !loansWithSchedulesIds.contains(loan.getId()))
                .collect(Collectors.toList());
    }

}
