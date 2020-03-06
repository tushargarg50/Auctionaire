package com.publicis.acutionaire.calculators;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import com.publicis.auctionaire.calculators.BidAmountsCalculator;
import com.publicis.auctionaire.pojo.Bid;
import com.publicis.auctionaire.pojo.Bidder;

public class BidAmountCalculatorTest {
	
	private BidAmountsCalculator bidAmountsCalculator;
	
	@Before
	public void init() {
		bidAmountsCalculator = new BidAmountsCalculator();
	}
	
	@Test
	public void testCalculateMaxPossibleBidCalculator() {
		Bidder bidder = new Bidder("abc", new Bid(BigDecimal.valueOf(50), BigDecimal.valueOf(79), BigDecimal.valueOf(3), LocalDateTime.now()));
		assertEquals(BigDecimal.valueOf(77), bidAmountsCalculator.calculateMaxPossibleBidForBidder(bidder));
	}
	
	@Test(expected=NullPointerException.class)
	public void testCalculateMaxPossibleBidCalculatorWithNullBid() {
		Bidder bidder = new Bidder("abc", null);
		bidAmountsCalculator.calculateMaxPossibleBidForBidder(bidder);
	}
	
	@Test(expected=NullPointerException.class)
	public void testCalculateMaxPossibleBidCalculatorWithNullBidder() {
		bidAmountsCalculator.calculateMaxPossibleBidForBidder(null);
	}
	
	@Test
	public void testMaxMultiplier() {
		Bidder bidder = new Bidder("abc", new Bid(BigDecimal.valueOf(50), BigDecimal.valueOf(79), BigDecimal.valueOf(3), LocalDateTime.now()));
		assertEquals(BigDecimal.valueOf(9), bidAmountsCalculator.getMaxMultiplier(bidder));
	}
	
	@Test
	public void testGetDiffernceInMaxPossibleBids() {
		Bidder bidder = new Bidder("abc", new Bid(BigDecimal.valueOf(50), BigDecimal.valueOf(80), BigDecimal.valueOf(3), LocalDateTime.now()));
		Bidder bidder2 = new Bidder("def", new Bid(BigDecimal.valueOf(60), BigDecimal.valueOf(82), BigDecimal.valueOf(2), LocalDateTime.now()));
		assertEquals(BigDecimal.valueOf(2), bidAmountsCalculator.getDifferenceInMaxPossibleBids(bidder2, bidder));
	}
	

}
