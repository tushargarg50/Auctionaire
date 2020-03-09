package com.publicis.auctionaire;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

public class AuctionFactoryImplTest {
	
	private AuctionFactory auctionFactory;

	
	@Before
	public void init() {
		auctionFactory = new AuctionFactoryImpl();
	}

	@Test
	public void createAuction() throws Exception {
		Auction auction = auctionFactory.createAuction("highestBid");
		assertNotNull(auction);
	}
	
	
	@Test
	public void createAuctionFail() throws Exception {
		Auction auction = auctionFactory.createAuction("ABC");
		assertNull(auction);
	}



}
