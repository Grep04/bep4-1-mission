package com.back.shared.market.event;

import com.back.shared.market.dto.OrderDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MarketOrderPaymentRequestedEvent {
    private OrderDto order;
    private long pgPaymentAmount;
}
