package com.publicis.auctionaire.calculators;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

import com.publicis.auctionaire.pojo.Bidder;

public class BidAmountsCalculator {

	public BigDecimal calculateMaxPossibleBidForBidder(Bidder bidder) {
		Objects.requireNonNull(bidder);
		Objects.requireNonNull(bidder.getBid());
		return bidder.getBid().getStartingBid().add(bidder.getBid().getAutoIncrementAmount().multiply(getMaxMultiplier(bidder)));
	}

	public BigDecimal getMaxMultiplier(Bidder bidder) {
		Objects.requireNonNull(bidder);
		Objects.requireNonNull(bidder.getBid());
		return getDifferenceInMaxAndStartingBid(bidder).divide(bidder.getBid().getAutoIncrementAmount(), 0, RoundingMode.DOWN);
	}

	public BigDecimal getDifferenceInMaxAndStartingBid(Bidder bidder) {
		Objects.requireNonNull(bidder);
		Objects.requireNonNull(bidder.getBid());
		return bidder.getBid().getMaxBid().subtract(bidder.getBid().getStartingBid());
	}

	public BigDecimal getDifferenceInMaxPossibleBids(Bidder bidderWithHigherBid, Bidder bidderWithLowerBid) {
		Objects.requireNonNull(bidderWithHigherBid);
		Objects.requireNonNull(bidderWithLowerBid);
		return calculateMaxPossibleBidForBidder(bidderWithHigherBid).subtract(calculateMaxPossibleBidForBidder(bidderWithLowerBid));
	}
	
	public BigDecimal calculateWinningBidAmount(Bidder bidder, BigDecimal winningFactor) {
		Objects.requireNonNull(bidder);
		Objects.requireNonNull(winningFactor);
		return bidder.getBid().getStartingBid().add(bidder.getBid().getAutoIncrementAmount().multiply(winningFactor));
	}

}
