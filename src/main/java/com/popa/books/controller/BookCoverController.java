package com.popa.books.controller;


import com.google.gson.JsonObject;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import java.io.*;

@RestController
@RequestMapping("/cover")
public class BookCoverController {

    @RequestMapping(value = "/front", method = RequestMethod.POST)
    public String uploadFrontCover(@RequestParam("frontCoverUpload") MultipartFile imageData) throws ServletException, IOException {
        JsonObject response = new JsonObject();
        response.addProperty("success", true);
        response.addProperty("imageData", new String(Base64Utils.encode(imageData.getBytes())));
        return response.toString();
    }

    @RequestMapping(value = "/back", method = RequestMethod.POST)
    public String uploadBackCoverImage(@RequestParam("backCoverUpload") MultipartFile imageData) throws ServletException, IOException {
        JsonObject response = new JsonObject();
        response.addProperty("success", true);
        response.addProperty("imageData", new String(Base64Utils.encode(imageData.getBytes())));
        return response.toString();
    }
}
