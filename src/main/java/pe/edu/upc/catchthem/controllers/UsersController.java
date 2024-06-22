package pe.edu.upc.catchthem.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.catchthem.dtos.*;
import pe.edu.upc.catchthem.entities.Users;
import pe.edu.upc.catchthem.serviceInterfaces.IUsersService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private IUsersService uS;

    @PostMapping
    public void registrar(@RequestBody UsersDTO dto){
        ModelMapper m = new ModelMapper();
        Users u = m.map(dto,Users.class);
        u.setPassword(u.getPassword());
        uS.insert(u);
    }
    @GetMapping
    public List<UsersDTO> listar(){
        return uS.listar().stream().map(x-> {
            ModelMapper m = new ModelMapper();
            return m.map(x,UsersDTO.class);
        }).collect(Collectors.toList());
    }
    @DeleteMapping("/{id}")

    public void delete(@PathVariable("id") Long id) {
        uS.eliminar(id);
    }


    @PostMapping("/login")
    public boolean login(@RequestBody UsersDTO dto) {
        Users user = uS.findUserByEmailAndPassword(dto.getEmail(), dto.getPassword());
        return user != null;
    }






}
