package com.fw.fo.main.service;

import com.fw.core.dto.fo.FoGolfPriceDTO;
import com.fw.core.dto.fo.FoReservationDTO;
import com.fw.core.mapper.db1.fo.FoMainMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class FoMainService {
    private final FoMainMapper foMainMapper;
}
