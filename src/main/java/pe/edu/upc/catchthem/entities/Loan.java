package pe.edu.upc.catchthem.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "Loan")
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount", length = 60, nullable = false)
    private int amount;
    @Column(name = "totalInstallments", length = 60, nullable = false)
    private int totalInstallments;
    @Column(name = "status", length = 60, nullable = false)
    private String status;
    @ManyToOne
    @JoinColumn(name = "contractid")
    @JsonIgnore
    private Contract contractid;

    public Loan() {
    }

    public Loan(Long id, int amount, int totalInstallments, String status, Contract contractid) {
        this.id = id;
        this.amount = amount;
        this.totalInstallments = totalInstallments;
        this.status = status;
        this.contractid = contractid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getTotalInstallments() {
        return totalInstallments;
    }

    public void setTotalInstallments(int totalInstallments) {
        this.totalInstallments = totalInstallments;
    }

    public Contract getContractid() {
        return contractid;
    }

    public void setContractid(Contract contractid) {
        this.contractid = contractid;
    }
}
