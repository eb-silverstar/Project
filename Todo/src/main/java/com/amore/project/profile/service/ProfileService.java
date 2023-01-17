package com.amore.project.profile.service;

import com.amore.project.profile.vo.ProfileVO;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProfileService {

    private List<ProfileVO> profileRepository;

    @PostConstruct
    public void init() {
        profileRepository = new ArrayList<>();

        for(int i = 1; i < 6; i++) {
            ProfileVO profile = new ProfileVO(i);
            profileRepository.add(profile);
        }
    }

    /**
     * 프로필 목록 조회
     *
     * @return profileRepository
     */
    public List<ProfileVO> getProfileList() {
        return profileRepository;
    }

    /**
     * 특정 사용자의 프로필 조회
     *
     * @param userId
     * @return profile
     */
    public ProfileVO getProfile(int userId) {
        ProfileVO profile = null;

        for(ProfileVO vo : profileRepository) {
            if(vo.getUserId() == userId) {
                profile = vo;
                break;
            }
        }

        return profile;
    }

}
