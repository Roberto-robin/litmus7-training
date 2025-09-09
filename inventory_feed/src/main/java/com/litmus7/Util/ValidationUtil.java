package com.litmus7.Util;

import com.litmus7.Dto.Dto;

public class ValidationUtil {
    public static boolean isValid(Dto dto) {
        if (dto == null){System.out.println("dto emptyyyyyyyy"); return false;}

       
        if (dto.getSku() == null || dto.getSku().trim().isEmpty()){ System.out.println("sku emptyyyyyyyyyy");return false;}



        if (dto.getProductName() == null || dto.getProductName().trim().isEmpty()) {System.out.println("pname emptyyyyyyyy");return false;}


        if (dto.getQuantity() < 0) {System.out.println("qty emptyyyyyyyy");return false;}

        if (dto.getPrice() < 0) {System.out.println("price emptyyyyyyyy"); return false;}

        return true;
    }
    
    
}
