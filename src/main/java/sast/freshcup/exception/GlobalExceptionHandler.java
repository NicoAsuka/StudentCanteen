package sast.freshcup.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import sast.freshcup.response.Result;
import sast.freshcup.service.RedisService;

import java.util.stream.Collectors;

/**
 * @author: 風楪fy
 * @create: 2022-01-15 17:15
 **/
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @Autowired
    RedisService redisService;

    @ExceptionHandler(LocalRunTimeException.class)
    public Result localRunTimeException(LocalRunTimeException e) {
        log.error("异常", e);
        if (e.getErrorEnum() != null) {
            redisService.set("errorEnum",e.getErrorEnum());
            return Result.failure(e.getErrorEnum());
        } else {
            redisService.set("message",e.getMessage());
            return Result.failure(e.getMessage());
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result handlerValidationException(MethodArgumentNotValidException e) {
        log.error("参数校验异常", e);
        //流处理，获取错误信息
        String messages = e.getBindingResult().getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.joining("\n"));
        redisService.set("messages",messages);
        return Result.failure(messages);
    }
}
