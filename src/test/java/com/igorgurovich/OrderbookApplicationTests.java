package com.igorgurovich;

import com.igorgurovich.domain.OrderBook;
import com.igorgurovich.domain.OrderResponse;
import com.igorgurovich.domain.Product;
import com.igorgurovich.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderbookApplicationTests {

	@Autowired
	OrderService orderService;

	@Test
	public void contextLoads() {
	}

	@Test
	public void testGetProduct() {
		Product product = orderService.getProduct("BTC-USD");
		assertThat(product).isNotNull();

		product = orderService.getProduct("USD-BTC");
		assertThat(product).isNotNull();

		product = orderService.getProduct("XYZ");
		assertThat(product).isNull();
	}

	@Test
	public void testGetOrderBook() {
		Product product = orderService.getProduct("BTC-USD");

		OrderBook orderBook = orderService.getOrderBook(product);
		assertThat(product).isNotNull();
	}

	@Test
	public void testOrderServiceBuyBTC() {
		OrderResponse response = orderService.placeMarketOrder("buy", "BTC", "USD", "1.00");
		assertThat(response).isNotNull();
	}

	@Test
	public void testOrderServiceSellBTC() {
		OrderResponse response = orderService.placeMarketOrder("sell", "BTC", "USD", "10.00");
		assertThat(response).isNotNull();
	}

	@Test
	public void testOrderServiceBuyUSD() {
		OrderResponse response = orderService.placeMarketOrder("buy", "USD", "BTC", "1000.00");
		assertThat(response).isNotNull();
	}

}
