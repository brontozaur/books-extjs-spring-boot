package com.popa.books.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.popa.books.controller.autor.GetTreeAutoriEventHandler;
import com.popa.books.controller.book.*;
import com.popa.books.controller.editura.GetTreeEdituriEventHandler;
import com.popa.books.util.RequestUtils;

public class EventHandlerFactory {

    private EventHandlerFactory() {
    }

    public static EventHandler getHandler(final HttpServletRequest request) throws ServletException {
        final String eventName = RequestUtils.getString(request, "event");
        EventHandler handler = null;
        if (Events.SAVE_BOOK.equals(eventName)) {
            handler = new SaveBookEventHandler();
        } else if (Events.GET_TREE_AUTORI.equals(eventName)) {
            handler = new GetTreeAutoriEventHandler();
        } else if (Events.GET_TREE_BOOKS.equals(eventName)) {
            handler = new GetTreeBooksEventHandler();
        } else if (Events.GET_TREE_EDITURI.equals(eventName)) {
            handler = new GetTreeEdituriEventHandler();
        } else if (Events.IMAGE_LOADER.equals(eventName)) {
            handler = new ImageLoaderHandler();
        }
        if (handler == null) {
            throw new NullPointerException("no handler was found for event: " + eventName);
        }
        return handler;
    }
}
