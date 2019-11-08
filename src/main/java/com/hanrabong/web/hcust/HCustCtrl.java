package com.hanrabong.web.hcust;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping("/hcusts")
@Log4j
public class HCustCtrl {
	
	private static final Logger logger = LoggerFactory.getLogger(HCustCtrl.class);
	@Autowired Map<String, Object>map;
	@Autowired HCust hcust;
	@Autowired HCustMapper hCustMapper;
	
	
	@GetMapping("/{cid}/exist")
	public Map<?,?> existId(@PathVariable String cid){
		Function<String, Integer> p  =o -> hCustMapper.existId(cid); 
		map.clear();
		map.put("msg", (p.apply(cid) == 0) ? "SUCCESS": "FAIL");
		return map;
	}
	
	
	@PostMapping("/")
	public Map<?,?> join(@RequestBody HCust param) {
		Supplier<Integer> n = ()-> hCustMapper.lastCNum();
		param.setCnum(String.valueOf(n.get()+1));
		Consumer<HCust> c = t->hCustMapper.insertHCust(param);
		c.accept(param);
		map.clear();
		map.put("msg", "SUCCESS");
		return map;
	}
	
	@PostMapping("/{cid}")
	public HCust login(@PathVariable String cid ,@RequestBody HCust param) {
		Function<HCust,HCust> f = t ->  hCustMapper.selectByCidCpw(param); 
		return f.apply(param);
	}
	
	@GetMapping("/{cid}")
	public HCust searchHCustById(@PathVariable String cid ,@RequestBody HCust param) {
		Function<HCust,HCust> f = t ->  hCustMapper.selectByCidCpw(param); 
		return f.apply(param);
	}
	
	@PutMapping("/{cid}")
	public String updateHCust(@PathVariable String cid ,@RequestBody HCust param) {
		Consumer<HCust> c = t->hCustMapper.insertHCust(param);
		c.accept(param);
		return "SUCCESS";
		
	}
	
	@DeleteMapping("/{cid}")
	public String removeHCust(@PathVariable String cid ,@RequestBody HCust param) {
		Consumer<HCust> c = t->hCustMapper.insertHCust(param);
		c.accept(param);
		return "SUCCESS";
		
	}
	
	
	
	
}