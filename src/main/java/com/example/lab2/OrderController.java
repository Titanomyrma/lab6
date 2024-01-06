package com.example.lab2;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {
    @Autowired
    private final OrderRepository orderRepository;

    @GetMapping("/orders")
    public List<Order> index(){
        return orderRepository.findAll();
    }

    @GetMapping("/order/{id}")
    public Order getOrder(@PathVariable("id") Long id){
        return orderRepository.findById(id).orElse(null);
    }

    @PostMapping("/orders/add")
    public void addOrder(@RequestBody Order order){
        orderRepository.save(order);
    }

    @PutMapping("/order/{id}/update")
    public void updateOrder(@PathVariable("id") Long id, @RequestBody Order order){
        if(orderRepository.existsById(id)){
            Order orderToUpdate = orderRepository.findById(id).orElse(null);
            orderToUpdate = order;
            orderToUpdate.setId(id);
            orderRepository.save(orderToUpdate);
        }

    }

    @DeleteMapping("/order/{id}/delete")
    public void deleteOrder(@PathVariable("id") Long id){
        orderRepository.deleteById(id);
    }
}
