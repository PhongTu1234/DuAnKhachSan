package com.poly.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poly.Service.HotelService;
import com.poly.Service.RoomTypesService;
import com.poly.Service.ServiceService;
import com.poly.entity.Hotels;
import com.poly.entity.RoomTypes;
import com.poly.entity.Services;

@Controller
public class HotelController {

	@Autowired
	HotelService hService;

	@Autowired
	RoomTypesService rtService;

	@Autowired
	ServiceService svService;

	@RequestMapping("/hotel/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id) {
		Hotels item = hService.findById(id);
		model.addAttribute("item", item);
		return "product/single-product-variable";
	}

	@RequestMapping("/hotel/all")
	public String Level(Model model) {
		List<RoomTypes> roomtype = rtService.findAll();
		List<Hotels> hoteltype = hService.findAll();
		List<Services> services = svService.findAll();
		// model.addAttribute("roomtype", roomtype);
		// int counta = roomtype.size();
		model.addAttribute("roomtype", roomtype);
		model.addAttribute("hoteltype", hoteltype);
		model.addAttribute("services", services);
		List<Hotels> item = hService.findAll();
		int count = item.size();
		model.addAttribute("counthotel", count);

		List<Hotels> HotelLevel0 = hService.findHotelByHotelLevel0();
		model.addAttribute("countHotelLevel0", HotelLevel0.size());

		List<Hotels> HotelLevel1 = hService.findHotelByHotelLevel1();
		model.addAttribute("countHotelLevel1", HotelLevel1.size());

		List<Hotels> HotelLevel2 = hService.findHotelByHotelLevel2();
		model.addAttribute("countHotelLevel2", HotelLevel2.size());

		List<Hotels> HotelLevel3 = hService.findHotelByHotelLevel3();
		model.addAttribute("countHotelLevel3", HotelLevel3.size());

		List<Hotels> HotelLevel4 = hService.findHotelByHotelLevel4();
		model.addAttribute("countHotelLevel4", HotelLevel4.size());

		List<Hotels> HotelLevel5 = hService.findHotelByHotelLevel5();
		model.addAttribute("countHotelLevel5", HotelLevel5.size());

		int start = 1;
		int last = start - 1;
		int next = start + 1;
		int SOLuongTrongTrang = 15;
		double end = count / SOLuongTrongTrang;
		double endRounded = Math.ceil(end);
		// model.addAttribute("count", endRounded);
		List<Hotels> items = hService.findPage((start - 1) * SOLuongTrongTrang);
		model.addAttribute("items", items);
		model.addAttribute("last", last);
		model.addAttribute("start", start);
		model.addAttribute("next", next);
		return "shop";
	}

	@RequestMapping("/hotel/lpage={last}")
	public String Last(Model model, @PathVariable("last") String last) {

		List<RoomTypes> roomtype = rtService.findAll();
		List<Hotels> hoteltype = hService.findAll();
		List<Services> services = svService.findAll();
		// model.addAttribute("roomtype", roomtype);
		// int counta = roomtype.size();
		model.addAttribute("roomtype", roomtype);
		model.addAttribute("hoteltype", hoteltype);
		model.addAttribute("services", services);
		List<Hotels> item = hService.findAll();
		int count = item.size();
		model.addAttribute("counthotel", count);

		List<Hotels> HotelLevel0 = hService.findHotelByHotelLevel0();
		model.addAttribute("countHotelLevel0", HotelLevel0.size());

		List<Hotels> HotelLevel1 = hService.findHotelByHotelLevel1();
		model.addAttribute("countHotelLevel1", HotelLevel1.size());

		List<Hotels> HotelLevel2 = hService.findHotelByHotelLevel2();
		model.addAttribute("countHotelLevel2", HotelLevel2.size());

		List<Hotels> HotelLevel3 = hService.findHotelByHotelLevel3();
		model.addAttribute("countHotelLevel3", HotelLevel3.size());

		List<Hotels> HotelLevel4 = hService.findHotelByHotelLevel4();
		model.addAttribute("countHotelLevel4", HotelLevel4.size());

		List<Hotels> HotelLevel5 = hService.findHotelByHotelLevel5();
		model.addAttribute("countHotelLevel5", HotelLevel5.size());

		int start = Integer.parseInt(last);
		if (start == 1) {
			List<Hotels> items = hService.findPage((start - 1) * 15);
			model.addAttribute("items", items);
			model.addAttribute("start", start);
			model.addAttribute("next", start + 1);
		} else {
			List<Hotels> items = hService.findPage((start) * 15);
			model.addAttribute("items", items);
			model.addAttribute("last", start - 1);
			model.addAttribute("start", start);
			model.addAttribute("next", start + 1);
		}
		return "shop";
	}

	@RequestMapping("/hotel/npage={next}")
	public String Next(Model model, @PathVariable("next") String next) {

		List<RoomTypes> roomtype = rtService.findAll();
		List<Hotels> hoteltype = hService.findAll();
		List<Services> services = svService.findAll();
		// model.addAttribute("roomtype", roomtype);
		// int counta = roomtype.size();
		model.addAttribute("roomtype", roomtype);
		model.addAttribute("hoteltype", hoteltype);
		model.addAttribute("services", services);
		List<Hotels> item = hService.findAll();
		int count = item.size();
		model.addAttribute("counthotel", count);

		List<Hotels> HotelLevel0 = hService.findHotelByHotelLevel0();
		model.addAttribute("countHotelLevel0", HotelLevel0.size());

		List<Hotels> HotelLevel1 = hService.findHotelByHotelLevel1();
		model.addAttribute("countHotelLevel1", HotelLevel1.size());

		List<Hotels> HotelLevel2 = hService.findHotelByHotelLevel2();
		model.addAttribute("countHotelLevel2", HotelLevel2.size());

		List<Hotels> HotelLevel3 = hService.findHotelByHotelLevel3();
		model.addAttribute("countHotelLevel3", HotelLevel3.size());

		List<Hotels> HotelLevel4 = hService.findHotelByHotelLevel4();
		model.addAttribute("countHotelLevel4", HotelLevel4.size());

		List<Hotels> HotelLevel5 = hService.findHotelByHotelLevel5();
		model.addAttribute("countHotelLevel5", HotelLevel5.size());

		int start = Integer.parseInt(next);
		double end = count / 15;
		double endRounded = Math.ceil(end);

		if (endRounded < start) {
			List<Hotels> items = hService.findPage((start - 2) * 15);
			model.addAttribute("items", items);
			model.addAttribute("last", start - 1);
			model.addAttribute("start", start);
		} else {

			List<Hotels> items = hService.findPage((start - 1) * 15);
			model.addAttribute("items", items);
			model.addAttribute("last", start - 1);
			model.addAttribute("start", start);
			model.addAttribute("next", start + 1);
		}
		return "shop";
	}

	@RequestMapping("/hotel/0to1")
	public String Level1(Model model) {
		int start = 1;
		int last = start - 1;
		int next = start + 1;
		int SOLuongTrongTrang = 15;
		List<Hotels> item = hService.findHotelByHotelLevelstarttoend(1, 2, 4);
		model.addAttribute("items", item);
		return "shop";
	}

	@RequestMapping("/hotel/1to2")

	public String Level2(Model model) {
		int start = 1;
		int last = start - 1;
		int next = start + 1;
		int SOLuongTrongTrang = 15;
		List<Hotels> item = hService.findHotelByHotelLevel1();
		model.addAttribute("items", item);
		return "shop";
	}

	@RequestMapping("/hotel/2to3")
	public String Level3(Model model) {
		int start = 1;
		int last = start - 1;
		int next = start + 1;
		int SOLuongTrongTrang = 15;
		List<Hotels> item = hService.findHotelByHotelLevel2();
		model.addAttribute("items", item);
		return "shop";
	}

	@RequestMapping("/hotel/3to4")
	public String Level4(Model model) {
		int start = 1;
		int last = start - 1;
		int next = start + 1;
		int SOLuongTrongTrang = 15;
		List<Hotels> item = hService.findHotelByHotelLevel3();
		model.addAttribute("items", item);
		return "shop";
	}

	@RequestMapping("/hotel/4to5")
	public String Level5(Model model) {
		int start = 1;
		int last = start - 1;
		int next = start + 1;
		int SOLuongTrongTrang = 15;
		List<Hotels> item = hService.findHotelByHotelLevel4();
		model.addAttribute("items", item);
		return "shop";
	}

	@RequestMapping("/hotel/5")
	public String LevelMax(Model model) {
		int start = 1;
		int last = start - 1;
		int next = start + 1;
		int SOLuongTrongTrang = 15;
		List<Hotels> item = hService.findHotelByHotelLevel5();
		model.addAttribute("items", item);
		return "shop";
	}

//	@RequestMapping("/hotel/0to1")
//	public String Level1(Model model) {
//		String startLV = "1";
//		String endLV = "2";
//		List<Hotels> item = hService.findByHotelTypeHotelLevelBetween(startLV, endLV);
//		model.addAttribute("item", item);
//		return "shop";
//	}

}
