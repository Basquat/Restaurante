package com.ads.restaurante.Controller;

import com.ads.restaurante.Model.managerModel;
import com.ads.restaurante.Repository.managerRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import com.ads.restaurante.Service.*;

@RestController
@RequestMapping("/managerLogin")
@CrossOrigin(origins = "*")
public class managerController {

    @Autowired
    private managerRepository repository;
    private managerService service;

    @PostMapping("/addManager")
    public String addManager(@RequestBody managerModel model){
        service.cadastrarManager(model);
        return "manager salvo";
    }

    @DeleteMapping("{managerID}")
    public void deleteManager(@PathVariable Long managerID){
        repository.deleteById(managerID);
    }

    @PutMapping("/{managerID}")
    public managerModel putModel(@PathVariable Long managerID, @RequestBody managerModel detailModel){
        managerModel model = repository.findById(managerID).orElseThrow(() -> new RuntimeException("Manager não foi encontrado"));
        model.setManagerCPF(detailModel.getManagerCPF());
        model.setManagerPassword(detailModel.getManagerPassword());
        model.setManagerUsername(detailModel.getManagerUsername());

        return service.cadastrarManager(model);
    }

    @GetMapping
    public List<managerModel> getAllModels(){
        return (List<managerModel>) repository.findAll();
    }

    @GetMapping("/{managerID}")
    public managerModel getModelByID(@PathVariable Long managerID){
        return repository.findById(managerID).orElseThrow(() -> new RuntimeException("Manager não foi encontrado"));
    }
}
