package pe.edu.upc.catchthem.serviceInterfaces;

import pe.edu.upc.catchthem.entities.Cliente;
import pe.edu.upc.catchthem.entities.Users;

import java.util.List;

public interface ClienteService {
    public void insert(Cliente cliente);
    public void eliminar(Long id);
    public List<Cliente> listar();
    public Cliente listardni(String dni);
    Cliente listarId(Long id);
}
