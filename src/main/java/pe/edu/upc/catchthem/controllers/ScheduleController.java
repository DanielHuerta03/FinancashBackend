package pe.edu.upc.catchthem.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.catchthem.dtos.DeudasDTO;
import pe.edu.upc.catchthem.dtos.ScheduleDTO;
import pe.edu.upc.catchthem.entities.Contract;
import pe.edu.upc.catchthem.entities.Loan;
import pe.edu.upc.catchthem.entities.Schedule;
import pe.edu.upc.catchthem.entities.Cliente;
import pe.edu.upc.catchthem.serviceInterfaces.ScheduleService;
import pe.edu.upc.catchthem.serviceInterfaces.LoanService;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/Schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService lS;

    @Autowired
    private LoanService loanService;

    @PostMapping("/Post")
    public void registrar(@RequestBody ScheduleDTO dto) {
        ModelMapper m = new ModelMapper();
        Schedule u = m.map(dto, Schedule.class);
        lS.insert(u);
    }

    @PutMapping("/UpdateStatusAndQuota")
    public void updateStatusAndQuota() {
        List<Schedule> schedules = lS.findAll();
        LocalDate today = LocalDate.now();

        for (Schedule schedule : schedules) {
            if (schedule.getDueDate().isBefore(today) && !schedule.getStatus().equals("pagado")) {
                schedule.setStatus("vencido");
                Loan loan = schedule.getLoanid();
                Contract contract = loan.getContractid();
                double penaltyRate = Double.parseDouble(contract.getPenaltyRate()) / 100;
                long td = ChronoUnit.DAYS.between(schedule.getDueDate(), today);
                int m = 0;

                switch (contract.getPenaltyPeriod().toLowerCase()) {
                    case "anual":
                        m = 360;
                        break;
                    case "semestral":
                        m = 180;
                        break;
                    case "mensual":
                        m = 30;
                        break;
                    case "bimestral":
                        m = 69;
                        break;
                }

                double Im = 0;
                double cuota = schedule.getCuota();
                if (contract.getTypePenaltyRate().equalsIgnoreCase("efectiva")) {
                    Im = cuota * (Math.pow((1 + penaltyRate), (td / (double) m)) - 1);
                } else if (contract.getTypePenaltyRate().equalsIgnoreCase("nominal")) {
                    Im = cuota * (Math.pow((1 + penaltyRate / m), td) - 1);
                }

                schedule.setCuota(cuota + Im);
                lS.update(schedule);
            }
        }
    }

    @PutMapping("/MarkAsPaid/{id}")
    public void markAsPaid(@PathVariable Long id) {
        Schedule schedule = lS.findById(id);
        if (schedule != null) {
            schedule.setStatus("pagado");
            lS.update(schedule);
        }
    }

    @GetMapping("/AllDebt/{year}/{month}")
    public List<DeudasDTO> getAllDebt(@PathVariable int year, @PathVariable int month) {
        List<Schedule> schedules = lS.findAll();
        Map<Cliente, List<Schedule>> groupedSchedules = schedules.stream()
                .filter(s -> {
                    LocalDate dueDate = s.getDueDate();
                    return (dueDate.getYear() == year && dueDate.getMonthValue() == month && !s.getStatus().equals("pagado")) ||
                            s.getStatus().equals("vencido");
                })
                .collect(Collectors.groupingBy(s -> s.getLoanid().getContractid().getClientId()));

        List<DeudasDTO> deudasDTOList = new ArrayList<>();

        for (Map.Entry<Cliente, List<Schedule>> entry : groupedSchedules.entrySet()) {
            Cliente cliente = entry.getKey();
            List<Schedule> clienteSchedules = entry.getValue();

            DeudasDTO deuda = new DeudasDTO();
            deuda.setId(cliente.getId());
            deuda.setCliente(cliente.getFirstName()); // Ajustar el nombre del método según tu entidad Cliente
            deuda.setMes(month);
            deuda.setSchedules(clienteSchedules);

            double montoTotal = clienteSchedules.stream().mapToDouble(Schedule::getCuota).sum();
            deuda.setMontoTotal(String.valueOf(montoTotal));

            deudasDTOList.add(deuda);
        }

        return deudasDTOList;
    }


    @PutMapping("/MarkAllAsPaid/{clientId}/{year}/{month}")
    public void markAllAsPaid(@PathVariable Long clientId, @PathVariable int year, @PathVariable int month) {
        List<Schedule> schedules = lS.findAll();

        for (Schedule schedule : schedules) {
            Cliente cliente = schedule.getLoanid().getContractid().getClientId();
            if (cliente.getId().equals(clientId)) {
                if (schedule.getDueDate().getYear() == year && (schedule.getDueDate().getMonthValue() == month || schedule.getStatus().equals("vencido"))) {
                    schedule.setStatus("pagado");
                    lS.update(schedule);
                }
            }
        }
    }


    @GetMapping("/ByLoan/{loanId}")
    public List<ScheduleDTO> getSchedulesByLoanId(@PathVariable Long loanId) {
        List<Schedule> schedules = lS.findSchedulesByLoanId(loanId);
        ModelMapper modelMapper = new ModelMapper();
        return schedules.stream()
                .map(schedule -> modelMapper.map(schedule, ScheduleDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/ByUserAndMonth/{userId}/{month}/{year}")
    public List<ScheduleDTO> getSchedulesByUserAndMonth(
            @PathVariable Long userId, @PathVariable int month, @PathVariable int year) {
        List<Schedule> schedules = lS.findAll();
        ModelMapper modelMapper = new ModelMapper();
        return schedules.stream()
                .filter(schedule -> schedule.getLoanid().getContractid().getClientId().getId().equals(userId)
                        && schedule.getDueDate().getMonthValue() == month
                        && schedule.getDueDate().getYear() == year)
                .map(schedule -> modelMapper.map(schedule, ScheduleDTO.class))
                .collect(Collectors.toList());
    }

}
