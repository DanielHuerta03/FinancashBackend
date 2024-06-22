package pe.edu.upc.catchthem.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
@Entity
@Table(name = "Contract")
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "typeRate", length = 60, nullable = false)
    private String typeRate;
    @Column(name = "rate", length = 60, nullable = false)
    private String rate;
    @Column(name = "period", length = 60, nullable = false)
    private String period;
    @Column(name = "tem", length = 60, nullable = false)
    private double tem;
    @Column(name = "typePenaltyRate", length = 60, nullable = false)
    private String typePenaltyRate;
    @Column(name = "penaltyRate", length = 60, nullable = false)
    private String penaltyRate;
    @Column(name = "penaltyPeriod", length = 9, nullable = false)
    private String penaltyPeriod;
    @Column(name = "paymentDay")
    private String paymentDay;
    @OneToOne
    @JoinColumn(name = "clientId", unique = true)
    @JsonIgnore
    private Cliente clientId;

    public Contract() {
    }

    public Contract(Cliente clientId, String paymentDay, String penaltyPeriod, String penaltyRate, String typePenaltyRate, double tem, String period, String rate, String typeRate, Long id) {
        this.clientId = clientId;
        this.paymentDay = paymentDay;
        this.penaltyPeriod = penaltyPeriod;
        this.penaltyRate = penaltyRate;
        this.typePenaltyRate = typePenaltyRate;
        this.tem = tem;
        this.period = period;
        this.rate = rate;
        this.typeRate = typeRate;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeRate() {
        return typeRate;
    }

    public void setTypeRate(String typeRate) {
        this.typeRate = typeRate;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getTypePenaltyRate() {
        return typePenaltyRate;
    }

    public double getTem() {
        return tem;
    }

    public void setTem(double tem) {
        this.tem = tem;
    }

    public void setTypePenaltyRate(String typePenaltyRate) {
        this.typePenaltyRate = typePenaltyRate;
    }

    public String getPenaltyRate() {
        return penaltyRate;
    }

    public void setPenaltyRate(String penaltyRate) {
        this.penaltyRate = penaltyRate;
    }

    public String getPenaltyPeriod() {
        return penaltyPeriod;
    }

    public void setPenaltyPeriod(String penaltyPeriod) {
        this.penaltyPeriod = penaltyPeriod;
    }

    public String getPaymentDay() {
        return paymentDay;
    }

    public void setPaymentDay(String paymentDay) {
        this.paymentDay = paymentDay;
    }

    public Cliente getClientId() {
        return clientId;
    }

    public void setClientId(Cliente clientId) {
        this.clientId = clientId;
    }
}
