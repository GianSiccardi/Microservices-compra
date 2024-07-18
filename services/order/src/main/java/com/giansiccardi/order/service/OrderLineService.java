package com.giansiccardi.order.service;

import com.giansiccardi.order.dto.OrderLineRequest;
import com.giansiccardi.order.dto.OrderLineResponse;
import com.giansiccardi.order.repository.OrderLineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderLineService {

    private final OrderLineRepository orderLineRepository;
    private final OrderLineMapper orderLineMapper;

    public Integer saveOrderLine(OrderLineRequest orderLineRequest) {
        var order= orderLineMapper.toOrderLine(orderLineRequest);
        return orderLineRepository.save(order).getId();
    }

    public List<OrderLineResponse> findAllByOrderId(Integer id) {
return orderLineRepository.findAllByOrderId(id)
        .stream()
        .map(orderLineMapper::toOrderLineResponse)
        .collect(Collectors.toList());
    }
}
