package pe.edu.upc.catchthem.serviceImplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.catchthem.entities.Cliente;
import pe.edu.upc.catchthem.repositories.ClienteRepository;
import pe.edu.upc.catchthem.serviceInterfaces.ClienteService;

import java.util.List;

@Service
public class ClienteServiceImplement implements ClienteService {

    @Autowired
    private ClienteRepository ClienteRepository;
    @Override

    public void eliminar(Long id){
        ClienteRepository.deleteById(id);
    }
    public void insert(Cliente Cliente){
        ClienteRepository.save(Cliente);
    }
    public List<Cliente> listar() {
        return ClienteRepository.findAll();
    }

    @Override
    public Cliente listardni(String dni) {
        return ClienteRepository.findByDni(dni);
    }

    @Override
    public Cliente listarId(Long id) {
        return ClienteRepository.findById(id).orElse(null);
    }
}
