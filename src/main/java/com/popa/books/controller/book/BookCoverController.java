package com.popa.books.controller.book;


import com.google.gson.JsonObject;
import com.popa.books.config.BooksApplicationProperties;
import com.popa.books.model.BookCover;
import com.popa.books.repository.BookCoverRepository;
import com.popa.books.repository.BookRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import java.io.*;
import java.util.Date;

@RestController
@RequestMapping("/cover")
public class BookCoverController {

    private static final Logger logger = Logger.getLogger(BookCoverController.class);

    @Autowired
    BookCoverRepository repository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    BooksApplicationProperties props;

    /**
     * This method looks for a book cover record in the database for the current book.
     * If found, back and front fields will be tested to see if they contain real images.
     * If they do, a test is done to see if those images are not already exported. If they do,
     * their path is returned on the response. Otherwise, byte[] data is being serialized and
     * after that the serialized path is returned. The server path is mapped to a relative
     * application path, therefore...it works ;-)
     * @param bookId
     * @return
     * @throws ServletException
     */
    @RequestMapping(method=RequestMethod.GET)
    public String getCovers(@RequestParam("bookId") Long bookId) throws ServletException {

        JsonObject response = new JsonObject();
        try {
            BookCover cover = repository.findByBook(bookRepository.findOne(bookId));
            if (cover != null) {
                byte[] frontCoverBytes = cover.getFront();
                if (frontCoverBytes != null && frontCoverBytes.length > 0) {
                    String desiredFrontCoverName = bookId + "_front" + "_exported.jpg";
                    File testFile = new File(props.getUploadDir() + File.separator + desiredFrontCoverName);
                    if (!testFile.exists() || !testFile.isFile()) {
                        writeFileToDisk(testFile.getAbsolutePath(), frontCoverBytes);
                    }
                    response.addProperty("frontCoverPath", props.getCoversUploadPath() + desiredFrontCoverName + "?time=" + new Date());
                }
                byte[] backCoverBytes = cover.getBack();
                if (backCoverBytes != null && backCoverBytes.length > 0) {
                    String desiredBackCoverName = bookId + "_back" + "_exported.jpg";
                    File testFile = new File(props.getUploadDir() + File.separator + desiredBackCoverName);
                    if (!testFile.exists() || !testFile.isFile()) {
                        writeFileToDisk(testFile.getAbsolutePath(), backCoverBytes);
                    }
                    response.addProperty("backCoverPath", props.getCoversUploadPath() + desiredBackCoverName + "?time=" + new Date());
                }
            }
        } catch (Exception exc) {
            logger.error(exc, exc);
            throw new ServletException(exc.getMessage(), exc);
        }

        response.addProperty("success", true);
        return response.toString();
    }

    @RequestMapping(value = "/front", method=RequestMethod.POST)
    public String uploadFrontCover(@RequestParam("bookId") Long bookId,
                                   @RequestParam("frontCoverUpload") MultipartFile imageData) throws ServletException {
        return handleFileUpload(imageData, bookId, true);
    }

    @RequestMapping(method=RequestMethod.DELETE)
    public void deleteFrontCover(@RequestParam("imageName") String imageName) throws ServletException {
        if (StringUtils.isEmpty(imageName)) {
            throw new ServletException("No upload to delete!");
        }
        //de forma /covers/1front_whatever.jpg?time=Sat%20Jan%2023%2001:05:44%20EET%202016
        String image = imageName.substring(imageName.indexOf(props.getCoversUploadPath())+ props.getCoversUploadPath().length());
        image = image.substring(0, image.lastIndexOf('?'));
        File file = new File(props.getUploadDir() + image);
        if (!file.exists() || !file.isFile()) {
            throw new ServletException("File '" + imageName + "' does not exist on '" +
                    props.getUploadDir() + "' location.");
        }
        if (!file.delete()) {
            throw new ServletException("Could not delete the file at '" + file.getPath());
        }
    }

    @RequestMapping(value = "/back", method=RequestMethod.POST)
    public String uploadBackCover(@RequestParam("bookId") Long bookId,
                                   @RequestParam("backCoverUpload") MultipartFile imageData) throws ServletException {
        return handleFileUpload(imageData, bookId, false);
    }

    private String handleFileUpload(MultipartFile imageData, Long bookId, boolean isFrontCover) throws ServletException {
        if (!imageData.isEmpty()) {
            try {
                byte[] bytes = imageData.getBytes();

                final String exportFileName = props.getUploadDir() + File.separator + bookId + (isFrontCover? "_front":"_back")+ ".jpg";
                final String fileName = writeFileToDisk(exportFileName, bytes);

                JsonObject response = new JsonObject();
                response.addProperty("success", true);
                response.addProperty("fileName", props.getCoversUploadPath() + fileName + "?time=" + new Date());
                return response.toString();

            } catch (Exception exc) {
                logger.error(exc, exc);
                throw new ServletException(exc.getMessage(), exc);
            }
        }
        return "";
    }

    private String writeFileToDisk(String fileName, byte[] bytes) throws IOException {
        File file = new File(fileName);
        file.deleteOnExit();
        BufferedOutputStream stream =
                new BufferedOutputStream(new FileOutputStream(file));
        stream.write(bytes);
        stream.close();
        return file.getName();
    }
}
