package com.sapient.dao;

public class DummyProductDao implements ProductDao{

	@Override
	public long count() {
		
		return 100;
	}

}
