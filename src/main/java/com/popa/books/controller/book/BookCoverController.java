package com.popa.books.controller.book;


import com.google.gson.JsonObject;
import com.popa.books.config.BooksApplicationProperties;
import com.popa.books.repository.BookCoverRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

@RestController
@RequestMapping("/cover")
public class BookCoverController {

    private static final Logger logger = Logger.getLogger(BookCoverController.class);

    @Autowired
    BookCoverRepository repository;

    @Autowired
    BooksApplicationProperties props;

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

                File file = new File(props.getUploadDir() + File.separator + bookId + (isFrontCover? "front_":"back_")+ imageData.getOriginalFilename());
                file.deleteOnExit();
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(file));
                stream.write(bytes);
                stream.close();

                JsonObject response = new JsonObject();
                response.addProperty("success", true);
                response.addProperty("fileName", props.getCoversUploadPath() + file.getName() + "?time=" + new Date());
                return response.toString();

            } catch (Exception exc) {
                logger.error(exc, exc);
                throw new ServletException(exc.getMessage(), exc);
            }
        }
        return "";
    }
}
