package com.popa.books.repository;

import com.popa.books.model.Editura;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EdituraRepository  extends CrudRepository<Editura, Long>{


}
