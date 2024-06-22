package pe.edu.upc.catchthem.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Schedule")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "installmentNumber", length = 60, nullable = false)
    private int installmentNumber;
    @Column(name = "cuota", length = 60, nullable = false)
    private double cuota;
    @Column(name = "status", length = 60, nullable = false)
    private String status;
    @Column(name = "dueDate", length = 60, nullable = false)
    private LocalDate dueDate;
    @ManyToOne
    @JoinColumn(name = "loanid")
    @JsonIgnore
    private Loan loanid;
    @ManyToOne
    @JoinColumn(name = "clientid")
    @JsonIgnore
    private Cliente clientid;

    public Schedule() {
    }

    public Schedule(Long id, int installmentNumber, double cuota, String status, LocalDate dueDate, Loan loanid, Cliente clientid) {
        this.id = id;
        this.installmentNumber = installmentNumber;
        this.cuota = cuota;
        this.status = status;
        this.dueDate = dueDate;
        this.loanid = loanid;
        this.clientid = clientid;
    }

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
