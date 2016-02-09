package com.popa.books.controller;

import com.popa.books.config.BooksApplicationProperties;
import com.popa.books.model.Book;
import com.popa.books.model.BookCover;
import com.popa.books.model.api.BookDTO;
import com.popa.books.model.node.BookNode;
import com.popa.books.model.node.Node;
import com.popa.books.repository.AutorRepository;
import com.popa.books.repository.BookRepository;
import com.popa.books.repository.CategorieRepository;
import com.popa.books.repository.EdituraRepository;
import com.popa.books.util.RequestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    public final static String BASE64_PREFIX = "data:image/jpeg;base64,";

    public static final String SEARCH_TYPE_BOOKS_TREE = "treeBooks";
    public static final String SEARCH_TYPE_AUTORI_TREE = "treeAutori";
    public static final String SEARCH_TYPE_EDITURI_TREE = "treeEdituri";
    public static final String SEARCH_TYPE_GRID = "grid";

    @Autowired
    private BookRepository repository;

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private CategorieRepository categorieRepository;

    @Autowired
    private EdituraRepository edituraRepository;

    @Autowired
    private BooksApplicationProperties props;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Book getBook(@PathVariable Long id) {
        return repository.findOne(id);
    }

    /**
     * @param currentPage current page, starting with 1
     * @param pageSize number of records per page
     * @param filterValue: first letter of the book, autor id, editura id or root name (ignored in this case
     * @param searchType: SEARCH_TYPE_AUTORI_TREE, SEARCH_TYPE_BOOKS_TREE, SEARCH_TYPE_EDITURI_TREE, SEARCH_TYPE_GRID
     * @return
     * @throws ServletException
     */
    @RequestMapping(method = RequestMethod.GET)
    public Page<Book> getBooks(@RequestParam(value = "page") Integer currentPage,
                               @RequestParam(value = "limit") Integer pageSize,
                               @RequestParam(value = "filterValue") Object filterValue,
                               @RequestParam(value = "searchType") String searchType) throws ServletException {
        Pageable pageable = new PageRequest(currentPage - 1, pageSize);
        if (SEARCH_TYPE_GRID.equals(searchType)) {
            return repository.findAll(pageable);
        } else if (SEARCH_TYPE_BOOKS_TREE.equals(searchType)) {
            if (StringUtils.isEmpty(filterValue.toString())) { //books with no title
                return repository.findByTitleIsNull(pageable);
            }
            return repository.findByTitleStartingWith(pageable, filterValue.toString());
        } else if (SEARCH_TYPE_AUTORI_TREE.equals(searchType)) {
            Long autorId = Long.valueOf(filterValue.toString());
            if (Node.NOT_AVAILABLE_ID == autorId) {
                return repository.findByAuthorIsNull(pageable);
            }
            return repository.findByAuthorAutorId(pageable, Long.valueOf(filterValue.toString()));
        } else if (SEARCH_TYPE_EDITURI_TREE.equals(searchType)) {
            Long idEditura = Long.valueOf(filterValue.toString());
            if (Node.NOT_AVAILABLE_ID == idEditura) {
                return repository.findByEdituraIsNull(pageable);
            }
            return repository.findByEdituraIdEditura(pageable, Long.valueOf(filterValue.toString()));
        }
        throw new ServletException("invalid request configuration!");
    }

    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    public List<Node> getBooksTree() {

        List<Node> books = new ArrayList<>();

        // we count books with no name filled
        Long booksWithNoTitle = repository.countByTitleIsNull();

        if (booksWithNoTitle > 0) {
            BookNode bean = new BookNode();
            bean.setLeaf(true);
            bean.setLoaded(true);
            bean.setHowManyBooks(booksWithNoTitle);
            bean.setName(Node.NOT_AVAILABLE);
            bean.setId(Node.NOT_AVAILABLE_ID);
            books.add(bean);
        }

        List<Object[]> nodeSQLs = repository.findBooksAndCount();

        for (Object[] objects : nodeSQLs) {
            BookNode bean = new BookNode();
            bean.setLeaf(true);
            bean.setLoaded(true);
            bean.setName(objects[0].toString().toUpperCase());
            bean.setId(RandomUtils.nextLong()); //we need a random id, because "id" is used internally by extjs
            bean.setHowManyBooks(Long.valueOf(objects[1].toString()));
            books.add(bean);
        }

        return books;
    }

    @Transactional
    @RequestMapping(method = RequestMethod.POST)
    public Book createNewBook(@RequestBody BookDTO dto) throws ServletException {
        Book book = new Book();
        convertDtoToBook(book, dto);
        return repository.save(book);
    }

    //if no complex objects (e.g. Autor, Editura, etc) were in the Book object,
    //a Book could have been used instead of BookDTO directly.
    @Transactional
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Book updateBook(@PathVariable Long id, @RequestBody BookDTO dto) throws ServletException {
        Book book = repository.findOne(id);
        convertDtoToBook(book, dto);
        return repository.save(book);
    }

    //delete cover is not necessary, since is done by hibernate/jpa automatically
    @Transactional
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteBook(@PathVariable Long id) {
        repository.delete(id);
    }

    private void convertDtoToBook(Book book, BookDTO dto) {
        if (dto.getIdAutor() != null) {
            book.setAuthor(autorRepository.findOne(dto.getIdAutor()));
        } else {
            book.setAuthor(null);
        }
        if (dto.getIdCategorie() != null) {
            book.setCategorie(categorieRepository.findOne(dto.getIdCategorie()));
        } else {
            book.setCategorie(null);
        }
        book.setCitita("on".equals(dto.getCitita())
                || "true".equals(dto.getCitita())
                || "1".equals(dto.getCitita())
                || "yes".equals(dto.getCitita()));
        book.setAnAparitie(dto.getAnAparitie());
        if (dto.getIdEditura() != null) {
            book.setEditura(edituraRepository.findOne(dto.getIdEditura()));
        } else {
            book.setEditura(null);
        }
        book.setHeight(dto.getHeight());
        book.setWidth(dto.getWidth());
        book.setIsbn(dto.getIsbn());
        book.setNrPagini(dto.getNrPagini());
        book.setOriginalTitle(dto.getOriginalTitle());
        book.setSerie(dto.getSerie());
        book.setTitle(dto.getTitle());

        boolean hasFrontCover = StringUtils.isNotEmpty(dto.getFrontCoverData());
        boolean hasBackCover = StringUtils.isNotEmpty(dto.getBackCoverData());

        if (book.getBookCover() == null && (!hasFrontCover && !hasFrontCover)) { //save covers is not needed
            return;
        }
        if (book.getBookCover() == null) {
            book.setBookCover(new BookCover());
        }
        BookCover bookCover = book.getBookCover();
        if (hasFrontCover && dto.getFrontCoverData().indexOf(BASE64_PREFIX) == 0) {
            bookCover.setFront(Base64Utils.decodeFromString((dto.getFrontCoverData().substring(BASE64_PREFIX.length()))));
        } else {
            bookCover.setFront(null);
        }
        if (hasBackCover && dto.getBackCoverData().indexOf(BASE64_PREFIX) == 0) {
            bookCover.setBack(Base64Utils.decodeFromString((dto.getBackCoverData().substring(BASE64_PREFIX.length()))));
        } else {
            bookCover.setBack(null);
        }
    }
}
