package com.example.lab2;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class Lab2ApplicationTests {

	@Autowired
	MockMvc mvc;

	@MockBean
	OrderController orderController;





	@Test
	void getAllOrdersReturnAllOrders() throws Exception {

		Mockito.when(this.orderController.index()).thenReturn(getOrders());

		mvc.perform(get("/orders"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(5));
	}



	@Test
	void findOneShouldReturnValidOrder() throws Exception {
		Mockito.when(this.orderController.getOrder(3L)).thenReturn(getOrders().get(0));

		mvc.perform(get("/order/3"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(3L))
				.andExpect(jsonPath("$.price").value(350))
				.andExpect(jsonPath("$.quantity").value(4))
				.andExpect(jsonPath("$.name").value("Cake"));
	}

	@Test
	void addOrderShouldAddNewOrder() throws Exception {
		Order ord = new Order(105L, 15, 234, "Pesdsdn");
		this.orderController.addOrder(ord);
		Mockito.when(this.orderController.getOrder(105L)).thenReturn(ord);

		mvc.perform(get("/order/105"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(105L))
				.andExpect(jsonPath("$.price").value(15))
				.andExpect(jsonPath("$.quantity").value(234))
				.andExpect(jsonPath("$.name").value("Pesdsdn"));
	}

	@Test
	void updateOrderShouldChangeOrder() throws Exception {
		Order ord = new Order(104L, 15, 234432, "Pesdsdn");
		this.orderController.updateOrder(104L,ord);
		Mockito.when(this.orderController.getOrder(104L)).thenReturn(ord);

		mvc.perform(get("/order/104"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(104L))
				.andExpect(jsonPath("$.price").value(15))
				.andExpect(jsonPath("$.quantity").value(234432))
				.andExpect(jsonPath("$.name").value("Pesdsdn"));
	}

	@Test
	void deleteOrderShouldDeleteOrder() throws Exception {
		this.orderController.deleteOrder(3L);
		Mockito.when(this.orderController.getOrder(3L)).thenReturn(null);

		mvc.perform(get("/order/3"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").doesNotExist());
	}
	private List<Order> getOrders() {
		Order one = new Order(3L, 350, 4, "Cake");
		Order two = new Order(4L, 2345345, 2, "Nuke");
		Order three = new Order(102L, 23, 234, "Pen");
		Order four = new Order(103L, 23, 234, "Pen");
		Order five = new Order(104L, 23, 234, "Pesdsdn");
		return List.of(one, two, three, four, five);
	}

}
