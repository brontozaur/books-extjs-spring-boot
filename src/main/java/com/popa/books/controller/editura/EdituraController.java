package com.popa.books.controller.editura;

import com.popa.books.controller.Events;
import com.popa.books.model.Editura;
import com.popa.books.repository.EdituraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/editura")
public class EdituraController {

    @Autowired
    EdituraRepository repository;

    @RequestMapping("loadAll")
    public List<Editura> getAllBooks(){
            List<Editura> edituri =  new ArrayList<>();
            Iterable<Editura> it = repository.findAll();
            for (Editura editura: it) {
                edituri.add(editura);
            }
            return edituri;
    }
}
