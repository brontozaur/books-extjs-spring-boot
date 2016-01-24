package com.popa.books.controller.autor;

import com.popa.books.model.Autor;
import com.popa.books.repository.AutorRepository;
import com.popa.books.util.RequestUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/autor")
public class AutorController {

    private static final Logger logger = Logger.getLogger(AutorController.class);

    @Autowired
    private AutorRepository repository;

    @RequestMapping(method = RequestMethod.GET)
    public List<Autor> getAllAutori(){
        List<Autor> categorii =  new ArrayList<>();
        Iterable<Autor> it = repository.findAll();
        for (Autor Autor: it) {
            categorii.add(Autor);
        }
        return categorii;
    }

    @RequestMapping(method = RequestMethod.POST)
    public void createNewAutor(@RequestParam String nume,
                               @RequestParam String dataNasterii){
        Autor autor = new Autor();
        autor.setAutorId(0);
        autor.setDataNasterii(RequestUtils.parseDate(dataNasterii));
        autor.setNume(nume);
        repository.save(autor);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void updateAutor(@PathVariable Long id,
                            @RequestParam String nume,
                            @RequestParam String dataNasterii){
        Autor autor = repository.findOne(id);
        autor.setNume(nume);
        autor.setDataNasterii(RequestUtils.parseDate(dataNasterii));
        repository.save(autor);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteAutor(@PathVariable Long id){
        repository.delete(id);
    }
}
