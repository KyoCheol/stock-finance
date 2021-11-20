package com.board.seochu.finance.util.model;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class CustomModelMapper {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper;
    }

    /**
     * @Method 설명 : entity의 데이터들을 dto로 modelmapper 라이브러리를 통해 같은 네임을 가진 객체에게 값을 매핑시킨다.
     * @param <T> Dto 타입의 제네릭 변수
     * @param <V> Entity 타입의 제네릭 변수
     * @return
     */
//    public <T, V> T toMapping(V entity, Class<T> dto) {
//        return dto.cast(modelMapper.map(entity, dto));
//    }
}
