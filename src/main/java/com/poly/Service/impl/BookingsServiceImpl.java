package com.poly.Service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.DAO.BookingsDAO;
import com.poly.Service.BookingsService;
import com.poly.entity.Bookings;

@Service
public class BookingsServiceImpl implements BookingsService {

	@Autowired
	BookingsDAO htdao;

	@Override
	public List<Bookings> findAll() {
		return htdao.findAll();
	}

	@Override
	public Bookings findById(Integer id) {
		return htdao.findById(id).get();
	}

	public Bookings create(Bookings Bookings) {
		return htdao.save(Bookings);
	}

	@Override
	public Bookings update(Bookings Bookings) {
		return htdao.save(Bookings);
	}

	@Override
	public void delete(Integer id) {
		htdao.deleteById(id);
	}

}