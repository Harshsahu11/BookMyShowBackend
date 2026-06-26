package com.bms.BookMyShow.repository;

import com.bms.BookMyShow.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment,Long> {

    Optional<Payment> findByTransactionId(Long transactionId);

}
