package com.jx372.aoptest;

import org.springframework.stereotype.Service;

@Service
public class ProductService {
	public ProductVo find(String keyword){
		
		System.out.println("finding...");
		
//		if( 2 + 1 == 3){  // 리턴 문까지 도달하기 위해 if문을 생성 해 준것이다. 속이는 행위.
//			throw new RuntimeException("ProductServiceException Occurs");
//		}
		
		return new ProductVo(keyword);
	}
	
}
