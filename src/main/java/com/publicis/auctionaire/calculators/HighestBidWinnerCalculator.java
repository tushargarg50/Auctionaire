package com.publicis.auctionaire.calculators;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

import com.publicis.auctionaire.pojo.Bidder;
import com.publicis.auctionaire.pojo.Item;
import com.publicis.auctionaire.pojo.Winner;

public class HighestBidWinnerCalculator implements WinnerCalculator {

	private final BidAmountsCalculator bidAmountsCalculator;

	public HighestBidWinnerCalculator(BidAmountsCalculator bidAmountsCalculator) {
		this.bidAmountsCalculator = bidAmountsCalculator;
	}

	@Override
	public int compare(Bidder bidder1, Bidder bidder2) {
		int bidComparison = bidAmountsCalculator.calculateMaxPossibleBidForBidder(bidder2).compareTo(bidAmountsCalculator.calculateMaxPossibleBidForBidder(bidder1));
		return bidComparison == 0 ? WinnerCalculator.super.compare(bidder1, bidder2) : bidComparison;
	}

	@Override
	public Winner getWinner(List<Bidder> bidders, Item item) {
		Objects.requireNonNull(bidders);
		Bidder winningBidder = null;
		BigDecimal winningFactor = BigDecimal.ZERO;
		if (bidders.size() == 1) {
			winningBidder = bidders.stream().findFirst().get();
		}
		if (winningBidder == null) {
			List<Bidder> biddersList = new CopyOnWriteArrayList<Bidder>(bidders);
			Collections.sort(biddersList, this);
			winningBidder = biddersList.stream().findFirst().get();
			Bidder secondHighestPossibleBidder = biddersList.stream().skip(1).findFirst().get();
			BigDecimal diffInMaxPossiblebids = bidAmountsCalculator.getDifferenceInMaxPossibleBids(winningBidder, secondHighestPossibleBidder);
			BigDecimal factorByWhichMaxBidCanBeReduced = diffInMaxPossiblebids.divide(winningBidder.getBid().getAutoIncrementAmount(), 0, RoundingMode.DOWN);
			winningFactor = bidAmountsCalculator.getMaxMultiplier(winningBidder).subtract(factorByWhichMaxBidCanBeReduced);
		}
		return new Winner(winningBidder, item, bidAmountsCalculator.calculateWinningBidAmount(winningBidder, winningFactor));
	}
}
