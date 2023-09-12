package com.example.urssublogapi

import org.modelmapper.ModelMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ApiConfig {
    @Bean
    fun modelMapper(): ModelMapper {
        return ModelMapper()
    }
}
