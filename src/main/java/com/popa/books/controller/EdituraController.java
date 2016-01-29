package com.popa.books.controller;

import com.popa.books.model.Editura;
import com.popa.books.model.node.EdituraNode;
import com.popa.books.model.node.Node;
import com.popa.books.model.node.NodeSQL;
import com.popa.books.repository.BookRepository;
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

    @Autowired
    private BookRepository bookRepository;

    @RequestMapping(method = RequestMethod.GET)
    public List<Editura> getAllEdituri(){
            List<Editura> edituri =  new ArrayList<>();
            Iterable<Editura> it = repository.findAll();
            for (Editura editura: it) {
                edituri.add(editura);
            }
            return edituri;
    }

    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    public List<Node> getEdituriTree(){

        List<Node> edituri =  new ArrayList<>();

        Long booksWithNoEditura = bookRepository.countByEdituraIsNull();
        if (booksWithNoEditura > 0) {
            EdituraNode bean = new EdituraNode();
            bean.setLeaf(true);
            bean.setLoaded(true);
            bean.setHowManyBooks(booksWithNoEditura);
            bean.setName(Node.NOT_AVAILABLE);
            bean.setId(Node.NOT_AVAILABLE);
            edituri.add(bean);
        }

        List<NodeSQL> nodeSQLs = repository.findEdituriAndBookCount();

        for (NodeSQL nodeSQL : nodeSQLs) {
            EdituraNode bean = new EdituraNode();
            bean.setLeaf(true);
            bean.setLoaded(true);
            bean.setHowManyBooks(nodeSQL.getCount());
            bean.setName(nodeSQL.getName());
            bean.setId(nodeSQL.getName());
            edituri.add(bean);
        }

        return edituri;
    }

    @RequestMapping(method = RequestMethod.POST)
    public void createNewEditura(@RequestParam String title){
        Editura editura = new Editura();
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
