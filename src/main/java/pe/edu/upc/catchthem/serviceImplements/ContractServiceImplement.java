package pe.edu.upc.catchthem.serviceImplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.catchthem.entities.Contract;
import pe.edu.upc.catchthem.repositories.ContractRepository;
import pe.edu.upc.catchthem.repositories.UserRepository;
import pe.edu.upc.catchthem.serviceInterfaces.ContractService;

import java.util.List;

@Service
public class ContractServiceImplement implements ContractService {
    @Autowired
    private ContractRepository ContractRepository;

    @Override
    public void createContract(Contract contract) {
        ContractRepository.save(contract);
    }

    @Override
    public Contract GetContractByClientId(long id) {
        return ContractRepository.GetContractByClientId(id);
    }

    @Override
    public void eliminar(Long id) {
        ContractRepository.deleteById(id);
    }


}
