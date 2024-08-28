package com.example.demo.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FundTransfer {
	
	private int sourceAccountId;
	private int tragetAccountId;
	private long transferamount;

}
