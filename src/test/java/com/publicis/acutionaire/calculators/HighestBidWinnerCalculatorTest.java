package com.publicis.acutionaire.calculators;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.publicis.auctionaire.calculators.BidAmountsCalculator;
import com.publicis.auctionaire.calculators.HighestBidWinnerCalculator;
import com.publicis.auctionaire.pojo.Bid;
import com.publicis.auctionaire.pojo.Bidder;
import com.publicis.auctionaire.pojo.Item;
import com.publicis.auctionaire.pojo.Winner;;

@RunWith(MockitoJUnitRunner.class)
public class HighestBidWinnerCalculatorTest {
	
	@InjectMocks
	private HighestBidWinnerCalculator winnerCalculator;
	
	@Mock
	private BidAmountsCalculator bidAmountCalculator;
	
	@Before
	public void init() {
		when(bidAmountCalculator.getDifferenceInMaxPossibleBids(any(Bidder.class), any(Bidder.class))).thenCallRealMethod();
		when(bidAmountCalculator.calculateWinningBidAmount(any(Bidder.class), any(BigDecimal.class))).thenCallRealMethod();
	}
	
	@Test(expected=NullPointerException.class)
	public void testForNullPointerException() {
		winnerCalculator.getWinner(null, null);
	}
	
	@Test
	public void testSingleBidder() {
		Item item = new Item("Abc");
		Bidder bidder1 = new Bidder("Alice",
				new Bid(BigDecimal.valueOf(50), BigDecimal.valueOf(80), BigDecimal.valueOf(3), LocalDateTime.now()));
		Bidder bidder2 = new Bidder("Alice",
				new Bid(BigDecimal.valueOf(50), BigDecimal.valueOf(80), BigDecimal.valueOf(3), LocalDateTime.now()));
		when(bidAmountCalculator.getDifferenceInMaxAndStartingBid(bidder1)).thenReturn(BigDecimal.valueOf(30));
		when(bidAmountCalculator.calculateMaxPossibleBidForBidder(bidder1)).thenReturn(BigDecimal.valueOf(80));
		when(bidAmountCalculator.getMaxMultiplier(bidder1)).thenReturn(BigDecimal.valueOf(10));
		Winner winningBidder = winnerCalculator.getWinner(Arrays.asList(bidder1), item);
		assertNotNull(winningBidder);
		assertEquals("Alice", winningBidder.getBidder().getBidderName());
		assertEquals(BigDecimal.valueOf(50), winningBidder.getWinningBidAmount());
		assertEquals(item.getItemName(), winningBidder.getItem().getItemName());
		assertTrue(winningBidder.getBidder().equals(bidder1));
		assertTrue(winningBidder.getBidder().getBid().equals(bidder1.getBid()));
		assertTrue(item.equals(winningBidder.getItem()));
		assertFalse(winningBidder.getItem().equals(null));
		assertFalse(winningBidder.equals(bidder1));
		assertFalse(bidder1.equals(winningBidder.getItem()));
		assertFalse(winningBidder.getItem().equals(bidder1));
		assertFalse(bidder1.equals(null));
		assertFalse(bidder1.getBid().equals(null));
		assertTrue(bidder1.equals(bidder2));
		assertTrue(bidder1.hashCode() == bidder1.hashCode());
		
	}
	
	@Test
	public void testWinnerBasedOnTime() {
		Item item = new Item("Abc");
		List<Bidder> list = biddersListWithWait();
		when(bidAmountCalculator.getDifferenceInMaxAndStartingBid(list.get(0))).thenReturn(BigDecimal.valueOf(30));
		when(bidAmountCalculator.getDifferenceInMaxAndStartingBid(list.get(1))).thenReturn(BigDecimal.valueOf(30));
		when(bidAmountCalculator.getDifferenceInMaxAndStartingBid(list.get(2))).thenReturn(BigDecimal.valueOf(30));
		when(bidAmountCalculator.calculateMaxPossibleBidForBidder(list.get(0))).thenReturn(BigDecimal.valueOf(80));
		when(bidAmountCalculator.calculateMaxPossibleBidForBidder(list.get(1))).thenReturn(BigDecimal.valueOf(85));
		when(bidAmountCalculator.calculateMaxPossibleBidForBidder(list.get(2))).thenReturn(BigDecimal.valueOf(85));
		when(bidAmountCalculator.getMaxMultiplier(list.get(0))).thenReturn(BigDecimal.valueOf(10));
		when(bidAmountCalculator.getMaxMultiplier(list.get(1))).thenReturn(BigDecimal.valueOf(6));
		when(bidAmountCalculator.getMaxMultiplier(list.get(2))).thenReturn(BigDecimal.valueOf(6));
		
		
		Winner winningBidder = winnerCalculator.getWinner(list, item);
		assertNotNull(winningBidder);
		assertEquals("Aaron", winningBidder.getBidder().getBidderName());
		assertEquals(BigDecimal.valueOf(85), winningBidder.getWinningBidAmount());
	}
	
	@Test
	public void testGetWinner1() {
		Item item = new Item("ABC");
		List<Bidder> list = biddersList();
		when(bidAmountCalculator.getDifferenceInMaxAndStartingBid(list.get(0))).thenReturn(BigDecimal.valueOf(30));
		when(bidAmountCalculator.getDifferenceInMaxAndStartingBid(list.get(1))).thenReturn(BigDecimal.valueOf(22));
		when(bidAmountCalculator.getDifferenceInMaxAndStartingBid(list.get(2))).thenReturn(BigDecimal.valueOf(30));
		when(bidAmountCalculator.calculateMaxPossibleBidForBidder(list.get(0))).thenReturn(BigDecimal.valueOf(80));
		when(bidAmountCalculator.calculateMaxPossibleBidForBidder(list.get(1))).thenReturn(BigDecimal.valueOf(82));
		when(bidAmountCalculator.calculateMaxPossibleBidForBidder(list.get(2))).thenReturn(BigDecimal.valueOf(85));
		when(bidAmountCalculator.getMaxMultiplier(list.get(0))).thenReturn(BigDecimal.valueOf(10));
		when(bidAmountCalculator.getMaxMultiplier(list.get(1))).thenReturn(BigDecimal.valueOf(11));
		when(bidAmountCalculator.getMaxMultiplier(list.get(2))).thenReturn(BigDecimal.valueOf(6));
		
		
		Winner winningBidder = winnerCalculator.getWinner(list, item);
		assertNotNull(winningBidder);
		assertEquals("Amanda", winningBidder.getBidder().getBidderName());
		assertEquals(BigDecimal.valueOf(85), winningBidder.getWinningBidAmount());
	}
	
	@Test
	public void testGetWinnerSecondInList() {
		Item item = new Item("ABC");
		List<Bidder> list = biddersListSeconWinner();
		when(bidAmountCalculator.getDifferenceInMaxAndStartingBid(list.get(0))).thenReturn(BigDecimal.valueOf(30));
		when(bidAmountCalculator.getDifferenceInMaxAndStartingBid(list.get(1))).thenReturn(BigDecimal.valueOf(20));
		when(bidAmountCalculator.getDifferenceInMaxAndStartingBid(list.get(2))).thenReturn(BigDecimal.valueOf(30));
		when(bidAmountCalculator.calculateMaxPossibleBidForBidder(list.get(0))).thenReturn(BigDecimal.valueOf(80));
		when(bidAmountCalculator.calculateMaxPossibleBidForBidder(list.get(1))).thenReturn(BigDecimal.valueOf(80));
		when(bidAmountCalculator.calculateMaxPossibleBidForBidder(list.get(2))).thenReturn(BigDecimal.valueOf(80));
		when(bidAmountCalculator.getMaxMultiplier(list.get(0))).thenReturn(BigDecimal.valueOf(10));
		when(bidAmountCalculator.getMaxMultiplier(list.get(1))).thenReturn(BigDecimal.valueOf(10));
		when(bidAmountCalculator.getMaxMultiplier(list.get(2))).thenReturn(BigDecimal.valueOf(6));
		
		
		Winner winningBidder = winnerCalculator.getWinner(list, item);
		assertNotNull(winningBidder);
		assertEquals("Alice", winningBidder.getBidder().getBidderName());
		assertEquals(BigDecimal.valueOf(80), winningBidder.getWinningBidAmount());
	}
	
	@Test
	public void testGetWinner2() {
		Item item = new Item("ABC");
		List<Bidder> list = biddersList2();
		when(bidAmountCalculator.getDifferenceInMaxAndStartingBid(list.get(0))).thenReturn(BigDecimal.valueOf(25));
		when(bidAmountCalculator.getDifferenceInMaxAndStartingBid(list.get(1))).thenReturn(BigDecimal.valueOf(126));
		when(bidAmountCalculator.getDifferenceInMaxAndStartingBid(list.get(2))).thenReturn(BigDecimal.valueOf(100));
		when(bidAmountCalculator.calculateMaxPossibleBidForBidder(list.get(0))).thenReturn(BigDecimal.valueOf(724));
		when(bidAmountCalculator.calculateMaxPossibleBidForBidder(list.get(1))).thenReturn(BigDecimal.valueOf(719));
		when(bidAmountCalculator.calculateMaxPossibleBidForBidder(list.get(2))).thenReturn(BigDecimal.valueOf(721));
		when(bidAmountCalculator.getMaxMultiplier(list.get(0))).thenReturn(BigDecimal.valueOf(12));
		when(bidAmountCalculator.getMaxMultiplier(list.get(1))).thenReturn(BigDecimal.valueOf(8));
		when(bidAmountCalculator.getMaxMultiplier(list.get(2))).thenReturn(BigDecimal.valueOf(12));
		Winner winningBidder = winnerCalculator.getWinner(list, item);
		assertNotNull(winningBidder);
		assertEquals("Alice", winningBidder.getBidder().getBidderName());
		assertEquals(BigDecimal.valueOf(722), winningBidder.getWinningBidAmount());
	}
	
	@Test
	public void testGetWinner3() {
		Item item = new Item("Abc");
		List<Bidder> list = biddersList3();
		when(bidAmountCalculator.getDifferenceInMaxAndStartingBid(list.get(0))).thenReturn(BigDecimal.valueOf(500));
		when(bidAmountCalculator.getDifferenceInMaxAndStartingBid(list.get(1))).thenReturn(BigDecimal.valueOf(300));
		when(bidAmountCalculator.getDifferenceInMaxAndStartingBid(list.get(2))).thenReturn(BigDecimal.valueOf(699));
		when(bidAmountCalculator.calculateMaxPossibleBidForBidder(list.get(0))).thenReturn(BigDecimal.valueOf(3000));
		when(bidAmountCalculator.calculateMaxPossibleBidForBidder(list.get(1))).thenReturn(BigDecimal.valueOf(3001));
		when(bidAmountCalculator.calculateMaxPossibleBidForBidder(list.get(2))).thenReturn(BigDecimal.valueOf(2995));
		when(bidAmountCalculator.getMaxMultiplier(list.get(0))).thenReturn(BigDecimal.valueOf(1));
		when(bidAmountCalculator.getMaxMultiplier(list.get(1))).thenReturn(BigDecimal.valueOf(1));
		when(bidAmountCalculator.getMaxMultiplier(list.get(2))).thenReturn(BigDecimal.valueOf(2));
		
		
		Winner winningBidder = winnerCalculator.getWinner(list, item);
		assertNotNull(winningBidder);
		assertEquals("Aaron", winningBidder.getBidder().getBidderName());
		assertEquals(BigDecimal.valueOf(3001), winningBidder.getWinningBidAmount());
	}
	
	private List<Bidder> biddersListWithWait() {
		Bidder bidder1 = new Bidder("Alice",
				new Bid(BigDecimal.valueOf(50), BigDecimal.valueOf(80), BigDecimal.valueOf(3), LocalDateTime.now()));
		Bidder bidder2 = new Bidder("Aaron",
				new Bid(BigDecimal.valueOf(55), BigDecimal.valueOf(85), BigDecimal.valueOf(5), LocalDateTime.now()));
		try{
			Thread.sleep(2000);
		}catch (InterruptedException e) {
			e.printStackTrace();
		}
		Bidder bidder3 = new Bidder("Amanda",
				new Bid(BigDecimal.valueOf(55), BigDecimal.valueOf(85), BigDecimal.valueOf(5), LocalDateTime.now()));
		List<Bidder> bidders = new ArrayList<Bidder>();
		bidders.add(bidder1);
		bidders.add(bidder2);
		bidders.add(bidder3);
		return bidders;
	}

	private List<Bidder> biddersList() {
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
	
	private List<Bidder> biddersListSeconWinner() {
		Bidder bidder1 = new Bidder("Alice",
				new Bid(BigDecimal.valueOf(50), BigDecimal.valueOf(80), BigDecimal.valueOf(3), LocalDateTime.now()));
		Bidder bidder2 = new Bidder("Aaron",
				new Bid(BigDecimal.valueOf(60), BigDecimal.valueOf(80), BigDecimal.valueOf(2), LocalDateTime.now()));
		Bidder bidder3 = new Bidder("Amanda",
				new Bid(BigDecimal.valueOf(55), BigDecimal.valueOf(80), BigDecimal.valueOf(5), LocalDateTime.now()));
		List<Bidder> bidders = new ArrayList<Bidder>();
		bidders.add(bidder1);
		bidders.add(bidder2);
		bidders.add(bidder3);
		return bidders;
	}
	
	private List<Bidder> biddersList2() {
		Bidder bidder1 = new Bidder("Alice",
				new Bid(BigDecimal.valueOf(700), BigDecimal.valueOf(725), BigDecimal.valueOf(2), LocalDateTime.now()));
		Bidder bidder2 = new Bidder("Aaron",
				new Bid(BigDecimal.valueOf(599), BigDecimal.valueOf(725), BigDecimal.valueOf(15), LocalDateTime.now()));
		Bidder bidder3 = new Bidder("Amanda",
				new Bid(BigDecimal.valueOf(625), BigDecimal.valueOf(725), BigDecimal.valueOf(8), LocalDateTime.now()));
		List<Bidder> bidders = new ArrayList<Bidder>();
		bidders.add(bidder1);
		bidders.add(bidder2);
		bidders.add(bidder3);
		return bidders;
	}

	private List<Bidder> biddersList3() {
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
