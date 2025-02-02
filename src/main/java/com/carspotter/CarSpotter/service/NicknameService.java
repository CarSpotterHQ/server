package com.carspotter.CarSpotter.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class NicknameService {
    private static final List<String> adjectives = Arrays.asList(
            "멋진", "귀여운", "용감한", "지혜로운", "신속한", "강력한", "유쾌한", "장난꾸러기", "신비로운", "깜찍한",
            "활발한", "믿음직한", "자유로운", "사랑스러운", "냉철한", "상냥한", "씩씩한", "섬세한", "화려한", "단단한",
            "호기심 많은", "정직한", "달콤한", "겁 없는", "대담한", "반짝이는", "총명한", "다정한", "순수한", "터프한"
    );

    private static final List<String> animals = Arrays.asList(
            "호랑이", "사자", "여우", "늑대", "코끼리", "곰", "독수리", "다람쥐", "부엉이", "올빼미",
            "팬더", "카멜레온", "캥거루", "고양이", "개", "고래", "악어", "독사", "펭귄", "두루미",
            "사슴", "원숭이", "하이에나", "돌고래", "치타", "수달", "너구리", "오소리", "말", "코알라"
    );

    public String getRandomNickname() {
        Random random = new Random();
        String randomAdjective = adjectives.get(random.nextInt(adjectives.size()));
        String randomAnimal = animals.get(random.nextInt(animals.size()));

        String generatedName = randomAdjective + " " + randomAnimal;
        log.debug("generate name : {}", generatedName);
        return generatedName;
    }
}
