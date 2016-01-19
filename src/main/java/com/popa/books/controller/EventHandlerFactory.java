package com.popa.books.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.popa.books.controller.autor.DeleteAutorEventHandler;
import com.popa.books.controller.autor.GetAutorsEventHandler;
import com.popa.books.controller.autor.GetTreeAutoriEventHandler;
import com.popa.books.controller.autor.SaveAutorEventHandler;
import com.popa.books.controller.book.*;
import com.popa.books.controller.categorie.DeleteCategorieEventHandler;
import com.popa.books.controller.categorie.GetCategoriiEventHandler;
import com.popa.books.controller.categorie.SaveCategorieEventHandler;
import com.popa.books.controller.editura.DeleteEdituraEventHandler;
import com.popa.books.controller.editura.GetEdituriEventHandler;
import com.popa.books.controller.editura.GetTreeEdituriEventHandler;
import com.popa.books.controller.editura.SaveEdituraEventHandler;
import com.popa.books.util.RequestUtils;

public class EventHandlerFactory {

    private EventHandlerFactory() {
    }

    public static EventHandler getHandler(final HttpServletRequest request) throws ServletException {
        final String eventName = RequestUtils.getString(request, "event");
        EventHandler handler = null;
        if (Events.SAVE_BOOK.equals(eventName)) {
            handler = new SaveBookEventHandler();
        } else if (Events.GET_BOOKS.equals(eventName)) {
            handler = new GetBooksEventHandler();
        } else if (Events.DEL_BOOK.equals(eventName)) {
            handler = new DeleteBookEventHandler();
        } else if (Events.GET_AUTORS.equals(eventName)) {
            handler = new GetAutorsEventHandler();
        } else if (Events.GET_EDITURI.equals(eventName)) {
            handler = new GetEdituriEventHandler();
        } else if (Events.DEL_EDITURA.equals(eventName)) {
            handler = new DeleteEdituraEventHandler();
        } else if (Events.SAVE_EDITURA.equals(eventName)) {
            handler = new SaveEdituraEventHandler();
        } else if (Events.DEL_AUTOR.equals(eventName)) {
            handler = new DeleteAutorEventHandler();
        } else if (Events.SAVE_AUTOR.equals(eventName)) {
            handler = new SaveAutorEventHandler();
        } else if (Events.SAVE_CATEGORIE.equals(eventName)) {
            handler = new SaveCategorieEventHandler();
        } else if (Events.DEL_CATEGORIE.equals(eventName)) {
            handler = new DeleteCategorieEventHandler();
        } else if (Events.GET_CATEGORII.equals(eventName)) {
            handler = new GetCategoriiEventHandler();
        } else if (Events.GET_TREE_AUTORI.equals(eventName)) {
            handler = new GetTreeAutoriEventHandler();
        } else if (Events.GET_TREE_BOOKS.equals(eventName)) {
            handler = new GetTreeBooksEventHandler();
        } else if (Events.GET_TREE_EDITURI.equals(eventName)) {
            handler = new GetTreeEdituriEventHandler();
        } else if (Events.IMAGE_LOADER.equals(eventName)) {
            handler = new ImageLoaderHandler();
        } else if (Events.DELETE_UPLOAD.equals(eventName)) {
            handler = new DeleteUploadEventHandler();
        }
        if (handler == null) {
            throw new NullPointerException("no handler was found for event: " + eventName);
        }
        return handler;
    }
}
