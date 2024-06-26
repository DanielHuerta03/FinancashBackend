package pe.edu.upc.catchthem.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import pe.edu.upc.catchthem.dtos.ClienteDTO;
import pe.edu.upc.catchthem.dtos.ContractDTO;
import pe.edu.upc.catchthem.dtos.LoanDto;
import pe.edu.upc.catchthem.dtos.ScheduleDTO;
import pe.edu.upc.catchthem.entities.Cliente;
import pe.edu.upc.catchthem.entities.Contract;
import pe.edu.upc.catchthem.entities.Loan;
import pe.edu.upc.catchthem.entities.Schedule;
import pe.edu.upc.catchthem.serviceInterfaces.ClienteService;
import pe.edu.upc.catchthem.serviceInterfaces.ContractService;
import pe.edu.upc.catchthem.serviceInterfaces.LoanService;
import pe.edu.upc.catchthem.serviceInterfaces.ScheduleService;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/Loan")
public class LoanController {
    @Autowired
    private LoanService lS;
    @Autowired
    private ContractService cS;
    @Autowired
    private ClienteService cSS;
    @Autowired
    private ScheduleService scheduleService;

    @PostMapping("/Post")
    public void registrar(@RequestBody LoanDto dto) {
        ModelMapper m = new ModelMapper();
        Loan u = m.map(dto, Loan.class);
        lS.insert(u);
    }

    @GetMapping("/calculateFutureValue/{loanId}")
    public double calculateFutureValue(@PathVariable("loanId") Long loanId) {
        // Obtener el préstamo usando el ID
        Loan loan = lS.findById(loanId);

        // Obtener el monto (C) del préstamo
        double C = loan.getAmount();

        // Obtener el contrato asociado
        Contract contract = loan.getContractid();

        // Obtener la tasa efectiva periódica (TEP) del contrato y dividirla entre 100
        double TEP = contract.getTem();

        // Calcular la diferencia de días (N° días trasladar)
        LocalDate today = LocalDate.now();
        LocalDate nextPaymentDay = getNextPaymentDay(contract.getPaymentDay());
        long daysToTransfer = ChronoUnit.DAYS.between(today, nextPaymentDay);

        // N° días TEP
        int daysTEP = 30;

        // Calcular el valor futuro (S)
        double S = C * Math.pow(1 + TEP, (double) daysToTransfer / daysTEP);

        // Crear y guardar el schedule
        createSchedule(loan, S, nextPaymentDay);

        return S;
    }

    @GetMapping("/calculateFrenchMethod/{loanId}")
    public List<ScheduleDTO> calculateFrenchMethod(@PathVariable("loanId") Long loanId) {
        // Obtener el préstamo usando el ID
        Loan loan = lS.findById(loanId);

        // Obtener el monto (C) del préstamo
        double C = loan.getAmount();

        // Obtener el contrato asociado
        Contract contract = loan.getContractid();

        // Obtener la tasa efectiva periódica (TEP) del contrato y dividirla entre 100
        double TEP = contract.getTem();

        // Obtener el número de cuotas (n) del préstamo
        int n = loan.getTotalInstallments();

        // Calcular la cuota (R) usando la fórmula francesa
        double R = C * ((TEP * Math.pow(1 + TEP, n)) / (Math.pow(1 + TEP, n) - 1));

        // Crear y guardar los schedules
        return createFrenchSchedules(loan, R, n);
    }

    @GetMapping("/verifyAndUpdateLoanStatus")
    public void verifyAndUpdateLoanStatus() {
        List<Loan> loans = lS.findAll();
        for (Loan loan : loans) {
            List<Schedule> schedules = scheduleService.findSchedulesByLoanId(loan.getId());
            boolean allPaid = schedules.stream().allMatch(schedule -> !schedule.getStatus().equals("vencido") && !schedule.getStatus().equals("pendiente"));
            if (allPaid) {
                loan.setStatus("pagado");
                lS.update(loan);
            }
        }
    }

    private LocalDate getNextPaymentDay(String paymentDay) {
        // Obtener el próximo día de pago basado en la fecha actual y el día de pago proporcionado
        LocalDate today = LocalDate.now();
        int paymentDayOfMonth = Integer.parseInt(paymentDay);

        LocalDate nextPaymentDate = today.withDayOfMonth(paymentDayOfMonth);
        if (nextPaymentDate.isBefore(today) || nextPaymentDate.equals(today)) {
            nextPaymentDate = nextPaymentDate.plusMonths(1);
        }
        return nextPaymentDate;
    }

    private void createSchedule(Loan loan, double S, LocalDate nextPaymentDay) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setInstallmentNumber(1);
        scheduleDTO.setCuota(S);
        scheduleDTO.setStatus("pendiente");
        scheduleDTO.setDueDate(nextPaymentDay);
        scheduleDTO.setLoanid(loan);
        scheduleDTO.setClientid(loan.getContractid().getClientId());

        ModelMapper modelMapper = new ModelMapper();
        Schedule schedule = modelMapper.map(scheduleDTO, Schedule.class);
        scheduleService.insert(schedule);
    }

    private List<ScheduleDTO> createFrenchSchedules(Loan loan, double R, int n) {
        List<ScheduleDTO> schedules = new ArrayList<>();
        LocalDate nextPaymentDay = getNextPaymentDay(loan.getContractid().getPaymentDay());

        for (int i = 1; i <= n; i++) {
            ScheduleDTO scheduleDTO = new ScheduleDTO();
            scheduleDTO.setInstallmentNumber(i);
            scheduleDTO.setCuota(R);
            scheduleDTO.setStatus("pendiente");
            scheduleDTO.setDueDate(nextPaymentDay.plusMonths(i - 1));
            scheduleDTO.setLoanid(loan);
            scheduleDTO.setClientid(loan.getContractid().getClientId());

            ModelMapper modelMapper = new ModelMapper();
            Schedule schedule = modelMapper.map(scheduleDTO, Schedule.class);
            scheduleService.insert(schedule);

            schedules.add(scheduleDTO);
        }

        return schedules;
    }

    @GetMapping("/loansWithoutSchedules")
    public List<Loan> getLoansWithoutSchedules() {
        return lS.findLoansWithoutSchedules();
    }

    @GetMapping("/loansByClient/{clientId}")
    public List<LoanDto> getLoansByClientId(@PathVariable Long clientId) {
        List<Loan> loans = lS.findLoansByClientId(clientId);
        ModelMapper modelMapper = new ModelMapper();
        return loans.stream()
                .map(loan -> modelMapper.map(loan, LoanDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/sumLoanAmountsByClient/{clientId}")
    public double sumLoanAmountsByClientId(@PathVariable Long clientId) {
        List<Loan> loans = lS.findLoansByClientId(clientId);
        return loans.stream()
                .filter(loan -> "pendiente".equalsIgnoreCase(loan.getStatus()))
                .mapToDouble(Loan::getAmount)
                .sum();
    }

    @GetMapping("/checkCreditLimit/{clientId}/{monto}")
    public boolean checkCreditLimit(@PathVariable Long clientId, @PathVariable int monto) {
        // Obtener los préstamos pendientes
        double totalLoanAmount = sumLoanAmountsByClientId(clientId);

        // Obtener el límite de crédito del cliente
        Cliente cliente = cSS.listarId(clientId);
        int creditLimit = cliente.getCreditLimit();

        // Verificar si los créditos pendientes más el monto ingresado son menores al límite de crédito
        return totalLoanAmount + monto <= creditLimit;
    }

}
