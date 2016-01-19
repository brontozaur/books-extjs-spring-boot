package com.popa.books.controller.categorie;

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
    CategorieRepository repository;

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
    public void createNewCategorie(@RequestParam String title){
        Categorie categorie = new Categorie();
        categorie.setIdCategorie(0);
        categorie.setNumeCategorie(title);
        repository.save(categorie);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void updateCategorie(@PathVariable Long id, @RequestParam String title){
        Categorie categorie = repository.findOne(id);
        categorie.setNumeCategorie(title);
        repository.save(categorie);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteCategorie(@PathVariable Long id){
        repository.delete(id);
    }
}
