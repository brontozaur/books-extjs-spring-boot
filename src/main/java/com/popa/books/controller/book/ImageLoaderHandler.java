package com.popa.books.controller.book;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.google.gson.JsonObject;
import com.popa.books.controller.EventHandler;
import com.popa.books.model.BookCover;
import com.popa.books.util.RequestUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;


/**
 * Image loader handler. Can do the following things:
 * - upload front and back covers;
 * - serialize on server front and back cover data.
 * In upload mode, it should not test if the image exists or not, but override it directly.
 *  Handler is accepting the following parameters:
 *      - bookId = the id of the selected book.Mandatory.;
 *      - isUpload = specifies if we need to upload a new file or just export/search for existing file. False, if not specified.
 *      - isFrontCover = specified if we handle front or back cover. False (therefore back cover is default!), if not specifed.
 *      - frontCoverUpload = data for the new upload of the front cover, as byte array. Mandatory in case of new upload.
 *      - backCoverData = data for the new upload of the back cover, as byte array. Mandatory in case of new upload.
 *
 */
public class ImageLoaderHandler extends EventHandler {

    private static final Logger logger = Logger.getLogger(ImageLoaderHandler.class);

    @Override
    public String handleEvent(HttpServletRequest request) throws ServletException {
        try {
            final boolean isUpload = Boolean.valueOf(request.getParameter("isUpload"));
            if (isUpload) {
                return uploadNewFile(request);
            }
            String fileAlreadyExist = handleFileExistOnServerCase(request);
            if (fileAlreadyExist != null) {
                return fileAlreadyExist;
            }
            return exportImageData(request);
        } catch (Exception exc) {
            logger.error(exc, exc);
            throw new ServletException(exc);
        }
    }

    private String handleFileExistOnServerCase(final HttpServletRequest request) {
        final String bookId = request.getParameter("bookId");
        final boolean isFrontCover = Boolean.valueOf(request.getParameter("isFrontCover"));
        final String fileName = bookId + (isFrontCover ? "front.jpg" : ".jpg");
        File serverFile = new File(RequestUtils.getImagePath(fileName));
        if (serverFile.exists() && serverFile.isFile()) {
            JsonObject response = new JsonObject();
            response.addProperty("success", isFrontCover);
            response.addProperty("fileName", serverFile.getName() + "?time=" + new Date());
            return response.toString();
        }
        return null;
    }

    private String uploadNewFile(final HttpServletRequest request) throws ServletException, IOException {
        final String bookId = request.getParameter("bookId");
        final boolean isFrontCover = Boolean.valueOf(request.getParameter("isFrontCover"));
        byte[] imageData;
        if (isFrontCover) {
            imageData = RequestUtils.getByteArray(request, "frontCoverUpload");
        } else {
            imageData = RequestUtils.getByteArray(request, "backCoverUpload");
        }
        File file = new File(RequestUtils.exportImageToDisk(bookId, true));
        file.deleteOnExit();
        FileOutputStream fso = new FileOutputStream(file);
        fso.write(imageData);
        fso.close();

        JsonObject response = new JsonObject();
        response.addProperty("success", true);
        response.addProperty("fileName", file.getName() + "?time=" + new Date());
        return response.toString();
    }

    private String exportImageData(final HttpServletRequest request) throws ServletException, IOException {
        EntityManager conn = null;
        try {
//            conn = BorgPersistence.getEntityManager();
            final String bookId = request.getParameter("bookId");
            final boolean isFrontCover = Boolean.valueOf(request.getParameter("isFrontCover"));
            Query query = conn.createNamedQuery(isFrontCover ? BookCover.QUERY_GET_FRONT : BookCover.QUERY_GET_BACK);
            query.setParameter("bookId", Long.valueOf(bookId));
            List imageData = query.getResultList();
            if (!imageData.isEmpty()) {
                byte[] imageContent = (byte[]) imageData.get(0);
                if (imageContent != null && imageContent.length > 0) {
                    File file = new File(RequestUtils.exportImageToDisk(bookId, isFrontCover));
                    file.deleteOnExit();
                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write((byte[]) imageData.get(0));
                    fos.close();
                    JsonObject response = new JsonObject();
                    response.addProperty("success", true);
                    response.addProperty("fileName", file.getName() + "?time=" + new Date());
                    return response.toString();
                }
            }
            return null;
        } catch (Exception e) {
            throw new ServletException(e);
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }
}
