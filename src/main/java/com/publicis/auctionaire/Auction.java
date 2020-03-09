package com.publicis.auctionaire;

import java.util.List;

import com.publicis.auctionaire.pojo.Bidder;
import com.publicis.auctionaire.pojo.Item;
import com.publicis.auctionaire.pojo.Winner;

public interface Auction {

	public Winner getWinner(List<Bidder> bidders, Item item);
}
