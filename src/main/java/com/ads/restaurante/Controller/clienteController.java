package com.ads.restaurante.Controller;


import com.ads.restaurante.Model.clienteModel;
import com.ads.restaurante.Repository.clienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ads.restaurante.Service.*;
import java.util.List;

@RestController
@RequestMapping("/cliente")
public class clienteController {

    @Autowired
    private clienteRepository repository;
    private clienteService service;

    public clienteController(clienteRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/addCliente")
    public String addCliente(@RequestBody clienteModel model){
        service.cadastrarCliente(model);
        return "Cliente Salvo";
    }

    @DeleteMapping("{clienteID}")
    public void deletCliente(@PathVariable Long clienteID){
        repository.deleteById(clienteID);
    }

    @PutMapping("/{clienteID}")
    public clienteModel PutModel(@PathVariable Long clienteID, @RequestBody clienteModel DetailModel){
        clienteModel model = repository.findById(clienteID).orElseThrow(() -> new RuntimeException("Cliente Não foi Encontrado"));
        model.setClientePassword(DetailModel.getClientePassword());
        model.setClienteUsername(DetailModel.getClienteUsername());
        model.setEmailCliente(DetailModel.getEmailCliente());
        model.setRedeSocialCliente(DetailModel.getRedeSocialCliente());
        model.setTelefoneCliente(DetailModel.getTelefoneCliente());

        return service.cadastrarCliente(model);
    }

    @GetMapping
    public List<clienteModel> GetAllModels(){
        return (List<clienteModel>) repository.findAll();
    }

    @GetMapping("/{clienteID}")
    public clienteModel GetModelByID(@PathVariable Long clienteID){
        return repository.findById(clienteID).orElseThrow(() -> new RuntimeException("Cliente Não foi Encontrado"));
    }
}
