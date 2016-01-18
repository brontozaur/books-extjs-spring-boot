package com.popa.books.servlet.handler;

import java.io.File;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.popa.books.servlet.util.RequestUtils;
import org.apache.log4j.Logger;

public class DeleteUploadEventHandler extends EventHandler {

    private static final Logger logger = Logger.getLogger(SaveAutorEventHandler.class);

    @Override
    public String handleEvent(HttpServletRequest request) throws ServletException {
        try {
            final String bookId = request.getParameter("bookId");
            final boolean isFrontCover = Boolean.valueOf(request.getParameter("isFrontCover"));
            final String fileName = bookId + (isFrontCover ? "front.jpg" : ".jpg");
            File serverFile = new File(RequestUtils.getImagePath(fileName));
            if (serverFile.exists() && serverFile.isFile()) {
                if (!serverFile.delete()) {
                    throw new ServletException("Cannot delete the file: " + serverFile.getName());
                }
                return null;
            }
            throw new ServletException("No upload defined yet!");
        } catch (Exception exc) {
            logger.error(exc, exc);
            throw new ServletException(exc);
        }
    }
}
