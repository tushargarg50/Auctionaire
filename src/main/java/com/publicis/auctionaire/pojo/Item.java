package com.publicis.auctionaire.pojo;

import java.util.Objects;

public class Item {

	private final String itemName;

	public Item(String itemName) {
		this.itemName = itemName;
	}

	public String getItemName() {
		return itemName;
	}

	@Override
	public int hashCode() {
		return Objects.hash(itemName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		return Objects.equals(this.itemName, other.itemName);
	}

}
