package com.publicis.auctionaire;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.publicis.auctionaire.calculators.WinnerCalculator;
import com.publicis.auctionaire.pojo.Bid;
import com.publicis.auctionaire.pojo.Bidder;
import com.publicis.auctionaire.pojo.Item;
import com.publicis.auctionaire.pojo.Winner;

@RunWith(MockitoJUnitRunner.class)
public class HighestBidAuctionImplTest {

	@InjectMocks
	private HighestBidAuctionImpl auction;

	@Mock
	private WinnerCalculator winnerCalculator;

	@Test
	public void testGetWinnerForBicycle() throws Exception {
		Item item = new Item("Bicycle");
		List<Bidder> bicycleBidders = biddersForBicycle();
		Winner winner = new Winner(bicycleBidders.get(2), item, BigDecimal.valueOf(85));

		when(winnerCalculator.getWinner(anyListOf(Bidder.class), any(Item.class))).thenReturn(winner);

		Winner actualWinner = auction.getWinner(biddersForBicycle(), item);
		assertEquals(BigDecimal.valueOf(85), actualWinner.getWinningBidAmount());
		assertEquals("Amanda", actualWinner.getBidder().getBidderName());

	}

	@Test
	public void testGetWinnerForScooter() throws Exception {
		Item item = new Item("Scooter");
		List<Bidder> scooterBidders = biddersForScooter();
		Winner winner = new Winner(scooterBidders.get(0), item, BigDecimal.valueOf(722));

		when(winnerCalculator.getWinner(anyListOf(Bidder.class), any(Item.class))).thenReturn(winner);

		Winner actualWinner = auction.getWinner(scooterBidders, item);
		assertEquals(BigDecimal.valueOf(722), actualWinner.getWinningBidAmount());
		assertEquals("Alice", actualWinner.getBidder().getBidderName());

	}

	@Test
	public void testGetWinnerForBoat() throws Exception {
		Item item = new Item("Boat");
		List<Bidder> boatBidders = biddersForBoat();
		Winner winner = new Winner(boatBidders.get(1), item, BigDecimal.valueOf(3001));

		when(winnerCalculator.getWinner(anyListOf(Bidder.class), any(Item.class))).thenReturn(winner);

		Winner actualWinner = auction.getWinner(biddersForBoat(), item);
		assertEquals(BigDecimal.valueOf(3001), actualWinner.getWinningBidAmount());
		assertEquals("Aaron", actualWinner.getBidder().getBidderName());
	}

	@Test
	public void testSingleBidder() throws Exception {
		Item item = new Item("Piano");
		List<Bidder> singleBidderList = singleBidderList();
		Winner winner = new Winner(singleBidderList.get(0), item, BigDecimal.valueOf(170));

		when(winnerCalculator.getWinner(anyListOf(Bidder.class), any(Item.class))).thenReturn(winner);

		Winner actualWinner = auction.getWinner(singleBidderList, item);
		assertEquals(BigDecimal.valueOf(170), actualWinner.getWinningBidAmount());
		assertEquals("Linda", actualWinner.getBidder().getBidderName());
	}

	private List<Bidder> singleBidderList() {
		Bidder bidder1 = new Bidder("Linda",
				new Bid(BigDecimal.valueOf(170), BigDecimal.valueOf(240), BigDecimal.valueOf(3), LocalDateTime.now()));
		List<Bidder> bidders = new ArrayList<Bidder>();
		bidders.add(bidder1);
		return bidders;
	}

	@Test
	public void testGetWinner1() throws Exception {
		Item item = new Item("Record Player");
		List<Bidder> recordPlayerBidders = biddersForRecordPlayer();
		Winner winner = new Winner(recordPlayerBidders.get(0), item, BigDecimal.valueOf(239));

		when(winnerCalculator.getWinner(anyListOf(Bidder.class), any(Item.class))).thenReturn(winner);

		Winner actualWinner = auction.getWinner(recordPlayerBidders, item);
		assertEquals(BigDecimal.valueOf(239), actualWinner.getWinningBidAmount());
		assertEquals("Linda", actualWinner.getBidder().getBidderName());
	}

	@Test
	public void testGetWinner2() throws Exception {
		Item item = new Item("Snow Shoes");
		List<Bidder> snowShoesBidders = biddersForSnowShoes();
		Winner winner = new Winner(snowShoesBidders.get(2), item, BigDecimal.valueOf(72));

		when(winnerCalculator.getWinner(anyListOf(Bidder.class), any(Item.class))).thenReturn(winner);

		Winner actualWinner = auction.getWinner(snowShoesBidders, item);
		assertEquals(BigDecimal.valueOf(72), actualWinner.getWinningBidAmount());
		assertEquals("Eric", actualWinner.getBidder().getBidderName());
	}

	@Test
	public void testGetWinner3() throws Exception {
		Item item = new Item("Piano");
		List<Bidder> pianoBidders = biddersForPiano();
		Winner winner = new Winner(pianoBidders.get(1), item, BigDecimal.valueOf(70.00));

		when(winnerCalculator.getWinner(anyListOf(Bidder.class), any(Item.class))).thenReturn(winner);

		Winner actualWinner = auction.getWinner(pianoBidders, item);
		assertEquals(BigDecimal.valueOf(70.00), actualWinner.getWinningBidAmount());
		assertEquals("Dave", actualWinner.getBidder().getBidderName());
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
		Bidder bidder1 = new Bidder("Alice", new Bid(BigDecimal.valueOf(2500), BigDecimal.valueOf(3000),
				BigDecimal.valueOf(500), LocalDateTime.now()));
		Bidder bidder2 = new Bidder("Aaron", new Bid(BigDecimal.valueOf(2800), BigDecimal.valueOf(3100),
				BigDecimal.valueOf(201), LocalDateTime.now()));
		Bidder bidder3 = new Bidder("Amanda", new Bid(BigDecimal.valueOf(2501), BigDecimal.valueOf(3200),
				BigDecimal.valueOf(247), LocalDateTime.now()));
		List<Bidder> bidders = new ArrayList<Bidder>();
		bidders.add(bidder1);
		bidders.add(bidder2);
		bidders.add(bidder3);
		return bidders;
	}

	private List<Bidder> biddersForRecordPlayer() {
		Bidder bidder1 = new Bidder("Linda",
				new Bid(BigDecimal.valueOf(170), BigDecimal.valueOf(240), BigDecimal.valueOf(3), LocalDateTime.now()));
		Bidder bidder2 = new Bidder("Dave",
				new Bid(BigDecimal.valueOf(160), BigDecimal.valueOf(243), BigDecimal.valueOf(7), LocalDateTime.now()));
		Bidder bidder3 = new Bidder("Eric",
				new Bid(BigDecimal.valueOf(190), BigDecimal.valueOf(240), BigDecimal.valueOf(4), LocalDateTime.now()));
		List<Bidder> bidders = new ArrayList<Bidder>();
		bidders.add(bidder1);
		bidders.add(bidder2);
		bidders.add(bidder3);
		return bidders;
	}

	private List<Bidder> biddersForSnowShoes() {
		Bidder bidder1 = new Bidder("Linda",
				new Bid(BigDecimal.valueOf(30), BigDecimal.valueOf(70), BigDecimal.valueOf(4), LocalDateTime.now()));
		Bidder bidder2 = new Bidder("Dave",
				new Bid(BigDecimal.valueOf(30), BigDecimal.valueOf(70), BigDecimal.valueOf(3), LocalDateTime.now()));
		Bidder bidder3 = new Bidder("Eric",
				new Bid(BigDecimal.valueOf(40), BigDecimal.valueOf(90), BigDecimal.valueOf(2), LocalDateTime.now()));
		List<Bidder> bidders = new ArrayList<Bidder>();
		bidders.add(bidder1);
		bidders.add(bidder2);
		bidders.add(bidder3);
		return bidders;
	}

	private List<Bidder> biddersForPiano() {
		Bidder bidder1 = new Bidder("Linda", new Bid(BigDecimal.valueOf(20.00), BigDecimal.valueOf(65.00),
				BigDecimal.valueOf(2.00), LocalDateTime.now()));
		Bidder bidder2 = new Bidder("Dave", new Bid(BigDecimal.valueOf(10.00), BigDecimal.valueOf(70.00),
				BigDecimal.valueOf(15.00), LocalDateTime.now()));
		Bidder bidder3 = new Bidder("Eric", new Bid(BigDecimal.valueOf(22.00), BigDecimal.valueOf(70.00),
				BigDecimal.valueOf(8.00), LocalDateTime.now()));
		List<Bidder> bidders = new ArrayList<Bidder>();
		bidders.add(bidder1);
		bidders.add(bidder2);
		bidders.add(bidder3);
		return bidders;
	}

}
