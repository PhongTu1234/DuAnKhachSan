package com.poly.Service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.DAO.RoomTypesDAO;
import com.poly.Service.RoomTypesService;
import com.poly.entity.RoomTypes;

@Service
public class RoomTypesServiceImpl implements RoomTypesService {

	@Autowired
	RoomTypesDAO rtdao;

	@Override
	public List<RoomTypes> findAll() {
		return rtdao.findAll();
	}

	@Override
	public RoomTypes findById(String id) {
		return rtdao.findById(id).get();
	}

	public RoomTypes create(RoomTypes HotelTypes) {
		return rtdao.save(HotelTypes);
	}

	@Override
	public RoomTypes update(RoomTypes HotelTypes) {
		return rtdao.save(HotelTypes);
	}

	@Override
	public void delete(String id) {
		rtdao.deleteById(id);
	}

}