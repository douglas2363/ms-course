package com.devsuperior.hrpayroll.service;

import com.devsuperior.hrpayroll.feignclients.WorkerFeiginClient;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.devsuperior.hrpayroll.entities.Payment;
import com.devsuperior.hrpayroll.entities.Worker;


@Service
public class PaymentService {


    @Autowired
    private WorkerFeiginClient workerFeiginClient;

    public Payment getPayment(long workerId, int days) {
        try {
            Worker worker = workerFeiginClient.findById(workerId).getBody();
            if (worker == null) {
                throw new IllegalStateException("Worker not found for id: " + workerId);
            }
            return new Payment(worker.getName(), worker.getDailyIncome(), days);
        } catch (FeignException.FeignClientException e) {
            // Handle Feign client exception (e.g., 404 Not Found, 403 Forbidden, etc.)
            throw new RuntimeException("Error while retrieving worker data: " + e.getMessage(), e);
        } catch (FeignException.FeignServerException e) {
            // Handle Feign server exception (e.g., 5xx Server Error)
            throw new RuntimeException("Server error while retrieving worker data: " + e.getMessage(), e);
        } catch (FeignException e) {
            // Handle other Feign exceptions (e.g., timeouts, connectivity issues)
            throw new RuntimeException("Error while communicating with worker service: " + e.getMessage(), e);
        }
    }
}
