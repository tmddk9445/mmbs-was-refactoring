package com.mong.mmbs.dto.response.order;

import java.util.List;

import com.mong.mmbs.dto.OrderListResponseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderGetListResponseDto {

    List<OrderListResponseDto> orderGetListResponseDto;

}
