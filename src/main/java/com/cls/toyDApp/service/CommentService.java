package com.cls.toyDApp.service;

import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.Network;
import org.springframework.stereotype.Service;

import com.cls.toyDApp.config.FabricConfig;
import com.cls.toyDApp.fabric.FabricHelper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {
	
	private final FabricHelper fabricHelper;
	private final FabricConfig fabricConfig;
	
	public void setComment() {
		
		try {
			
			Network network = fabricHelper.gateway();
			Contract contract = network.getContract(fabricConfig.getChaincodeName());
			contract.submitTransaction("CreateComment", "1", "helloworld");
			
		} catch (Exception e) {
			
			System.out.println(e.toString());
			
		}
	}
	
	public void getComment() {
		
		try {
			
			Network network = fabricHelper.gateway();
			Contract contract = network.getContract(fabricConfig.getChaincodeName());
			byte[] result = contract.evaluateTransaction("QueryComment", "1");
			System.out.println("result : "+ new String(result));
			
		} catch (Exception e) {
			
			System.out.println(e.toString());
			
		}
	}
}
