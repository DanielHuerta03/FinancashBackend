package pe.edu.upc.catchthem.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import pe.edu.upc.catchthem.dtos.ContractDTO;
import pe.edu.upc.catchthem.entities.Contract;
import pe.edu.upc.catchthem.serviceInterfaces.ContractService;

@RestController
@RequestMapping("/Contract")
public class ContractController {
    @Autowired
    private ContractService cS;

    @PostMapping("/Post")
    public void registrar(@RequestBody ContractDTO dto){
        ModelMapper m = new ModelMapper();

        double tem = calcularTem(dto.getTypeRate(), dto.getRate(), dto.getPeriod());
        dto.setTem(tem);

        Contract u = m.map(dto, Contract.class);
        cS.createContract(u);
    }

    @DeleteMapping("/Delete/{id}")
    public void delete(@PathVariable("id") Long id) {
        cS.eliminar(id);
    }

    @GetMapping("/{id}")
    public ContractDTO GetContractByClientId(@PathVariable("id") long id) {
        ModelMapper m = new ModelMapper();
        Contract contract = cS.GetContractByClientId(id);
        return m.map(contract, ContractDTO.class);
    }

    private double calcularTem(String typeRate, String rate, String period) {
        double rateValue = Double.parseDouble(rate) / 100;
        int m = 0;

        switch (period.toLowerCase()) {
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
                m = 60;
                break;
            default:
                throw new IllegalArgumentException("Período no válido");
        }

        if (typeRate.equalsIgnoreCase("nominal")) {
            return Math.pow((1 + rateValue / m), 30) - 1;
        } else if (typeRate.equalsIgnoreCase("efectiva")) {
            return Math.pow((1 + rateValue), 30.0 / m) - 1;
        } else {
            throw new IllegalArgumentException("Tipo de tasa no válido");
        }
    }
}
