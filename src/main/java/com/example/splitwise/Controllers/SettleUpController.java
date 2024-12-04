package com.example.splitwise.Controllers;

import com.example.splitwise.DTOs.*;
import com.example.splitwise.Services.SettleUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class SettleUpController {
    private final SettleUpService settleUpService;

    @Autowired
    public SettleUpController(SettleUpService settleUpService) {
        this.settleUpService = settleUpService;
    }

    public SettleUpUserResponseDTO settleUpUser(SettleUpUserRequestDTO settleUpUserRequestDTO) {
        SettleUpUserResponseDTO responseDTO = new SettleUpUserResponseDTO();
        try {
            List<Transaction> transactions = settleUpService.SettleUpUser(settleUpUserRequestDTO.getUserId());
            responseDTO.setTransactions(transactions);
            responseDTO.setStatus(ResponseStatus.SUCCESS);
        } catch (Exception e) {
            responseDTO.setStatus(ResponseStatus.FAILURE);
        }
        return responseDTO;
    }

    SettleUpGroupResponseDTO settleUpGroup(SettleUpGroupRequestDTO settleUpUserRequestDTO ){
        SettleUpGroupResponseDTO responseDTO = new SettleUpGroupResponseDTO();
        try {
            List<Transaction> transactions = settleUpService.SettleUpGroup(settleUpUserRequestDTO.getGroupId());
            responseDTO.setTransactions(transactions);
            responseDTO.setStatus(ResponseStatus.SUCCESS);
        } catch (Exception e) {
            responseDTO.setStatus(ResponseStatus.FAILURE);
        }
        return responseDTO;
    }
}
