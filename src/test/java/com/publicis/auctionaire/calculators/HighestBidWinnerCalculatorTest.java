package com.publicis.auctionaire.calculators;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.publicis.auctionaire.calculators.HighestBidWinnerCalculator;
import com.publicis.auctionaire.calculators.WinnerCalculator;
import com.publicis.auctionaire.pojo.Bid;
import com.publicis.auctionaire.pojo.Bidder;
import com.publicis.auctionaire.pojo.Item;
import com.publicis.auctionaire.pojo.Winner;;

public class HighestBidWinnerCalculatorTest {
	
	private WinnerCalculator winnerCalculator;
	
	@Before
	public void init() {
		winnerCalculator = new HighestBidWinnerCalculator();
	}
	
	@Test(expected=NullPointerException.class)
	public void testForNullPointerException() {
		winnerCalculator.getWinner(null, null);
	}
	
	@Test
	public void testSingleBidder() throws Exception {
		Item item = new Item("Piano");
		List<Bidder> singleBidderList = singleBidderList();

		Winner actualWinner = winnerCalculator.getWinner(singleBidderList, item);
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

		Winner actualWinner = winnerCalculator.getWinner(recordPlayerBidders, item);
		assertEquals(BigDecimal.valueOf(239), actualWinner.getWinningBidAmount());
		assertEquals("Linda", actualWinner.getBidder().getBidderName());
	}

	@Test
	public void testGetWinner2() throws Exception {
		Item item = new Item("Snow Shoes");
		List<Bidder> snowShoesBidders = biddersForSnowShoes();

		Winner actualWinner = winnerCalculator.getWinner(snowShoesBidders, item);
		assertEquals(BigDecimal.valueOf(72), actualWinner.getWinningBidAmount());
		assertEquals("Eric", actualWinner.getBidder().getBidderName());
	}

	@Test
	public void testGetWinner3() throws Exception {
		Item item = new Item("Piano");
		List<Bidder> pianoBidders = biddersForPiano();

		Winner actualWinner = winnerCalculator.getWinner(pianoBidders, item);
		assertEquals(BigDecimal.valueOf(70.00), actualWinner.getWinningBidAmount());
		assertEquals("Dave", actualWinner.getBidder().getBidderName());
		assertEquals("Piano", actualWinner.getItem().getItemName());
		assertEquals(item, actualWinner.getItem());
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
