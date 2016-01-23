package com.popa.books.controller.book;

import com.popa.books.config.BooksApplicationProperties;
import com.popa.books.model.Book;
import com.popa.books.model.BookCover;
import com.popa.books.repository.*;
import com.popa.books.util.RequestUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.transaction.Transactional;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    private static final Logger logger = Logger.getLogger(BookController.class);

    @Autowired
    BookRepository repository;

    @Autowired
    AutorRepository autorRepository;

    @Autowired
    CategorieRepository categorieRepository;

    @Autowired
    EdituraRepository edituraRepository;

    @Autowired
    BookCoverRepository bookCoverRepository;

    @Autowired
    BooksApplicationProperties props;

    @RequestMapping(method = RequestMethod.GET)
    public List<Book> getAllBooks(){
        return (List<Book>) repository.findAll();
    }

    @Transactional
    @RequestMapping(method = RequestMethod.POST)
    public void createNewBook(@RequestBody BookDTO dto) throws ServletException {
        Book book = new Book();
        book.setBookId(0L);
        convertDtoToBook(book, dto);
        book = repository.saveAndFlush(book);

       saveBookCovers(book);
    }

    @Transactional
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void updateBook(@PathVariable Long id, @RequestBody BookDTO dto) throws ServletException {
        Book book = repository.findOne(id);
        convertDtoToBook(book, dto);
        book = repository.saveAndFlush(book);

        saveBookCovers(book);
    }

    private File getNewUpload(final Long bookId, final boolean isFrontCover) {
        final String exportFileName = props.getUploadDir() + File.separator + bookId + (isFrontCover? "_front":"_back")+ ".jpg";
        File uploadedFile = new File(exportFileName);
        if (uploadedFile.exists() && uploadedFile.isFile()) {
            return uploadedFile;
        }
        return null;
    }

    @Transactional
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteBook(@PathVariable Long id){
        repository.delete(id);

        //delete cover is necessary?
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

    private byte[] loadFile(final File file) throws IOException {
        byte[] bFile = new byte[(int) file.length()];
        BufferedInputStream fileInputStream = new BufferedInputStream(new FileInputStream(file));
        //convert file into array of bytes
        fileInputStream.read(bFile);
        fileInputStream.close();

        return bFile;
    }

    private void saveBookCovers(final Book book) throws ServletException {
        try {
            final File newFrontCoverUpload = getNewUpload(book.getBookId(), true);
            final File newBackCoverUpload = getNewUpload(book.getBookId(), false);
            if (newBackCoverUpload == null && newFrontCoverUpload == null ) { //save cover is not needed
                return;
            }
            BookCover bookCover = new BookCover();
            bookCover.setBook(book);
            if (newFrontCoverUpload != null) {
                bookCover.setFront(loadFile(newFrontCoverUpload));
            }
            if (newBackCoverUpload != null) {
                bookCover.setBack(loadFile(newBackCoverUpload));
            }
            bookCoverRepository.save(bookCover);
        } catch (Exception exc) {
            logger.error(exc, exc);
            throw new ServletException(exc.getMessage(), exc);
        }
    }

}
