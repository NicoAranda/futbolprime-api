package com.futbolprime.futbolprime_api.repository;

import com.futbolprime.futbolprime_api.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PagoRepository extends JpaRepository<Pago, Long> {
    List<Pago> findAllByTransaccionRef(String transaccionRef);
}
