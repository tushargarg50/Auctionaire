package com.publicis.auctionaire.pojo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Bid {

	private final BigDecimal startingBid;
	private final BigDecimal maxBid;
	private final BigDecimal autoIncrementAmount;
	private final LocalDateTime bidTime;

	public Bid(BigDecimal startingBid, BigDecimal maxBid, BigDecimal autoIncrementAmount, LocalDateTime bidTime) {
		this.startingBid = startingBid;
		this.maxBid = maxBid;
		this.autoIncrementAmount = autoIncrementAmount;
		this.bidTime = bidTime;
	}

	public BigDecimal getStartingBid() {
		return startingBid;
	}

	public BigDecimal getMaxBid() {
		return maxBid;
	}

	public BigDecimal getAutoIncrementAmount() {
		return autoIncrementAmount;
	}

	public LocalDateTime getBidTime() {
		return bidTime;
	}

	@Override
	public int hashCode() {
		return Objects.hash(autoIncrementAmount, bidTime, maxBid, startingBid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bid other = (Bid) obj;
		return Objects.equals(this.autoIncrementAmount, other.autoIncrementAmount)
				&& Objects.equals(this.bidTime, other.bidTime) 
				&& Objects.equals(this.maxBid, other.maxBid)
				&& Objects.equals(this.startingBid, other.startingBid);
	}

}
