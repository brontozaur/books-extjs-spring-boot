package com.popa.books;

import com.popa.books.modules.AutorTests;
import com.popa.books.modules.EdituraTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AutorTests.class,
        EdituraTests.class})
public class TestSuite {
}
