package com.publicis.auctionaire.calculators;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.publicis.auctionaire.pojo.Bidder;
import com.publicis.auctionaire.pojo.Item;
import com.publicis.auctionaire.pojo.Winner;

public class HighestBidWinnerCalculator implements WinnerCalculator {

	@Override
	public int compare(Bidder bidder1, Bidder bidder2) {
		int bidComparison = calculateMaxPossibleBidForBidder(bidder2).compareTo(calculateMaxPossibleBidForBidder(bidder1));
		return bidComparison == 0 ? WinnerCalculator.super.compare(bidder1, bidder2) : bidComparison;
	}

	@Override
	public Winner getWinner(List<Bidder> bidders, Item item) {
		Bidder winningBidder = null;
		BigDecimal winningBidAmount = BigDecimal.ZERO;
		if (bidders.size() == 1) {
			winningBidder = bidders.stream().findFirst().get();
			winningBidAmount = winningBidder.getBid().getStartingBid();
		}
		if (winningBidder == null) {
			List<Bidder> biddersList = new CopyOnWriteArrayList<Bidder>(bidders);
			Collections.sort(biddersList, this);
			
			winningBidder = biddersList.get(0);
			Bidder secondHighestPossibleBidder = biddersList.get(1);
			
			winningBidAmount = calculateWinningBidAmount(winningBidder, secondHighestPossibleBidder);
		}
		return new Winner(winningBidder, item, winningBidAmount);
	}

	private BigDecimal calculateWinningBidAmount(Bidder winningBidder, Bidder secondHighestPossibleBidder) {
		
		BigDecimal diffInMaxPossiblebids = calculateMaxPossibleBidForBidder(winningBidder).subtract(calculateMaxPossibleBidForBidder(secondHighestPossibleBidder));
		BigDecimal factorByWhichMaxBidCanBeReduced = diffInMaxPossiblebids.divide(winningBidder.getBid().getAutoIncrementAmount(), 0, RoundingMode.DOWN);
		BigDecimal winningFactor = getMaxMultiplier(winningBidder).subtract(factorByWhichMaxBidCanBeReduced);
		
		BigDecimal winningBidAmount = winningBidder.getBid().getStartingBid().add(winningBidder.getBid().getAutoIncrementAmount().multiply(winningFactor));
		
		if(isBidAmountTied(winningBidder, secondHighestPossibleBidder, winningBidAmount)) {
			winningBidAmount = winningBidAmount.add(winningBidder.getBid().getAutoIncrementAmount());
		}
		
		return winningBidAmount;
	}

	private boolean isBidAmountTied(Bidder winningBidder, Bidder secondHighestPossibleBidder, BigDecimal winningBidAmount) {
		return winningBidAmount.compareTo(calculateMaxPossibleBidForBidder(secondHighestPossibleBidder)) == 0
				&& winningBidAmount.add(winningBidder.getBid().getAutoIncrementAmount()).compareTo(winningBidder.getBid().getMaxBid()) < 1;
	}
	
	private BigDecimal calculateMaxPossibleBidForBidder(Bidder bidder) {
		return bidder.getBid().getStartingBid().add(bidder.getBid().getAutoIncrementAmount().multiply(getMaxMultiplier(bidder)));
	}
	
	private BigDecimal getMaxMultiplier(Bidder bidder) {
		return bidder.getBid().getMaxBid().subtract(bidder.getBid().getStartingBid()).divide(bidder.getBid().getAutoIncrementAmount(), 0, RoundingMode.DOWN);
	}

}
