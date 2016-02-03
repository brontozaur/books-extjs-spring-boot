package com.popa.books.controller;

import com.popa.books.model.Autor;
import com.popa.books.model.api.AutorListWrapper;
import com.popa.books.model.node.AutorNode;
import com.popa.books.model.node.Node;
import com.popa.books.model.node.NodeSQL;
import com.popa.books.repository.AutorRepository;
import com.popa.books.repository.BookRepository;
import com.popa.books.util.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/autor")
public class AutorController {

    @Autowired
    private AutorRepository repository;

    @Autowired
    private BookRepository bookRepository;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Autor getAutor(@PathVariable Long id){
        return repository.findOne(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    public AutorListWrapper getAutori(@RequestParam(value = "page") Integer currentPage,
                                      @RequestParam(value = "limit") Integer pageSize) {
        Pageable pageable = new PageRequest(currentPage-1, pageSize);
        Page<Autor> autorList = repository.findAll(pageable);
        return new AutorListWrapper(autorList.getTotalElements(), autorList.getContent());
    }

    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    public List<Node> getAutoriTree(){
        List<Node> autori =  new ArrayList<>();

        // we count books without authors first
        Long booksWithNoAuthor = bookRepository.countByAuthorIsNull();
        if (booksWithNoAuthor > 0) {
            AutorNode bean = new AutorNode();
            bean.setLeaf(true);
            bean.setLoaded(true);
            bean.setHowManyBooks(booksWithNoAuthor);
            bean.setName(Node.NOT_AVAILABLE);
            bean.setId(Node.NOT_AVAILABLE);
            autori.add(bean);
        }

        List<NodeSQL> nodeSQLs = repository.findAutorsAndBookCountUsingHQLAndNodeSQL();

        for (NodeSQL nodeSQL : nodeSQLs) {
            AutorNode bean = new AutorNode();
            bean.setLeaf(true);
            bean.setLoaded(true);
            bean.setHowManyBooks(nodeSQL.getCount());
            bean.setName(nodeSQL.getName());
            bean.setId(nodeSQL.getName());
            autori.add(bean);
        }
        return autori;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Autor createNewAutor(@RequestBody Autor autor){
        return repository.save(autor);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Autor updateAutor(@RequestBody Autor autor){
        return repository.save(autor);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteAutor(@PathVariable Long id){
        repository.delete(id);
    }
}
