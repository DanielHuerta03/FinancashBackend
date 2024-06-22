package pe.edu.upc.catchthem.dtos;

import pe.edu.upc.catchthem.entities.Cliente;
import pe.edu.upc.catchthem.entities.Schedule;

import java.util.List;

public class DeudasDTO {
    private Long id;

    private String Cliente;

    private String MontoTotal;

    private int Mes;

    private List<Schedule> Schedules;

    public int getMes() {
        return Mes;
    }

    public void setMes(int mes) {
        Mes = mes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCliente() {
        return Cliente;
    }

    public void setCliente(String cliente) {
        Cliente = cliente;
    }

    public String getMontoTotal() {
        return MontoTotal;
    }

    public void setMontoTotal(String montoTotal) {
        MontoTotal = montoTotal;
    }

    public List<Schedule> getSchedules() {
        return Schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        Schedules = schedules;
    }
}
