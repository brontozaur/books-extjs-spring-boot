package com.popa.books.controller.book;

import com.popa.books.model.Book;
import com.popa.books.repository.AutorRepository;
import com.popa.books.repository.BookRepository;
import com.popa.books.repository.CategorieRepository;
import com.popa.books.repository.EdituraRepository;
import com.popa.books.util.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {


    @Autowired
    BookRepository repository;

    @Autowired
    AutorRepository autorRepository;

    @Autowired
    CategorieRepository categorieRepository;

    @Autowired
    EdituraRepository edituraRepository;

    @RequestMapping(method = RequestMethod.GET)
    public List<Book> getAllBooks(){
        return (List<Book>) repository.findAll();
    }

    @Transactional
    @RequestMapping(method = RequestMethod.POST)
    public void createNewBook(@RequestBody BookDTO dto){
        Book book = new Book();
        book.setBookId(0L);
        convertDtoToBook(book, dto);
        repository.save(book);

        //TODO front/back cover save
    }

    @Transactional
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void updateBook(@PathVariable Long id, @RequestParam BookDTO dto){
        Book book = repository.findOne(id);
        convertDtoToBook(book, dto);
        repository.save(book);

        //TODO front/back cover save
    }

    @Transactional
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteBook(@PathVariable Long id){
        repository.delete(id);

        //TODO delete front/back covers
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
        book.setCitita(dto.getCitita());
        book.setDataAparitie(RequestUtils.parseDate(dto.getDataAparitie()));
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
    }

}
