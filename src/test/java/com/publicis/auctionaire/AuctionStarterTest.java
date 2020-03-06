package com.publicis.auctionaire;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.publicis.auctionaire.calculators.BidAmountsCalculator;
import com.publicis.auctionaire.calculators.HighestBidWinnerCalculator;
import com.publicis.auctionaire.pojo.Bid;
import com.publicis.auctionaire.pojo.Bidder;
import com.publicis.auctionaire.pojo.Item;
import com.publicis.auctionaire.pojo.Winner;

public class AuctionStarterTest {

	private static final int NO_OF_THREADS = 1000;
	private ExecutorService service;

	@Before
	public void init() {
		service = Executors.newCachedThreadPool();
	}

	@After
	public void destroy() {
		service.shutdown();
		try {
			service.awaitTermination(2000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			service.shutdownNow();
		}
	}

	@Test
	public void testMultipleAuctionComplete() {

		List<Item> itemList = new ArrayList<Item>();
		Item item = new Item("Bicycle");
		Item item2 = new Item("Scooter");
		Item item3 = new Item("Boat");
		itemList.add(item);
		itemList.add(item2);
		itemList.add(item3);
		Map<Item, List<Bidder>> itemBasedBidder = new HashMap<Item, List<Bidder>>();
		itemBasedBidder.put(item, biddersForBicycle());
		itemBasedBidder.put(item2, biddersForScooter());
		itemBasedBidder.put(item3, biddersForBoat());

		List<Future<Winner>> futureList = new ArrayList<Future<Winner>>();

		for (Item i : itemList) {
			AuctionStarter auctionStarter = new AuctionStarter(i, itemBasedBidder.get(i), new HighestBidWinnerCalculator(new BidAmountsCalculator()));
			for (int it = 0; it < NO_OF_THREADS; it++)
				futureList.add(service.submit(auctionStarter));
		}
		try {
			for (Future<Winner> future : futureList) {
				Winner winner = future.get();
				assertNotNull(winner);
				if(winner.getItem().equals(item)) {
					assertEquals(BigDecimal.valueOf(85), winner.getWinningBidAmount());
					assertEquals("Amanda", winner.getBidder().getBidderName());
				} else if(winner.getItem().equals(item2)) {
					assertEquals(BigDecimal.valueOf(722), winner.getWinningBidAmount());
					assertEquals("Alice", winner.getBidder().getBidderName());
				}else {
					assertEquals(BigDecimal.valueOf(3001), winner.getWinningBidAmount());
					assertEquals("Aaron", winner.getBidder().getBidderName());
				}
			}
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testBicycleComplete() {
		List<Item> itemList = new ArrayList<Item>();
		Item item = new Item("Bicycle");
		itemList.add(item);
		List<Future<Winner>> futureList = new ArrayList<Future<Winner>>();
		List<Bidder> bidders = biddersForBicycle();

		Winner winningBid = null;

		for (Item it : itemList) {
			AuctionStarter auctionStarter = new AuctionStarter(it, bidders, new HighestBidWinnerCalculator(new BidAmountsCalculator()));
			for (int i = 0; i < NO_OF_THREADS; i++)
				futureList.add(service.submit(auctionStarter));
		}
		try {
			for (Future<Winner> future : futureList) {
				winningBid = future.get();
				if (winningBid != null) {
					assertEquals(BigDecimal.valueOf(85), winningBid.getWinningBidAmount());
					assertEquals("Amanda", winningBid.getBidder().getBidderName());
					assertTrue(winningBid.equals(new Winner(winningBid.getBidder(), item, BigDecimal.valueOf(85))));
				}
			}
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testScooterComplete() {
		List<Item> itemList = new ArrayList<Item>();
		Item item2 = new Item("Scooter");
		itemList.add(item2);
		List<Bidder> bidders = biddersForScooter();

		List<Future<Winner>> futureList = new ArrayList<Future<Winner>>();

		Winner winningBid = null;
		for (Item i : itemList) {
			AuctionStarter auctionStarter = new AuctionStarter(i, bidders, new HighestBidWinnerCalculator(new BidAmountsCalculator()));
			for (int it = 0; it < NO_OF_THREADS; it++)
				futureList.add(service.submit(auctionStarter));
		}
		try {
			for (Future<Winner> future : futureList) {
				winningBid = future.get();
				if (winningBid != null) {
					assertEquals(BigDecimal.valueOf(722), winningBid.getWinningBidAmount());
					assertEquals("Alice", winningBid.getBidder().getBidderName());
				}
			}
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testBoatComplete() {
		List<Item> itemList = new ArrayList<Item>();
		Item item3 = new Item("Boat");
		itemList.add(item3);

		List<Bidder> bidders = biddersForBoat();

		List<Future<Winner>> futureList = new ArrayList<Future<Winner>>();

		Winner winningBid = null;
		for (Item i : itemList) {
			AuctionStarter auctionStarter = new AuctionStarter(i, bidders, new HighestBidWinnerCalculator(new BidAmountsCalculator()));
			for (int it = 0; it < NO_OF_THREADS; it++)
				futureList.add(service.submit(auctionStarter));
		}
		try {
			for (Future<Winner> future : futureList) {
				winningBid = future.get();
				if (winningBid != null) {
					assertEquals(BigDecimal.valueOf(3001), winningBid.getWinningBidAmount());
					assertEquals("Aaron", winningBid.getBidder().getBidderName());
				}
			}
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}

	}

	private List<Bidder> biddersForBicycle() {
		Bidder bidder1 = new Bidder("Alice",
				new Bid(BigDecimal.valueOf(50), BigDecimal.valueOf(80), BigDecimal.valueOf(3), LocalDateTime.now()));
		Bidder bidder2 = new Bidder("Aaron",
				new Bid(BigDecimal.valueOf(60), BigDecimal.valueOf(82), BigDecimal.valueOf(2), LocalDateTime.now()));
		Bidder bidder3 = new Bidder("Amanda",
				new Bid(BigDecimal.valueOf(55), BigDecimal.valueOf(85), BigDecimal.valueOf(5), LocalDateTime.now()));
		List<Bidder> bidders = new ArrayList<Bidder>();
		bidders.add(bidder1);
		bidders.add(bidder2);
		bidders.add(bidder3);
		return bidders;
	}

	private List<Bidder> biddersForScooter() {
		Bidder bidder1 = new Bidder("Alice",
				new Bid(BigDecimal.valueOf(700), BigDecimal.valueOf(725), BigDecimal.valueOf(2), LocalDateTime.now()));// 722
		Bidder bidder2 = new Bidder("Aaron",
				new Bid(BigDecimal.valueOf(599), BigDecimal.valueOf(725), BigDecimal.valueOf(15), LocalDateTime.now()));// max
		// 719
		Bidder bidder3 = new Bidder("Amanda",
				new Bid(BigDecimal.valueOf(625), BigDecimal.valueOf(725), BigDecimal.valueOf(8), LocalDateTime.now()));// 721
		List<Bidder> bidders = new ArrayList<Bidder>();
		bidders.add(bidder1);
		bidders.add(bidder2);
		bidders.add(bidder3);
		return bidders;
	}

	private List<Bidder> biddersForBoat() {
		Bidder bidder1 = new Bidder("Alice", 
				new Bid(BigDecimal.valueOf(2500), BigDecimal.valueOf(3000), BigDecimal.valueOf(500), LocalDateTime.now()));
		Bidder bidder2 = new Bidder("Aaron", 
				new Bid(BigDecimal.valueOf(2800), BigDecimal.valueOf(3100), BigDecimal.valueOf(201), LocalDateTime.now()));
		Bidder bidder3 = new Bidder("Amanda", 
				new Bid(BigDecimal.valueOf(2501), BigDecimal.valueOf(3200), BigDecimal.valueOf(247), LocalDateTime.now()));
		List<Bidder> bidders = new ArrayList<Bidder>();
		bidders.add(bidder1);
		bidders.add(bidder2);
		bidders.add(bidder3);
		return bidders;
	}

}
