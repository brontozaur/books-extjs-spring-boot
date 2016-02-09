package com.popa.books.controller;

import com.popa.books.model.Autor;
import com.popa.books.model.node.AutorNode;
import com.popa.books.model.node.Node;
import com.popa.books.model.node.NodeSQL;
import com.popa.books.repository.AutorRepository;
import com.popa.books.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/autor")
public class AutorController {

    @Autowired
    private AutorRepository repository;

    @Autowired
    private BookRepository bookRepository;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Autor getAutor(@PathVariable Long id) {
        return repository.findOne(id);
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Page<Autor> getAutori(@RequestParam(value = "page") Integer currentPage,
                                 @RequestParam(value = "limit") Integer pageSize) {
        Pageable pageable = new PageRequest(currentPage - 1, pageSize);
        return repository.findAll(pageable);
    }

    @RequestMapping(value = "/tree", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Node> getAutoriTree() {
        List<Node> autori = new ArrayList<>();

        // we count books without authors first
        Long booksWithNoAuthor = bookRepository.countByAuthorIsNull();
        if (booksWithNoAuthor > 0) {
            AutorNode bean = new AutorNode();
            bean.setLeaf(true);
            bean.setLoaded(true);
            bean.setHowManyBooks(booksWithNoAuthor);
            bean.setName(Node.NOT_AVAILABLE);
            bean.setId(Node.NOT_AVAILABLE_ID);
            autori.add(bean);
        }

        List<NodeSQL> nodeSQLs = repository.findAutorsAndBookCountUsingHQLAndNodeSQL();

        for (NodeSQL nodeSQL : nodeSQLs) {
            AutorNode bean = new AutorNode();
            bean.setLeaf(true);
            bean.setLoaded(true);
            bean.setHowManyBooks(nodeSQL.getCount());
            bean.setName(nodeSQL.getName());
            bean.setId(nodeSQL.getId());
            autori.add(bean);
        }
        return autori;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Autor createNewAutor(@RequestBody Autor autor) {
        return repository.save(autor);
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Autor updateAutor(@RequestBody Autor autor) throws ServletException {
        Autor dbAutor = repository.findOne(autor.getAutorId());
        if (dbAutor == null) {
            throw new ServletException("There is no autor record with id = " + autor.getAutorId());
        }
        return repository.saveAndFlush(autor);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void deleteAutor(@PathVariable Long id) throws ServletException {
        Autor dbAutor = repository.findOne(id);
        if (dbAutor == null) {
            throw new ServletException("There is no autor record with id = " + id);
        }
        repository.delete(id);
    }
}
