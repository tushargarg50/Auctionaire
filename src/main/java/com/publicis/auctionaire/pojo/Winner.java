package com.publicis.auctionaire.pojo;

import java.math.BigDecimal;
import java.util.Objects;

public class Winner {

	private final Bidder bidder;
	private final Item item;
	private final BigDecimal winningBidAmount;

	public Winner(Bidder bidder, Item item, BigDecimal winningBidAmount) {
		this.bidder = bidder;
		this.item = item;
		this.winningBidAmount = winningBidAmount;
	}

	public Bidder getBidder() {
		return bidder;
	}

	public Item getItem() {
		return item;
	}
	
	public BigDecimal getWinningBidAmount() {
		return winningBidAmount;
	}

	@Override
	public int hashCode() {
		return Objects.hash(bidder, item, winningBidAmount);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Winner other = (Winner) obj;
		return Objects.equals(this.bidder, other.bidder) 
				&& Objects.equals(this.item, other.item)
				&& Objects.equals(this.winningBidAmount, other.winningBidAmount);
	}

}
