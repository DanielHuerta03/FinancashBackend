package pe.edu.upc.catchthem.serviceInterfaces;

import pe.edu.upc.catchthem.entities.Users;

import java.util.List;

public interface IUsersService {
    public void insert(Users users);
    public void eliminar(Long id);
    public List<Users> listar();
    Users findUserByEmailAndPassword(String email, String password);


}