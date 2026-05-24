package com.ads.restaurante.Controller;

import com.ads.restaurante.Repository.pratoRepository;
import com.ads.restaurante.Model.pratoModel;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@RestController
@RequestMapping("/prato")
public class pratoController {

    @Autowired
    private pratoRepository repository;

    @PostMapping("/addPrato")
    public String addprato(@RequestBody pratoModel model){
        repository.save(model);
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

        return repository.save(model);
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
