package com.popa.books.controller.autor;

import com.popa.books.model.Autor;
import com.popa.books.model.node.AutorNode;
import com.popa.books.model.node.Node;
import com.popa.books.repository.AutorRepository;
import com.popa.books.util.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/autor")
public class AutorController {

    @Autowired
    private AutorRepository repository;

    @RequestMapping(method = RequestMethod.GET)
    public List<Autor> getAllAutori(){
        List<Autor> autori =  new ArrayList<>();
        Iterable<Autor> it = repository.findAll();
        for (Autor Autor: it) {
            autori.add(Autor);
        }
        return autori;
    }

    @RequestMapping(value = "/tree-load", method = RequestMethod.GET)
    public List<Node> getAutoriTree(){
        return loadAutori(null, null, null);
    }

    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    public List<Node> getAutoriTree(@RequestParam String nodeId,
                                    @RequestParam String root,
                                    @RequestParam String displayMode){
        return loadAutori(nodeId, root, displayMode);
    }

    private List<Node> loadAutori(String nodeId, String root, String displayMode) {
        List<Node> autori =  new ArrayList<>();
        AutorNode node = new AutorNode();
        node.setHowManyAutors(2);
        node.setHowManyBooks(1);
        node.setLeaf(true);
        node.setName("Autor node");
        autori.add(node);
        return autori;
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
