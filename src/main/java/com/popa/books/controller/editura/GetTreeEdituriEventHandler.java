package com.popa.books.controller.editura;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.popa.books.controller.EventHandler;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.popa.books.model.node.EdituraNode;
import com.popa.books.model.node.Node;

public class GetTreeEdituriEventHandler extends EventHandler {

    private static final Logger logger = Logger.getLogger(GetTreeEdituriEventHandler.class);

    @Override
    public String handleEvent(final HttpServletRequest request) throws ServletException {
        try {
            List<Node> nodeList = new ArrayList<Node>();
            EdituraNode nonLetterBean = null;
            final boolean isRoot = Boolean.valueOf(request.getParameter("root"));
            if (isRoot || StringUtils.isEmpty(request.getParameter("nodeId"))) {
                final boolean isFlatMode = "flat".equals(request.getParameter("displayMode"));
                if (!isFlatMode) {
                    String sql = "SELECT @firstletter as SUBSTRING(e.numeEditura,1,1), "
                            + "(SELECT COUNT(1) FROM Editura a1 WHERE SUBSTRING(a1.numeEditura,1,1) LIKE @firstletter) AS nrEdituri,"
                            + "(SELECT COUNT(1) FROM Book b WHERE b.idEditura = e.idEditura) AS booksNumber "
                            + "FROM Editura e GROUP BY @firstletter";
                    List<Object[]> lettersList = new ArrayList<>();
                    for (Object[] data : lettersList) {
                        EdituraNode bean = new EdituraNode();
                        String letter = String.valueOf(data[0]);
                        final int howManyEdituri = Integer.valueOf(String.valueOf(data[1]));
                        final int howManyBooks = Integer.valueOf(String.valueOf(data[2]));
                        bean.setHowManyEdituri(howManyEdituri);
                        bean.setHowManyBooks(howManyBooks);
                        bean.setLeaf(false);
                        bean.setLoaded(false);
                        if (StringUtils.isEmpty(letter) || !Character.isLetter(letter.charAt(0))) {
                            letter = Node.ALL;
                            if (nonLetterBean == null) {
                                nonLetterBean = new EdituraNode();
                                nonLetterBean.setLeaf(false);
                                nonLetterBean.setLoaded(false);
                                nonLetterBean.setName(letter);
                                nonLetterBean.setId(letter);
                            }
                            nonLetterBean.setHowManyEdituri(nonLetterBean.getHowManyEdituri() + howManyEdituri);
                            nonLetterBean.setHowManyBooks(nonLetterBean.getHowManyBooks() + howManyBooks);
                            continue;
                        }
                        bean.setName(letter.toUpperCase());
                        bean.setId(letter);
                        nodeList.add(bean);
                    }
                    if (nonLetterBean != null) {
                        nodeList.add(nonLetterBean);
                    }
                } else {
                    String sql = "SELECT e.numeEditura, (SELECT COUNT(1) FROM Book b WHERE b.idEditura = e.idEditura) AS bookCount FROM Editura e";
                    List<Object[]> lettersList = new ArrayList<>();
                    for (Object[] data : lettersList) {
                        EdituraNode bean = new EdituraNode();
                        String numeAutor = String.valueOf(data[0]);
                        final int howManyBooks = Integer.valueOf(String.valueOf(data[1]));
                        bean.setLeaf(true);
                        bean.setLoaded(true);
                        bean.setHowManyBooks(howManyBooks);
                        bean.setName(numeAutor);
                        bean.setId(numeAutor);
                        nodeList.add(bean);
                    }
                }
            } else {
                final String nodeId = request.getParameter("nodeId");
                String where = "a.numeEditura LIKE '" + nodeId.toLowerCase() + "%' OR a.numeEditura LIKE '" + nodeId.toUpperCase() + "%'";
                if (Node.ALL.equals(nodeId)) {
                    where = "a.numeEditura NOT LIKE 'A%' AND a.numeEditura NOT LIKE 'B%' AND a.numeEditura NOT LIKE 'C%' AND a.numeEditura NOT LIKE 'D%' AND a.numeEditura NOT LIKE 'E%' AND a.numeEditura NOT LIKE 'F%' AND "
                            + "a.numeEditura NOT LIKE 'G%' AND a.numeEditura NOT LIKE 'H%' AND a.numeEditura NOT LIKE 'I%' AND a.numeEditura NOT LIKE 'J%' AND a.numeEditura NOT LIKE 'K%' AND a.numeEditura NOT LIKE 'L%' AND "
                            + "a.numeEditura NOT LIKE 'M%' AND a.numeEditura NOT LIKE 'N%' AND a.numeEditura NOT LIKE 'O%' AND a.numeEditura NOT LIKE 'P%' AND a.numeEditura NOT LIKE 'R%' AND a.numeEditura NOT LIKE 'S%' AND "
                            + "a.numeEditura NOT LIKE 'T%' AND a.numeEditura NOT LIKE 'U%' AND a.numeEditura NOT LIKE 'W%' AND a.numeEditura NOT LIKE 'X%' AND a.numeEditura NOT LIKE 'W%' AND a.numeEditura NOT LIKE 'Y%' "
                            + "AND a.numeEditura NOT LIKE 'Z%'";
                }
                String sql = "SELECT a.numeEditura, (SELECT COUNT(1) FROM Book b WHERE b.idEditura = a.idEditura) AS bookCount FROM Editura a where " + where;
                List<Object[]> lettersList = new ArrayList<>();
                for (Object[] data : lettersList) {
                    EdituraNode bean = new EdituraNode();
                    String numeEditura = String.valueOf(data[0]);
                    final int howManyBooks = Integer.valueOf(String.valueOf(data[1]));
                    bean.setLeaf(true);
                    bean.setLoaded(true);
                    bean.setHowManyBooks(howManyBooks);
                    if (StringUtils.isEmpty(numeEditura)) {
                        numeEditura = Node.ALL;
                    }
                    bean.setName(numeEditura);
                    bean.setId(numeEditura);
                    nodeList.add(bean);
                }
            }
            System.err.println(new Gson().toJson(nodeList));
            return new Gson().toJson(nodeList);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ServletException(e);
        }
    }
}
