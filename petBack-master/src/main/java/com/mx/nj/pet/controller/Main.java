package com.mx.nj.pet.controller;

public class Main {

	public static void main(String[] args) {
		int records = 12;
		int recordsPerPage = 5;
		int pageCount = (records + recordsPerPage - 1) / recordsPerPage;
		System.out.println(pageCount);
	}

}
