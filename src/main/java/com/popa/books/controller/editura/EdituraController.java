package com.popa.books.controller.editura;

import com.popa.books.model.Editura;
import com.popa.books.model.node.Node;
import com.popa.books.repository.EdituraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/editura")
public class EdituraController {

    @Autowired
    private EdituraRepository repository;

    @RequestMapping(method = RequestMethod.GET)
    public List<Editura> getAllEdituri(){
            List<Editura> edituri =  new ArrayList<>();
            Iterable<Editura> it = repository.findAll();
            for (Editura editura: it) {
                edituri.add(editura);
            }
            return edituri;
    }

    @RequestMapping(value = "/tree-load", method = RequestMethod.GET)
    public List<Node> getEdituriTree(){
        return new ArrayList<>();
    }

    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    public List<Node> getEdituriTree(@RequestParam String nodeId,
                                    @RequestParam String root,
                                    @RequestParam String displayMode){
        return new ArrayList<>();
    }

    @RequestMapping(method = RequestMethod.POST)
    public void createNewEditura(@RequestParam String title){
        Editura editura = new Editura();
        editura.setIdEditura(0L);
        editura.setNumeEditura(title);
        repository.save(editura);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void updateEditura(@PathVariable Long id, @RequestParam String title){
        Editura editura = repository.findOne(id);
        editura.setNumeEditura(title);
        repository.save(editura);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteEditura(@PathVariable Long id){
        repository.delete(id);
    }

}
