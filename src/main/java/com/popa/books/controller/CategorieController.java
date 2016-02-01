package com.popa.books.controller;

import com.popa.books.model.Categorie;
import com.popa.books.repository.CategorieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/categorie")
public class CategorieController {


    @Autowired
    private CategorieRepository repository;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Categorie getCategorie(@PathVariable Long id){
        return repository.findOne(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Categorie> getAllCategorii(){
        List<Categorie> categorii =  new ArrayList<>();
        Iterable<Categorie> it = repository.findAll();
        for (Categorie Categorie: it) {
            categorii.add(Categorie);
        }
        return categorii;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Categorie createNewCategorie(@RequestBody Categorie categorie){
        return repository.save(categorie);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Categorie updateCategorie(@RequestBody Categorie categorie){
        return repository.save(categorie);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteCategorie(@PathVariable Long id){
        repository.delete(id);
    }
}
