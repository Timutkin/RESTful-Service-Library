package ru.timutkin.restfulapplication.service.iservice;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Target;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.timutkin.restfulapplication.dto.UserDTO;
import ru.timutkin.restfulapplication.entity.BookEntity;
import ru.timutkin.restfulapplication.entity.UserEntity;
import ru.timutkin.restfulapplication.exception.BookNotFoundException;
import ru.timutkin.restfulapplication.exception.EmailAlreadyExistsException;
import ru.timutkin.restfulapplication.exception.UserNotFoundException;
import ru.timutkin.restfulapplication.mapper.UserMapper;
import ru.timutkin.restfulapplication.repository.BookRepository;
import ru.timutkin.restfulapplication.repository.UserRepository;
import ru.timutkin.restfulapplication.service.UserService;
import ru.timutkin.restfulapplication.web.constant.ResponseConstant;
import ru.timutkin.restfulapplication.web.constant.WebConstant;
import ru.timutkin.restfulapplication.web.request.UserBookRequest;
import ru.timutkin.restfulapplication.web.response.UserBookResponse;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class IUserService implements UserService {

    UserRepository userRepository;

    BookRepository bookRepository;

    UserMapper userMapper;

    @Transactional
    @Override
    public Long createUser(UserDTO userDTO) {

        if (userRepository.existsByEmail(userDTO.getEmail())){
            throw new EmailAlreadyExistsException(
                    String.format(ResponseConstant.USER_WITH_EMAIL_ALREADY_EXISTS,userDTO.getEmail()));
        }

        UserEntity userEntity = userMapper.userDtoToUserEntity(userDTO);

        userRepository.save(userEntity);

        return userEntity.getId();
    }
    @Transactional
    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)){
            throw new UserNotFoundException(String.format("User with id = %s not found",id));
        }
        userRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public UserDTO getUserById(Long id) {
        Optional<UserEntity> mayBeUser = userRepository.findById(id);
        if (mayBeUser.isEmpty()){
            throw new UserNotFoundException(String.format(ResponseConstant.USER_WITH_ID_NOT_FOUND,id));
        }
        return userMapper.userEntityToUserDto(mayBeUser.get());
    }

    @Override
    @Transactional
    public void updateUser(UserDTO updateUserDTO) {
        if (!userRepository.existsById(updateUserDTO.getId())){
            throw new UserNotFoundException(String.format(ResponseConstant.USER_WITH_ID_NOT_FOUND,updateUserDTO.getId()));
        }
        if (userRepository.existsByEmail(updateUserDTO.getEmail())){
            throw new EmailAlreadyExistsException(
                    String.format(ResponseConstant.USER_WITH_EMAIL_ALREADY_EXISTS,updateUserDTO.getEmail()));
        }
        UserEntity userEntity = userMapper.userDtoToUserEntity(updateUserDTO);
        userRepository.save(userEntity);

    }

    @Override
    @Transactional
    public UserBookResponse bindBookToUser(UserBookRequest userBookRequest) {
        Long userId = userBookRequest.getUserId();
        List<Long> booksId = userBookRequest.getBooksId();
        Optional<UserEntity> userEntity = userRepository.findById(userId);

        if (userEntity.isEmpty()){
            throw new UserNotFoundException(String.format(ResponseConstant.USER_WITH_ID_NOT_FOUND,userId));
        }

        UserEntity user = userEntity.get();

        UserBookResponse userBookResponse = new UserBookResponse();
        userBookResponse.setUserDTO(userMapper.userEntityToUserDto(user));

        booksId.stream().distinct()
                .forEach(bookId ->{
                    if (!bookRepository.existsById(bookId)){
                        throw new BookNotFoundException(String.format(ResponseConstant.BOOK_WITH_ID_D_NOT_FOUND,bookId));
                    }
                    BookEntity book = bookRepository.getBookEntityById(bookId);
                    user.addBook(book);
                    userBookResponse.addTittle(book.getTitle());
                });

        userRepository.saveAndFlush(user);

        return userBookResponse;
    }
}
