package com.popa.books.controller.book;


import com.google.gson.JsonObject;
import com.popa.books.repository.BookCoverRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @RequestMapping(method= RequestMethod.GET)
    public @ResponseBody String provideUploadInfo() {
        return null;
    }

    @RequestMapping(method=RequestMethod.POST)
    public String handleFileUpload(@RequestParam("bookId") Long bookId,
                                                 @RequestParam("isFrontCover") boolean isFrontCover,
                                                 @RequestParam("frontCoverUpload") MultipartFile imageData){
        if (!imageData.isEmpty()) {
            try {
                byte[] bytes = imageData.getBytes();

                File file = new File("/usr/local/logs/covers" + File.separator + bookId + (isFrontCover? "front":"")+ ".jpg");
                file.deleteOnExit();
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(file));
                stream.write(bytes);
                stream.close();

                JsonObject response = new JsonObject();
                response.addProperty("success", true);
                response.addProperty("fileName", file.getName() + "?time=" + new Date());
                return response.toString();

            } catch (Exception exc) {
                logger.error(exc, exc);
            }
        }
       return "";
    }
}
