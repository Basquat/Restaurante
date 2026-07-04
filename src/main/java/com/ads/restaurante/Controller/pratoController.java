package com.ads.restaurante.Controller;

import com.ads.restaurante.Repository.pratoRepository;
import com.ads.restaurante.Model.pratoModel;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import com.ads.restaurante.Service.*;

@RestController
@RequestMapping("/prato")
public class pratoController {

    @Autowired
    private pratoRepository repository;
    private pratoService service;

    @PostMapping("/addPrato")
    public String addprato(@RequestBody pratoModel model){
        service.cadastrarPrato(model);
        return "Prato Salvo";
    }

    @DeleteMapping("{pratoID}")
    public void deletePrato(@PathVariable Long pratoID){
        repository.deleteById(pratoID);
    }

    @PutMapping("/{pratoID}")
    public pratoModel PutModel(@PathVariable Long pratoID, @RequestBody pratoModel DetailModel){
        pratoModel model = repository.findById(pratoID).orElseThrow(() -> new RuntimeException("Prato não encontrado"));
        model.setPratoNome(DetailModel.getPratoNome());
        model.setPratoSaindo(DetailModel.getPratoSaindo());
        model.setPratoValor(DetailModel.getPratoValor());

        return service.cadastrarPrato(model);
    }

    @GetMapping
    public List<pratoModel> GetAllModels(){
        return (List<pratoModel>) repository.findAll();
    }

    @GetMapping("/{pratoID}")
    public pratoModel GetAllModelByID(@PathVariable Long pratoID){
        return repository.findById(pratoID).orElseThrow(() -> new RuntimeException("Prato não encontrado"));
    }
}
