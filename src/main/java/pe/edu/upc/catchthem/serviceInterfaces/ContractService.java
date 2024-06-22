package pe.edu.upc.catchthem.serviceInterfaces;



import pe.edu.upc.catchthem.entities.Cliente;
import pe.edu.upc.catchthem.entities.Contract;

import java.util.List;

public interface ContractService {
    public void createContract(Contract contract);
    public Contract GetContractByClientId(long id);
    public void eliminar(Long id);
}
