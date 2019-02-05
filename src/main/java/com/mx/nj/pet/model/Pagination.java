package com.mx.nj.pet.model;

import java.util.List;

public class Pagination {

	private int total;
	private int pages;
	private int itemsPerPage;
	private List<?>item;
	
	public Pagination(){
		
	}
	
	public Pagination(int total, int pages, int itemsPerPAge, List<?> item) {
		super();
		this.total = total;
		this.pages = pages;
		this.itemsPerPage = itemsPerPage;
		this.item = item;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public int getItemsPerPage() {
		return itemsPerPage;
	}

	public void setItemsPerPage(int itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
	}

	public List<?> getItem() {
		return item;
	}

	public void setItem(List<?> item) {
		this.item = item;
	}
	
}
