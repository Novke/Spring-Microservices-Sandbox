package com.sandbox.PhotoAppApiUsers.users.ui.service;

import com.sandbox.PhotoAppApiUsers.users.data.AlbumsServiceClient;
import com.sandbox.PhotoAppApiUsers.users.data.UserEntity;
import com.sandbox.PhotoAppApiUsers.users.shared.UserDto;
import com.sandbox.PhotoAppApiUsers.users.ui.model.AlbumResponseModel;
import com.sandbox.PhotoAppApiUsers.users.ui.repository.UsersRepository;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class UsersServiceImpl implements UsersService{

    @Autowired
    UsersRepository usersRepository;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    AlbumsServiceClient albumsServiceClient;
    @Value("${albums.url}")
    String albumsUrl;
    @Override
    public UserDto createUser(UserDto userDetails) {

        userDetails.setUserId(UUID.randomUUID().toString());
//        userDetails.setPassword(passwordEncoder.encode(userDetails.getPassword()));

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userEntity = modelMapper.map(userDetails, UserEntity.class);
        userEntity.setEncryptedPassword(passwordEncoder.encode(userDetails.getPassword()));

        UserEntity createdUser = usersRepository.save(userEntity);
        return modelMapper.map(createdUser, UserDto.class);
    }

    @Override
    public UserDto getUserDetailsByEmail(String email) {
        UserEntity entity = usersRepository.findByEmail(email);

        if (entity == null) throw new UsernameNotFoundException(email);

        return new ModelMapper().map(entity, UserDto.class);
    }

    @Override
    public UserDto getUserByUserId(String userId) {

        UserEntity userEntity = usersRepository.findByUserId(userId);
        if (userEntity == null) throw new UsernameNotFoundException("User not found");

        UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);

        //   REST TEMPLATE
//        String url = String.format(albumsUrl, userId);
//
//        ResponseEntity<List<AlbumResponseModel>> albumsListResponse = restTemplate.exchange(
//                url,
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<List<AlbumResponseModel>>() {}
//        );
//        List<AlbumResponseModel> albumsList = albumsListResponse.getBody();

//        FEIGN CLIENT
        try {
            List<AlbumResponseModel> albumsList = albumsServiceClient.getAlbums(userId);
            userDto.setAlbums(albumsList);
        } catch (FeignException ex){
            log.debug(ex.getMessage());
        } catch (ResponseStatusException ex){
            System.out.println("RESP ST EXC!!!");
            log.debug(ex.getMessage());
        }


        return userDto;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = usersRepository.findByEmail(username);

        if (userEntity == null) throw new UsernameNotFoundException(username);

        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), true, true, true, true, new ArrayList<>());
    }
}
