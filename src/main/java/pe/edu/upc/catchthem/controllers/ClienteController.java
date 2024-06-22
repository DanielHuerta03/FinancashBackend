package pe.edu.upc.catchthem.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.catchthem.dtos.ClienteDTO;
import pe.edu.upc.catchthem.dtos.UsersDTO;
import pe.edu.upc.catchthem.entities.Cliente;
import pe.edu.upc.catchthem.entities.Users;
import pe.edu.upc.catchthem.serviceInterfaces.ClienteService;
import pe.edu.upc.catchthem.serviceInterfaces.IUsersService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/Client")
public class ClienteController {
    @Autowired
    private ClienteService cS;

    @PostMapping("/Post")
    public void registrar(@RequestBody ClienteDTO dto){
        ModelMapper m = new ModelMapper();
        Cliente u = m.map(dto,Cliente.class);
        cS.insert(u);
    }
    @GetMapping("/GetAll")
    public List<ClienteDTO> listar(){
        return cS.listar().stream().map(x-> {
            ModelMapper m = new ModelMapper();
            return m.map(x,ClienteDTO.class);
        }).collect(Collectors.toList());
    }
    @DeleteMapping("/Delete/{id}")
    public void delete(@PathVariable("id") Long id) {
        cS.eliminar(id);
    }

    @GetMapping("/{DNI}")
    public ClienteDTO listarId(@PathVariable("DNI") String dni) {
        ModelMapper m = new ModelMapper();
        Cliente cliente = cS.listardni(dni);
        return m.map(cliente, ClienteDTO.class);
    }

    @GetMapping("/id/{id}")
    public ClienteDTO listarId(@PathVariable("id") Long id) {
        ModelMapper m = new ModelMapper();
        Cliente cliente = cS.listarId(id);
        return m.map(cliente, ClienteDTO.class);
    }
}
