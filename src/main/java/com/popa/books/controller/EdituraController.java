package com.popa.books.controller;

import com.popa.books.model.Editura;
import com.popa.books.model.api.EdituraListWrapper;
import com.popa.books.model.node.EdituraNode;
import com.popa.books.model.node.Node;
import com.popa.books.model.node.NodeSQL;
import com.popa.books.repository.BookRepository;
import com.popa.books.repository.EdituraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Editura getEditura(@PathVariable Long id){
        return repository.findOne(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    public EdituraListWrapper getEdituri(@RequestParam Integer start, @RequestParam Integer limit){
        Pageable pageable = new PageRequest(start, limit);
        Page<Editura> edituriList = repository.findAll(pageable);
        return new EdituraListWrapper(edituriList.getTotalElements(), edituriList.getContent());
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
    public Editura createNewEditura(@RequestBody Editura editura){
       return repository.save(editura);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Editura updateEditura(@RequestBody Editura editura){
        return repository.save(editura);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteEditura(@PathVariable Long id){
        repository.delete(id);
    }

}
