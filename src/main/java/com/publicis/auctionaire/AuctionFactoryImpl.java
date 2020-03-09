package com.publicis.auctionaire;

import com.publicis.auctionaire.calculators.HighestBidWinnerCalculator;

public class AuctionFactoryImpl implements AuctionFactory {

	public Auction createAuction(String auctionType) {
		switch (auctionType) {
		case "highestBid":
			return new HighestBidAuction(new HighestBidWinnerCalculator());
		default:
			return null;
		}
	}

}
