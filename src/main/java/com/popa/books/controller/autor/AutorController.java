package com.popa.books.controller.autor;

import com.popa.books.model.Autor;
import com.popa.books.model.node.AutorNode;
import com.popa.books.model.node.Node;
import com.popa.books.model.node.AutorNodeSQL;
import com.popa.books.repository.AutorRepository;
import com.popa.books.repository.BookRepository;
import com.popa.books.util.RequestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
                                    @RequestParam Boolean root,
                                    @RequestParam String displayMode){
        return loadAutori(nodeId, root, displayMode);
    }

    private List<Node> loadAutori(String nodeId, Boolean root, String displayMode) {
        final boolean isRoot = root != null && Boolean.valueOf(root);
        final boolean isFlatMode = "flat".equals(displayMode);
        if (isRoot || StringUtils.isEmpty(nodeId)) {
            if (isFlatMode) {
                //authors and
                return getTreeDataForFlatMode();
            } else {
                //letter mode
                return getTreeDataLetters();
            }
        } else {
            return getAuthorsForALetter(nodeId);
        }
    }

    private List<Node> getAuthorsForALetter(final String letter) {
        List<Node> autori =  new ArrayList<>();
//TODO
        return autori;
    }

    private List<Node> getTreeDataLetters() {
        List<Node> autori =  new ArrayList<>();
        List<Object[]> autorNodeSQLs = repository.findAuthorsAndBooksAndGroupByLetter();
        for (Object[] autorNodeSQL : autorNodeSQLs) {
            AutorNode bean = new AutorNode();
            bean.setLeaf(false);
            bean.setLoaded(false);
            bean.setHowManyBooks(Long.valueOf(autorNodeSQL[1].toString()));
            bean.setHowManyAutors(Long.valueOf(autorNodeSQL[2].toString()));
            bean.setName(autorNodeSQL[0].toString());
            bean.setId(autorNodeSQL[0].toString());
            autori.add(bean);
        }
        return autori;
    }

    private List<Node> getTreeDataForFlatMode(){
        List<Node> autori =  new ArrayList<>();

        // we count books without authors first
        Long booksWithNoAuthor = bookRepository.countByAuthorIsNull();
        if (booksWithNoAuthor > 0) {
            AutorNode bean = new AutorNode();
            bean.setLeaf(true);
            bean.setLoaded(true);
            bean.setHowManyBooks(booksWithNoAuthor);
            bean.setName("Fara autor");
            bean.setId("Fara autor");
            autori.add(bean);
        }

        List<AutorNodeSQL> autorNodeSQLs = repository.findAutorsAndBookCountUsingHQLAndNodeSQL();

        for (AutorNodeSQL autorNodeSQL : autorNodeSQLs) {
            AutorNode bean = new AutorNode();
            bean.setLeaf(true);
            bean.setLoaded(true);
            bean.setHowManyBooks(autorNodeSQL.getHowManyBooks());
            bean.setName(autorNodeSQL.getAuthorName());
            bean.setId(autorNodeSQL.getAuthorName());
            autori.add(bean);
        }
        return autori;
    }

    @RequestMapping(method = RequestMethod.POST)
    public void createNewAutor(@RequestParam String nume,
                               @RequestParam String dataNasterii){
        Autor autor = new Autor();
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
