package com.popa.books;

import com.popa.books.controller.AutorControllerTest;
import com.popa.books.controller.BookControllerTest;
import com.popa.books.controller.CategorieControllerTest;
import com.popa.books.controller.EdituraControllerTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AutorControllerTest.class,
        EdituraControllerTest.class,
        CategorieControllerTest.class,
        BookControllerTest.class})
public class TestSuite {
}
