package br.com.grupo63.techchallenge.common.gateway.repository;

import java.util.List;
import java.util.Optional;

public interface IJpaRepository<E> {

    Optional<E> findByIdAndDeletedFalse(Long id);

    List<E> findByDeletedFalse();

}
