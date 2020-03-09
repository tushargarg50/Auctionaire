package com.publicis.auctionaire;

import java.util.List;

import com.publicis.auctionaire.calculators.WinnerCalculator;
import com.publicis.auctionaire.pojo.Bidder;
import com.publicis.auctionaire.pojo.Item;
import com.publicis.auctionaire.pojo.Winner;

public class HighestBidAuctionImpl implements Auction {
	
	private final WinnerCalculator winnerCalculator;
	
	public HighestBidAuctionImpl(WinnerCalculator winnerCalculator) {
		this.winnerCalculator = winnerCalculator;
	}

	@Override
	public Winner getWinner(List<Bidder> bidders, Item item) {
		return winnerCalculator.getWinner(bidders, item);
	}

}
