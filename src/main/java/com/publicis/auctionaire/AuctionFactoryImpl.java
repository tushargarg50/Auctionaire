package com.publicis.auctionaire;

import com.publicis.auctionaire.calculators.HighestBidWinnerCalculatorImpl;

public class AuctionFactoryImpl implements AuctionFactory {

	public Auction createAuction(String auctionType) {
		switch (auctionType) {
		case "highestBid":
			return new HighestBidAuctionImpl(new HighestBidWinnerCalculatorImpl());
		default:
			return null;
		}
	}

}
