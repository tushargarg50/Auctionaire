package com.publicis.auctionaire.calculators;

import java.util.Comparator;
import java.util.List;

import com.publicis.auctionaire.pojo.Bidder;
import com.publicis.auctionaire.pojo.Item;
import com.publicis.auctionaire.pojo.Winner;

public interface WinnerCalculator extends Comparator<Bidder> {

	@Override
	default int compare(Bidder o1, Bidder o2) {
		return o1.getBid().getBidTime().compareTo(o2.getBid().getBidTime());
	}

	Winner getWinner(List<Bidder> bidders, Item item) ;

}
