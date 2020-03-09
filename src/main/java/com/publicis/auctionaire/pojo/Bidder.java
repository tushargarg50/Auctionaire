package com.publicis.auctionaire.pojo;

import java.util.Objects;

public class Bidder {

	private final String bidderName;
	private final Bid bid;

	public Bidder(String bidderName, Bid bid) {
		this.bidderName = bidderName;
		this.bid = bid;
	}

	public String getBidderName() {
		return bidderName;
	}

	public Bid getBid() {
		return bid;
	}

	@Override
	public int hashCode() {
		return Objects.hash(bidderName, bid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bidder other = (Bidder) obj;
		return Objects.equals(this.bidderName, other.bidderName)
				&& Objects.equals(this.bid, other.bid);
	}

}
