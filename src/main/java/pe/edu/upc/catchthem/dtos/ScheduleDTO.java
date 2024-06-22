package pe.edu.upc.catchthem.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import pe.edu.upc.catchthem.entities.Cliente;
import pe.edu.upc.catchthem.entities.Loan;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

public class ScheduleDTO {
    private Long id;
    private int installmentNumber;
    private double cuota;
    private String status;
    private LocalDate dueDate;
    private Loan loanid;
    private Cliente clientid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getInstallmentNumber() {
        return installmentNumber;
    }

    public void setInstallmentNumber(int installmentNumber) {
        this.installmentNumber = installmentNumber;
    }

    public double getCuota() {
        return cuota;
    }

    public void setCuota(double cuota) {
        this.cuota = cuota;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Loan getLoanid() {
        return loanid;
    }

    public void setLoanid(Loan loanid) {
        this.loanid = loanid;
    }

    public Cliente getClientid() {
        return clientid;
    }

    public void setClientid(Cliente clientid) {
        this.clientid = clientid;
    }
}
