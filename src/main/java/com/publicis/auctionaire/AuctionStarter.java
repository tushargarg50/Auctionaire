package com.publicis.auctionaire;

import java.util.List;
import java.util.concurrent.Callable;

import com.publicis.auctionaire.calculators.WinnerCalculator;
import com.publicis.auctionaire.pojo.Bidder;
import com.publicis.auctionaire.pojo.Item;
import com.publicis.auctionaire.pojo.Winner;

public class AuctionStarter implements Callable<Winner> {

	private final Item item;
	private final List<Bidder> bidders;
	private final WinnerCalculator winerCalculator;

	public AuctionStarter(Item item, List<Bidder> bidders, WinnerCalculator winnerCalculator) {
		this.item = item;
		this.bidders = bidders;
		this.winerCalculator = winnerCalculator;
	}

	@Override
	public Winner call() throws Exception {
		return winerCalculator.getWinner(bidders, item);
	}

}
