package com.maids.library_system.patron.repositories;

import com.maids.library_system.patron.entities.Patron;
import org.springframework.data.repository.CrudRepository;

public interface PatronRepository extends CrudRepository<Patron, Long>{

}