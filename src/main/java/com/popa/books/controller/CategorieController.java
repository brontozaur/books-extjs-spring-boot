package com.popa.books.controller;

import com.popa.books.model.Categorie;
import com.popa.books.repository.CategorieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

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
    public Page<Categorie> getCategorii(@RequestParam(value = "page") Integer currentPage,
                                             @RequestParam(value = "limit") Integer pageSize) {
        Pageable pageable = new PageRequest(currentPage-1, pageSize);
        return repository.findAll(pageable);
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
