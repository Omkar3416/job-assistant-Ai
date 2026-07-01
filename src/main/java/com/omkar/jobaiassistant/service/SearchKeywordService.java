package com.omkar.jobaiassistant.service;

import com.omkar.jobaiassistant.entity.User;

import java.util.List;

public interface SearchKeywordService {

    List<String> buildSearchKeywords(
            User user
    );

}