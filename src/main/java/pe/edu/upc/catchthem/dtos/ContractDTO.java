package pe.edu.upc.catchthem.dtos;

import pe.edu.upc.catchthem.entities.Cliente;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

public class ContractDTO {
    private Long id;


    private String typeRate;

    private String rate;

    private String period;

    private double tem;

    private String typePenaltyRate;

    private String penaltyRate;

    private String penaltyPeriod;

    private String paymentDay;

    private Cliente clientId;

    public double getTem() {
        return tem;
    }

    public void setTem(double tem) {
        this.tem = tem;
    }

    public Cliente getClientId() {
        return clientId;
    }

    public void setClientId(Cliente clientId) {
        this.clientId = clientId;
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

   }