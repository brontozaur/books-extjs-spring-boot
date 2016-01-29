package com.popa.books.controller;


import com.google.gson.JsonObject;
import com.popa.books.config.BooksApplicationProperties;
import com.popa.books.model.Book;
import com.popa.books.model.BookCover;
import com.popa.books.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import java.io.*;
import java.text.MessageFormat;
import java.util.Date;

@RestController
@RequestMapping("/cover")
public class BookCoverController {

    private static final Logger logger = LoggerFactory.getLogger(BookCoverController.class);

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BooksApplicationProperties props;

    /**
     * This method looks for a book cover record in the database for the current book.
     * If found, back and front fields will be tested to see if they contain real images.
     * If they do, byte[] data is being serialized and
     * after that the serialized path is returned. The server path is mapped to a relative
     * application path, therefore...it works ;-)
     * @param bookId
     * @return
     * @throws ServletException
     */
    @RequestMapping(method=RequestMethod.GET)
    public String getBookCoversPath(@RequestParam("bookId") Long bookId) throws ServletException {

        JsonObject response = new JsonObject();
        try {
            Book book = bookRepository.findOne(bookId);
            BookCover cover = book.getBookCover();
            if (cover != null) {
                byte[] frontCoverBytes = cover.getFront();
                if (frontCoverBytes != null && frontCoverBytes.length > 0) {
                    String desiredFrontCoverName = MessageFormat.format(props.getCoversFormat(),
                            new Object[]{bookId, "exported", "front"});
                    book.setFrontCoverPath(desiredFrontCoverName);
                    File testFile = new File(props.getFullUploadDir() + desiredFrontCoverName);
                    writeFileToDisk(testFile.getAbsolutePath(), frontCoverBytes);
                    response.addProperty("frontCoverPath", props.getCoversUploadPath() + desiredFrontCoverName + "?time=" + new Date());
                }
                byte[] backCoverBytes = cover.getBack();
                if (backCoverBytes != null && backCoverBytes.length > 0) {
                    String desiredBackCoverName = MessageFormat.format(props.getCoversFormat(),
                            new Object[]{bookId, "exported", "back"});
                    book.setBackCoverPath(desiredBackCoverName);
                    File testFile = new File(props.getFullUploadDir() + desiredBackCoverName);
                    writeFileToDisk(testFile.getAbsolutePath(), backCoverBytes);
                    response.addProperty("backCoverPath", props.getCoversUploadPath() + desiredBackCoverName + "?time=" + new Date());
                }
            }
        } catch (Exception exc) {
            logger.error(exc.getMessage(), exc);
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
    public void deleteFrontCoverImage(@RequestParam("imageName") String imageName) throws ServletException {
        if (StringUtils.isEmpty(imageName)) {
            throw new ServletException("No upload to delete!");
        }
        // original file: /covers/imageName.jpg?time=Sat%20Jan%2023%2001:05:44%20EET%202016
        if (imageName.indexOf('?') != -1) {
            imageName = imageName.substring(0, imageName.indexOf('?'));
        }
        //file is now: /covers/imageName.jpg
        if (imageName.indexOf(props.getCoversUploadPath()) != -1) {
            imageName = imageName.substring(imageName.indexOf(props.getCoversUploadPath())+ props.getCoversUploadPath().length());
        }
        //file is now imageName.jpg
        File file = new File(props.getFullUploadDir() + imageName);
        if (!file.exists() || !file.isFile()) {
            throw new ServletException("File '" + imageName + "' does not exist on '" +
                    props.getFullUploadDir() + "' location.");
        }
        if (!file.delete()) {
            throw new ServletException("Could not delete the file at '" + file.getPath());
        }
    }

    @RequestMapping(value = "/back", method=RequestMethod.POST)
    public String uploadBackCoverImage(@RequestParam("bookId") Long bookId,
                                   @RequestParam("backCoverUpload") MultipartFile imageData) throws ServletException {
        return handleFileUpload(imageData, bookId, false);
    }

    private String handleFileUpload(MultipartFile imageData, Long bookId, boolean isFrontCover) throws ServletException {
        if (!imageData.isEmpty()) {
            try {
                byte[] bytes = imageData.getBytes();
                String uploadName = MessageFormat.format(props.getCoversFormat(),
                        new Object[]{bookId, String.valueOf(System.currentTimeMillis()), isFrontCover ? "front":"back"});
                final String uploadPath = props.getFullUploadDir() + uploadName;
                final String fileName = writeFileToDisk(uploadPath, bytes);

                JsonObject response = new JsonObject();
                response.addProperty("success", true);
                response.addProperty("fileName", props.getCoversUploadPath() + fileName + "?time=" + new Date());
                return response.toString();

            } catch (Exception exc) {
                logger.error(exc.getMessage(), exc);
                throw new ServletException(exc.getMessage(), exc);
            }
        }
        return "";
    }

    private String writeFileToDisk(String fileName, byte[] bytes) throws IOException {
        File file = new File(fileName);
        file.deleteOnExit();
        FileOutputStream fos = new FileOutputStream(file);
        BufferedOutputStream stream = new BufferedOutputStream(fos);
        stream.write(bytes);
        fos.close();
        stream.close();
        return file.getName();
    }
}
